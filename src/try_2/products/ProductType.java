package try_2.products;

public enum ProductType {
    MILK("milk"),
    CARAMEL("caramel"),
    VANILLA("vanilla"),
    ESPRESSO("espresso"),
    SUGAR("sugar");


    private String name;
    ProductType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
