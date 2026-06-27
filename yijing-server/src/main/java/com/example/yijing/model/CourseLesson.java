package com.example.yijing.model;

import java.util.List;

public record CourseLesson(
        Long id,
        Integer orderNo,
        String title,
        String coreConcept,
        String plainExplanation,
        String diagram,
        String example,
        List<String> quizQuestions
) {
}

