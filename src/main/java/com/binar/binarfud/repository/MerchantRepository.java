package com.binar.binarfud.repository;

import com.binar.binarfud.model.Merchant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, String> {

    Optional<Merchant> getMerchantByMerchantCode(String merchantCode);

    boolean existsByMerchantCode(String merchantCode);

    void deleteByMerchantCode(String merchantCode);

    @Query("SELECT m FROM Merchant m")
    Page<Merchant> getMerchantWithPagination(Pageable pageable);
}
