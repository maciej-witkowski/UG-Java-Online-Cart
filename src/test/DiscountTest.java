package test;

import main.Cart;
import main.Discount;
import main.Product;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class DiscountTest {

    private Discount discountRebate;
    private Discount discountFreeProduct;
    private Discount discountFreeMug;
    private Discount discountProductRebate;

    @Before
    public void setUp() throws Exception {
        discountRebate = new Discount(300, 0.05);
        discountFreeProduct = new Discount(2);
        discountFreeMug = new Discount(200d);
        discountProductRebate = new Discount("002", 0.30);
    }

    @Test
    @DisplayName("Wrong price for all products")
    public void testRebateWrongPriceReq() {
        Exception thrown = Assertions.assertThrows(
            Exception.class,
            () -> new Discount(-123, 0.05),
            "Price must be a positive number"
        );

        Assertions.assertEquals(thrown.getMessage(), "Price must be a positive number");
    }

    @Test
    @DisplayName("Wrong rebate applied to products")
    public void testRebateWrongRebate() {
        Exception thrown = Assertions.assertThrows(
            Exception.class,
            () -> new Discount(123, 1.50),
            "The rebate cannot be more than 100%"
        );

        Assertions.assertEquals(thrown.getMessage(), "The rebate cannot be more than 100%");
    }

    @Test
    @DisplayName("Wrong number of products")
    public void testFreeProductWrongProductsNumReq() {
        Exception thrown = Assertions.assertThrows(
            Exception.class,
            () -> new Discount(-1),
            "The number of products cannot be less than 0"
        );

        Assertions.assertEquals(thrown.getMessage(), "The number of products cannot be less than 0");
    }

    @Test
    @DisplayName("Wrong length of product number")
    public void testFreeMugWrongCode() {
        Exception thrown = Assertions.assertThrows(
            Exception.class,
            () -> new Discount(-50d),
            "Price must be a positive number"
        );

        Assertions.assertEquals(thrown.getMessage(), "Price must be a positive number");
    }

    @Test
    @DisplayName("Wrong length of product number")
    public void testProductRebateWrongCode() {
        Exception thrown = Assertions.assertThrows(
            Exception.class,
            () -> new Discount("0000", 0.05),
            "The length of product code must be 3!"
        );

        Assertions.assertEquals(thrown.getMessage(), "The length of product code must be 3!");
    }

    @Test
    @DisplayName("Wrong rebate applied to products")
    public void testProductRebateWrongRebate() {
        Exception thrown = Assertions.assertThrows(
            Exception.class,
            () -> new Discount("123", 0.00),
            "The rebate cannot be more than 100%"
        );

        Assertions.assertEquals(thrown.getMessage(), "The rebate cannot be more than 100%");
    }

    @Test
    @DisplayName("Check if the product collection has been modified after meeting the discount conditions - Rebate")
    public void testRebateApplied() throws Exception {
        Cart cart = new Cart(new Discount[]{discountRebate});

        cart.addProduct(new Product("001", "testProduct1", 150));
        cart.addProduct(new Product("002", "testProduct2", 220.99));

        Assertions.assertEquals(352.44, cart.getPrice());
    }

    @Test
    @DisplayName("Check if the product collection has NOT been modified after meeting the discount conditions - Rebate")
    public void testRebateNotApplied() throws Exception {
        Cart cart = new Cart(new Discount[]{discountRebate});

        cart.addProduct(new Product("001", "testProduct1", 150));
        cart.addProduct(new Product("002", "testProduct2", 120.99));

        Assertions.assertEquals(270.99, cart.getPrice());
    }

    @Test
    @DisplayName("Check if the product collection has been modified after meeting the discount conditions - FreeProduct")
    public void testFreeProductApplied() throws Exception {
        Cart cart = new Cart(new Discount[]{discountFreeProduct});

        cart.addProduct(new Product("001", "testProduct1", 150));
        cart.addProduct(new Product("002", "testProduct2", 120.99));
        cart.addProduct(new Product("003", "testProduct3", 220.99));

        Assertions.assertEquals(0, cart.getProducts()[0].getDiscountPrice());
    }

    @Test
    @DisplayName("Check if the product collection has NOT been modified after meeting the discount conditions - FreeProduct")
    public void testFreeProductNotApplied() throws Exception {
        Cart cart = new Cart(new Discount[]{discountFreeProduct});

        cart.addProduct(new Product("001", "testProduct1", 150));
        cart.addProduct(new Product("002", "testProduct2", 120.99));

        Assertions.assertEquals(120.99, cart.getProducts()[0].getDiscountPrice());
    }

    @Test
    @DisplayName("Check if the product collection has been modified after meeting the discount conditions - FreeMug")
    public void testFreeMugApplied() throws Exception {
        Cart cart = new Cart(new Discount[]{discountFreeMug});

        cart.addProduct(new Product("001", "testProduct1", 150));
        cart.addProduct(new Product("002", "testProduct2", 120.99));

        Assertions.assertEquals(3, cart.getProducts().length);
    }

    @Test
    @DisplayName("Check if the product collection has not been modified after meeting the discount conditions - FreeMug")
    public void testFreeMugNotApplied() throws Exception {
        Cart cart = new Cart(new Discount[]{discountFreeMug});

        cart.addProduct(new Product("001", "testProduct1", 150));

        Assertions.assertEquals(1, cart.getProducts().length);
    }

    @Test
    @DisplayName("Check if the product collection has been modified after meeting the discount conditions - ProductRebate")
    public void testProductRebateApplied() throws Exception {
        Cart cart = new Cart(new Discount[]{discountProductRebate});

        cart.addProduct(new Product("001", "testProduct1", 150));
        cart.addProduct(new Product("002", "testProduct2", 120.99));
        cart.addProduct(new Product("003", "testProduct3", 220.99));

        Assertions.assertEquals(84.69, cart.getProducts()[0].getDiscountPrice());
    }

    @Test
    @DisplayName("Check if the product collection has NOT been modified after meeting the discount conditions - ProductRebate")
    public void testProductRebateNotApplied() throws Exception {
        Cart cart = new Cart(new Discount[]{discountProductRebate});

        cart.addProduct(new Product("001", "testProduct1", 150));
        cart.addProduct(new Product("003", "testProduct3", 220.99));

        Assertions.assertEquals(370.99, cart.getPrice());
    }

    @Test
    @DisplayName("Check if the rebate has been removed, if after updating the cart, it does not meet the requirements - Rebate")
    public void testRebateSpecialCase() throws Exception {
        Cart cart = new Cart(new Discount[]{discountRebate});

        cart.addProduct(new Product("001", "testProduct1", 150));
        cart.addProduct(new Product("002", "testProduct2", 220.99));
        cart.addProduct(new Product("003", "testProduct3", 20.59));

        cart.removeProduct("002");

        Assertions.assertEquals(
            Arrays.toString(Arrays.stream(cart.getProducts()).map(Product::getDiscountPrice).toArray()),
            Arrays.toString(Arrays.stream(cart.getProducts()).map(Product::getPrice).toArray())
        );
    }

    @Test
    @DisplayName("Check if the rebate has been removed, if after updating the cart, it does not meet the requirements - FreeProduct")
    public void testFreeProductSpecialCase() throws Exception {
        Cart cart = new Cart(new Discount[]{discountFreeProduct});

        cart.addProduct(new Product("001", "testProduct1", 150));
        cart.addProduct(new Product("002", "testProduct2", 120.99));
        cart.addProduct(new Product("003", "testProduct3", 220.99));

        cart.removeProduct("003");

        Assertions.assertEquals(
            Arrays.toString(Arrays.stream(cart.getProducts()).map(Product::getDiscountPrice).toArray()),
            Arrays.toString(Arrays.stream(cart.getProducts()).map(Product::getPrice).toArray())
        );
    }

    @Test
    @DisplayName("Check if the rebate has been removed, if after updating the cart, it does not meet the requirements - FreeMug")
    public void testFreeMugSpecialCase() throws Exception {
        Cart cart = new Cart(new Discount[]{discountFreeMug});

        cart.addProduct(new Product("001", "testProduct1", 150));
        cart.addProduct(new Product("002", "testProduct2", 120.99));

        cart.removeProduct("002");

        Assertions.assertEquals(1, cart.getProducts().length);
    }

    @Test
    @DisplayName("Check if the rebate has been removed, if after updating the cart, it does not meet the requirements - ProductRebate")
    public void testProductRebateSpecialCase() throws Exception {
        Cart cart = new Cart(new Discount[]{discountProductRebate});

        cart.addProduct(new Product("001", "testProduct1", 150));
        cart.addProduct(new Product("002", "testProduct2", 120.99));
        cart.addProduct(new Product("003", "testProduct3", 220.99));

        cart.removeProduct("002");

        Assertions.assertEquals(
            Arrays.toString(Arrays.stream(cart.getProducts()).map(Product::getDiscountPrice).toArray()),
            Arrays.toString(Arrays.stream(cart.getProducts()).map(Product::getPrice).toArray())
        );
    }

}
