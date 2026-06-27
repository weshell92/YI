package com.example.yijing.model;

public record CaseStudy(
        Long id,
        String title,
        String category,
        String background,
        Long hexagramId,
        String analysis,
        String modernAnalysis,
        String advice,
        Integer sortOrder,
        Boolean published
) {
}

