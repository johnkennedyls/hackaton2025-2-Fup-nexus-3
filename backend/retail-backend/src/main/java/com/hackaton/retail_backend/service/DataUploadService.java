package com.hackaton.retail_backend.service;

import com.hackaton.retail_backend.dto.UploadResultDto;
import org.springframework.web.multipart.MultipartFile;

public interface DataUploadService {
    UploadResultDto processFile(MultipartFile file) throws Exception;
}
