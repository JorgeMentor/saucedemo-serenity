package userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class CheckoutPage {
    public static final Target FIRSTNAME = Target.the("nombre").located(By.id("first-name"));
    public static final Target LASTNAME  = Target.the("apellido").located(By.id("last-name"));
    public static final Target ZIP       = Target.the("código postal").located(By.id("postal-code"));
    public static final Target CONTINUE  = Target.the("continuar").located(By.id("continue"));
    public static final Target FINISH    = Target.the("finalizar").located(By.id("finish"));
}