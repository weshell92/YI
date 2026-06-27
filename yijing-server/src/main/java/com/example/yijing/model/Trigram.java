package com.example.yijing.model;

public record Trigram(
        Long id,
        String name,
        String symbol,
        String nature,
        String element,
        String attribute,
        String humanMeaning,
        String modernScenario,
        String plainExplanation,
        Integer sortOrder
) {
}

