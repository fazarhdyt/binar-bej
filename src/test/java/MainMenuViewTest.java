import model.CartItem;
import model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import service.IMenuService;
import service.IShopService;
import service.IValidationService;
import view.MainMenuView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainMenuViewTest {

    private MainMenuView mainMenuView;
    private List<Product> mockProducts;
    private List<CartItem> listCartItems;

    @Mock
    private IMenuService menuService;

    @Mock
    private IShopService shopService;

    @Mock
    private IValidationService validationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mainMenuView = new MainMenuView(menuService, shopService, validationService);
        // Mock list of products
        mockProducts = new ArrayList<>();
        mockProducts.add(new Product(1, "Product 1", 10000));
        mockProducts.add(new Product(2, "Product 2", 20000));

        // Mock listCartItems
        listCartItems = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        System.setIn(System.in);
    }

    @Test
    void testRunApp_success() {
        // Mock user input and behavior
        Mockito.when(validationService.inputOrder(mockProducts)).thenReturn(1);
        Mockito.when(validationService.inputUser("qty => ", "masukkan hanya dengan angka positif", "[0-9]+")).thenReturn("2");
        Mockito.when(shopService.getProductById(mockProducts, 1)).thenReturn(Optional.ofNullable(mockProducts.get(0)));

        // Mock menuService behavior
        Mockito.doNothing().when(menuService).printMenu(mockProducts, "Selamat datang di Warkop Top Global");
        Mockito.doNothing().when(menuService).printConfirmAndPayment(Mockito.anyList());
        Mockito.doNothing().when(menuService).printConfirmOrder(Mockito.any(Product.class));
        Mockito.doNothing().when(menuService).failGenerateReceipt();
        Mockito.doNothing().when(menuService).generateReceipt(Mockito.anyList(), Mockito.anyString());

        mainMenuView.runApp();
        Assertions.assertDoesNotThrow(() -> mainMenuView.runApp());
    }

    @Test
    void testHandleConfirmAndPayment() {
        // Mock user input and behavior
        Mockito.when(validationService.inputUser("=> ", "masukkan hanya dengan angka 0-2", "^[0-2]$")).thenReturn("1");

        // Mock menuService behavior
        Mockito.doNothing().when(menuService).printConfirmAndPayment(Mockito.anyList());
        Mockito.doNothing().when(menuService).failGenerateReceipt();
        Mockito.doNothing().when(menuService).generateReceipt(Mockito.anyList(), Mockito.anyString());

        mainMenuView.handleConfirmAndPayment();
        Assertions.assertDoesNotThrow(() -> mainMenuView.handleConfirmAndPayment());
    }

    @Test
    void testHandleProductSelection() {
        // Mock user input and behavior
        Mockito.when(shopService.getProductById(mockProducts, 2)).thenReturn(Optional.of(mockProducts.get(0)));
        Mockito.when(validationService.inputUser("qty => ", "masukkan hanya dengan angka positif", "[0-9]+")).thenReturn("3");

        // Mock menuService behavior
        Mockito.doNothing().when(menuService).printConfirmOrder(Mockito.any(Product.class));

        mainMenuView.handleProductSelection(2);
        Assertions.assertDoesNotThrow(() -> mainMenuView.handleProductSelection(2));
    }
}
