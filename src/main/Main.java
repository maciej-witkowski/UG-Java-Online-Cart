package main;

public class Main {
    public static void main(String[] args) throws Exception {
        Cart cart = new Cart(new Discount[]{
            new Discount(200d),
            new Discount("008", 0.30),
            new Discount(300, 0.05),
            new Discount(2)
        });

        cart.addProduct(new Product("131", "APPLE Iphone 11", 49));
        cart.addProduct(new Product("031", "APPLE Iphone 11 PRO", 49));
        cart.addProduct(new Product("075", "Chłodziarko-zamrażarka ELECTROLUX", 3399));
        cart.addProduct(new Product("108", "Telewizor SONY", 7390));
        cart.addProduct(new Product("108", "Telewizor SONY", 7390));
        cart.addProduct(new Product("028", "Konsola PlayStation 5", 2399));
        cart.addProduct(new Product("531", "APPLE Iphone 8", 1149));
        cart.addProduct(new Product("631", "APPLE Iphone 4", 9));
        cart.addProduct(new Product("008", "OUTLET Mikrofalówka SAMSUNG", 359));

        cart.display();

        Product cheap = cart.getCheapestProduct();
        System.out.println(cheap.getName());
        System.out.println(cheap.getPrice());
        System.out.println(cheap.getDiscountPrice());

        Product expensive = cart.getMostExpProduct();
        System.out.println(expensive.getName());
        System.out.println(expensive.getPrice());
        System.out.println(expensive.getDiscountPrice());

        Product[] cheapProducts = cart.getNCheapestProducts(2);
        for (Product product: cheapProducts) {
            System.out.println(product.getName());
            System.out.println(product.getPrice());
            System.out.println(product.getDiscountPrice());
        }

        Product[] expensiveProducts = cart.getNMostExpProducts(2);
        for (Product product: expensiveProducts) {
            System.out.println(product.getName());
            System.out.println(product.getPrice());
            System.out.println(product.getDiscountPrice());
        }

        cart.sortByDiscountPrice();
        cart.display();

        cart.sortByPrice();
        cart.display();

        cart.sortByName();
        cart.display();

        cart.addDiscount(new Discount(300, 0.05));
        cart.display();

        cart.addDiscount(new Discount(2));
        cart.display();

        cart.removeProduct("028");
        cart.removeProduct("531");
        cart.removeProduct("108");
        cart.removeProduct("075");
        cart.removeProduct("131");
        cart.removeProduct("008");
        cart.display();
    }
}
