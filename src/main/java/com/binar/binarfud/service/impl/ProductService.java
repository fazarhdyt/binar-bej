package com.binar.binarfud.service.impl;

import com.binar.binarfud.model.Merchant;
import com.binar.binarfud.model.Product;
import com.binar.binarfud.repository.MerchantRepository;
import com.binar.binarfud.repository.ProductRepository;
import com.binar.binarfud.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Override
    public Product createProduct(Product product, String merchantCode) {
        try {
            log.info("trying to create product");
            if (!merchantRepository.existsByMerchantCode(merchantCode)) {
                throw new RuntimeException("merchant not found");
            }
            Merchant merchant = merchantRepository.getMerchantByMerchantCode(merchantCode).get();
            product.setMerchant(merchant);
            productRepository.save(product);
            log.info("create product with product code: {} successfully", product.getProductCode());
            return product;
        } catch (Exception e) {
            e.getMessage();
            log.error("create product with product code: {} failed", product.getProductCode());
            throw e;
        }
    }

    @Override
    public Product getProductByProductCode(String productCode) {
        try {
            log.info("trying to get product with product code: {}", productCode);
            if (!productRepository.existsByProductCode(productCode)) {
                throw new IllegalArgumentException("product with product code " + productCode + " not found");
            }
            Product product = productRepository.getProductByProductCode(productCode).get();
            log.info("get product with product code: {} successfully", productCode);
            return product;
        } catch (Exception e) {
            e.getMessage();
            log.error("get product with product code: {} failed", productCode);
            throw e;
        }
    }

    @Override
    public Product updateProductByProductCode(String productCode, Product product) {
        try {
            log.info("trying to update product with product code: {}", productCode);
            if (!productRepository.existsByProductCode(productCode)) {
                throw new IllegalArgumentException("product with product code " + productCode + " not found");
            }
            product.setProductCode(productCode);
            log.info("update product with product code: {} successfully", productCode);
            return productRepository.save(product);
        } catch (Exception e) {
            e.getMessage();
            log.error("update product with product code: {} failed", productCode);
            throw e;
        }
    }

    @Override
    public List<Product> getProducts() {
        try {
            log.info("trying to get all products");
            List<Product> products = productRepository.findAll();
            if (products.isEmpty()) {
                throw new RuntimeException("products is empty");
            }
            log.info("get products successfully");
            return products;
        } catch (Exception e) {
            e.getMessage();
            log.error("get products failed");
            throw e;
        }
    }

    @Override
    public Page<Product> getProductsWithPagination(int page) {
        try {
            log.info("trying to get products with pagination");
            Page<Product> products = productRepository.getProductWithPagination(PageRequest.of(page, 3));
            log.info("get products with pagination successfully");
            return products;
        } catch (Exception e) {
            e.getMessage();
            log.error("get products with pagination failed");
            throw e;
        }

    }

    @Override
    @Transactional
    public void deleteProductByProductCode(String productCode) {
        try {
            log.info("trying to delete product with product code: {}", productCode);
            if (!productRepository.existsByProductCode(productCode)) {
                throw new RuntimeException("product not found");
            }
            log.info("delete product with product code: {} successfully", productCode);
            productRepository.deleteByProductCode(productCode);
        } catch (Exception e) {
            e.getMessage();
            log.error("delete product with product code: {} failed", productCode);
            throw e;
        }
    }
}
