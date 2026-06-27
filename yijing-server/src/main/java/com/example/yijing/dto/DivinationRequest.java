package com.example.yijing.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record DivinationRequest(
        @NotBlank(message = "question is required")
        String question,
        String method,
        Integer luckyNumber,
        Long selectedHexagramId,
        List<Integer> movingLines
) {
}

