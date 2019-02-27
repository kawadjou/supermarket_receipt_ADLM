package fr.esiea.supermarket.model;

public class Discount {
    private final String description;
    private final double discountAmount;
    private final Product product;

    public Discount(Product product, String description, double discountAmount) {
        this.product = product;
        this.description = description;
        this.discountAmount = discountAmount;
    }
    public Discount(String description, double discountAmount) {
      Product discoutpd = new Product("Discount");
      this.product = discoutpd;
      this.description = description;
      this.discountAmount = discountAmount;
    }

    public String getDescription() {
        return description;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public Product getProduct() {
        return product;
    }

}
