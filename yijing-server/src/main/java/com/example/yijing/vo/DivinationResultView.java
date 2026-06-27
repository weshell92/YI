package com.example.yijing.vo;

import java.util.List;

public record DivinationResultView(
        Long recordId,
        String question,
        HexagramSummary primaryHexagram,
        List<Integer> movingLines,
        HexagramSummary changedHexagram,
        String hexagramAnalysis,
        String realityExplanation,
        List<String> riskWarnings,
        List<String> actionSuggestions,
        String observationPoints,
        String reviewPrompt
) {
}

