package test;

import main.Cart;
import main.Discount;
import main.Product;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class CartTest {

    private Cart cart;
    private Cart cartToSort;

    @Before
    public void setUp() throws Exception {
        cart = new Cart(new Discount[]{});
        cartToSort = new Cart(new Discount[]{
            new Discount(200d),
            new Discount("008", 0.30),
            new Discount(300, 0.05),
            new Discount(2)
        });

        cartToSort.addProduct(new Product("075", "Chłodziarko-zamrażarka ELECTROLUX", 3399));
        cartToSort.addProduct(new Product("108", "Telewizor SONY", 7390));
        cartToSort.addProduct(new Product("531", "APPLE Iphone 8", 1149));
        cartToSort.addProduct(new Product("008", "OUTLET Mikrofalówka SAMSUNG", 3590));
    }

    @Test
    @DisplayName("Check if the product has been added to the array")
    public void testAddProductProducts() throws Exception {
        cart.addProduct(new Product("001", "testProduct1", 150));

        Assertions.assertEquals(1, cart.getProducts().length);
    }

    @Test
    @DisplayName("Check if the price has been updated")
    public void testAddProductPrice() throws Exception {
        cart.addProduct(new Product("001", "testProduct1", 150));

        Assertions.assertEquals(150, cart.getPrice());
    }

    @Test
    @DisplayName("Check if the products has been sorted")
    public void testAddProductSort() throws Exception {
        cart.addProduct(new Product("001", "testProduct1", 150));
        cart.addProduct(new Product("002", "testProduct2", 120.99));

        Assertions.assertEquals(
            Arrays.toString(new String[]{"testProduct2", "testProduct1"}),
            Arrays.toString(Arrays.stream(cart.getProducts()).map(Product::getName).toArray())
        );
    }

    @Test
    @DisplayName("Check if the product has been removed to the array")
    public void testRemoveProductProducts() throws Exception {
        cart.addProduct(new Product("001", "testProduct1", 150));
        cart.addProduct(new Product("002", "testProduct2", 120.99));
        cart.removeProduct("001");

        Assertions.assertEquals(1, cart.getProducts().length);
    }

    @Test
    @DisplayName("Check if exactly one product has been removed, if there were more than one in the array")
    public void testRemoveProductProductsOne() throws Exception {
        cart.addProduct(new Product("001", "testProduct1", 150));
        cart.addProduct(new Product("002", "testProduct2", 120.99));
        cart.addProduct(new Product("002", "testProduct2", 120.99));
        cart.removeProduct("002");

        Assertions.assertEquals(2, cart.getProducts().length);
    }

    @Test
    @DisplayName("Check if the price has been updated")
    public void testRemoveProductPrice() throws Exception {
        cart.addProduct(new Product("001", "testProduct1", 150));
        cart.addProduct(new Product("002", "testProduct2", 120.99));
        cart.removeProduct("002");

        Assertions.assertEquals(150, cart.getPrice());
    }

    @Test
    @DisplayName("Check if the products has been sorted")
    public void testRemoveProductSort() throws Exception {
        cart.addProduct(new Product("001", "testProduct1", 150));
        cart.addProduct(new Product("002", "testProduct2", 120.99));
        cart.addProduct(new Product("003", "testProduct3", 220.99));
        cart.removeProduct("002");

        Assertions.assertEquals(
            Arrays.toString(new String[]{"testProduct1", "testProduct3"}),
            Arrays.toString(Arrays.stream(cart.getProducts()).map(Product::getName).toArray())
        );
    }

    @Test
    @DisplayName("Check if the products has been sorted - byDiscountPrice")
    public void testSortByDiscountPrice() {
        cartToSort.sortByDiscountPrice();

        Assertions.assertEquals(
            Arrays.toString(new String[]{
                "Firmowy kubek do kawy",
                "APPLE Iphone 8",
                "OUTLET Mikrofalówka SAMSUNG",
                "Chłodziarko-zamrażarka ELECTROLUX",
                "Telewizor SONY"
            }),
            Arrays.toString(Arrays.stream(cartToSort.getProducts()).map(Product::getName).toArray())
        );
    }

    @Test
    @DisplayName("Check if the products has been sorted - byPrice")
    public void testSortByPrice() {
        cartToSort.sortByPrice();

        Assertions.assertEquals(
            Arrays.toString(new String[]{
                "Firmowy kubek do kawy",
                "APPLE Iphone 8",
                "Chłodziarko-zamrażarka ELECTROLUX",
                "OUTLET Mikrofalówka SAMSUNG",
                "Telewizor SONY"
            }),
            Arrays.toString(Arrays.stream(cartToSort.getProducts()).map(Product::getName).toArray())
        );
    }

    @Test
    @DisplayName("Check if the products has been sorted - byName")
    public void testSortByName() {
        cartToSort.sortByName();

        Assertions.assertEquals(
            Arrays.toString(new String[]{
                "APPLE Iphone 8",
                "Chłodziarko-zamrażarka ELECTROLUX",
                "Firmowy kubek do kawy",
                "OUTLET Mikrofalówka SAMSUNG",
                "Telewizor SONY"
            }),
            Arrays.toString(Arrays.stream(cartToSort.getProducts()).map(Product::getName).toArray())
        );
    }

    @Test
    @DisplayName("Get the cheapest product from the array")
    public void testGetCheapestProduct() {
        Assertions.assertEquals("Firmowy kubek do kawy", cartToSort.getCheapestProduct().getName());
    }

    @Test
    @DisplayName("Get the most expensive product from the array")
    public void testGetMostExpProduct() {
        Assertions.assertEquals("Telewizor SONY", cartToSort.getMostExpProduct().getName());
    }

    @Test
    @DisplayName("Get the N cheapest products from the array")
    public void testGetNCheapestProducts() {
        Assertions.assertEquals(
            Arrays.toString(new String[]{
                "Firmowy kubek do kawy",
                "APPLE Iphone 8",
                "OUTLET Mikrofalówka SAMSUNG"
            }),
            Arrays.toString(Arrays.stream(cartToSort.getNCheapestProducts(3)).map(Product::getName).toArray())
        );
    }

    @Test
    @DisplayName("Get the N most expensive products from the array")
    public void testGetNMostExpProducts() {
        Assertions.assertEquals(
            Arrays.toString(new String[]{
                "Telewizor SONY",
                "Chłodziarko-zamrażarka ELECTROLUX",
                "OUTLET Mikrofalówka SAMSUNG"
            }),
            Arrays.toString(Arrays.stream(cartToSort.getNMostExpProducts(3)).map(Product::getName).toArray())
        );
    }

}
