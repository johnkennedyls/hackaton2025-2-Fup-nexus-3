package com.hackaton.retail_backend.repository;

import com.hackaton.retail_backend.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("""
        SELECT COALESCE(SUM(si.quantity), 0)
        FROM Sale s
        JOIN s.items si
        WHERE si.product.id = :productId
        AND s.store.id = :storeId
        AND si.size.id = :sizeId
        AND s.saleDate BETWEEN :startDate AND :endDate
        """)
    Object findSalesQuantityByProduct(
            @Param("productId") Long productId,
            @Param("storeId") Long storeId,
            @Param("sizeId") Long sizeId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

}
