package fr.esiea.supermarket.model;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class SupermarketTest {

    @Test
    public void testSomething() {
        SupermarketCatalog catalog = new FakeCatalog();

        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);
        Assertions.assertThat(catalog.getUnitPrice(toothbrush)).as("toothbrush price").isEqualTo(0.99);

        Product apples = new Product("apples", ProductUnit.Kilo);
        catalog.addProduct(apples, 1.99);
        Assertions.assertThat(catalog.getUnitPrice(apples)).as("apple price").isEqualTo(1.99);

        ShoppingCart cart = new ShoppingCart();
        Assertions.assertThat(cart.getItems().size()).as("cart size").isEqualTo(0);

        cart.addItemQuantity(apples, 2.5);
        Assertions.assertThat(cart.getItems().size()).as("cart size").isEqualTo(1);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, toothbrush, 10.0);

        Receipt receipt = teller.checksOutArticlesFrom(cart);
        Assertions.assertThat(receipt.getTotalPrice()).as("cart price").isEqualTo(2.5*1.99);

    }

    @Test
    public void threeForTwoTest() {

        SupermarketCatalog catalog = new FakeCatalog();

        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);
        Assertions.assertThat(catalog.getUnitPrice(toothbrush)).as("toothbrush price").isEqualTo(0.99);

        ShoppingCart cart = new ShoppingCart();
        Assertions.assertThat(cart.getItems().size()).as("cart size").isEqualTo(0);
        cart.addItemQuantity(toothbrush, 3.0);
        Assertions.assertThat(cart.getItems().size()).as("cart size").isEqualTo(1);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, toothbrush,0);

        Receipt receipt = teller.checksOutArticlesFrom(cart);
        Assertions.assertThat(receipt.getTotalPrice()).as("cart price").isEqualTo(0.99 * 2);

    }

    @Test
    public void tenPercentDiscountTest() {

        SupermarketCatalog catalog = new FakeCatalog();

        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);

        Product apples = new Product("apples", ProductUnit.Kilo);
        catalog.addProduct(apples, 1.99);

        ShoppingCart cart = new ShoppingCart();

        cart.addItemQuantity(apples, 3);
        cart.addItemQuantity(toothbrush, 3);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, toothbrush, 10.0);
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, apples, 10.0);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        Assertions.assertThat(receipt.getTotalPrice()).as("cart price").isEqualTo(((3*1.99)-(3*1.99*0.1))+((3*0.99)-(3*0.99*0.1)));

    }

    @Test
    public void twoForAmountTest() {

        SupermarketCatalog catalog = new FakeCatalog();
        Product toothpaste = new Product("toothpaste", ProductUnit.Each);
        catalog.addProduct(toothpaste,2);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothpaste, 2);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.TwoForAmount, toothpaste,3);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        Assertions.assertThat(receipt.getTotalPrice()).as("cart price").isEqualTo(3);


    }

    @Test
    public void fiveForAmountTest() {

        SupermarketCatalog catalog = new FakeCatalog();
        Product toothpaste = new Product("toothpaste", ProductUnit.Each);
        catalog.addProduct(toothpaste,2);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothpaste, 6);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.FiveForAmount, toothpaste,7);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        Assertions.assertThat(receipt.getTotalPrice()).as("cart price").isEqualTo(9);


    }

    @Test
    public void getTest(){
        SupermarketCatalog catalog = new FakeCatalog();

        Product toothpaste = new Product("toothpaste", ProductUnit.Each);
        Assertions.assertThat(toothpaste.getUnit()).isEqualTo(ProductUnit.Each);
        catalog.addProduct(toothpaste,5);


        // Offer getters
        Offer offer = new Offer(SpecialOfferType.FiveForAmount,toothpaste,7);
        Assertions.assertThat(offer.getProduct()).isEqualTo(toothpaste);

        // Discount getters
        Discount discount = new Discount(toothpaste,"two e free",2);
        Assertions.assertThat(discount.getDescription()).isEqualTo("two e free");
        Assertions.assertThat(discount.getProduct()).isEqualTo(toothpaste);
        Assertions.assertThat(discount.getDiscountAmount()).isEqualTo(2);



        Teller teller = new Teller(catalog);

        Receipt receipt = new Receipt();
        receipt.addProduct(toothpaste,2,5,10);
        receipt.addDiscount(discount);

        // ReceiptItem getters
        ReceiptItem ritem = new ReceiptItem(toothpaste,2,5,10);
        Assertions.assertThat(ritem.getPrice()).isEqualTo(5);
        Assertions.assertThat(ritem.getQuantity()).isEqualTo(2);
        Assertions.assertThat(ritem.getTotalPrice()).isEqualTo(10);
        Assertions.assertThat(ritem.getProduct()).isEqualTo(toothpaste);

        //Receipt getters
        List<ReceiptItem> items = new ArrayList<ReceiptItem>();
        items.add(ritem);
        List<Discount> discounts = new ArrayList<Discount>();
        discounts.add(discount);
        Assertions.assertThat(receipt.getItems()).isEqualTo(items);
        Assertions.assertThat(receipt.getDiscounts()).isEqualTo(discounts);
        Assertions.assertThat(receipt.getTotalPrice()).isEqualTo(8);
    }
    @Test
    public void equalsTest(){
        Product toothpaste = new Product("toothpaste", ProductUnit.Each);
        Product apples = new Product("apples", ProductUnit.Each);
        Assertions.assertThat(toothpaste.equals(toothpaste)).isTrue();
        Assertions.assertThat(toothpaste.equals(apples)).isFalse();


        ReceiptItem ritem = new ReceiptItem(toothpaste,2,5,10);
        ReceiptItem ritembis = new ReceiptItem(toothpaste,4,6,13);
        Assertions.assertThat(ritem.equals(ritem)).isTrue();
        Assertions.assertThat(ritem.equals(ritembis)).isFalse();

        Assertions.assertThat(ritem.equals(toothpaste)).isFalse();
        Assertions.assertThat(toothpaste.equals(ritem)).isFalse();
    }
    @Test
    public void hashcodeTest (){
    Product toothpaste = new Product("toothpaste", ProductUnit.Each);
    ReceiptItem ritem = new ReceiptItem(toothpaste,2,5,10);
    Assertions.assertThat(ritem.hashCode()).isEqualTo(Objects.hash(toothpaste,5.0,10.0,2.0));
    }

    @Test
    public void shoppingCartTest(){
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothpaste = new Product("toothpaste", ProductUnit.Each);
        catalog.addProduct(toothpaste,5);
        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothpaste, 3);
        cart.addItemQuantity(toothpaste, 3);
        cart.addItem(toothpaste);
        Teller teller = new Teller(catalog);
        Receipt receipt = teller.checksOutArticlesFrom(cart);
        Assertions.assertThat(receipt.getTotalPrice()).as("cart price").isEqualTo(35);

    }

}
