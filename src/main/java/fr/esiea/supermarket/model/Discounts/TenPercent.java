package fr.esiea.supermarket.model.Discounts;

import fr.esiea.supermarket.model.Discount;
import fr.esiea.supermarket.model.Product;
import fr.esiea.supermarket.model.SupermarketCatalog;

import java.util.Map;

public class TenPercent implements Offer {

        private Product productForOffer;
        private double argumentForOffer;
        private Discount discount=null;


        public TenPercent(Product product, double argument){
            this.productForOffer=product;
            this.argumentForOffer=argument;
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


            discount = new Discount(productForOffer, this.argumentForOffer + "% off", quantity * unitPrice * this.argumentForOffer / 100.0);



            productQuantities.put(productForOffer,(double)quantity);

            return productQuantities;
        }

    }

