package com.example.yijing.model;

import java.util.List;

public record CourseLesson(
        Long id,
        Integer orderNo,
        String stage,
        String title,
        String overview,
        String coreConcept,
        String plainExplanation,
        List<String> termNotes,
        List<String> bodyParagraphs,
        String diagram,
        String example,
        String realWorldAnalogy,
        List<String> commonMistakes,
        List<String> followUpLinks,
        List<Long> relatedTrigramIds,
        List<Long> relatedHexagramIds,
        List<String> quizQuestions
) {
}
