package com.binar.binarfud.service.impl;

import com.binar.binarfud.model.Merchant;
import com.binar.binarfud.repository.MerchantRepository;
import com.binar.binarfud.service.IMerchantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MerchantServiceTest {

    @Autowired
    private IMerchantService merchantService;

    @Autowired
    private MerchantRepository merchantRepository;

    @BeforeEach
    public void setUp() {
        // Clear the database before each test
        merchantRepository.deleteAll();
    }

    @Test
    public void testCreateMerchant_success() {

        Merchant merchantToSave = new Merchant();
        merchantToSave.setMerchantCode("M123");
        merchantToSave.setMerchantName("Merchant 1");
        merchantToSave.setMerchantLocation("Location 1");
        merchantToSave.setOpen(true);

        // Act
        Merchant createdMerchant = merchantService.createMerchant(merchantToSave);

        // Assert
        assertNotNull(createdMerchant.getMerchantCode());
        assertEquals(merchantToSave.getMerchantCode(), createdMerchant.getMerchantCode());
        assertEquals(merchantToSave.getMerchantName(), createdMerchant.getMerchantName());
        assertEquals(merchantToSave.getMerchantLocation(), createdMerchant.getMerchantLocation());
        assertEquals(merchantToSave.isOpen(), createdMerchant.isOpen());
    }

    @Test
    public void testGetMerchantByMerchantCode_merchantExists() {

        String merchantCode = "M123";
        Merchant merchant = new Merchant();
        merchant.setMerchantCode(merchantCode);
        merchantService.createMerchant(merchant);

        // Act
        Merchant result = merchantService.getMerchantByMerchantCode(merchantCode);

        // Assert
        assertNotNull(result);
        assertEquals(merchantCode, result.getMerchantCode());
    }

    @Test
    public void testGetMerchantByMerchantCode_merchantNotFound() {

        String merchantCode = "NonExistentMerchantCode";

        // Act and Assert
        assertThrows(RuntimeException.class, () -> merchantService.getMerchantByMerchantCode(merchantCode));
    }

    @Test
    public void testUpdateMerchantByMerchantCode_merchantExists() {

        String merchantCode = "M123";
        Merchant existingMerchant = new Merchant();
        existingMerchant.setMerchantCode(merchantCode);
        merchantService.createMerchant(existingMerchant);

        Merchant updatedMerchant = new Merchant();
        updatedMerchant.setMerchantCode(merchantCode);
        updatedMerchant.setMerchantName("Updated Merchant");
        updatedMerchant.setMerchantLocation("Updated Location");
        updatedMerchant.setOpen(false);

        // Act
        Merchant result = merchantService.updateMerchantByMerchantCode(merchantCode, updatedMerchant);

        // Assert
        assertNotNull(result);
        assertEquals(merchantCode, result.getMerchantCode());
        assertEquals(updatedMerchant.getMerchantName(), result.getMerchantName());
        assertEquals(updatedMerchant.getMerchantLocation(), result.getMerchantLocation());
        assertEquals(updatedMerchant.isOpen(), result.isOpen());

        // Verify that the merchant has been updated in the database
        Optional<Merchant> updatedMerchantInDb = merchantRepository.getMerchantByMerchantCode(merchantCode);
        assertTrue(updatedMerchantInDb.isPresent());
        assertEquals(updatedMerchant.getMerchantName(), updatedMerchantInDb.get().getMerchantName());
        assertEquals(updatedMerchant.getMerchantLocation(), updatedMerchantInDb.get().getMerchantLocation());
        assertEquals(updatedMerchant.isOpen(), updatedMerchantInDb.get().isOpen());
    }

    @Test
    public void testUpdateMerchantByMerchantCode_merchantNotFound() {

        String merchantCode = "NonExistentMerchantCode";
        Merchant updatedMerchant = new Merchant();

        // Act and Assert
        assertThrows(RuntimeException.class, () -> merchantService.updateMerchantByMerchantCode(merchantCode, updatedMerchant));
    }

    @Test
    public void testGetOpenMerchants_merchantsExist() {

        Merchant openMerchant1 = new Merchant();
        openMerchant1.setMerchantCode("MERCH1");
        openMerchant1.setMerchantName("Open Merchant 1");
        openMerchant1.setOpen(true);

        Merchant openMerchant2 = new Merchant();
        openMerchant2.setMerchantCode("MERCH2");
        openMerchant2.setMerchantName("Open Merchant 2");
        openMerchant2.setOpen(true);

        Merchant closedMerchant = new Merchant();
        closedMerchant.setMerchantCode("MERCH3");
        closedMerchant.setMerchantName("Closed Merchant");
        closedMerchant.setOpen(false);

        merchantRepository.saveAll(Arrays.asList(openMerchant1, openMerchant2, closedMerchant));

        // Act
        List<Merchant> result = merchantService.getOpenMerchants();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(Merchant::isOpen));
    }

    @Test
    public void testGetOpenMerchants_noOpenMerchants() {

        Merchant closedMerchant1 = new Merchant();
        closedMerchant1.setMerchantCode("MERCH1");
        closedMerchant1.setMerchantName("Closed Merchant 1");
        closedMerchant1.setOpen(false);

        Merchant closedMerchant2 = new Merchant();
        closedMerchant2.setMerchantCode("MERCH2");
        closedMerchant2.setMerchantName("Closed Merchant 2");
        closedMerchant2.setOpen(false);

        merchantRepository.saveAll(Arrays.asList(closedMerchant1, closedMerchant2));

        // Act
        List<Merchant> result = merchantService.getOpenMerchants();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetOpenMerchants_noMerchants() {
        // Act and Assert
        assertThrows(RuntimeException.class, () -> merchantService.getOpenMerchants());
    }

    @Test
    public void testGetMerchants_nonEmptyList() {

        Merchant merchant1 = new Merchant();
        Merchant merchant2 = new Merchant();
        merchant1.setMerchantCode("MERCH001");
        merchant2.setMerchantCode("MERCH002");
        merchantService.createMerchant(merchant1);
        merchantService.createMerchant(merchant2);

        // Act
        List<Merchant> result = merchantService.getMerchants();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetMerchants_emptyList() {
        // Act and Assert
        assertThrows(RuntimeException.class, () -> merchantService.getMerchants());
    }

    @Test
    public void testGetMerchantsWithPagination_success() {
        // Act and Assert
        Merchant merchant1 = new Merchant();
        merchant1.setMerchantCode("MERCH1");
        merchant1.setMerchantName("Merchant 1");
//        merchant1.setPrice(10.0);

        Merchant merchant2 = new Merchant();
        merchant2.setMerchantCode("MERCH2");
        merchant2.setMerchantName("Merchant 2");
//        merchant2.setPrice(20.0);

        Merchant product3 = new Merchant();
        product3.setMerchantCode("MERCH3");
        product3.setMerchantName("Merchant 3");
//        product3.setPrice(30.0);

        merchantRepository.saveAll(Arrays.asList(merchant1, merchant2, product3));

        // Act
        Page<Merchant> page = merchantService.getMerchantsWithPagination(0);

        // Assert
        assertNotNull(page);
        assertEquals(3, page.getContent().size());

        Page<Merchant> page2 = merchantService.getMerchantsWithPagination(1);
        assertEquals(0, page2.getContent().size());
    }

    @Test
    public void testDeleteMerchantByMerchantCode_merchantExists() {

        String merchantCode = "M123";
        Merchant existingMerchant = new Merchant();
        existingMerchant.setMerchantCode(merchantCode);
        merchantService.createMerchant(existingMerchant);

        // Act
        merchantService.deleteMerchantByMerchantCode(merchantCode);

        // Assert
        Optional<Merchant> deletedMerchant = merchantRepository.getMerchantByMerchantCode(merchantCode);
        assertFalse(deletedMerchant.isPresent());
    }

    @Test
    public void testDeleteMerchantByMerchantCode_merchantNotFound() {

        String merchantCode = "NonExistentMerchantCode";

        // Act and Assert
        assertThrows(RuntimeException.class, () -> merchantService.deleteMerchantByMerchantCode(merchantCode));
    }
}
