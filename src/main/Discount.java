package main;

import java.util.Arrays;
import java.util.Comparator;

public class Discount {
    private final String type;
    private Double priceReq = null;
    private Double rebate = null;
    private Integer productsNumReq = null;
    private String productCode = null;

    public Discount(double priceReq, double rebate) throws Exception {
        if (priceReq < 0) { throw new Exception("Price must be a positive number"); }
        if (rebate > 1 || rebate <= 0) { throw new Exception("The rebate cannot be more than 100%"); }

        this.type = "rebate";
        this.priceReq = priceReq;
        this.rebate = rebate;
    }

    public Discount(int productsNumReq) throws Exception {
        if (productsNumReq < 0) { throw new Exception("The number of products cannot be less than 0"); }

        this.type = "freeProduct";
        this.productsNumReq = productsNumReq;
    }

    public Discount(double priceReq) throws Exception {
        if (priceReq < 0) { throw new Exception("Price must be a positive number"); }

        this.type = "freeMug";
        this.priceReq = priceReq;
    }

    public Discount(String productCode, double rebate) throws Exception {
        if (productCode.length() != 3) { throw new Exception("The length of product code must be 3!"); }
        if (rebate > 1 || rebate <= 0) { throw new Exception("The rebate cannot be more than 100%"); }

        this.type = "productRebate";
        this.rebate = rebate;
        this.productCode = productCode;
    }

    public Product[] checkIfFulfill(Product[] products) throws Exception {
        double scale = Math.pow(10, 2);

        if (this.type.equals("rebate") && Arrays.stream(products).mapToDouble(Product::getDiscountPrice).sum() > this.priceReq) {
            for (Product product : products) {
                product.discountPrice = Math.round(product.discountPrice * (1 - this.rebate) * scale) / scale;
            }
        }

        if (this.type.equals("freeProduct") && products.length > productsNumReq) {
            Comparator<Product> byDiscountPrice = Comparator.comparing(Product::getDiscountPrice);
            Arrays.sort(products, byDiscountPrice);

            if (products[0].getCode().equals("000")) {
                products[1].discountPrice = 0d;
            } else {
                products[0].discountPrice = 0d;
            }
        }

        if (this.type.equals("freeMug") && Arrays.stream(products).mapToDouble(Product::getDiscountPrice).sum() > this.priceReq) {
            products = Arrays.copyOf(products, products.length + 1);
            products[products.length - 1] = new Product("000", "Firmowy kubek do kawy", 0);
        }

        if (this.type.equals("productRebate") && Arrays.stream(products).anyMatch(product -> product.getCode().equals(this.productCode))) {
            for (Product product : products) {
                if (product.getCode().equals(this.productCode)) {
                    product.discountPrice = Math.round(product.discountPrice * (1 - this.rebate) * scale) / scale;
                }
            }
        }
        return products;
    }
}
