package com.binar.binarfud.service.impl;

import com.binar.binarfud.model.Merchant;
import com.binar.binarfud.repository.MerchantRepository;
import com.binar.binarfud.service.IMerchantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MerchantService implements IMerchantService {

    @Autowired
    private MerchantRepository merchantRepository;

    @Override
    public Merchant createMerchant(Merchant merchant) {
        try {
            log.info("trying to create merchant");
            merchantRepository.save(merchant);
            log.info("create merchant with merchant code: {} successfully", merchant.getMerchantCode());
            return merchant;
        } catch (Exception e) {
            e.getMessage();
            log.error("create merchant with merchant code: {} failed", merchant.getMerchantCode());
            throw e;
        }
    }

    @Override
    public Merchant getMerchantByMerchantCode(String merchantCode) {
        try {
            log.info("trying to get merchant with merchant code: {}", merchantCode);
            if (!merchantRepository.existsByMerchantCode(merchantCode)) {
                throw new RuntimeException("merchant not found");
            }
            Merchant merchant = merchantRepository.getMerchantByMerchantCode(merchantCode).get();
            log.info("get merchant with merchant code: {} successfully", merchantCode);
            return merchant;
        } catch (Exception e) {
            e.getMessage();
            log.error("get merchant with merchant code: {} failed", merchantCode);
            throw e;
        }
    }

    @Override
    public Merchant updateMerchantByMerchantCode(String merchantCode, Merchant merchant) {
        try {
            log.info("trying to update merchant with merchant code: {}", merchantCode);
            if (!merchantRepository.existsByMerchantCode(merchantCode)) {
                throw new IllegalArgumentException("merchant with merchantCode " + merchantCode + " not found");
            }
            merchant.setMerchantCode(merchantCode);
            log.info("update merchant with merchant code: {} successfully", merchantCode);
            return merchantRepository.save(merchant);
        } catch (Exception e) {
            e.getMessage();
            log.error("update merchant with merchant code: {} failed", merchantCode);
            throw e;
        }
    }

    @Override
    public List<Merchant> getMerchants() {
        try {
            log.info("trying to get all merchant");
            List<Merchant> merchants = merchantRepository.findAll();
            if (merchants.isEmpty()) {
                throw new RuntimeException("merchant is empty");
            }
            log.info("get merchants successfully");
            return merchants;
        } catch (Exception e) {
            e.getMessage();
            log.error("get merchants failed");
            throw e;
        }
    }

    @Override
    public List<Merchant> getOpenMerchants() {
        try {
            log.info("trying to get open merchants");
            List<Merchant> merchants = merchantRepository.findAll();
            if (merchants.isEmpty()) {
                throw new RuntimeException("all merchants is closed");
            }
            log.info("get open merchants successfully");
            return merchants.stream()
                    .filter(Merchant::isOpen)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.getMessage();
            log.error("get open merchants failed");
            throw e;
        }
    }

    @Override
    public Page<Merchant> getMerchantsWithPagination(int page) {
        try {
            log.info("trying to get merchants with pagination");
            Page<Merchant> merchants = merchantRepository.getMerchantWithPagination(PageRequest.of(page, 3));
            log.info("get merchants with pagination successfully");
            return merchants;
        } catch (Exception e) {
            e.getMessage();
            log.error("get merchants with pagination failed");
            throw e;
        }
    }

    @Override
    @Transactional
    public void deleteMerchantByMerchantCode(String merchantCode) {
        try {
            log.info("trying to delete merchant with merchant code: {}", merchantCode);
            if (!merchantRepository.existsByMerchantCode(merchantCode)) {
                throw new RuntimeException("merchant not found");
            }
            log.info("delete merchant with merchant code: {} successfully", merchantCode);
            merchantRepository.deleteByMerchantCode(merchantCode);
        } catch (Exception e) {
            e.getMessage();
            log.error("delete merchant with merchant code: {} failed", merchantCode);
            throw e;
        }
    }
}
