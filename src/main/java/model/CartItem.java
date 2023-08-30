package main.java.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    private Product product;
    private int qty;
    private double totalPrice;

    public CartItem(Product product, int qty) {
        this.product = product;
        this.qty = qty;
        this.setTotalPrice();
    }

    public void setTotalPrice(){
        this.totalPrice = product.getPrice() * qty;
    }
}
