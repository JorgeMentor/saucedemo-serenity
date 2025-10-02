package userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class ProductsPage {
    public static final Target ADD_BACKPACK   =
            Target.the("Agregar Sauce Labs Backpack").located(By.id("add-to-cart-sauce-labs-backpack"));
    public static final Target ADD_BIKE_LIGHT =
            Target.the("Agregar Sauce Labs Bike Light").located(By.id("add-to-cart-sauce-labs-bike-light"));
    public static final Target CART_BUTTON    =
            Target.the("Carrito").located(By.id("shopping_cart_container"));
}
