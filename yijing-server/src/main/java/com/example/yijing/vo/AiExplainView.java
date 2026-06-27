package com.example.yijing.vo;

import java.util.List;

public record AiExplainView(
        String restatedQuestion,
        String primaryExplanation,
        String movingLineExplanation,
        String changedHexagramExplanation,
        String situationAnalysis,
        List<String> riskWarnings,
        List<String> actionSuggestions,
        String observationPoints,
        String reviewSuggestion
) {
}
