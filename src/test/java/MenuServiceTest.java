import model.CartItem;
import model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.ProductRepository;
import service.impl.MenuService;
import service.impl.ShopService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class MenuServiceTest {

    MenuService menuService;
    private List<Product> products = ProductRepository.getProducts();

    @BeforeEach
    public void setUp() {
        menuService = new MenuService();
    }

    @AfterEach
    public void tearDown() {
        menuService = null;
    }

    @Test
    public void printMenu_success() {
        Assertions.assertDoesNotThrow(() -> menuService.printMenu(products, "title"));
    }

    @Test
    public void printConfirmOrder_success() {
        ShopService shopService = new ShopService();
        Optional<Product> productOptional = shopService.getProductById(products, 4);

        Assertions.assertDoesNotThrow(() -> {
            Product product = productOptional.orElseThrow(() -> new NoSuchElementException("Product not found"));
            menuService.printConfirmOrder(product);
        });
    }

    @Test
    public void printConfirmAndPayment_success() {
        List<CartItem> cartItems = new ArrayList<>();
        Assertions.assertDoesNotThrow(() -> menuService.printConfirmAndPayment(cartItems));
    }

    @Test
    public void failGenerateReceipt_success() {
        Assertions.assertDoesNotThrow(() -> menuService.failGenerateReceipt());
    }

    @Test
    public void GenerateReceipt_success() {
        List<CartItem> cartItems = new ArrayList<>();
        Assertions.assertDoesNotThrow(() -> menuService.generateReceipt(cartItems, "title"));
    }

    @Test
    public void printMenu_failed_null() {
        Assertions.assertThrows(NullPointerException.class, () -> menuService.printMenu(null, "title"));
    }

    @Test
    public void printConfirmOrder_failed_null() {
        Assertions.assertThrows(NullPointerException.class, () -> menuService.printConfirmOrder(null));
    }

    @Test
    public void printConfirmOrder_failed_noSuchElement() {
        ShopService shopService = new ShopService();
        Optional<Product> productOptional = shopService.getProductById(products, 89);

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            Product product = productOptional.orElseThrow(() -> new NoSuchElementException("Product not found"));
            menuService.printConfirmOrder(product);
        });
    }

    @Test
    public void printConfirmAndPayment_failed_null() {
        Assertions.assertThrows(NullPointerException.class, () -> menuService.printConfirmAndPayment(null));
    }

    @Test
    public void GenerateReceipt_failed_null() {
        Assertions.assertThrows(NullPointerException.class, () -> menuService.generateReceipt(null, "title"));
    }
}
