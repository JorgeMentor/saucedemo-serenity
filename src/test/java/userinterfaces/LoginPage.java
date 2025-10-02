package userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class LoginPage {
    public static final Target USERNAME_INPUT = Target.the("campo de usuario").located(By.id("user-name"));
    public static final Target PASSWORD_INPUT = Target.the("campo de contraseña").located(By.id("password"));
    public static final Target LOGIN_BUTTON   = Target.the("botón de login").located(By.id("login-button"));
}