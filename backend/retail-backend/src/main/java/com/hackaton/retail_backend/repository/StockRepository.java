package com.hackaton.retail_backend.repository;

import com.hackaton.retail_backend.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    @Query("""
        SELECT st.product.id, st.product.name, st.product.barcode,
               SUM(st.quantity),
               (SELECT COALESCE(SUM(si.quantity), 0)
                FROM SaleItem si
                JOIN si.sale s
                WHERE si.product.id = st.product.id
                  AND (:storeId IS NULL OR s.store.id = :storeId)
                  AND s.saleDate >= :startDate)
        FROM Stock st
        WHERE :storeId IS NULL OR st.store.id = :storeId
        GROUP BY st.product.id, st.product.name, st.product.barcode
        ORDER BY SUM(st.quantity) DESC
        """)
    List<Object[]> findProductRotationByStore(
            @Param("storeId") Long storeId,
            @Param("startDate") LocalDateTime startDate
    );
}
