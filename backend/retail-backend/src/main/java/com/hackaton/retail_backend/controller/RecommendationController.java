package com.hackaton.retail_backend.controller;

import com.hackaton.retail_backend.dto.RecommendationDto;
import com.hackaton.retail_backend.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/actions")
    public ResponseEntity<List<RecommendationDto>> getRecommendations(
            @RequestParam(required = false) Long storeId) {
        List<RecommendationDto> recommendations = recommendationService.generateRecommendations(storeId);
        return ResponseEntity.ok(recommendations);
    }

    @GetMapping("/urgent")
    public ResponseEntity<List<RecommendationDto>> getUrgentActions(
            @RequestParam(required = false) Long storeId) {
        List<RecommendationDto> urgentActions = recommendationService.getUrgentActions(storeId);
        return ResponseEntity.ok(urgentActions);
    }

    @PostMapping("/enhance/{productId}")
    public ResponseEntity<RecommendationDto> enhanceWithAI(
            @PathVariable Long productId,
            @RequestBody RecommendationDto recommendation) {
        RecommendationDto enhanced = recommendationService.enhanceWithAI(recommendation);
        return ResponseEntity.ok(enhanced);
    }
}
