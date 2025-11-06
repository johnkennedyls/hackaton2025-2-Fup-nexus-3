package com.hackaton.retail_backend.dto.huggingface;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HuggingFaceRequest {
    private String inputs;

    @JsonProperty("parameters")
    private Parameters parameters;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Parameters {
        @JsonProperty("max_new_tokens")
        private Integer maxNewTokens;
        private Double temperature;
        @JsonProperty("return_full_text")
        private Boolean returnFullText;
    }
}
