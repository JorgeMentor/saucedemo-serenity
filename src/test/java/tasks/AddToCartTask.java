package tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import userinterfaces.ProductsPage;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class AddToCartTask implements Task {

    public static AddToCartTask twoItems() {
        return instrumented(AddToCartTask.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(ProductsPage.ADD_BACKPACK),
                Click.on(ProductsPage.ADD_BIKE_LIGHT),
                Click.on(ProductsPage.CART_BUTTON)
        );
    }
}
