package fr.esiea.supermarket.model;


import fr.esiea.supermarket.ReceiptPrinter;
import fr.esiea.supermarket.model.Discounts.*;
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
        teller.addSpecialOffer(new TenPercent(toothbrush, 10.0));

        Receipt receipt = teller.checksOutArticlesFrom(cart);
        Assertions.assertThat(receipt.getTotalPrice()).as("cart price").isEqualTo(2.5*1.99);

    }

    @Test
    public void threeForTwoTest() {

        SupermarketCatalog catalog = new FakeCatalog();

        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);
        Product apples = new Product("apples", ProductUnit.Kilo);
        catalog.addProduct(apples, 1.99);


        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 3.0);
        ShoppingCart cart2 = new ShoppingCart();
        cart2.addItemQuantity(toothbrush, 1.0);
        ShoppingCart cart3 = new ShoppingCart();
        cart3.addItemQuantity(toothbrush, 3.0);
        ShoppingCart cart4 = new ShoppingCart();
        cart4.addItemQuantity(toothbrush, 3.0);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(new ThreeForTwo(toothbrush));

        Teller teller4 = new Teller(catalog);
        teller4.addSpecialOffer(new ThreeForTwo(toothbrush));

        Teller teller2 = new Teller(catalog);
        teller2.addSpecialOffer(new FiveForAmount(toothbrush,0));

        Teller teller3 = new Teller(catalog);
        teller3.addSpecialOffer(new ThreeForTwo(apples));


        Receipt receipt = teller.checksOutArticlesFrom(cart);
        Receipt receipt2 = teller4.checksOutArticlesFrom(cart2);
        Receipt receipt3 = teller2.checksOutArticlesFrom(cart3);
        Receipt receipt4 = teller3.checksOutArticlesFrom(cart4);

        Assertions.assertThat(receipt.getTotalPrice()).isEqualTo(0.99 * 2);
        Assertions.assertThat(receipt2.getTotalPrice()).isEqualTo(0.99 * 1);
        Assertions.assertThat(receipt3.getTotalPrice()).isEqualTo(0.99 * 3);
        Assertions.assertThat(receipt4.getTotalPrice()).isEqualTo(0.99 * 3);

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
        teller.addSpecialOffer(new TenPercent(toothbrush, 10.0));
        teller.addSpecialOffer(new TenPercent( apples, 10.0));

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

        ShoppingCart cart2 = new ShoppingCart();
        cart2.addItemQuantity(toothpaste, 1);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(new TwoForAmount(toothpaste,3));
        Receipt receipt = teller.checksOutArticlesFrom(cart);
        Assertions.assertThat(receipt.getTotalPrice()).as("cart price").isEqualTo(3);

        Teller teller2 = new Teller(catalog);
        teller2.addSpecialOffer(new TwoForAmount(toothpaste,3));
        Receipt receipt2 = teller2.checksOutArticlesFrom(cart2);
        Assertions.assertThat(receipt2.getTotalPrice()).as("cart price").isEqualTo(2);


    }

    @Test
    public void fiveForAmountTest() {

        SupermarketCatalog catalog = new FakeCatalog();
        Product toothpaste = new Product("toothpaste", ProductUnit.Each);
        Product apples = new Product("apples", ProductUnit.Each);
        catalog.addProduct(toothpaste,2);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothpaste, 5);
        ShoppingCart cart3 = new ShoppingCart();
        cart3.addItemQuantity(toothpaste, 5);
        ShoppingCart cart4 = new ShoppingCart();
        cart4.addItemQuantity(toothpaste, 5);
        ShoppingCart cart2 = new ShoppingCart();
        cart2.addItemQuantity(toothpaste, 3);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(new FiveForAmount(toothpaste,7));
        Teller tellerb = new Teller(catalog);
        tellerb.addSpecialOffer(new FiveForAmount(toothpaste,7));
        Teller teller2 = new Teller(catalog);
        teller2.addSpecialOffer(new ThreeForTwo(toothpaste));
        Teller teller3 = new Teller(catalog);
        teller3.addSpecialOffer(new FiveForAmount( apples,7));

        Receipt receipt = teller.checksOutArticlesFrom(cart);
        Receipt receipt2 = tellerb.checksOutArticlesFrom(cart2);
        Receipt receipt3 = teller2.checksOutArticlesFrom(cart3);
        Receipt receipt4 = teller3.checksOutArticlesFrom(cart4);

        Assertions.assertThat(receipt.getTotalPrice()).isEqualTo(7);
        Assertions.assertThat(receipt2.getTotalPrice()).isEqualTo(6);
        Assertions.assertThat(receipt3.getTotalPrice()).isEqualTo(8);
        Assertions.assertThat(receipt4.getTotalPrice()).isEqualTo(10);


    }
    @Test
    public void withoutDiscountTest() {

        SupermarketCatalog catalog = new FakeCatalog();
        Product toothpaste = new Product("toothpaste", ProductUnit.Each);
        Product apples = new Product("apples", ProductUnit.Each);
        catalog.addProduct(toothpaste,2);
        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothpaste, 6);
        Teller teller = new Teller(catalog);
        Receipt receipt = teller.checksOutArticlesFrom(cart);
        Assertions.assertThat(receipt.getTotalPrice()).isEqualTo(12);
    }


    @Test
    public void getTest(){
        SupermarketCatalog catalog = new FakeCatalog();

        Product toothpaste = new Product("toothpaste", ProductUnit.Each);
        Assertions.assertThat(toothpaste.getUnit()).isEqualTo(ProductUnit.Each);
        catalog.addProduct(toothpaste,5);


        // Offer getters
        //Offer offer = new Offer(SpecialOfferType.FiveForAmount,toothpaste,7);
        //Assertions.assertThat(offer.getProduct()).isEqualTo(toothpaste);

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
        Product toothpaste2 = new Product("toothpaste", ProductUnit.Kilo);
        Product toothpaste3 = new Product("toothpaste", ProductUnit.Each);
        Product apples = new Product("apples", ProductUnit.Each);
        ReceiptItem ritem = new ReceiptItem(toothpaste,2,5,10);
        ReceiptItem ritem2 = new ReceiptItem(toothpaste,4,6,13);
        ReceiptItem ritem3 = new ReceiptItem(toothpaste,3,6,11);
        ReceiptItem ritem4 = new ReceiptItem(toothpaste,4,6,11);
        ReceiptItem ritem5 = new ReceiptItem(apples,4,6,11);

        Assertions.assertThat(toothpaste.equals(toothpaste)).isTrue();
        Assertions.assertThat(toothpaste.equals(apples)).isFalse();

        Assertions.assertThat(toothpaste.equals(null)).isFalse();
        Assertions.assertThat(toothpaste.equals(ritem)).isFalse();

        Assertions.assertThat(toothpaste.equals(toothpaste2)).isFalse();
        Assertions.assertThat(toothpaste.equals(toothpaste3)).isTrue();

        Assertions.assertThat(ritem.equals(ritem)).isTrue();
        Assertions.assertThat(ritem.equals(ritem2)).isFalse();
        Assertions.assertThat(ritem.equals(null)).isFalse();
        Assertions.assertThat(ritem.equals(toothpaste)).isFalse();
        Assertions.assertThat(ritem2.equals(ritem3)).isFalse();
        Assertions.assertThat(ritem3.equals(ritem4)).isFalse();
        Assertions.assertThat(ritem4.equals(ritem5)).isFalse();


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
    @Test
    public void ReceiptPrinterTest(){
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothpaste = new Product("toothpaste", ProductUnit.Each);
        Product apples = new Product("apples", ProductUnit.Kilo);
        catalog.addProduct(toothpaste,5);
        catalog.addProduct(apples,2.5);
        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothpaste, 3);
        cart.addItemQuantity(apples,1);
        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(new TenPercent(toothpaste, 10.0));
        Receipt receipt = teller.checksOutArticlesFrom(cart);
        ReceiptPrinter printer = new ReceiptPrinter();
        ReceiptPrinter printer2 = new ReceiptPrinter(20);
        printer.printReceipt(receipt);
        Assertions.assertThat(printer.printReceipt(receipt)).isEqualTo("toothpaste                         15.00" + "\n" +"  5.00 * 3"+ "\n"+"apples                              2.50" +"\n"+ "10.0% off(toothpaste)              -1.50"+"\n"+ "\n"+"Total:                             16.00");

    }
    @Test
    public void BundleTest(){
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothpaste = new Product("toothpaste", ProductUnit.Each);
        Product apples = new Product("apples", ProductUnit.Each);
        catalog.addProduct(toothpaste,2);
        catalog.addProduct(apples,2);
        ShoppingCart cart = new ShoppingCart();

        cart.addItemQuantity(toothpaste, 3);
        cart.addItemQuantity(apples,2);

        Teller teller = new Teller(catalog);
        Bundle bundle = new Bundle(6);
        bundle.addProducttoBendle(toothpaste,2);
        bundle.addProducttoBendle(apples,2);
        teller.addBundle(bundle);
       // Receipt receipt = teller.checksOutArticlesFrom(cart);
      //  Assertions.assertThat(receipt.getTotalPrice()).as("cart price").isEqualTo(8);

        ShoppingCart cart2 = new ShoppingCart();

        cart2.addItemQuantity(toothpaste, 3);
        cart2.addItemQuantity(apples,2);

        Teller teller2 = new Teller(catalog);
        Bundle bundle2 = new Bundle(6);
        bundle2.addProducttoBendle(toothpaste,3);
        bundle2.addProducttoBendle(apples,3);
        teller2.addBundle(bundle2);
        Receipt receipt2 = teller2.checksOutArticlesFrom(cart);
        Assertions.assertThat(receipt2.getTotalPrice()).as("cart price").isEqualTo(10);


    }
}
