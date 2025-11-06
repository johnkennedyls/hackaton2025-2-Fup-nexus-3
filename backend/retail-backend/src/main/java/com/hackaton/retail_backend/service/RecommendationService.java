package com.hackaton.retail_backend.service;

import com.hackaton.retail_backend.dto.RecommendationDto;

import java.util.List;

public interface RecommendationService {
    List<RecommendationDto> generateRecommendations(Long storeId);
    List<RecommendationDto> getUrgentActions(Long storeId);
    RecommendationDto enhanceWithAI(RecommendationDto recommendation);
}
