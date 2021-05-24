package main;

public class Product {

    public final String code;
    public final String name;
    public final double price;
    public Double discountPrice;

    public Product(String code, String name, double price) throws Exception {
        if (code.length() != 3) { throw new Exception("The length of product code must be 3!"); }
        if (price < 0) { throw new Exception("Price must be a positive number"); }

        this.code = code;
        this.name = name;
        this.price = price;
        this.discountPrice = price;
    }

    public String getCode() { return this.code; }

    public String getName() { return this.name; }

    public double getPrice() { return this.price; }

    public double getDiscountPrice() { return this.discountPrice; }
}
