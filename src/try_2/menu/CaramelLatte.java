package try_2.menu;

import try_2.menu.boundary.CoffeeProduct;
import try_2.products.Inventory;
import try_2.products.ProductType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CaramelLatte implements CoffeeProduct {
    private static final List<ProductType> RECIPE = Arrays.asList(
            ProductType.MILK,
            ProductType.CARAMEL,
            ProductType.ESPRESSO
    );

    private Set<ProductType> usedProducts = new HashSet<>();

    @Override
    public boolean canPrepare() {
        for(ProductType type : getRecipe()){
            if (!Inventory.getInstance().checkForProductQuantityAvailability(type)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void prepare() {
        for(ProductType type : getRecipe()){
            Inventory.getInstance().removeProductQuantity(type, 1);
        }
        System.out.println("Caramel Latte is ready!");
    }

    @Override
    public String getName() {
        return "Caramel Latte";
    }

    @Override
    public List<ProductType> getRecipe() {
        return RECIPE;
    }

    @Override
    public Set<ProductType> tryPrepare(){
        Inventory inventory = Inventory.getInstance();
        Set<ProductType> notAvailableProducts = new HashSet<>();
        for (ProductType type : getRecipe()) {
            randomSleep(1000, 3000);
            if(!inventory.checkForProductQuantityAvailability(type) && !usedProducts.contains(type)){
                notAvailableProducts.add(type);
                continue;
            }
            if(usedProducts.contains(type)) {
                continue;
            }
            inventory.removeProductQuantity(type, 1);
            this.usedProducts.add(type);
        }
        System.out.println(getName() + " used products: " + usedProducts.toString());
        return notAvailableProducts;
    }

    @Override
    public boolean isCoffeeProductReady(){
        return new HashSet<>(RECIPE).equals(usedProducts);
    }
}
