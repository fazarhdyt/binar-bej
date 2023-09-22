import model.CartItem;
import model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.ProductRepository;
import service.impl.ShopService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ShopServiceTest {

    ShopService shopService;
    List<Product> products = ProductRepository.getProducts();

    @BeforeEach
    public void setUp() {
        shopService = new ShopService();
    }

    @AfterEach
    public void tearDown() {
        shopService = null;
    }

    @Test
    public void getProductById_success() {
        int id = 1;
        Product product = null;
        Optional<Product> productOptional = shopService.getProductById(products, id);
        Product getProduct = productOptional.orElseThrow(() -> new NoSuchElementException("Product not found"));
        for (Product item : products) {
            if (item.getId() == id) {
                product = item;
            }
        }
        Assertions.assertSame(product, getProduct);
    }

    @Test
    public void addToCartShop_success() {
        List<CartItem> cartItemList = new ArrayList<>();
        int id = 1;
        int qty = 2;
        Optional<Product> productOptional = shopService.getProductById(products, id);

        Assertions.assertDoesNotThrow(() -> {
            Product product = productOptional.orElseThrow(() -> new NoSuchElementException("Product not found"));
            shopService.addToCartShop(cartItemList, product, qty);
        });
    }

    @Test
    public void getProductById_failed_null() {
        int id = 7;

        Assertions.assertThrows(NullPointerException.class, () -> shopService.getProductById(null, id));
    }

    @Test
    public void addToCartShop_failed_null() {
        int id = 1;
        int qty = 2;
        Optional<Product> productOptional = shopService.getProductById(products, id);

        Assertions.assertThrows(NullPointerException.class, () -> {
            Product product = productOptional.orElseThrow(() -> new NoSuchElementException("Product not found"));
            shopService.addToCartShop(null, product, qty);
        });
    }

    @Test
    public void addToCartShop_failed_noSuchElement() {
        int id = 22;
        int qty = 2;
        Optional<Product> productOptional = shopService.getProductById(products, id);

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            Product product = productOptional.orElseThrow(() -> new NoSuchElementException("Product not found"));
            shopService.addToCartShop(null, product, qty);
        });
    }

}
