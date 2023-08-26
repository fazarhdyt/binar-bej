package main.java;

import main.java.model.CartItem;
import main.java.model.Product;
import main.java.repository.ProductRepository;
import main.java.service.ShopService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Product> listProducts = ProductRepository.getProducts();
        List<CartItem> listCartItems = new ArrayList<>();

        int choose;
        int qty;
        boolean isLooping = true;
        String regexNumeric = "[0-9]+";
        String regexMenuConfirmAndPayment = "^[0-2]$";

        do {
            Menu.printMenu(listProducts, "Selamat datang di Warkop Top Global");
            choose = Validation.inputOrder(listProducts);
            switch (choose) {
                case 0:
                    System.exit(0);
                    break;
                case 99:
                    Menu.printConfirmAndPayment(listCartItems);
                    choose = Integer.parseInt(Validation.inputUser("=> ", "masukkan hanya dengan angka 0-2", regexMenuConfirmAndPayment));
                    if (choose == 1) {
                        Menu.generateReceipt(listCartItems, "Warkop Top Global");
                        isLooping = false;
                    } else if (choose == 2) {
                        break;
                    } else if (choose == 0) {
                        System.exit(0);
                    }
                    break;
                default:
                    Product product = ShopService.getProductById(listProducts, choose);
                    Menu.printConfirmOrder(product);
                    qty = Integer.parseInt(Validation.inputUser("qty =>", "masukkan hanya dengan angka", regexNumeric));
                    if(qty != 0){
                        ShopService.addToCartShop(listCartItems, product, qty);
                    }
            }
        } while (isLooping);
    }
}
