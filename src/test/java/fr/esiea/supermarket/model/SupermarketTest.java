package fr.esiea.supermarket.model;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

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



}
