package com.hackaton.retail_backend.repository;

import com.hackaton.retail_backend.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("""
        SELECT p.id, p.name, p.barcode, c.name, 
               SUM(si.quantity), SUM(si.quantity * si.unitPrice)
        FROM SaleItem si
        JOIN si.product p
        JOIN p.category c
        JOIN si.sale s
        WHERE (:storeId IS NULL OR s.store.id = :storeId)
          AND s.saleDate BETWEEN :startDate AND :endDate
        GROUP BY p.id, p.name, p.barcode, c.name
        ORDER BY SUM(si.quantity) DESC
        """)
    List<Object[]> findTopProductsByStore(
            @Param("storeId") Long storeId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("""
        SELECT SUM(s.totalAmount), COUNT(s)
        FROM Sale s
        WHERE (:storeId IS NULL OR s.store.id = :storeId)
          AND s.saleDate BETWEEN :startDate AND :endDate
        """)
    Object[] findGeneralMetricsByStore(
            @Param("storeId") Long storeId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
