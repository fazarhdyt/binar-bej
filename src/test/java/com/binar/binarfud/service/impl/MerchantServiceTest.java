package com.binar.binarfud.service.impl;

import com.binar.binarfud.dto.MerchantDto;
import com.binar.binarfud.exception.ResourceNotFoundException;
import com.binar.binarfud.model.Merchant;
import com.binar.binarfud.repository.MerchantRepository;
import com.binar.binarfud.service.EntityMapper;
import com.binar.binarfud.service.IMerchantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MerchantServiceTest {

    @InjectMocks
    private MerchantService merchantService;

    @Mock
    private MerchantRepository merchantRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateMerchant_success() {
        Merchant merchant = new Merchant();
        merchant.setMerchantCode("Merchant001");
        merchant.setMerchantName("testMerchant");

        when(merchantRepository.save(merchant)).thenReturn(merchant);

        MerchantDto result = merchantService.createMerchant(merchant);

        verify(merchantRepository, times(1)).save(merchant);
        assertNotNull(result);
        assertEquals(result, EntityMapper.merchantToMerchantDto(merchant));
    }

    @Test
    public void testGetMerchantByMerchantCode_success() {
        String merchantCode = "Merchant001";
        Merchant merchant = new Merchant();
        merchant.setMerchantCode(merchantCode);

        when(merchantRepository.existsByMerchantCode(merchantCode)).thenReturn(true);
        when(merchantRepository.getMerchantByMerchantCode(merchantCode)).thenReturn(Optional.of(merchant));

        MerchantDto result = merchantService.getMerchantByMerchantCode(merchantCode);

        assertNotNull(result);
        assertEquals(result, EntityMapper.merchantToMerchantDto(merchant));
        assertEquals(merchantCode, result.getMerchantCode());
    }

    @Test
    public void testGetMerchantByMerchantCode_merchantNotFound() {
        String merchantCode = "NonExistentMerchant";

        when(merchantRepository.existsByMerchantCode(merchantCode)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> merchantService.getMerchantByMerchantCode(merchantCode));
    }

    @Test
    public void testUpdateMerchantByMerchantCode_success() {
        String merchantCode = "Merchant001";
        Merchant existingMerchant = new Merchant();
        existingMerchant.setMerchantCode(merchantCode);

        Merchant updatedMerchant = new Merchant();
        updatedMerchant.setMerchantCode(merchantCode);
        updatedMerchant.setMerchantName("Updated Merchant");
        updatedMerchant.setMerchantLocation("Updated Location");

        when(merchantRepository.existsByMerchantCode(merchantCode)).thenReturn(true);
        when(merchantRepository.getMerchantByMerchantCode(merchantCode)).thenReturn(Optional.of(existingMerchant));
        when(merchantRepository.save(existingMerchant)).thenReturn(existingMerchant);

        MerchantDto result = merchantService.updateMerchantByMerchantCode(merchantCode, updatedMerchant);

        assertNotNull(result);
        assertEquals(merchantCode, result.getMerchantCode());
        assertEquals(updatedMerchant.getMerchantName(), "Updated Merchant");
        assertEquals(updatedMerchant.getMerchantLocation(), "Updated Location");
        assertEquals(result, EntityMapper.merchantToMerchantDto(existingMerchant));

    }

    @Test
    public void testUpdateMerchantByMerchantCode_merchantNotFound() {
        String merchantCode = "NonExistentMerchant";
        Merchant updatedMerchant = new Merchant();

        when(merchantRepository.existsByMerchantCode(merchantCode)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> merchantService.updateMerchantByMerchantCode(merchantCode, updatedMerchant));
    }

    @Test
    public void testGetOpenMerchants_success() {
        List<Merchant> merchantList;

        Merchant openMerchant1 = new Merchant();
        openMerchant1.setMerchantCode("Merchant001");
        openMerchant1.setMerchantName("testMerchant1");
        openMerchant1.setOpen(true);

        Merchant openMerchant2 = new Merchant();
        openMerchant2.setMerchantCode("Merchant002");
        openMerchant2.setMerchantName("testMerchant2");
        openMerchant2.setOpen(true);

        Merchant closedMerchant = new Merchant();
        closedMerchant.setMerchantCode("Merchant003");
        closedMerchant.setMerchantName("testMerchant3");
        closedMerchant.setOpen(false);

        merchantList = Arrays.asList(openMerchant1, openMerchant2);

        when(merchantRepository.findAll()).thenReturn(merchantList);
        when(merchantRepository.getOpenMerchant()).thenReturn(merchantList);

        List<MerchantDto> result = merchantService.getOpenMerchants();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(MerchantDto::isOpen));
    }

    @Test
    public void testGetOpenMerchants_noOpenMerchants() {
        List<Merchant> merchantList;

        Merchant closedMerchant1 = new Merchant();
        closedMerchant1.setMerchantCode("Merchant001");
        closedMerchant1.setMerchantName("testMerchant1");
        closedMerchant1.setOpen(false);

        Merchant closedMerchant2 = new Merchant();
        closedMerchant2.setMerchantCode("Merchant002");
        closedMerchant2.setMerchantName("testMerchant2");
        closedMerchant2.setOpen(false);

        merchantList = Arrays.asList(closedMerchant1, closedMerchant2);

        when(merchantRepository.findAll()).thenReturn(merchantList);
        when(merchantRepository.getOpenMerchant()).thenReturn(new ArrayList<>());

        List<MerchantDto> result = merchantService.getOpenMerchants();

        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetOpenMerchants_noMerchants() {
        when(merchantRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(RuntimeException.class, () -> merchantService.getOpenMerchants());
    }

    @Test
    public void testGetMerchants_success() {
        List<Merchant> merchantList;

        Merchant merchant1 = new Merchant();
        merchant1.setMerchantCode("Merchant001");
        Merchant merchant2 = new Merchant();
        merchant2.setMerchantCode("Merchant002");

        merchantList = Arrays.asList(merchant1, merchant2);

        when(merchantRepository.findAll()).thenReturn(merchantList);

        List<MerchantDto> result = merchantService.getMerchants();

        assertNotNull(result);
        assertEquals(2, result.size());
        for (int i = 0; i < merchantList.size(); i++) {
            assertEquals(result.get(i), EntityMapper.merchantToMerchantDto(merchantList.get(i)));
        }
    }

    @Test
    public void testGetMerchants_emptyList() {
        when(merchantRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(RuntimeException.class, () -> merchantService.getMerchants());
    }

    @Test
    public void testGetMerchantsWithPagination_success() {
        List<Merchant> merchantList;
        int page = 0;

        Merchant merchant1 = new Merchant();
        merchant1.setMerchantCode("Merchant001");
        merchant1.setMerchantName("testMerchant1");

        Merchant merchant2 = new Merchant();
        merchant2.setMerchantCode("Merchant2");
        merchant2.setMerchantName("testMerchant2");

        Merchant merchant3 = new Merchant();
        merchant3.setMerchantCode("Merchant3");
        merchant3.setMerchantName("testMerchant3");

        merchantList = Arrays.asList(merchant1, merchant2, merchant3);

        Page<Merchant> merchantPage = new PageImpl<>(merchantList);
        when(merchantRepository.getMerchantWithPagination(PageRequest.of(page, 3))).thenReturn(merchantPage);

        Page<MerchantDto> result = merchantService.getMerchantsWithPagination(page);

        assertNotNull(result);
        assertEquals(3, result.getContent().size());
        assertEquals(result.getContent().get(1), EntityMapper.merchantToMerchantDto(merchantList.get(1)));
    }

    @Test
    public void testDeleteMerchantByMerchantCode_success() {
        String merchantCode = "Merchant001";

        when(merchantRepository.existsByMerchantCode(merchantCode)).thenReturn(true);

        merchantService.deleteMerchantByMerchantCode(merchantCode);

        verify(merchantRepository, times(1)).deleteByMerchantCode(merchantCode);
    }

    @Test
    public void testDeleteMerchantByMerchantCode_merchantNotFound() {
        String merchantCode = "NonExistentMerchant";

        assertThrows(ResourceNotFoundException.class, () -> merchantService.deleteMerchantByMerchantCode(merchantCode));
    }
}
