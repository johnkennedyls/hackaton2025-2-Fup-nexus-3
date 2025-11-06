package com.hackaton.retail_backend.dto.huggingface;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class HuggingFaceResponse {
    @JsonProperty("generated_text")
    private String generatedText;
}
