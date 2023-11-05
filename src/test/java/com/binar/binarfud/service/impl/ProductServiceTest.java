package com.binar.binarfud.service.impl;

import com.binar.binarfud.dto.ProductDto;
import com.binar.binarfud.exception.ResourceNotFoundException;
import com.binar.binarfud.model.Merchant;
import com.binar.binarfud.model.Product;
import com.binar.binarfud.repository.MerchantRepository;
import com.binar.binarfud.repository.ProductRepository;
import com.binar.binarfud.service.EntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private MerchantRepository merchantRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateProduct_success() {
        String merchantCode = "Merchant001";
        Merchant merchant = new Merchant();
        merchant.setMerchantCode(merchantCode);
        merchant.setMerchantName("testMerchant");

        Product product = new Product();
        product.setProductCode("Product001");
        product.setProductName("testProduct");

        when(merchantRepository.existsByMerchantCode(merchantCode)).thenReturn(true);
        when(merchantRepository.getMerchantByMerchantCode(merchantCode)).thenReturn(Optional.of(merchant));
        when(productRepository.save(product)).thenReturn(product);

        ProductDto result = productService.createProduct(product, merchantCode);

        verify(productRepository, times(1)).save(product);
        assertNotNull(result);
        assertEquals(result, EntityMapper.productToProductDto(product));
    }

    @Test
    public void testCreateProduct_merchantNotFound() {
        String merchantCode = "nonExistentMerchant";
        Product product = new Product();
        when(merchantRepository.existsByMerchantCode(merchantCode)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> productService.createProduct(product, merchantCode));
    }


    @Test
    public void testGetProductByProductCode_success() {
        String productCode = "Product001";
        Product product = new Product();
        product.setProductCode(productCode);

        when(productRepository.existsByProductCode(productCode)).thenReturn(true);
        when(productRepository.getProductByProductCode(productCode)).thenReturn(Optional.of(product));

        ProductDto result = productService.getProductByProductCode(productCode);

        assertNotNull(result);
        assertEquals(result, EntityMapper.productToProductDto(product));
        assertEquals(productCode, result.getProductCode());
    }

    @Test
    public void testGetProductByProductCode_productNotFound() {
        String productCode = "nonExistentProduct";

        when(productRepository.existsByProductCode(productCode)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductByProductCode(productCode));
    }

    @Test
    public void testUpdateProductByProductCode_success() {
        String productCode = "Product001";
        Product existingProduct = new Product();
        existingProduct.setProductCode(productCode);

        Product updatedProduct = new Product();
        updatedProduct.setProductName("Updated Product");

        when(productRepository.existsByProductCode(productCode)).thenReturn(true);
        when(productRepository.getProductByProductCode(productCode)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);

        ProductDto result = productService.updateProductByProductCode(productCode, updatedProduct);

        assertNotNull(result);
        assertEquals(productCode, result.getProductCode());
        assertEquals(result, EntityMapper.productToProductDto(existingProduct));
        assertEquals(existingProduct.getProductName(), "Updated Product");
    }

    @Test
    public void testUpdateProductByProductCode_productNotFound() {
        String productCode = "nonExistentProduct";
        Product updatedProduct = new Product();

        when(productRepository.existsByProductCode(productCode)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> productService.updateProductByProductCode(productCode, updatedProduct));
    }

    @Test
    public void testGetProducts_success() {
        List<Product> productList;

        Product product1 = new Product();
        product1.setProductCode("Product001");
        product1.setStock(10);

        Product product2 = new Product();
        product2.setProductCode("Product002");
        product2.setStock(5);

        productList = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(productList);

        List<ProductDto> result = productService.getProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
        for (int i = 0; i < productList.size(); i++) {
            assertEquals(result.get(i), EntityMapper.productToProductDto(productList.get(i)));
        }
    }

    @Test
    public void testGetProducts_emptyList() {
        when(productRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(RuntimeException.class, () -> productService.getProducts());
    }

    @Test
    public void testGetProductsWithPagination_success() {
        List<Product> productList;
        int page = 0;

        Product product1 = new Product();
        product1.setProductCode("Product001");
        product1.setStock(10);

        Product product2 = new Product();
        product2.setProductCode("Product002");
        product2.setStock(5);

        Product product3 = new Product();
        product3.setProductCode("Product003");
        product3.setStock(7);

        productList = Arrays.asList(product1, product2, product3);

        Page<Product> productPage = new PageImpl<>(productList);
        when(productRepository.getProductWithPagination(PageRequest.of(page, 3))).thenReturn(productPage);

        Page<ProductDto> result = productService.getProductsWithPagination(page);

        assertNotNull(result);
        assertEquals(3, result.getContent().size());
        assertEquals(result.getContent().get(0), EntityMapper.productToProductDto(productList.get(0)));
    }

    @Test
    @Transactional
    public void testDeleteProductByProductCode_success() {
        String productCode = "Product001";

        when(productRepository.existsByProductCode(productCode)).thenReturn(true);

        productService.deleteProductByProductCode(productCode);

        verify(productRepository, times(1)).deleteByProductCode(productCode);
    }

    @Test
    public void testDeleteProductByProductCode_productNotFound() {
        String productCode = "nonExistentProduct";

        assertThrows(ResourceNotFoundException.class, () -> productService.deleteProductByProductCode(productCode));
    }
}
