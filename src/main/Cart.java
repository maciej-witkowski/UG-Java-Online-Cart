package main;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

public class Cart {
    private Product[] products;
    private double price;
    private Discount[] discounts;

    public Cart(Discount[] discounts) {
        this.products = new Product[0];
        this.price = 0d;
        this.discounts = discounts;
    }

    public void addProduct(Product newProduct) throws Exception {
        this.products = Arrays.copyOf(this.products, this.products.length + 1);
        this.products[this.products.length - 1] = newProduct;

        this.updatePrice();
        this.resetProducts();

        for (Discount discount: this.discounts) {
            this.products = discount.checkIfFulfill(this.products);
            this.updatePrice();
        }

        this.sortProducts();
    }

    public void removeProduct(String code) throws Exception {
        int index = IntStream.range(0, this.products.length).filter(i -> this.products[i].getCode().equals(code)).findFirst().orElse(-1);
        this.products = IntStream.range(0, this.products.length).filter(i -> i != index).mapToObj(i -> this.products[i]).toArray(Product[]::new);

        this.updatePrice();
        this.resetProducts();

        for (Discount discount: this.discounts) {
            this.products = discount.checkIfFulfill(this.products);
            this.updatePrice();
        }

        this.sortProducts();
    }

    public void addDiscount(Discount newDiscount) throws Exception {
        this.discounts = Arrays.copyOf(this.discounts, this.discounts.length + 1);
        this.discounts[this.discounts.length - 1] = newDiscount;

        this.resetProducts();

        for (Discount discount: this.discounts) {
            this.products = discount.checkIfFulfill(this.products);
            this.updatePrice();
        }

        this.sortProducts();
    }

    private void updatePrice() {
        this.price = 0d;
        for (Product product: this.products) {
            this.price += product.getDiscountPrice();
        }
        this.price = Math.round(this.price * Math.pow(10, 2)) / Math.pow(10, 2);
    }

    private void resetProducts() {
        for (Product product: this.products) {
            product.discountPrice = product.price;
        }
        this.products = Arrays.stream(this.products).filter(product -> !product.getCode().equals("000")).toArray(Product[]::new);
    }

    private void sortProducts() {
        Comparator<Product> byDiscountPrice = Comparator.comparing(Product::getDiscountPrice);
        Comparator<Product> byPrice = Comparator.comparing(Product::getPrice);
        Comparator<Product> byName = Comparator.comparing(Product::getName);
        Arrays.sort(this.products, byDiscountPrice.thenComparing(byPrice).thenComparing(byName));
    }

    public double getPrice() {
        return this.price;
    }

    public Product[] getProducts() {
        return this.products;
    }

    public void sortByDiscountPrice() {
        Comparator<Product> byDiscountPrice = Comparator.comparing(Product::getDiscountPrice);
        Arrays.sort(this.products, byDiscountPrice);
    }

    public void sortByPrice() {
        Comparator<Product> byPrice = Comparator.comparing(Product::getPrice);
        Arrays.sort(this.products, byPrice);
    }

    public void sortByName() {
        Comparator<Product> byName = Comparator.comparing(Product::getName);
        Arrays.sort(this.products, byName);
    }

    public Product getCheapestProduct() {
        Comparator<Product> byDiscountPrice = Comparator.comparing(Product::getDiscountPrice);
        Comparator<Product> byPrice = Comparator.comparing(Product::getPrice);

        Product[] temp = this.products;
        Arrays.sort(temp, byDiscountPrice.thenComparing(byPrice));

        return temp[0];
    }

    public Product getMostExpProduct() {
        Comparator<Product> byDiscountPrice = Comparator.comparing(Product::getDiscountPrice).reversed();
        Comparator<Product> byPrice = Comparator.comparing(Product::getPrice).reversed();

        Product[] temp = this.products;
        Arrays.sort(temp, byDiscountPrice.thenComparing(byPrice));

        return temp[0];
    }

    public Product[] getNCheapestProducts(int n) {
        Comparator<Product> byDiscountPrice = Comparator.comparing(Product::getDiscountPrice);
        Comparator<Product> byPrice = Comparator.comparing(Product::getPrice);

        Product[] temp = this.products;
        Arrays.sort(temp, byDiscountPrice.thenComparing(byPrice));

        return Arrays.copyOfRange(temp, 0, n);
    }

    public Product[] getNMostExpProducts(int n) {
        Comparator<Product> byDiscountPrice = Comparator.comparing(Product::getDiscountPrice).reversed();
        Comparator<Product> byPrice = Comparator.comparing(Product::getPrice).reversed();

        Product[] temp = this.products;
        Arrays.sort(temp, byDiscountPrice.thenComparing(byPrice));

        return Arrays.copyOfRange(temp, 0, n);
    }


    public void display() {
        for (Product product: this.products) {
            System.out.println(
                "\n########################################################" +
                "\n#\tKod produktu: " + product.getCode() +
                "\n#\tNazwa produktu: " + product.getName() +
                "\n#\tCena produktu: " + product.getPrice() +
                "\n#\tCena produktu po uwzględnieniu promocji : " + product.getDiscountPrice() +
                "\n########################################################"
            );
        }
        System.out.println("\n\nSuma: " + this.price + "zł");
    }
}
