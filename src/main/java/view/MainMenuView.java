package view;

import model.CartItem;
import model.Product;
import repository.ProductRepository;
import service.IMenuService;
import service.IShopService;
import service.IValidationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainMenuView {

    private final IMenuService menuService;
    private final IShopService shopService;
    private final IValidationService validationService;

    private final String regexNumeric = "[0-9]+";
    private final String regexMenuConfirmAndPayment = "^[0-2]$";
    private final List<Product> listProducts;
    private final List<CartItem> listCartItems;

    public MainMenuView(IMenuService menuService, IShopService shopService, IValidationService validationService) {
        this.menuService = menuService;
        this.shopService = shopService;
        this.validationService = validationService;
        this.listProducts = ProductRepository.getProducts();
        this.listCartItems = new ArrayList<>();
    }

    public void runApp() {
        int choose;
        boolean isLooping = true;

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
                    isLooping = false;
                    break;
                case 99:
                    handleConfirmAndPayment();
                    break;
                default:
                    handleProductSelection(choose);
            }
        } while (isLooping);
    }

    public void handleConfirmAndPayment() {
        menuService.printConfirmAndPayment(listCartItems);
        int choose = Integer.parseInt(validationService.inputUser("=> ", "masukkan hanya dengan angka 0-2", regexMenuConfirmAndPayment));

        switch (choose) {
            case 1:
                if (listCartItems.isEmpty()) {
                    menuService.failGenerateReceipt();
                    validationService.inputUser("tekan enter untuk kembali ke menu utama", null, "^$|^.+$");
                } else {
                    menuService.generateReceipt(listCartItems, "Warkop Top Global");
                    System.exit(0);
                }
                break;
            case 2:
                break;
            case 0:
                System.exit(0);
                break;
            default:
                break;
        }
    }

    public void handleProductSelection(int productId) {
        try {
            Optional<Product> product = shopService.getProductById(listProducts, productId);
            if (product.isPresent()) {
                Product selectedProduct = product.get();
                menuService.printConfirmOrder(selectedProduct);
                int qty = Integer.parseInt(validationService.inputUser("qty => ", "masukkan hanya dengan angka positif", regexNumeric));

                if (qty > 0) {
                    shopService.addToCartShop(listCartItems, selectedProduct, qty);
                }
            }
        } catch (NumberFormatException nfe) {
            System.err.println("Error: " + nfe.getMessage());
            System.err.println("Masukkan dengan angka tidak melebihi batas integer!");
        }
    }

}
