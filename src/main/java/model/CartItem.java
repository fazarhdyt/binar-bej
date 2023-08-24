package main.java.model;

public class CartItem {
    private Product product;
    private int qty;
    private double totalPrice;

    public CartItem() {

    }

    public CartItem(Product product, int qty) {
        this.product = product;
        this.qty = qty;
        this.setTotalPrice();
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice() {
        this.totalPrice = product.getPrice() * qty;
    }
}
