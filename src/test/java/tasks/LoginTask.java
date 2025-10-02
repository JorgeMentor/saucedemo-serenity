package tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import userinterfaces.LoginPage;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class LoginTask implements Task {

    private final String user;
    private final String password;

    public LoginTask(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public static Performable withCredentials(String user, String password) {
        return instrumented(LoginTask.class, user, password);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Enter.theValue(user).into(LoginPage.USERNAME_INPUT),
                Enter.theValue(password).into(LoginPage.PASSWORD_INPUT),
                Click.on(LoginPage.LOGIN_BUTTON)
        );
    }
}