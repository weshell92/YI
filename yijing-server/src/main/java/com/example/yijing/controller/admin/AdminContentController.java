package com.example.yijing.controller.admin;

import com.example.yijing.common.ApiResponse;
import com.example.yijing.model.AiPromptTemplate;
import com.example.yijing.model.CaseStudy;
import com.example.yijing.model.CourseLesson;
import com.example.yijing.model.Hexagram;
import com.example.yijing.model.Trigram;
import com.example.yijing.service.ContentService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminContentController {

    private final ContentService contentService;

    public AdminContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping("/trigrams")
    public ApiResponse<?> listTrigrams() {
        return ApiResponse.ok(contentService.listTrigrams());
    }

    @PostMapping("/trigrams")
    public ApiResponse<?> createTrigram(@RequestBody Trigram trigram) {
        return ApiResponse.ok(contentService.saveTrigram(trigram));
    }

    @PutMapping("/trigrams/{id}")
    public ApiResponse<?> updateTrigram(@PathVariable Long id, @RequestBody Trigram trigram) {
        return ApiResponse.ok(contentService.saveTrigram(new Trigram(
                id,
                trigram.name(),
                trigram.symbol(),
                trigram.nature(),
                trigram.element(),
                trigram.attribute(),
                trigram.humanMeaning(),
                trigram.modernScenario(),
                trigram.plainExplanation(),
                trigram.sortOrder()
        )));
    }

    @DeleteMapping("/trigrams/{id}")
    public ApiResponse<?> deleteTrigram(@PathVariable Long id) {
        contentService.deleteTrigram(id);
        return ApiResponse.ok(null, "deleted");
    }

    @GetMapping("/hexagrams")
    public ApiResponse<?> listHexagrams() {
        return ApiResponse.ok(contentService.listHexagrams(null));
    }

    @PostMapping("/hexagrams")
    public ApiResponse<?> createHexagram(@RequestBody Hexagram hexagram) {
        return ApiResponse.ok(contentService.saveHexagram(hexagram));
    }

    @PutMapping("/hexagrams/{id}")
    public ApiResponse<?> updateHexagram(@PathVariable Long id, @RequestBody Hexagram hexagram) {
        return ApiResponse.ok(contentService.saveHexagram(new Hexagram(
                id,
                hexagram.sequenceNo(),
                hexagram.name(),
                hexagram.upperTrigramId(),
                hexagram.lowerTrigramId(),
                hexagram.structure(),
                hexagram.keywords(),
                hexagram.shortDescription(),
                hexagram.guaCi(),
                hexagram.tuanCi(),
                hexagram.xiangCi(),
                hexagram.plainExplanation(),
                hexagram.situationAnalysis(),
                hexagram.advice(),
                hexagram.careerExplanation(),
                hexagram.loveExplanation(),
                hexagram.relationExplanation(),
                hexagram.businessExplanation(),
                hexagram.commonMistakes(),
                hexagram.relatedHexagramIds()
        )));
    }

    @DeleteMapping("/hexagrams/{id}")
    public ApiResponse<?> deleteHexagram(@PathVariable Long id) {
        contentService.deleteHexagram(id);
        return ApiResponse.ok(null, "deleted");
    }

    @GetMapping("/cases")
    public ApiResponse<?> listCases() {
        return ApiResponse.ok(contentService.listCases(null));
    }

    @PostMapping("/cases")
    public ApiResponse<?> createCase(@RequestBody CaseStudy caseStudy) {
        return ApiResponse.ok(contentService.saveCase(caseStudy));
    }

    @PutMapping("/cases/{id}")
    public ApiResponse<?> updateCase(@PathVariable Long id, @RequestBody CaseStudy caseStudy) {
        return ApiResponse.ok(contentService.saveCase(new CaseStudy(
                id,
                caseStudy.title(),
                caseStudy.category(),
                caseStudy.background(),
                caseStudy.hexagramId(),
                caseStudy.analysis(),
                caseStudy.modernAnalysis(),
                caseStudy.advice(),
                caseStudy.sortOrder(),
                caseStudy.published()
        )));
    }

    @DeleteMapping("/cases/{id}")
    public ApiResponse<?> deleteCase(@PathVariable Long id) {
        contentService.deleteCase(id);
        return ApiResponse.ok(null, "deleted");
    }

    @GetMapping("/course")
    public ApiResponse<?> listCourses() {
        return ApiResponse.ok(contentService.listCourses());
    }

    @PostMapping("/course")
    public ApiResponse<?> createCourse(@RequestBody CourseLesson courseLesson) {
        return ApiResponse.ok(contentService.saveCourse(courseLesson));
    }

    @PutMapping("/course/{id}")
    public ApiResponse<?> updateCourse(@PathVariable Long id, @RequestBody CourseLesson courseLesson) {
        return ApiResponse.ok(contentService.saveCourse(new CourseLesson(
                id,
                courseLesson.orderNo(),
                courseLesson.title(),
                courseLesson.coreConcept(),
                courseLesson.plainExplanation(),
                courseLesson.diagram(),
                courseLesson.example(),
                courseLesson.quizQuestions()
        )));
    }

    @DeleteMapping("/course/{id}")
    public ApiResponse<?> deleteCourse(@PathVariable Long id) {
        contentService.deleteCourse(id);
        return ApiResponse.ok(null, "deleted");
    }

    @GetMapping("/ai/prompts")
    public ApiResponse<?> listPrompts() {
        return ApiResponse.ok(contentService.listPrompts());
    }

    @PostMapping("/ai/prompts")
    public ApiResponse<?> createPrompt(@RequestBody AiPromptTemplate promptTemplate) {
        return ApiResponse.ok(contentService.savePrompt(promptTemplate));
    }

    @PutMapping("/ai/prompts/{id}")
    public ApiResponse<?> updatePrompt(@PathVariable Long id, @RequestBody AiPromptTemplate promptTemplate) {
        return ApiResponse.ok(contentService.savePrompt(new AiPromptTemplate(
                id,
                promptTemplate.name(),
                promptTemplate.purpose(),
                promptTemplate.content(),
                promptTemplate.enabled()
        )));
    }

    @DeleteMapping("/ai/prompts/{id}")
    public ApiResponse<?> deletePrompt(@PathVariable Long id) {
        contentService.deletePrompt(id);
        return ApiResponse.ok(null, "deleted");
    }
}

