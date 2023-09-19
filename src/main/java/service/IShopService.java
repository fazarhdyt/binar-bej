package service;

import model.CartItem;
import model.Product;

import java.util.List;
import java.util.Optional;

public interface IShopService {

    Optional<Product> getProductById(List<Product> products, int id);

    void addToCartShop(List<CartItem> cartItems, Product product, int qty);

}
