package com.hackaton.retail_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class UploadResultDto {
    private final int successfulRecords;
    private final int failedRecords;
    private final List<String> errors;
}
