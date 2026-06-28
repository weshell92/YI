package com.example.yijing.model;

import java.util.List;

public record Hexagram(
        Long id,
        Integer sequenceNo,
        String name,
        Long upperTrigramId,
        Long lowerTrigramId,
        String hexagramSymbol,
        String hexagramCardTitle,
        String structure,
        List<String> keywords,
        String shortDescription,
        String guaCi,
        String tuanCi,
        String xiangCi,
        String daXiang,
        String wenYan,
        String xuGua,
        String zaGua,
        String shuoGua,
        String xiCiShang,
        String xiCiXia,
        String plainExplanation,
        String situationAnalysis,
        String advice,
        String careerExplanation,
        String loveExplanation,
        String relationExplanation,
        String businessExplanation,
        String commonMistakes,
        List<Long> relatedHexagramIds
) {
}
