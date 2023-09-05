package main.java.service.impl;

import main.java.model.CartItem;
import main.java.model.Product;
import main.java.service.IShopService;

import java.util.List;

public class ShopService implements IShopService {

    /**
     * method ini digunakan untuk mengambil makanan atau minuman berdasarkan id yang diinput user
     *
     * @param products
     * @param id
     * @return
     */
    public Product getProductById(List<Product> products, int id) {
        Product result = null;
        for (Product product : products) {
            if (product.getId() == id) {
                result = product;
                break;
            }
        }
        return result;
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
        boolean findItem = checkItem(cartItems, product);
        if (findItem) {
            for (CartItem cartItem : cartItems) {
                if (cartItem.getProduct().equals(product)) {
                    cartItem.setQty(cartItem.getQty() + qty);
                    cartItem.setTotalPrice();
                    break;
                }
            }
        } else {
            item = new CartItem(product, qty);
            cartItems.add(item);
        }
    }

    /**
     * method ini digunakan untuk mengecek apakah menu yang akan dipesan sudah ada di dalam keranjang atau belum
     *
     * @param cartItems
     * @param product
     * @return
     */
    public boolean checkItem(List<CartItem> cartItems, Product product) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId() == product.getId()) {
                return true;
            }
        }
        return false;
    }

}
