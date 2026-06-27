package com.example.yijing.service;

import com.example.yijing.dto.DivinationRequest;
import com.example.yijing.model.DivinationRecord;
import com.example.yijing.vo.DivinationResultView;

import java.util.List;

public interface DivinationService {
    DivinationResultView generate(DivinationRequest request);

    List<DivinationRecord> listRecords();

    DivinationRecord review(Long recordId, String reviewResult);
}

