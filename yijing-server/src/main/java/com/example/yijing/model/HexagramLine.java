package com.example.yijing.model;

public record HexagramLine(
        Long id,
        Long hexagramId,
        Integer lineNo,
        String lineName,
        String originalText,
        String xiangOriginalText,
        String plainExplanation,
        String modernMeaning,
        String advice
) {
}
