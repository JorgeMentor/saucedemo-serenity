package questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import userinterfaces.ConfirmationPage;

public class ConfirmationMessage implements Question<String> {
    public static ConfirmationMessage text(){
        return new ConfirmationMessage();
    }
    @Override
    public String answeredBy(Actor actor) {
        return ConfirmationPage.THANK_YOU_TITLE.resolveFor(actor).getText();
    }
}