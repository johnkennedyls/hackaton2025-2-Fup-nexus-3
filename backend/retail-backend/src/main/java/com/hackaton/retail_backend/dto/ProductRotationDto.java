package com.hackaton.retail_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRotationDto {
    private Long productId;
    private String productName;
    private String barcode;
    private Integer currentStock;
    private Integer totalSold;
    private BigDecimal rotationRate;
    private String rotationLevel; // "ALTA", "MEDIA", "BAJA"
    private Integer daysToStockout;
}
