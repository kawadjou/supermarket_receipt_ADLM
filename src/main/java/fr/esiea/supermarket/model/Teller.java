package fr.esiea.supermarket.model;

import fr.esiea.supermarket.model.Discounts.Bundle;
import fr.esiea.supermarket.model.Discounts.Offer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Teller {

    private final SupermarketCatalog catalog;
    private Map<Product, Offer> offers = new HashMap<>();
    private List<Bundle> bundles= new ArrayList<>();
    private double bundleamount = 0;



    public Teller(SupermarketCatalog catalog) {
        this.catalog = catalog;
    }

    public void addSpecialOffer(Offer SuperOffer) {
        this.offers.put(SuperOffer.getProduct(),SuperOffer);
    }
    public void addBundle(Bundle bundle) {
        this.bundles.add(bundle);
    }

    public Receipt checksOutArticlesFrom(ShoppingCart theCart) {
        Receipt receipt = new Receipt();
        List<ProductQuantity> productQuantities = theCart.getItems();
        for (Bundle bd : bundles){
            int tmp = 0;
            for (ProductQuantity itembundel : bd.getItems()){
                for (ProductQuantity itemcart : productQuantities){
                    if (itembundel.getProduct().equals(itemcart.getProduct()) && itembundel.getQuantity() <= itemcart.getQuantity()){
                        tmp+=1;
                        itemcart.setQuantity(itemcart.getQuantity() - itembundel.getQuantity());
                    }
                }
            }
            if (tmp == bd.getItems().size()) {
               bundleamount = bundleamount + bd.getPrice();

            }

        }
        Discount bundlesdiscount = new Discount("BD",bundleamount);
        receipt.addDiscount(bundlesdiscount);
        for (ProductQuantity pq: productQuantities) {
            Product p = pq.getProduct();
            double quantity = pq.getQuantity();
            double unitPrice = this.catalog.getUnitPrice(p);
            double price = quantity * unitPrice;
            receipt.addProduct(p, quantity, unitPrice, price);
        }
        theCart.handleOffers(receipt, this.offers, this.catalog);

        return receipt;
    }

}
