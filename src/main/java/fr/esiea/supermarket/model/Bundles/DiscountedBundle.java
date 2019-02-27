package fr.esiea.supermarket.model.Bundles;

import fr.esiea.supermarket.model.ProductQuantity;

import java.util.List;

public class DiscountedBundle implements Bundle {

    private List<ProductQuantity> productQuantitiesForThisOffer;

    public DiscountedBundle(List<ProductQuantity> productQuantities){
        this.productQuantitiesForThisOffer=productQuantities;
    }

    @Override
    public List<ProductQuantity> getProductQuantitiesForThisOffer(){
        return this.productQuantitiesForThisOffer;
    }

    @Override
    public boolean compareBundle(List<ProductQuantity> productQuantities){
        if(productQuantities.size() == this.productQuantitiesForThisOffer.size() ){
            for (ProductQuantity quantity : this.productQuantitiesForThisOffer ){
                if(productQuantities.contains(quantity))
                    productQuantities.remove(quantity);
                else
                    return false;
            }
            if(productQuantities.isEmpty())
                return true;
        }
        return false;
    }
}
