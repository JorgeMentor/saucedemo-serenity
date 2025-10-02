package userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class ConfirmationPage {
    public static final Target THANK_YOU_TITLE = Target.the("mensaje de confirmación")
            .located(By.cssSelector("h2.complete-header"));
}