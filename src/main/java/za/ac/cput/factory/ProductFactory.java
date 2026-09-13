package za.ac.cput.factory;
/*
AnimeStore.java
ProductFactory class
Author: Sisonke Mhlana(221805486)
*/
import za.ac.cput.domain.Product;
import za.ac.cput.util.Helper;

public class ProductFactory {
    public static Product createProduct( String name, double price, int stock, byte[] productImage) {
        return new Product.Builder()

                .setName(name)
                .setPrice(price)
                .setStock(stock)
                .setProductImage(productImage)
                .build();
    }
}