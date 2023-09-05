package main.java.service;

import main.java.model.CartItem;
import main.java.model.Product;

import java.util.List;

public interface IMenuService {

    void printMenu(List<Product> products, String title);

    void printConfirmOrder(Product product);

    void printConfirmAndPayment(List<CartItem> cartItems);

    void generateReceipt(List<CartItem> cartItems, String title);

    void failGenerateReceipt();
}
