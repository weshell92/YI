package com.example.yijing.dto;

import jakarta.validation.constraints.NotBlank;

public record AiExplainRequest(
        @NotBlank(message = "question is required")
        String question,
        Long primaryHexagramId,
        Long changedHexagramId
) {
}

