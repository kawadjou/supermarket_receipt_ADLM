package fr.esiea.supermarket.model.Discounts;

import fr.esiea.supermarket.model.Discount;
import fr.esiea.supermarket.model.Product;
import fr.esiea.supermarket.model.SupermarketCatalog;

import java.util.Map;

public interface Offer {

    Map<Product,Double> calculateDiscount(Map<Product, Double> productQuantities, SupermarketCatalog catalog);


    Discount getSuperOffer();

    Product getProduct();

}
