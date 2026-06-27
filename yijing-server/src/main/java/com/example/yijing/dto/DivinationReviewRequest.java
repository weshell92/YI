package com.example.yijing.dto;

import jakarta.validation.constraints.NotBlank;

public record DivinationReviewRequest(@NotBlank(message = "reviewResult is required") String reviewResult) {
}

