package com.example.yijing.vo;

import com.example.yijing.model.Hexagram;
import com.example.yijing.model.HexagramLine;
import com.example.yijing.model.Trigram;

import java.util.List;

public record HexagramDetailView(
        Hexagram hexagram,
        Trigram upperTrigram,
        Trigram lowerTrigram,
        List<HexagramLine> lines,
        List<HexagramSummary> relatedHexagrams
) {
}

