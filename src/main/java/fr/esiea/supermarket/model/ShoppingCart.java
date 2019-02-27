package fr.esiea.supermarket.model;

import fr.esiea.supermarket.model.Discounts.Offer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {

    private final List<ProductQuantity> items = new ArrayList<>();
    Map<Product, Double> productQuantities = new HashMap<>();


    List<ProductQuantity> getItems() {
        return new ArrayList<>(items);
    }

    void addItem(Product product) {
        this.addItemQuantity(product, 1.0);
    }

    Map<Product, Double> productQuantities() {
        return productQuantities;
    }


    public void addItemQuantity(Product product, double quantity) {
        items.add(new ProductQuantity(product, quantity));
        if (productQuantities.containsKey(product)) {
            productQuantities.put(product, productQuantities.get(product) + quantity);
        } else {
            productQuantities.put(product, quantity);
        }
    }

    void handleOffers(Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog) {
        Map<Product, Double> items = productQuantities();
        for (Product p: productQuantities().keySet()) {
            if (offers.containsKey(p)) {
                Offer offer = offers.get(p);
                items = offer.calculateDiscount(items, catalog);
                Discount discount = offer.getSuperOffer();
                discount = offer.getSuperOffer();
                if (discount != null)
                    receipt.addDiscount(discount);
            }

        }
    }
}
