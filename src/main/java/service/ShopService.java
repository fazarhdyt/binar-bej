package main.java.service;

import main.java.model.CartItem;
import main.java.model.Product;

import java.util.List;

public class ShopService {

    private ShopService(){
        throw new IllegalStateException("ShopService");
    }
    /**
     * function ini digunakan untuk mengambil makanan atau minuman berdasarkan id yang diinput user
     * @param products
     * @param id
     * @return
     */
    public static Product getProductById(List<Product> products, int id) {
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
     * function ini digunakan untuk menambahkan makanan dan minuman ke keranjang (cart shop)
     * @param cartItems
     * @param product
     * @param qty
     */
    public static void addToCartShop(List<CartItem> cartItems, Product product, int qty) {
        CartItem item = null;
        boolean findItem = checkItem(cartItems, product);
        if(findItem){
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

    public static CartItem getCartItem(List<CartItem> cartItems, Product product) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId() == product.getId()) {
                return item;
            }
        }
        return null;
    }

    public static boolean checkItem(List<CartItem> cartItems, Product product) {
        for (CartItem item : cartItems) {
            if(item == null){
                return false;
            }
            if (item.getProduct().getId() == product.getId()) {
                return true;
            }
        }
        return false;
    }
}
