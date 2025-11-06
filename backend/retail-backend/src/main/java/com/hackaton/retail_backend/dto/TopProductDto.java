package com.hackaton.retail_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopProductDto {
    private Long productId;
    private String productName;
    private String barcode;
    private String categoryName;
    private Integer totalQuantitySold;
    private BigDecimal totalRevenue;
    private Integer ranking;
}
