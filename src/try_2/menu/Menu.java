package try_2.menu;

import java.util.List;

public class Menu {
    private static final List<String> availableProductNames = List.of(
            "Caramel Latte",
            "Vanilla Latte",
            "Espresso Macchiato");

public boolean checkForCoffeeProductAvailability(String productName) {
    String normalizedInput = productName.toLowerCase().trim();
    return availableProductNames.stream()
            .map(String::toLowerCase)
            .anyMatch(name -> name.equals(normalizedInput));
    }

}

