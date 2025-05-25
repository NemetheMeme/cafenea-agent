package try_2.factory;

import try_2.menu.CaramelLatte;
import try_2.menu.boundary.CoffeeProduct;
import try_2.menu.EspressoMacchiato;
import try_2.menu.VanillaLatte;

public class CoffeeProductFactory {

    public static CoffeeProduct create(String productName){
        String normalizedInput = productName.toLowerCase();
        switch (normalizedInput) {
            case "caramel latte":
                return new CaramelLatte();
            case "vanilla latte":
                return new VanillaLatte();
            case "espresso macchiato":
                return new EspressoMacchiato();
            default:
                return null;
        }
    }
}
