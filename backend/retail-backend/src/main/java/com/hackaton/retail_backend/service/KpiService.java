package com.hackaton.retail_backend.service;

import com.hackaton.retail_backend.dto.KpiResponseDto;
import com.hackaton.retail_backend.dto.ProductRotationDto;
import com.hackaton.retail_backend.dto.TopProductDto;

import java.time.LocalDateTime;
import java.util.List;

public interface KpiService {
    List<TopProductDto> getTopProducts(Long storeId, LocalDateTime startDate, LocalDateTime endDate, Integer limit);
    List<ProductRotationDto> getProductRotation(Long storeId);
    KpiResponseDto getComprehensiveKpis(Long storeId, LocalDateTime startDate, LocalDateTime endDate);
}
