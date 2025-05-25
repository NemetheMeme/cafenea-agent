package try_2.tracking;

import try_2.products.ProductType;

import java.util.HashSet;
import java.util.Set;

public class SupplyRequestQueue {
    private static final SupplyRequestQueue instance = new SupplyRequestQueue();
    private static final Set<ProductType> neededProducts = new HashSet<>();
    private static Set<ProductType> pendingProducts = new HashSet<>();

    private SupplyRequestQueue() {}

    public static SupplyRequestQueue getInstance() {
        return instance;
    }

    public static synchronized void requestProduct(ProductType type) {
        if (!neededProducts.contains(type) && !pendingProducts.contains(type)) {
            neededProducts.add(type);
            pendingProducts.add(type);
        }
    }

    public static synchronized Set<ProductType> getAndClearRequestedProducts() {
        Set<ProductType> copy = new HashSet<>(neededProducts);
        System.out.println("SupplyRequestQueue copy: " + copy.toString());
        System.out.println("SupplyRequest before clear: " + neededProducts.toString());
        neededProducts.clear();
        System.out.println("SupplyRequest after clear: " + neededProducts.toString());
        return copy;
    }
    public static synchronized boolean hasRequests() {
        return !neededProducts.isEmpty();
    }

    public static synchronized void markRestockComplete(ProductType type) {
        pendingProducts.remove(type);
    }
}
