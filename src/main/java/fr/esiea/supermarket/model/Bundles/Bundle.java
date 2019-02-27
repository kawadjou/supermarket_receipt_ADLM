package fr.esiea.supermarket.model.Bundles;

import java.util.*;

import fr.esiea.supermarket.model.ProductQuantity;

public interface Bundle {

    List<ProductQuantity> getProductQuantitiesForThisOffer();
    boolean compareBundle(List<ProductQuantity> ProductQuantities);


}
