package com.hackaton.retail_backend.service.impl;

import com.hackaton.retail_backend.dto.RecommendationDto;
import com.hackaton.retail_backend.dto.huggingface.HuggingFaceRequest;
import com.hackaton.retail_backend.dto.huggingface.HuggingFaceResponse;
import com.hackaton.retail_backend.service.HuggingFaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class HuggingFaceServiceImpl implements HuggingFaceService {

    private final WebClient huggingFaceWebClient;

    @Value("${huggingface.model:mistralai/Mistral-7B-Instruct-v0.2}")
    private String model;

    @Value("${huggingface.max.tokens:200}")
    private Integer maxTokens;

    @Value("${huggingface.temperature:0.7}")
    private Double temperature;

    @Override
    public String generateMarketingMessage(RecommendationDto recommendation) {
        String prompt = buildPrompt(recommendation);

        try {
            return callHuggingFaceAPI(prompt);
        } catch (Exception e) {
            log.error("Error calling Hugging Face API: {}", e.getMessage());
            return recommendation.getRecommendation(); // Fallback al mensaje básico
        }
    }

    @Override
    public String generatePromotionalText(String productName, String actionType, Integer stock, Integer sales) {
        String prompt = buildSimplePrompt(productName, actionType, stock, sales);

        try {
            return callHuggingFaceAPI(prompt);
        } catch (Exception e) {
            log.error("Error calling Hugging Face API: {}", e.getMessage());
            return "Promoción especial en " + productName;
        }
    }

    private String callHuggingFaceAPI(String prompt) {
        HuggingFaceRequest request = HuggingFaceRequest.builder()
                .inputs(prompt)
                .parameters(HuggingFaceRequest.Parameters.builder()
                        .maxNewTokens(maxTokens)
                        .temperature(temperature)
                        .returnFullText(false)
                        .build())
                .build();

        HuggingFaceResponse[] responses = huggingFaceWebClient
                .post()
                .uri("/" + model)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(HuggingFaceResponse[].class)
                .timeout(Duration.ofSeconds(30))
                .onErrorResume(e -> {
                    log.error("Hugging Face API error: {}", e.getMessage());
                    return Mono.empty();
                })
                .block();

        if (responses != null && responses.length > 0) {
            String generatedText = responses[0].getGeneratedText();
            return cleanResponse(generatedText);
        }

        return null;
    }

    private String buildPrompt(RecommendationDto recommendation) {
        return switch (recommendation.getActionType()) {
            case "URGENT_RESTOCK" -> String.format(
                    """
                    [INST] Eres un experto en retail y marketing. Genera un mensaje de alerta URGENTE y profesional 
                    para el gerente de tienda sobre el siguiente producto que requiere reabastecimiento inmediato:
                    
                    Producto: %s
                    Talla: %s
                    Tienda: %s
                    Stock actual: %d unidades
                    Ventas últimos 30 días: %d unidades
                    
                    El mensaje debe:
                    - Ser conciso (máximo 150 palabras)
                    - Incluir el nivel de urgencia
                    - Sugerir cantidad específica a ordenar
                    - Mencionar impacto en ventas si no se reabastece
                    [/INST]
                    """,
                    recommendation.getProductName(),
                    recommendation.getSizeName(),
                    recommendation.getStoreName(),
                    recommendation.getCurrentStock(),
                    recommendation.getSoldLastMonth()
            );

            case "APPLY_DISCOUNT" -> String.format(
                    """
                    [INST] Eres un experto en marketing retail. Crea un mensaje promocional ATRACTIVO para liquidar 
                    el siguiente producto con inventario alto:
                    
                    Producto: %s
                    Stock: %d unidades
                    Categoría: %s
                    
                    El mensaje debe:
                    - Ser persuasivo y urgente
                    - Sugerir un porcentaje de descuento (20-40%%)
                    - Incluir un slogan llamativo
                    - Generar sensación de oportunidad limitada
                    - Máximo 120 palabras
                    [/INST]
                    """,
                    recommendation.getProductName(),
                    recommendation.getCurrentStock(),
                    recommendation.getCategoryName()
            );

            case "APPLY_PROMOTION" -> String.format(
                    """
                    [INST] Eres un especialista en marketing digital para retail. Diseña una campaña promocional 
                    CREATIVA para impulsar las ventas de:
                    
                    Producto: %s
                    Ventas actuales: %d unidades/mes (bajo rendimiento)
                    Stock disponible: %d unidades
                    
                    La campaña debe incluir:
                    - Un título atractivo
                    - Estrategia de promoción (2x1, bundle, descuento progresivo)
                    - Call-to-action
                    - Máximo 130 palabras
                    [/INST]
                    """,
                    recommendation.getProductName(),
                    recommendation.getSoldLastMonth(),
                    recommendation.getCurrentStock()
            );

            case "OPTIMIZE_STOCK" -> String.format(
                    """
                    [INST] Eres un consultor de optimización de inventario. Proporciona recomendaciones ESTRATÉGICAS 
                    para mejorar la rotación de:
                    
                    Producto: %s
                    Tasa de rotación actual: %.2f (muy baja)
                    Stock: %d unidades
                    
                    Incluye:
                    - Análisis del problema
                    - 3 acciones concretas para mejorar rotación
                    - Sugerencias de pricing/merchandising
                    - Máximo 150 palabras
                    [/INST]
                    """,
                    recommendation.getProductName(),
                    recommendation.getRotationRate(),
                    recommendation.getCurrentStock()
            );

            default -> String.format(
                    """
                    [INST] Genera un mensaje profesional sobre la gestión de inventario para el producto: %s. 
                    Sé breve y específico. [/INST]
                    """,
                    recommendation.getProductName()
            );
        };
    }

    private String buildSimplePrompt(String productName, String actionType, Integer stock, Integer sales) {
        return String.format(
                """
                [INST] Crea un mensaje de marketing breve (max 80 palabras) para el producto "%s".
                Acción: %s
                Stock: %d | Ventas: %d
                Debe ser persuasivo y profesional. [/INST]
                """,
                productName, actionType, stock, sales
        );
    }

    private String cleanResponse(String response) {
        if (response == null) {
            return "";
        }

        // Eliminar posibles artefactos del modelo
        response = response.trim()
                .replaceAll("\\[INST\\].*?\\[/INST\\]", "")
                .replaceAll("^\\s+|\\s+$", "")
                .replaceAll("\n{3,}", "\n\n");

        // Limitar longitud si es muy extenso
        if (response.length() > 500) {
            response = response.substring(0, 497) + "...";
        }

        return response;
    }
}
