package fr.esiea.supermarket.model.Discounts;

import fr.esiea.supermarket.model.Discount;
import fr.esiea.supermarket.model.Product;
import fr.esiea.supermarket.model.SupermarketCatalog;

import java.util.Map;

public class TwoForAmount implements Offer {

    private Product productForOffer;
    private double argumentForOffer;
    private Discount discount=null;


    public TwoForAmount(Product product, double argument){
        this.argumentForOffer=argument;
        this.productForOffer=product;
    }

    @Override
    public Product getProduct(){
        return this.productForOffer;
    }
    @Override
    public Discount getSuperOffer(){
        return this.discount;
    }

    @Override
    public Map<Product, Double> calculateDiscount(Map<Product, Double> productQuantities, SupermarketCatalog catalog){
        double quantity = productQuantities.get(productForOffer);
        double unitPrice = catalog.getUnitPrice(productForOffer);
        int quantityAsInt = (int) quantity;


        if (quantityAsInt >= 2) {
            double total = this.argumentForOffer * quantityAsInt / 2 + quantityAsInt % 2 * unitPrice;
            double discountN = unitPrice * quantity - total;
            discount = new Discount(productForOffer, "2 for " + this.argumentForOffer, discountN);
        }

        productQuantities.put(productForOffer,(double)quantityAsInt % 2);

        return productQuantities;
    }
}
