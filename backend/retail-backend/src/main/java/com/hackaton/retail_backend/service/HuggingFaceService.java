package com.hackaton.retail_backend.service;

import com.hackaton.retail_backend.dto.RecommendationDto;

public interface HuggingFaceService {
    String generateMarketingMessage(RecommendationDto recommendation);
    String generatePromotionalText(String productName, String actionType, Integer stock, Integer sales);
}
