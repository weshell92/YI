package com.example.yijing.controller;

import com.example.yijing.common.ApiResponse;
import com.example.yijing.dto.DivinationRequest;
import com.example.yijing.dto.DivinationReviewRequest;
import com.example.yijing.service.DivinationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/divination")
public class DivinationController {

    private final DivinationService divinationService;

    public DivinationController(DivinationService divinationService) {
        this.divinationService = divinationService;
    }

    @PostMapping
    public ApiResponse<?> create(@Valid @RequestBody DivinationRequest request) {
        return ApiResponse.ok(divinationService.generate(request));
    }

    @GetMapping("/records")
    public ApiResponse<?> listRecords() {
        return ApiResponse.ok(divinationService.listRecords());
    }

    @PostMapping("/{id}/review")
    public ApiResponse<?> review(@PathVariable Long id, @Valid @RequestBody DivinationReviewRequest request) {
        return ApiResponse.ok(divinationService.review(id, request.reviewResult()));
    }
}

