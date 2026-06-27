package com.example.yijing.service;

import com.example.yijing.dto.AiExplainRequest;
import com.example.yijing.vo.AiExplainView;

public interface AiExplainService {
    AiExplainView explain(AiExplainRequest request);
}

