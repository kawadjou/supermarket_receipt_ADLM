package fr.esiea.supermarket.model.Discounts;


import fr.esiea.supermarket.model.Discount;
import fr.esiea.supermarket.model.Product;
import fr.esiea.supermarket.model.SupermarketCatalog;

import java.util.Map;

public class ThreeForTwo implements Offer{

    private Product productForOffer;
    private Discount discount=null;


    public ThreeForTwo(Product product){
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
        int numberOfXs= quantityAsInt/3;

        if (quantityAsInt >= 3){
            double discountAmount = quantity * unitPrice - ((numberOfXs * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
            discount = new Discount(productForOffer, "3 for 2", discountAmount);
        }

        nbproducts.put(productForOffer, (double)quantityAsInt % 3);

        return nbproducts;
    }

}
