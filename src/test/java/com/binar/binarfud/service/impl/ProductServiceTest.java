package com.binar.binarfud.service.impl;

import com.binar.binarfud.model.Merchant;
import com.binar.binarfud.model.Product;
import com.binar.binarfud.repository.MerchantRepository;
import com.binar.binarfud.repository.ProductRepository;
import com.binar.binarfud.service.IMerchantService;
import com.binar.binarfud.service.IProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private IProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private IMerchantService merchantService;

    @Autowired
    private MerchantRepository merchantRepository;

    @BeforeEach
    void setUp() {
        merchantRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void testCreateProduct_success() {

        Merchant merchant = Merchant.builder()
                .merchantCode("MDR001")
                .merchantName("Madura Selalu Buka")
                .build();

        merchantRepository.save(merchant);

        Product productToSave = new Product();
        productToSave.setProductCode("ABC001");
        productToSave.setProductName("Baterai ABC");
        productToSave.setPrice(10000);

        // Act
        Product createdProduct = productService.createProduct(productToSave, merchant.getMerchantCode());

        // Assert
        assertNotNull(createdProduct.getProductCode());
        assertEquals(productToSave.getProductName(), createdProduct.getProductName());
        assertEquals(productToSave.getMerchant(), createdProduct.getMerchant());
    }

    @Test
    public void testGetProductByProductCode_productExists() {

        String productCode = "testProduct";
        Product product = new Product();
        product.setProductCode(productCode);
        productRepository.save(product);

        // Act
        Product result = productService.getProductByProductCode(productCode);

        // Assert
        assertNotNull(result);
        assertEquals(productCode, result.getProductCode());
    }

    @Test
    public void testGetProductByProductCode_productNotFound() {

        String productCode = "nonExistentProduct";

        // Act and Assert
        assertThrows(RuntimeException.class, () -> productService.getProductByProductCode(productCode));
    }

    @Test
    public void testUpdateProductByProductCode_productExists() {

        String productCode = "existingProduct";
        Product existingProduct = new Product();
        existingProduct.setProductCode(productCode);
        productRepository.save(existingProduct);

        Product updatedProduct = new Product();
        updatedProduct.setProductCode(productCode);
        updatedProduct.setProductName("product update name");
        updatedProduct.setPrice(7000);

        // Act
        Product result = productService.updateProductByProductCode(productCode, updatedProduct);

        // Assert
        assertNotNull(result);
        assertEquals(productCode, result.getProductCode());
        assertEquals(updatedProduct.getProductName(), result.getProductName());
        assertEquals(updatedProduct.getPrice(), result.getPrice());

        // Verify that the product has been updated in the database
        Optional<Product> updatedProductInDb = productRepository.findById(existingProduct.getProductCode());
        assertTrue(updatedProductInDb.isPresent());
        assertEquals(updatedProduct.getProductName(), updatedProductInDb.get().getProductName());
        assertEquals(updatedProduct.getPrice(), updatedProductInDb.get().getPrice());
    }

    @Test
    public void testUpdateProductByProductCode_productNotFound() {

        String productCode = "nonExistentProduct";
        Product updatedProduct = new Product();

        // Act and Assert
        assertThrows(RuntimeException.class, () -> productService.updateProductByProductCode(productCode, updatedProduct));
    }

    @Test
    public void testGetProducts_nonEmptyList() {

        Product product1 = new Product();
        Product product2 = new Product();
        product1.setProductCode("PROD1");
        product2.setProductCode("PROD2");
        productRepository.saveAll(Arrays.asList(product1, product2));

        // Act
        List<Product> result = productService.getProducts();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetProducts_emptyList() {
        // Act and Assert
        assertThrows(RuntimeException.class, () -> productService.getProducts());
    }

    @Test
    public void testGetProductsWithPagination_success() {
        // Act and Assert
        Product product1 = new Product();
        product1.setProductCode("PROD1");
        product1.setProductName("Product 1");
        product1.setPrice(10.0);

        Product product2 = new Product();
        product2.setProductName("Product 2");
        product2.setProductCode("PROD2");
        product2.setPrice(20.0);

        Product product3 = new Product();
        product3.setProductCode("PROD3");
        product3.setProductName("Product 3");
        product3.setPrice(30.0);

        productRepository.saveAll(Arrays.asList(product1, product2, product3));

        // Act
        Page<Product> page = productService.getProductsWithPagination(0);

        // Assert
        assertNotNull(page);
        assertEquals(3, page.getContent().size());

        Page<Product> page2 = productService.getProductsWithPagination(1);
        assertEquals(0, page2.getContent().size());
    }

    @Test
    @Transactional
    public void testDeleteProductByProductCode_productExists() {

        Merchant merchant = Merchant.builder()
                .merchantCode("MDR001")
                .merchantName("Madura Selalu Buka")
                .build();

        merchantRepository.save(merchant);

        String productCode = "existingProduct";
        Product existingProduct = new Product();
        existingProduct.setProductCode(productCode);
        productService.createProduct(existingProduct, merchant.getMerchantCode());

        // Act
        productService.deleteProductByProductCode(productCode);

        // Assert
        // Verify that the product has been deleted from the database
        Optional<Product> deletedProductInDb = productRepository.getProductByProductCode(productCode);
        assertFalse(deletedProductInDb.isPresent());
    }

    @Test
    public void testDeleteProductByProductCode_productNotFound() {

        String productCode = "nonExistentUser";

        // Act and Assert
        assertThrows(RuntimeException.class, () -> productService.deleteProductByProductCode(productCode));
    }
}
