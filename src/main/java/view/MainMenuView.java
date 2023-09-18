package view;

import model.CartItem;
import model.Product;
import repository.ProductRepository;
import service.IMenuService;
import service.IShopService;
import service.IValidationService;

import java.util.ArrayList;
import java.util.List;

public class MainMenuView {

    IMenuService menuService;
    IShopService shopService;
    IValidationService validationService;

    public MainMenuView(IMenuService menuService, IShopService shopService, IValidationService validationService) {
        this.menuService = menuService;
        this.shopService = shopService;
        this.validationService = validationService;
    }

    int choose;
    int qty;
    boolean isLooping = true;
    String regexNumeric = "[0-9]+";
    String regexMenuConfirmAndPayment = "^[0-2]$";
    List<Product> listProducts = ProductRepository.getProducts();
    List<CartItem> listCartItems = new ArrayList<>();

    public void runApp() {
        do {
            try {
                menuService.printMenu(listProducts, "Selamat datang di Warkop Top Global");
                choose = validationService.inputOrder(listProducts);
            } catch (NumberFormatException nfe) {
                System.err.println("Error: " + nfe.getMessage());
                System.err.println("Masukkan dengan angka tidak melebihi batas integer!");
                continue;
            }
            switch (choose) {
                case 0:
                    System.exit(0);
                    break;
                case 99:
                    menuService.printConfirmAndPayment(listCartItems);
                    choose = Integer.parseInt(validationService.inputUser("=> ", "masukkan hanya dengan angka 0-2", regexMenuConfirmAndPayment));
                    if (choose == 1) {
                        if (listCartItems.isEmpty()) {
                            menuService.failGenerateReceipt();
                            validationService.inputUser("tekan enter untuk kembali ke menu utama", null, "^$|^.+$");
                        } else {
                            menuService.generateReceipt(listCartItems, "Warkop Top Global");
                            isLooping = false;
                        }
                    } else if (choose == 2) {
                        break;
                    } else if (choose == 0) {
                        System.exit(0);
                    }
                    break;
                default:
                    try {
                        Product product = shopService.getProductById(listProducts, choose);
                        menuService.printConfirmOrder(product);
                        qty = Integer.parseInt(validationService.inputUser("qty => ", "masukkan hanya dengan angka positif", regexNumeric));
                        if (qty != 0) {
                            shopService.addToCartShop(listCartItems, product, qty);
                        }
                    } catch (NumberFormatException nfe) {
                        System.err.println("Error: " + nfe.getMessage());
                        System.err.println("Masukkan dengan angka tidak melebihi batas integer!");
                    }

            }
        } while (isLooping);
    }

}
