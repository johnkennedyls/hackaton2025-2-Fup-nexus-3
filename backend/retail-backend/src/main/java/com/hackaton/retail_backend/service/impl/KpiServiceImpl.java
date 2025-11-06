package com.hackaton.retail_backend.service.impl;

import com.hackaton.retail_backend.dto.KpiResponseDto;
import com.hackaton.retail_backend.dto.ProductRotationDto;
import com.hackaton.retail_backend.dto.TopProductDto;
import com.hackaton.retail_backend.model.Store;
import com.hackaton.retail_backend.repository.*;
import com.hackaton.retail_backend.service.KpiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KpiServiceImpl implements KpiService {

    private final SaleRepository saleRepository;
    private final StoreRepository storeRepository;
    private final StockRepository stockRepository;

    @Override
    public List<TopProductDto> getTopProducts(Long storeId, LocalDateTime startDate, LocalDateTime endDate, Integer limit) {
        validateDateRange(startDate, endDate);

        List<Object[]> results = saleRepository.findTopProductsByStore(storeId, startDate, endDate);

        return results.stream()
                .limit(limit != null && limit > 0 ? limit : 10)
                .map((Object[] row) -> {
                    TopProductDto dto = new TopProductDto();
                    dto.setProductId(((Number) row[0]).longValue());
                    dto.setProductName((String) row[1]);
                    dto.setBarcode((String) row[2]);
                    dto.setCategoryName((String) row[3]);
                    dto.setTotalQuantitySold(((Number) row[4]).intValue());
                    dto.setTotalRevenue((BigDecimal) row[5]);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductRotationDto> getProductRotation(Long storeId) {
        if (storeId != null && !storeRepository.existsById(storeId)) {
            throw new IllegalArgumentException("Tienda no encontrada con ID: " + storeId);
        }

        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        List<Object[]> results = stockRepository.findProductRotationByStore(storeId, thirtyDaysAgo);

        return results.stream()
                .map((Object[] row) -> {
                    ProductRotationDto dto = new ProductRotationDto();
                    dto.setProductId(((Number) row[0]).longValue());
                    dto.setProductName((String) row[1]);
                    dto.setBarcode((String) row[2]);
                    dto.setCurrentStock(((Number) row[3]).intValue());
                    dto.setTotalSold(row[4] != null ? ((Number) row[4]).intValue() : 0);

                    // Calcular tasa de rotación
                    BigDecimal rotationRate = calculateRotationRate(dto.getCurrentStock(), dto.getTotalSold(), 30);
                    dto.setRotationRate(rotationRate);

                    // Clasificar nivel de rotación
                    dto.setRotationLevel(classifyRotationLevel(rotationRate));

                    // Calcular días hasta agotamiento
                    dto.setDaysToStockout(calculateDaysToStockout(dto.getCurrentStock(), dto.getTotalSold(), 30));

                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public KpiResponseDto getComprehensiveKpis(Long storeId, LocalDateTime startDate, LocalDateTime endDate) {
        validateDateRange(startDate, endDate);

        KpiResponseDto response = new KpiResponseDto();
        response.setCalculatedAt(LocalDateTime.now());
        response.setStoreId(storeId != null ? storeId.toString() : "ALL");
        response.setPeriodStart(startDate);
        response.setPeriodEnd(endDate);

        if (storeId != null) {
            Store store = storeRepository.findById(storeId)
                    .orElseThrow(() -> new IllegalArgumentException("Tienda no encontrada con ID: " + storeId));
            response.setStoreName(store.getName());
        } else {
            response.setStoreName("Todas las tiendas");
        }

        // Calcular métricas generales - FIX AQUÍ
        Object[] generalMetrics = saleRepository.findGeneralMetricsByStore(storeId, startDate, endDate);

        // El array tiene [totalRevenue, totalSales]
        if (generalMetrics != null && generalMetrics.length >= 2) {
            response.setTotalRevenue(generalMetrics[0] != null ? (BigDecimal) generalMetrics[0] : BigDecimal.ZERO);
            response.setTotalSales(generalMetrics[1] != null ? ((Number) generalMetrics[1]).intValue() : 0);
        } else {
            response.setTotalRevenue(BigDecimal.ZERO);
            response.setTotalSales(0);
        }

        // Top productos
        response.setTopProducts(getTopProducts(storeId, startDate, endDate, 10));

        // Rotación de productos
        response.setProductRotation(getProductRotation(storeId));

        return response;
    }


    private BigDecimal calculateRotationRate(Integer currentStock, Integer totalSold, Integer days) {
        if (currentStock == null || currentStock == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal avgDailySales = new BigDecimal(totalSold).divide(new BigDecimal(days), 2, RoundingMode.HALF_UP);
        return avgDailySales.divide(new BigDecimal(currentStock), 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100));
    }

    private String classifyRotationLevel(BigDecimal rotationRate) {
        if (rotationRate.compareTo(new BigDecimal("10")) >= 0) {
            return "ALTA";
        } else if (rotationRate.compareTo(new BigDecimal("5")) >= 0) {
            return "MEDIA";
        } else {
            return "BAJA";
        }
    }

    private Integer calculateDaysToStockout(Integer currentStock, Integer totalSold, Integer days) {
        if (totalSold == null || totalSold == 0 || currentStock == null) {
            return null;
        }

        BigDecimal avgDailySales = new BigDecimal(totalSold).divide(new BigDecimal(days), 2, RoundingMode.HALF_UP);
        if (avgDailySales.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }

        return new BigDecimal(currentStock).divide(avgDailySales, 0, RoundingMode.UP).intValue();
    }

    private void validateDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin");
        }
    }
}
