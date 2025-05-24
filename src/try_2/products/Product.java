package try_2.products;

public class Product {

    private ProductType type;
    private int quantity;

    public Product( ProductType type, int quantity) {
        this.type = type;
        this.quantity = quantity;
    }

    public ProductType getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return type.name() + " (qty: " + quantity + ")";
    }

}
