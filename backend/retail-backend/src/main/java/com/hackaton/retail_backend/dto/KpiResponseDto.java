package com.hackaton.retail_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KpiResponseDto {
    private LocalDateTime calculatedAt;
    private String storeId;
    private String storeName;
    private LocalDateTime periodStart;
    private LocalDateTime periodEnd;
    private BigDecimal totalRevenue;
    private Integer totalSales;
    private List<TopProductDto> topProducts;
    private List<ProductRotationDto> productRotation;
}
