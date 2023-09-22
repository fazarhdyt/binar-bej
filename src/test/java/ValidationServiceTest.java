import model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import repository.ProductRepository;
import service.impl.ValidationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

public class ValidationServiceTest {

    ValidationService validationService;

    @BeforeEach
    public void setUp() {
        validationService = new ValidationService();
    }

    @AfterEach
    public void tearDown() {
        validationService = null;
    }

    @Test
    public void inputValidation_success() {
        boolean result = validationService.inputValidation("1", "[0-9]+");
        boolean result2 = validationService.inputValidation("99", "[0-9]+");
        boolean result3 = validationService.inputValidation("abc", "[0-9]+");
        boolean result4 = validationService.inputValidation("!@#$%", "[0-9]+");

        Assertions.assertEquals(true, result);
        Assertions.assertEquals(true, result2);
        Assertions.assertEquals(false, result3);
        Assertions.assertEquals(false, result4);

        Assertions.assertTrue(validationService.inputValidation("1", "[0-9]+"));
        Assertions.assertTrue(validationService.inputValidation("99", "[0-9]+"));
        Assertions.assertFalse(validationService.inputValidation("abc", "[0-9]+"));
        Assertions.assertFalse(validationService.inputValidation("!@#$%", "[0-9]+"));

        Assertions.assertDoesNotThrow(() -> validationService.inputValidation("!@#$%", "[0-9]+"));
    }

    @Test
    public void inputUser_success() {
        ValidationService validationService = new ValidationService();

        String input = "1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Assertions.assertEquals("1", validationService.inputUser("=> ", null, "[0-9]+"));
    }

    @Test
    public void checkAvailabilityOrderId_success() {
        List<Product> products = ProductRepository.getProducts();

        boolean result = validationService.checkAvailabilityOrderId(products, 5);
        boolean result2 = validationService.checkAvailabilityOrderId(products, 9);

        Assertions.assertEquals(true, result);
        Assertions.assertEquals(false, result2);

        Assertions.assertTrue(validationService.checkAvailabilityOrderId(products, 7));
        Assertions.assertFalse(validationService.checkAvailabilityOrderId(products, 17));

        Assertions.assertDoesNotThrow(() -> validationService.checkAvailabilityOrderId(products, 21));
    }

    @Test
    public void inputOrder_success() {
        List<Product> products = ProductRepository.getProducts();

        int input = 5;
        InputStream in = new ByteArrayInputStream(Integer.toString(input).getBytes());
        System.setIn(in);

        Assertions.assertEquals(5, validationService.inputOrder(products));
    }
}
