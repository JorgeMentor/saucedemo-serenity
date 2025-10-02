package tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import userinterfaces.CartPage;
import userinterfaces.CheckoutPage;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class CheckoutTask implements Task {

    private final String nombre;
    private final String apellido;
    private final String codigoPostal;

    public CheckoutTask(String nombre, String apellido, String codigoPostal) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.codigoPostal = codigoPostal;
    }

    public static Performable withData(String nombre, String apellido, String codigoPostal) {
        return instrumented(CheckoutTask.class, nombre, apellido, codigoPostal);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(CartPage.CHECKOUT_BUTTON),
                Enter.theValue(nombre).into(CheckoutPage.FIRSTNAME),
                Enter.theValue(apellido).into(CheckoutPage.LASTNAME),
                Enter.theValue(codigoPostal).into(CheckoutPage.ZIP),
                Click.on(CheckoutPage.CONTINUE),
                Click.on(CheckoutPage.FINISH)
        );
    }
}
