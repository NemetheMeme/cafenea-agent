package try_2.menu.boundary;

import try_2.products.ProductType;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public interface CoffeeProduct {

    boolean canPrepare();

    void prepare();

    Set<ProductType> tryPrepare();

    String getName();

    boolean isCoffeeProductReady();

    List<ProductType> getRecipe();
    default void randomSleep(int minMillis, int maxMillis) {
        int delay = ThreadLocalRandom.current().nextInt(minMillis, maxMillis + 1);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread sleep interrupted");
        }
    }

}
