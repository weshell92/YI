package com.example.yijing.vo;

import java.util.List;

public record HexagramSummary(
        Long id,
        Integer sequenceNo,
        String name,
        String structure,
        List<String> keywords,
        String shortDescription
) {
}

