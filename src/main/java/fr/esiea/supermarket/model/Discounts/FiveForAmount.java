package fr.esiea.supermarket.model.Discounts;

import fr.esiea.supermarket.model.Discount;
import fr.esiea.supermarket.model.Product;
import fr.esiea.supermarket.model.SupermarketCatalog;

import java.util.Map;

public class FiveForAmount implements Offer{

    private Product productForOffer;
    private double argumentForOffer;
    private Discount discount=null;


    public FiveForAmount(Product product, double argument){
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
    public Map<Product, Double> calcDiscount(Map<Product, Double> nbproducts, SupermarketCatalog catalog){
        double quantity = nbproducts.get(productForOffer);
        double unitPrice = catalog.getUnitPrice(productForOffer);
        int quantityAsInt = (int) quantity;
        int numberOfXs = quantityAsInt/5;


        if (quantityAsInt >= 5) {
            double discountTotal = unitPrice * quantity - (this.argumentForOffer * numberOfXs + quantityAsInt % 5 * unitPrice);
            discount = new Discount(productForOffer, 5 + " for " + this.argumentForOffer, discountTotal);
        }

        nbproducts.put(productForOffer,(double)quantityAsInt % 2);

        return nbproducts;
    }
}
