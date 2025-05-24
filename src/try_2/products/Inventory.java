package try_2.products;

import java.util.ArrayList;
import java.util.List;

/*
* Singleton -> a single inventory available
* */
public class Inventory {
    private static final Inventory instance = new Inventory();
    private List<Product> products;

    private Inventory() {
        products = new ArrayList<>();
        products.add(new Product(ProductType.MILK, 2));
        products.add(new Product(ProductType.CARAMEL, 2));
        products.add(new Product(ProductType.VANILLA, 2));
        products.add(new Product(ProductType.ESPRESSO, 2));
        products.add(new Product(ProductType.SUGAR, 2));
    }
    synchronized public void supply(ProductType type, int quantity) {
        System.out.println("Inventory before:" + products.toString());
        for (Product product : products) {
            if (product.getType() == type) {
                if(product.getQuantity() <= 0) {
                    product.setQuantity(product.getQuantity() + quantity);
                }
                System.out.println("Inventory after:" + products.toString());
                return;
            }
        }
        products.add(new Product( type, quantity));
    }

    synchronized public void removeProductQuantity(ProductType type, int quantity) {
        for (Product product : products) {
            if (product.getType() == type) {
                product.setQuantity(product.getQuantity() - quantity);
                return;
            }
        }
    }

    synchronized public boolean checkForProductQuantityAvailability(ProductType type) {
        for (Product product : products) {
            if (product.getType() == type) {
                if (product.getQuantity() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "products=" + products +
                '}';
    }

    public static Inventory getInstance() {
        return instance;
    }
}
