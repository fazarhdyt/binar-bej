package com.binar.binarfud.service;

import com.binar.binarfud.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductService {

    Product createProduct(Product product, String merchantCode);

    Product getProductByProductCode(String productCode);

    Product updateProductByProductCode(String productCode, Product product);

    List<Product> getProducts();

    Page<Product> getProductsWithPagination(int page);

    void deleteProductByProductCode(String productCode);
}
