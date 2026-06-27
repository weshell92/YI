package com.example.yijing.controller;

import com.example.yijing.common.ApiResponse;
import com.example.yijing.dto.AiExplainRequest;
import com.example.yijing.service.AiExplainService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AiExplainController {

    private final AiExplainService aiExplainService;

    public AiExplainController(AiExplainService aiExplainService) {
        this.aiExplainService = aiExplainService;
    }

    @PostMapping("/explain")
    public ApiResponse<?> explain(@Valid @RequestBody AiExplainRequest request) {
        return ApiResponse.ok(aiExplainService.explain(request));
    }
}

