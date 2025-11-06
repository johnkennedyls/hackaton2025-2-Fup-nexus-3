package com.hackaton.retail_backend.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class HistoricalDataDto {

    @CsvBindByName(column = "barcode", required = true)
    @NotNull
    private String barcode;

    @CsvBindByName(column = "store_id", required = true)
    @NotNull
    private Integer storeId;

    @CsvBindByName(column = "size_name", required = true)
    @NotNull
    private String sizeName;

    @CsvBindByName(column = "quantity", required = true)
    @NotNull
    @Positive
    private Integer quantity;

    @CsvBindByName(column = "unit_price", required = true)
    @NotNull
    @Positive
    private BigDecimal unitPrice;

    @CsvBindByName(column = "sale_date")
    @CsvDate("yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime saleDate;
}
