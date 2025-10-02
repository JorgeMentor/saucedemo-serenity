package stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;

import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.ensure.Ensure;

import questions.ConfirmationMessage;
import tasks.AddToCartTask;
import tasks.CheckoutTask;
import tasks.LoginTask;

import models.OrderData;
import support.DataLoader;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static net.serenitybdd.screenplay.actors.OnStage.*;

/**
 * Step definitions para el flujo de compra.
 * Soporta datos embebidos en el feature y también datos desde JSON.
 */
public class ShopStepDefinitions {

    // Estado del escenario cuando se usan datos desde JSON
    private OrderData data;

    // ----------------- Utilidad: lector tolerante de propiedades -----------------

    /** Lee una propiedad del POJO admitiendo varias variantes de nombre
     *  (p.ej. "user", "usuario", "username"). Prueba getter, método plano y campo.
     */
    private String read(OrderData src, String... candidates) {
        for (String name : candidates) {
            // 1) getter estándar: getXxx()
            String getterName = "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
            try {
                Method m = src.getClass().getMethod(getterName);
                Object v = m.invoke(src);
                if (v != null) return v.toString();
            } catch (NoSuchMethodException ignore) {
            } catch (Exception e) {
                throw new RuntimeException("Error leyendo " + getterName + "()", e);
            }

            // 2) método con el mismo nombre (p.ej. usuario())
            try {
                Method m = src.getClass().getMethod(name);
                Object v = m.invoke(src);
                if (v != null) return v.toString();
            } catch (NoSuchMethodException ignore) {
            } catch (Exception e) {
                throw new RuntimeException("Error leyendo método " + name + "()", e);
            }

            // 3) acceso por campo
            try {
                Field f = src.getClass().getDeclaredField(name);
                f.setAccessible(true);
                Object v = f.get(src);
                if (v != null) return v.toString();
            } catch (NoSuchFieldException ignore) {
            } catch (Exception e) {
                throw new RuntimeException("Error leyendo campo " + name, e);
            }
        }
        throw new IllegalStateException(
                "No se encontró ninguna propiedad " + Arrays.toString(candidates) + " en OrderData");
    }

    // ----------------- Datos desde JSON -----------------

    @Dado("cargo datos de compra de {string} en la fila {int}")
    public void cargoDatosDesdeJson(String fileName, Integer index) {
        List<OrderData> all = DataLoader.jsonOrders("data/" + fileName);
        this.data = all.get(index);
    }

    @Dado("que el usuario se autentica en SauceDemo")
    public void autenticaConDatosCargados() {
        if (data == null) {
            throw new IllegalStateException("No hay datos cargados. Ejecuta primero el paso 'cargo datos de compra...'.");
        }
        String user = read(data, "user", "usuario", "username");
        String password = read(data, "password", "clave");
        // Reutilizamos el step que crea el actor y hace login (evita NPE en theActorInTheSpotlight)
        autenticaEnSauceDemo(user, password);
    }

    @Y("visualiza el carrito y completa el formulario de compra")
    public void completaFormularioConDatosCargados() {
        if (data == null) {
            throw new IllegalStateException("No hay datos cargados. Ejecuta primero el paso 'cargo datos de compra...'.");
        }
        String nombre   = read(data, "firstName", "nombre");
        String apellido = read(data, "lastName", "apellido");
        String zip      = read(data, "zip", "codigoPostal", "postalCode");

        theActorInTheSpotlight().attemptsTo(
                CheckoutTask.withData(nombre, apellido, zip)
        );
    }

    // ----------------- Setup Screenplay -----------------

    @Before
    public void prepareStage() {
        setTheStage(new OnlineCast());
    }

    // ----------------- Steps para Scenario Outline (sin JSON) -----------------

    @Dado("que el usuario {string} se autentica en SauceDemo con la clave {string}")
    public void autenticaEnSauceDemo(String user, String password) {
        theActorCalled(user).attemptsTo(
                // Si tienes webdriver.base.url en serenity.conf, podrías usar Open.url("/")
                Open.url("https://www.saucedemo.com/"),
                LoginTask.withCredentials(user, password)
        );
    }

    @Cuando("agrega dos productos al carrito")
    public void agregaDosProductosAlCarrito() {
        theActorInTheSpotlight().attemptsTo(
                AddToCartTask.twoItems()
        );
    }

    @Y("visualiza el carrito y completa el formulario de compra con {string}, {string} y {string}")
    public void completaFormulario(String nombre, String apellido, String codigoPostal) {
        theActorInTheSpotlight().attemptsTo(
                CheckoutTask.withData(nombre, apellido, codigoPostal)
        );
    }

    @Entonces("debería ver el mensaje {string}")
    public void deberiaVerElMensaje(String mensajeEsperado) {
        theActorInTheSpotlight().attemptsTo(
                Ensure.that(ConfirmationMessage.text()).isEqualTo(mensajeEsperado)
        );
    }
}
