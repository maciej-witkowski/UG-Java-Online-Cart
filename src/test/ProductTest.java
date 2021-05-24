package test;

import main.Product;

import org.junit.Before;
import org.junit.Test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class ProductTest {

    private Product product;

    @Before
    public void setUp() throws Exception {
        product = new Product("123", "test", 123);
    }

    @Test
    @DisplayName("Wrong length of product number")
    public void testWrongCode() {
        Exception thrown = Assertions.assertThrows(
            Exception.class,
            () -> new Product("1234", "test", 85.99),
            "The length of product code must be 3!"
        );

        Assertions.assertEquals(thrown.getMessage(), "The length of product code must be 3!");
    }

    @Test
    @DisplayName("Wrong product price")
    public void testWrongPrice() {
        Exception thrown = Assertions.assertThrows(
            Exception.class,
            () -> new Product("123", "test", -123),
            "Price must be a positive number"
        );

        Assertions.assertEquals(thrown.getMessage(), "Price must be a positive number");
    }

    @Test
    @DisplayName("Get code of a product")
    public void testGetCode() {
        Assertions.assertEquals("123", product.getCode());
    }

    @Test
    @DisplayName("Get name of a product")
    public void testGetName() {
        Assertions.assertEquals("test", product.getName());
    }

    @Test
    @DisplayName("Get price of a product")
    public void testGetPrice() {
        Assertions.assertEquals(123, product.getPrice());
    }

    @Test
    @DisplayName("Get discount price of a product")
    public void testGetDiscountPrice() {
        Assertions.assertEquals(123, product.getDiscountPrice());
    }
}
