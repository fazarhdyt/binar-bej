package com.binar.binarfud.service;

import com.binar.binarfud.dto.ProductDto;
import com.binar.binarfud.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductService {

    ProductDto createProduct(Product product, String merchantCode);

    ProductDto getProductByProductCode(String productCode);

    ProductDto updateProductByProductCode(String productCode, Product product);

    List<ProductDto> getProducts();

    Page<ProductDto> getProductsWithPagination(int page);

    void deleteProductByProductCode(String productCode);
}
