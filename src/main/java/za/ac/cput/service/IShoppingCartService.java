package za.ac.cput.service;


import za.ac.cput.domain.ShoppingCart;

import java.util.List;

public interface IShoppingCartService extends IService<ShoppingCart, String> {
    ShoppingCart save(ShoppingCart shoppingcart);
    ShoppingCart findById(String id); // MAKE SURE THIS EXISTS
    List<ShoppingCart> getAll();
    boolean delete(String id);

    List<ShoppingCart> getShoppingCartsByUserId(String userId);

    List<ShoppingCart> getShoppingCartsByStatus(String status);
}