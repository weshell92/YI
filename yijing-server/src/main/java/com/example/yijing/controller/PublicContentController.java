package com.example.yijing.controller;

import com.example.yijing.common.ApiResponse;
import com.example.yijing.service.ContentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PublicContentController {

    private final ContentService contentService;

    public PublicContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping("/trigrams")
    public ApiResponse<?> listTrigrams() {
        return ApiResponse.ok(contentService.listTrigrams());
    }

    @GetMapping("/trigrams/{id}")
    public ApiResponse<?> getTrigram(@PathVariable Long id) {
        return ApiResponse.ok(contentService.getTrigram(id));
    }

    @GetMapping("/hexagrams")
    public ApiResponse<?> listHexagrams(@RequestParam(required = false) String q) {
        return ApiResponse.ok(contentService.listHexagrams(q));
    }

    @GetMapping("/hexagrams/{id}")
    public ApiResponse<?> getHexagram(@PathVariable Long id) {
        return ApiResponse.ok(contentService.getHexagramDetail(id));
    }

    @GetMapping("/hexagrams/{id}/lines")
    public ApiResponse<?> getHexagramLines(@PathVariable Long id) {
        return ApiResponse.ok(contentService.listHexagramLines(id));
    }

    @GetMapping("/cases")
    public ApiResponse<?> listCases(@RequestParam(required = false) String category) {
        return ApiResponse.ok(contentService.listCases(category));
    }

    @GetMapping("/cases/{id}")
    public ApiResponse<?> getCase(@PathVariable Long id) {
        return ApiResponse.ok(contentService.getCase(id));
    }

    @GetMapping("/learn/courses")
    public ApiResponse<?> listCourses() {
        return ApiResponse.ok(contentService.listCourses());
    }

    @GetMapping("/learn/courses/{id}")
    public ApiResponse<?> getCourse(@PathVariable Long id) {
        return ApiResponse.ok(contentService.getCourse(id));
    }
}

