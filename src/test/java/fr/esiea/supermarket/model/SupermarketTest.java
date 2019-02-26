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




}
