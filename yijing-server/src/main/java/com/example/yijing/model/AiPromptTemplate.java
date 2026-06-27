package com.example.yijing.model;

public record AiPromptTemplate(
        Long id,
        String name,
        String purpose,
        String content,
        Boolean enabled
) {
}

