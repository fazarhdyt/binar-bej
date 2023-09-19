package service.impl;

import model.CartItem;
import model.Product;
import service.IShopService;

import java.util.List;
import java.util.Optional;

public class ShopService implements IShopService {

    /**
     * method ini digunakan untuk mengambil makanan atau minuman berdasarkan id yang diinput user
     *
     * @param products
     * @param id
     * @return
     */
    public Optional<Product> getProductById(List<Product> products, int id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .findFirst();
    }

    /**
     * method ini digunakan untuk menambahkan makanan dan minuman ke keranjang (cart shop)
     *
     * @param cartItems
     * @param product
     * @param qty
     */
    public void addToCartShop(List<CartItem> cartItems, Product product, int qty) {
        CartItem item = null;
        Optional<CartItem> existingItem = cartItems.stream()
                .filter(cartItem -> cartItem.getProduct().equals(product))
                .findFirst();

        if (existingItem.isPresent()) {
            item = existingItem.get();
            item.setQty(item.getQty() + qty);
            item.setTotalPrice();
        } else {
            item = new CartItem(product, qty);
            cartItems.add(item);
        }
    }
}
