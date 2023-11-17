package com.binar.binarfud.service.impl;

import com.binar.binarfud.dto.MerchantDto;
import com.binar.binarfud.exception.ResourceNotFoundException;
import com.binar.binarfud.model.Merchant;
import com.binar.binarfud.repository.MerchantRepository;
import com.binar.binarfud.service.EntityMapper;
import com.binar.binarfud.service.IMerchantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MerchantService implements IMerchantService {

    @Autowired
    private MerchantRepository merchantRepository;

    @Override
    @Transactional
    public MerchantDto createMerchant(Merchant merchant) {
        try {
            log.info("trying to create merchant");
            merchantRepository.save(merchant);
            log.info("create merchant with merchant code: {} successfully", merchant.getMerchantCode());
            return EntityMapper.merchantToMerchantDto(merchant);
        } catch (Exception e) {
            log.error("create merchant with merchant code: {} failed\n", merchant.getMerchantCode() + e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public MerchantDto getMerchantByMerchantCode(String merchantCode) {
        try {
            log.info("trying to get merchant with merchant code: {}", merchantCode);
            if (!merchantRepository.existsByMerchantCode(merchantCode)) {
                throw new ResourceNotFoundException("merchant", "merchantCode", merchantCode);
            }
            Merchant merchant = merchantRepository.getMerchantByMerchantCode(merchantCode).get();
            log.info("get merchant with merchant code: {} successfully", merchantCode);
            return EntityMapper.merchantToMerchantDto(merchant);
        } catch (Exception e) {
            log.error("get merchant with merchant code: {} failed\n", merchantCode + e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public MerchantDto updateMerchantByMerchantCode(String merchantCode, Merchant merchant) {
        try {
            log.info("trying to update merchant with merchant code: {}", merchantCode);
            if (!merchantRepository.existsByMerchantCode(merchantCode)) {
                throw new ResourceNotFoundException("merchant", "merchantCode", merchantCode);
            }
            merchant.setMerchantCode(merchantCode);
            Merchant merchantUpdate = merchantRepository.getMerchantByMerchantCode(merchantCode).get();
            merchantUpdate.setMerchantName(merchant.getMerchantName() == null ? merchantUpdate.getMerchantName() : merchant.getMerchantName());
            merchantUpdate.setMerchantLocation(merchant.getMerchantLocation() == null ? merchantUpdate.getMerchantLocation() : merchant.getMerchantLocation());
            merchantUpdate.setOpen(merchant.getOpen() == null ? merchantUpdate.getOpen() : merchant.getOpen());
            log.info("update merchant with merchant code: {} successfully", merchantCode);
            return EntityMapper.merchantToMerchantDto(merchantRepository.save(merchantUpdate));
        } catch (Exception e) {
            log.error("update merchant with merchant code: {} failed\n", merchantCode + e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<MerchantDto> getMerchants() {
        try {
            log.info("trying to get all merchant");
            List<Merchant> merchants = merchantRepository.findAll();
            if (merchants.isEmpty()) {
                throw new RuntimeException("merchant is empty");
            }
            List<MerchantDto> merchantsDto = new ArrayList<>();
            for (Merchant merchant : merchants) {
                MerchantDto merchantDto = EntityMapper.merchantToMerchantDto(merchant);
                merchantsDto.add(merchantDto);
            }
            log.info("get merchants successfully");
            return merchantsDto;
        } catch (Exception e) {
            log.error("get merchants failed\n" + e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<MerchantDto> getOpenMerchants() {
        try {
            log.info("trying to get open merchants");
            if (merchantRepository.findAll().isEmpty()) {
                throw new RuntimeException("all merchants is closed");
            }
            List<Merchant> merchants = merchantRepository.getOpenMerchant();
            List<MerchantDto> merchantsDto = new ArrayList<>();
            for (Merchant merchant : merchants) {
                MerchantDto merchantDto = EntityMapper.merchantToMerchantDto(merchant);
                merchantsDto.add(merchantDto);
            }
            log.info("get open merchants successfully");
            return merchantsDto;
        } catch (Exception e) {
            log.error("get open merchants failed\n" + e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MerchantDto> getMerchantsWithPagination(int page) {
        try {
            log.info("trying to get merchants with pagination");
            Page<Merchant> merchants = merchantRepository.getMerchantWithPagination(PageRequest.of(page, 3));
            log.info("get merchants with pagination successfully");
            return EntityMapper.merchantToMerchantDto(merchants);
        } catch (Exception e) {
            log.error("get merchants with pagination failed\n" + e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void deleteMerchantByMerchantCode(String merchantCode) {
        try {
            log.info("trying to delete merchant with merchant code: {}", merchantCode);
            if (!merchantRepository.existsByMerchantCode(merchantCode)) {
                throw new ResourceNotFoundException("merchant", "merchantCode", merchantCode);
            }
            log.info("delete merchant with merchant code: {} successfully", merchantCode);
            merchantRepository.deleteByMerchantCode(merchantCode);
        } catch (Exception e) {
            log.error("delete merchant with merchant code: {} failed\n", merchantCode + e.getMessage());
            throw e;
        }
    }
}
