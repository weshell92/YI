package com.example.yijing.model;

import java.util.List;

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
        String suitableProblems,
        String commonPitfall,
        List<Long> relatedHexagramIds,
        Integer sortOrder
) {
}
