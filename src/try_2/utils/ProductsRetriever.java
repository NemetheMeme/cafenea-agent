package try_2.utils;

import try_2.menu.CaramelLatte;
import try_2.menu.EspressoMacchiato;
import try_2.menu.VanillaLatte;
import try_2.menu.boundary.CoffeeProduct;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductsRetriever {
    private Set<CoffeeProduct>  products= Set.of(
            new CaramelLatte(),
            new EspressoMacchiato(),
            new VanillaLatte()
    );

    public List<String> getProductsNames(){
        return products.stream()
                .map(CoffeeProduct::getName)
                .collect(Collectors.toList());
    }
    public String getFormattedProductRecipe(CoffeeProduct product) {
        return product.getRecipe().stream()
                .map(Object::toString)
                .map(String::toLowerCase)
                .collect(Collectors.joining(", "));
    }

    public Set<CoffeeProduct> getProducts() {
        return products;
    }
    public int getTotalProductsCount() {
        return products.size();
    }
}
