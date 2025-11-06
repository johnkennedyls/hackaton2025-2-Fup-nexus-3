package com.hackaton.retail_backend.service.impl;

import com.hackaton.retail_backend.dto.RecommendationDto;
import com.hackaton.retail_backend.enums.ActionType;
import com.hackaton.retail_backend.model.Stock;
import com.hackaton.retail_backend.repository.SaleRepository;
import com.hackaton.retail_backend.repository.StockRepository;
import com.hackaton.retail_backend.service.HuggingFaceService;
import com.hackaton.retail_backend.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final StockRepository stockRepository;
    private final SaleRepository saleRepository;
    private final HuggingFaceService huggingFaceService;
    // Umbrales configurables
    private static final int CRITICAL_STOCK_THRESHOLD = 5;
    private static final int LOW_STOCK_THRESHOLD = 10;
    private static final int HIGH_STOCK_THRESHOLD = 50;
    private static final int DAYS_FOR_ANALYSIS = 30;

    @Override
    public List<RecommendationDto> generateRecommendations(Long storeId) {
        List<RecommendationDto> recommendations = new ArrayList<>();
        List<Stock> stockList = storeId != null
                ? stockRepository.findByStoreId(storeId)
                : stockRepository.findAll();

        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(DAYS_FOR_ANALYSIS);

        for (Stock stock : stockList) {
            RecommendationDto recommendation = analyzeStock(stock, thirtyDaysAgo);
            if (recommendation != null) {
                // ✅ Agregar mejora con IA
                recommendation = enhanceWithAI(recommendation);
                recommendations.add(recommendation);
            }
        }

        recommendations.sort(Comparator.comparing(RecommendationDto::getPriority));
        return recommendations;
    }

    @Override
    public List<RecommendationDto> getUrgentActions(Long storeId) {
        return generateRecommendations(storeId).stream()
                .filter(r -> "HIGH".equals(r.getSeverity()))
                .toList();
    }


    @Override
    public RecommendationDto enhanceWithAI(RecommendationDto recommendation) {
        try {
            String aiMessage = huggingFaceService.generateMarketingMessage(recommendation);
            recommendation.setAiEnhancedMessage(aiMessage != null ? aiMessage : generateAIMessage(recommendation));
        } catch (Exception e) {
            log.error("Error enhancing recommendation with AI: {}", e.getMessage());
            recommendation.setAiEnhancedMessage(generateAIMessage(recommendation));
        }
        return recommendation;
    }

    private RecommendationDto analyzeStock(Stock stock, LocalDateTime startDate) {
        Integer currentStock = stock.getQuantity();
        Long productId = stock.getProduct().getId();
        Long storeId = stock.getStore().getId();
        Long sizeId = stock.getSize().getId();

        // Obtener ventas del último mes
        Integer soldLastMonth = getSalesInPeriod(productId, storeId, sizeId, startDate);

        // Calcular tasa de rotación (ventas / stock promedio)
        BigDecimal rotationRate = calculateRotationRate(currentStock, soldLastMonth);

        // Determinar acción recomendada
        ActionAnalysis analysis = determineAction(currentStock, soldLastMonth, rotationRate);

        // Si no requiere acción, retornar null
        if (analysis.actionType == ActionType.NO_ACTION) {
            return null;
        }

        return buildRecommendation(stock, soldLastMonth, rotationRate, analysis);
    }

    private Integer getSalesInPeriod(Long productId, Long storeId, Long sizeId, LocalDateTime startDate) {
        Object result = saleRepository.findSalesQuantityByProduct(
                productId, storeId, sizeId, startDate, LocalDateTime.now()
        );

        if (result == null) {
            return 0;
        }

        return ((Number) result).intValue();
    }

    private BigDecimal calculateRotationRate(Integer currentStock, Integer soldLastMonth) {
        if (currentStock == 0 || soldLastMonth == 0) {
            return BigDecimal.ZERO;
        }

        return BigDecimal.valueOf(soldLastMonth)
                .divide(BigDecimal.valueOf(currentStock), 2, RoundingMode.HALF_UP);
    }

    private ActionAnalysis determineAction(Integer currentStock, Integer soldLastMonth, BigDecimal rotationRate) {
        ActionAnalysis analysis = new ActionAnalysis();

        // STOCK CRÍTICO (menos de 5 unidades)
        if (currentStock <= CRITICAL_STOCK_THRESHOLD && soldLastMonth > 0) {
            analysis.actionType = ActionType.URGENT_RESTOCK;
            analysis.severity = "HIGH";
            analysis.priority = 1;
            analysis.recommendation = String.format(
                    "⚠️ URGENTE: Solo quedan %d unidades y se vendieron %d en el último mes. Reabastecer inmediatamente.",
                    currentStock, soldLastMonth
            );
            return analysis;
        }

        // STOCK BAJO (menos de 10 unidades) con ventas activas
        if (currentStock <= LOW_STOCK_THRESHOLD && soldLastMonth >= 5) {
            analysis.actionType = ActionType.MODERATE_RESTOCK;
            analysis.severity = "MEDIUM";
            analysis.priority = 2;
            analysis.recommendation = String.format(
                    "📦 Stock bajo: %d unidades disponibles. Ventas recientes: %d. Considerar reabastecimiento.",
                    currentStock, soldLastMonth
            );
            return analysis;
        }

        // STOCK ALTO (más de 50 unidades) sin ventas
        if (currentStock >= HIGH_STOCK_THRESHOLD && soldLastMonth == 0) {
            analysis.actionType = ActionType.APPLY_DISCOUNT;
            analysis.severity = "HIGH";
            analysis.priority = 1;
            analysis.recommendation = String.format(
                    "💰 Inventario alto (%d unidades) sin ventas en 30 días. Aplicar descuento del 20-30%%.",
                    currentStock
            );
            return analysis;
        }

        // STOCK ALTO con ventas bajas (menos de 3 ventas en 30 días)
        if (currentStock >= HIGH_STOCK_THRESHOLD && soldLastMonth > 0 && soldLastMonth < 3) {
            analysis.actionType = ActionType.APPLY_PROMOTION;
            analysis.severity = "MEDIUM";
            analysis.priority = 3;
            analysis.recommendation = String.format(
                    "🎯 Alto inventario (%d unidades) con ventas lentas (%d unidades/mes). Crear campaña promocional.",
                    currentStock, soldLastMonth
            );
            return analysis;
        }

        // ROTACIÓN MUY BAJA (menos de 0.1) con stock moderado
        if (rotationRate.compareTo(new BigDecimal("0.1")) < 0 && currentStock >= 20) {
            analysis.actionType = ActionType.OPTIMIZE_STOCK;
            analysis.severity = "LOW";
            analysis.priority = 4;
            analysis.recommendation = String.format(
                    "📊 Rotación baja (%.2f). Revisar estrategia de precios o visibilidad del producto.",
                    rotationRate
            );
            return analysis;
        }

        // Sin acción necesaria
        analysis.actionType = ActionType.NO_ACTION;
        analysis.severity = "LOW";
        analysis.priority = 5;
        return analysis;
    }

    private RecommendationDto buildRecommendation(Stock stock, Integer soldLastMonth,
                                                  BigDecimal rotationRate, ActionAnalysis analysis) {
        RecommendationDto dto = new RecommendationDto();

        dto.setProductId(stock.getProduct().getId());
        dto.setProductName(stock.getProduct().getName());
        dto.setBarcode(stock.getProduct().getBarcode());
        dto.setCategoryName(stock.getProduct().getCategory().getName());
        dto.setStoreId(stock.getStore().getId());
        dto.setStoreName(stock.getStore().getName());
        dto.setSizeName(stock.getSize().getName());

        dto.setCurrentStock(stock.getQuantity());
        dto.setSoldLastMonth(soldLastMonth);
        dto.setRotationRate(rotationRate);

        dto.setActionType(analysis.actionType.name());
        dto.setSeverity(analysis.severity);
        dto.setPriority(analysis.priority);
        dto.setRecommendation(analysis.recommendation);

        dto.setGeneratedAt(LocalDateTime.now());

        return dto;
    }

    private String generateAIMessage(RecommendationDto recommendation) {
        // Este método ahora es el FALLBACK si Hugging Face falla
        return switch (ActionType.valueOf(recommendation.getActionType())) {
            case URGENT_RESTOCK -> String.format(
                    "🚨 Alerta Crítica: '%s' (Talla %s) requiere reabastecimiento URGENTE en %s. " +
                            "Stock actual: %d unidades. Ventas últimos 30 días: %d unidades. " +
                            "Acción recomendada: Ordenar al menos %d unidades inmediatamente.",
                    recommendation.getProductName(),
                    recommendation.getSizeName(),
                    recommendation.getStoreName(),
                    recommendation.getCurrentStock(),
                    recommendation.getSoldLastMonth(),
                    recommendation.getSoldLastMonth() * 2
            );
            case APPLY_DISCOUNT -> String.format(
                    "💡 Oportunidad de Liquidación: '%s' (Talla %s) en %s tiene %d unidades sin rotación. " +
                            "Estrategia sugerida: Lanzar promoción '¡Últimas unidades al 25%% de descuento!' " +
                            "o crear combo '2x1' para acelerar la venta.",
                    recommendation.getProductName(),
                    recommendation.getSizeName(),
                    recommendation.getStoreName(),
                    recommendation.getCurrentStock()
            );
            case APPLY_PROMOTION -> String.format(
                    "🎯 Impulso de Ventas: '%s' necesita visibilidad. " +
                            "Crea campaña: 'Descubre la comodidad de %s - Oferta especial esta semana'. " +
                            "Sugerencia: Ubicar en zona destacada de %s.",
                    recommendation.getProductName(),
                    recommendation.getProductName(),
                    recommendation.getStoreName()
            );
            default -> recommendation.getRecommendation();
        };
    }

    // Clase interna para análisis
    private static class ActionAnalysis {
        ActionType actionType;
        String severity;
        Integer priority;
        String recommendation;
    }
}
