package com.binar.binarfud.service;

import com.binar.binarfud.dto.MerchantDto;
import com.binar.binarfud.model.Merchant;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IMerchantService {

    MerchantDto createMerchant(Merchant merchant);

    MerchantDto getMerchantByMerchantCode(String merchantCode);

    MerchantDto updateMerchantByMerchantCode(String merchantCode, Merchant merchant);

    List<MerchantDto> getMerchants();

    List<MerchantDto> getOpenMerchants();

    Page<MerchantDto> getMerchantsWithPagination(int page);

    void deleteMerchantByMerchantCode(String merchantCode);
}
