package com.binar.binarfud.service.impl;

import com.binar.binarfud.dto.ProductDto;
import com.binar.binarfud.exception.ProcessException;
import com.binar.binarfud.model.Merchant;
import com.binar.binarfud.model.Product;
import com.binar.binarfud.repository.MerchantRepository;
import com.binar.binarfud.repository.ProductRepository;
import com.binar.binarfud.service.EntityMapper;
import com.binar.binarfud.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Override
    public ProductDto createProduct(Product product, String merchantCode) {
        try {
            log.info("trying to create product");
            if (!merchantRepository.existsByMerchantCode(merchantCode)) {
                throw new ProcessException("merchant", "merchantCode", merchantCode);
            }
            Merchant merchant = merchantRepository.getMerchantByMerchantCode(merchantCode).get();
            product.setMerchant(merchant);
            productRepository.save(product);
            log.info("create product with product code: {} successfully", product.getProductCode());
            return EntityMapper.productToProductDto(product);
        } catch (Exception e) {
            log.error("create product with product code: {} failed\n", product.getProductCode() + e.getMessage());
            throw e;
        }
    }

    @Override
    public ProductDto getProductByProductCode(String productCode) {
        try {
            log.info("trying to get product with product code: {}", productCode);
            if (!productRepository.existsByProductCode(productCode)) {
                throw new ProcessException("product", "productCode", productCode);
            }
            Product product = productRepository.getProductByProductCode(productCode).get();
            log.info("get product with product code: {} successfully", productCode);
            return EntityMapper.productToProductDto(product);
        } catch (Exception e) {
            log.error("get product with product code: {} failed\n", productCode + e.getMessage());
            throw e;
        }
    }

    @Override
    public ProductDto updateProductByProductCode(String productCode, Product product) {
        try {
            log.info("trying to update product with product code: {}", productCode);
            if (!productRepository.existsByProductCode(productCode)) {
                throw new ProcessException("product", "productCode", productCode);
            }
            Product productUpdate = productRepository.getProductByProductCode(productCode).get();
            productUpdate.setProductCode(productCode);
            productUpdate.setProductName(product.getProductName() == null ? productUpdate.getProductName() : product.getProductName());
            productUpdate.setStock(product.getStock() == null ? productUpdate.getStock() : product.getStock());
            productUpdate.setPrice(product.getPrice() == null ? productUpdate.getPrice() : product.getPrice());
            productUpdate.setMerchant(product.getMerchant() == null ? productUpdate.getMerchant() : product.getMerchant());
            log.info("update product with product code: {} successfully", productCode);
            return EntityMapper.productToProductDto(productRepository.save(productUpdate));
        } catch (Exception e) {
            log.error("update product with product code: {} failed\n", productCode + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<ProductDto> getProducts() {
        try {
            log.info("trying to get all products");
            List<Product> products = productRepository.findAll().stream()
                    .filter(product -> product.getStock() > 0)
                    .collect(Collectors.toList());
            if (products.isEmpty()) {
                throw new RuntimeException("products is empty");
            }
            List<ProductDto> productsDto = new ArrayList<>();
            for (Product product : products) {
                ProductDto productDto = EntityMapper.productToProductDto(product);
                productsDto.add(productDto);
            }
            log.info("get products successfully");
            return productsDto;
        } catch (Exception e) {
            log.error("get products failed\n" + e.getMessage());
            throw e;
        }
    }

    @Override
    public Page<ProductDto> getProductsWithPagination(int page) {
        try {
            log.info("trying to get products with pagination");
            Page<Product> products = productRepository.getProductWithPagination(PageRequest.of(page, 3));
            log.info("get products with pagination successfully");
            return EntityMapper.productToProductDto(products);
        } catch (Exception e) {
            log.error("get products with pagination failed\n" + e.getMessage());
            throw e;
        }

    }

    @Override
    @Transactional
    public void deleteProductByProductCode(String productCode) {
        try {
            log.info("trying to delete product with product code: {}", productCode);
            if (!productRepository.existsByProductCode(productCode)) {
                throw new ProcessException("product", "productCode", productCode);
            }
            log.info("delete product with product code: {} successfully", productCode);
            productRepository.deleteByProductCode(productCode);
        } catch (Exception e) {
            log.error("delete product with product code: {} failed\n", productCode + e.getMessage());
            throw e;
        }
    }
}
