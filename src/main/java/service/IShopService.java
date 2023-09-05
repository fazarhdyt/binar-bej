package main.java.service;

import main.java.model.CartItem;
import main.java.model.Product;

import java.util.List;

public interface IShopService {

    Product getProductById(List<Product> products, int id);

    void addToCartShop(List<CartItem> cartItems, Product product, int qty);

    boolean checkItem(List<CartItem> cartItems, Product product);

}
