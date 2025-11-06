package com.hackaton.retail_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationDto {
    private Long productId;
    private String productName;
    private String barcode;
    private String categoryName;
    private Long storeId;
    private String storeName;
    private String sizeName;

    // Métricas de inventario
    private Integer currentStock;
    private Integer soldLastMonth;
    private BigDecimal rotationRate;

    // Recomendación
    private String actionType; // URGENT_RESTOCK, APPLY_DISCOUNT, MODERATE_RESTOCK
    private String severity; // HIGH, MEDIUM, LOW
    private String recommendation;
    private String aiEnhancedMessage; // Mensaje generado por IA

    // Metadatos
    private LocalDateTime generatedAt;
    private Integer priority; // 1-5 (1 = más urgente)
}
