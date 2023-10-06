package com.binar.binarfud.service;

import com.binar.binarfud.model.Merchant;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IMerchantService {

    Merchant createMerchant(Merchant merchant);

    Merchant getMerchantByMerchantCode(String merchantCode);

    Merchant updateMerchantByMerchantCode(String merchantCode, Merchant merchant);

    List<Merchant> getMerchants();

    List<Merchant> getOpenMerchants();

    Page<Merchant> getMerchantsWithPagination(int page);

    void deleteMerchantByMerchantCode(String merchantCode);
}
