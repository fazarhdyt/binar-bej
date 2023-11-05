package com.binar.binarfud.service;

import com.binar.binarfud.dto.*;
import com.binar.binarfud.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

public class EntityMapper {

    static ModelMapper modelMapper = new ModelMapper();

    public static MerchantDto merchantToMerchantDto(Merchant merchant) {
        return modelMapper.map(merchant, MerchantDto.class);
    }

    public static Page<MerchantDto> merchantToMerchantDto(Page<Merchant> page) {
        return page.map(merchant -> modelMapper.map(merchant, MerchantDto.class));
    }

    public static ProductDto productToProductDto(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }

    public static Page<ProductDto> productToProductDto(Page<Product> page) {
        return page.map(product -> modelMapper.map(product, ProductDto.class));
    }

    public static UserDto userToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public static OrderDetailDto orderDetailToOrderDto(OrderDetail orderDetail) {
        return modelMapper.map(orderDetail, OrderDetailDto.class);
    }
}
