package com.hackaton.retail_backend.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hackaton.retail_backend.dto.HistoricalDataDto;
import com.hackaton.retail_backend.dto.UploadResultDto;
import com.hackaton.retail_backend.model.*;
import com.hackaton.retail_backend.repository.*;
import com.hackaton.retail_backend.service.DataUploadService;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataUploadServiceImpl implements DataUploadService {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final SizeRepository sizeRepository;
    private final SaleRepository saleRepository;

    @Override
    @Transactional
    public UploadResultDto processFile(MultipartFile file) throws Exception {
        List<HistoricalDataDto> records = parseFile(file);
        List<String> errors = new ArrayList<>();
        int successCount = 0;

        for (int i = 0; i < records.size(); i++) {
            HistoricalDataDto dto = records.get(i);
            int rowNum = i + 2; // Asumiendo cabecera

            Optional<Product> productOpt = productRepository.findByBarcode(dto.getBarcode());
            Optional<Store> storeOpt = storeRepository.findById(Long.valueOf(dto.getStoreId()));
            Optional<Size> sizeOpt = sizeRepository.findByName(dto.getSizeName());

            if (productOpt.isEmpty()) {
                errors.add("Fila " + rowNum + ": Producto con código de barras '" + dto.getBarcode() + "' no encontrado.");
                continue;
            }
            if (storeOpt.isEmpty()) {
                errors.add("Fila " + rowNum + ": Tienda con ID '" + dto.getStoreId() + "' no encontrada.");
                continue;
            }
            if (sizeOpt.isEmpty()) {
                errors.add("Fila " + rowNum + ": Talla con nombre '" + dto.getSizeName() + "' no encontrada.");
                continue;
            }

            saveSale(dto, productOpt.get(), storeOpt.get(), sizeOpt.get());
            successCount++;
        }
        return new UploadResultDto(successCount, errors.size(), errors);
    }

    private void saveSale(HistoricalDataDto dto, Product product, Store store, Size size) {
        Sale sale = new Sale();
        sale.setStore(store);
        sale.setSaleDate(dto.getSaleDate() != null ? dto.getSaleDate() : java.time.LocalDateTime.now());

        SaleItem item = new SaleItem();
        item.setSale(sale);
        item.setProduct(product);
        item.setSize(size);
        item.setQuantity(dto.getQuantity());
        item.setUnitPrice(dto.getUnitPrice());

        sale.setItems(List.of(item));

        // Calcular el total manualmente
        BigDecimal totalAmount = dto.getUnitPrice().multiply(new BigDecimal(dto.getQuantity()));
        sale.setTotalAmount(totalAmount);

        saleRepository.save(sale);
    }

    private List<HistoricalDataDto> parseFile(MultipartFile file) throws Exception {
        String contentType = file.getContentType();
        if (Objects.equals(contentType, "application/json")) {
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            return mapper.readValue(file.getInputStream(), new TypeReference<>() {});
        } else if (Objects.equals(contentType, "text/csv")) {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                return new CsvToBeanBuilder<HistoricalDataDto>(reader)
                        .withType(HistoricalDataDto.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build()
                        .parse();
            }
        }
        throw new IllegalArgumentException("Formato de archivo no soportado. Use CSV o JSON.");
    }
}
