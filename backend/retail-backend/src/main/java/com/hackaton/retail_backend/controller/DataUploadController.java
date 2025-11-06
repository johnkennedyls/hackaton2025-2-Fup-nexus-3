package com.hackaton.retail_backend.controller;

import com.hackaton.retail_backend.dto.UploadResultDto;
import com.hackaton.retail_backend.service.DataUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/data-upload")
@RequiredArgsConstructor
public class DataUploadController {

    private final DataUploadService dataUploadService;

    @PostMapping
    public ResponseEntity<UploadResultDto> uploadHistoricalData(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(new UploadResultDto(0, 0, List.of("El archivo está vacío.")));
        }
        try {
            UploadResultDto result = dataUploadService.processFile(file);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UploadResultDto(0, 0, List.of("Error al procesar el archivo: " + e.getMessage())));
        }
    }
}
