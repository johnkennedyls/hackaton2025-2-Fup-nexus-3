package com.hackaton.retail_backend.controller;

import com.hackaton.retail_backend.dto.KpiResponseDto;
import com.hackaton.retail_backend.dto.ProductRotationDto;
import com.hackaton.retail_backend.dto.TopProductDto;
import com.hackaton.retail_backend.service.KpiService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/kpis")
@RequiredArgsConstructor
public class KpiController {

    private final KpiService kpiService;

    @GetMapping("/top-products")
    public ResponseEntity<List<TopProductDto>> getTopProducts(
            @RequestParam(required = false) Long storeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "10") Integer limit) {

        try {
            List<TopProductDto> topProducts = kpiService.getTopProducts(storeId, startDate, endDate, limit);
            return ResponseEntity.ok(topProducts);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/product-rotation")
    public ResponseEntity<List<ProductRotationDto>> getProductRotation(
            @RequestParam(required = false) Long storeId) {

        try {
            List<ProductRotationDto> rotation = kpiService.getProductRotation(storeId);
            return ResponseEntity.ok(rotation);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/comprehensive")
    public ResponseEntity<KpiResponseDto> getComprehensiveKpis(
            @RequestParam(required = false) Long storeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        try {
            KpiResponseDto kpis = kpiService.getComprehensiveKpis(storeId, startDate, endDate);
            return ResponseEntity.ok(kpis);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
