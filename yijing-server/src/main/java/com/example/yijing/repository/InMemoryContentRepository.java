package com.example.yijing.repository;

import com.example.yijing.model.AiPromptTemplate;
import com.example.yijing.model.CaseStudy;
import com.example.yijing.model.CourseLesson;
import com.example.yijing.model.DivinationRecord;
import com.example.yijing.model.Hexagram;
import com.example.yijing.model.HexagramLine;
import com.example.yijing.model.Trigram;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryContentRepository {

    private final Map<Long, Trigram> trigramStore = new ConcurrentHashMap<>();
    private final Map<Long, Hexagram> hexagramStore = new ConcurrentHashMap<>();
    private final Map<Long, List<HexagramLine>> lineStore = new ConcurrentHashMap<>();
    private final Map<Long, CaseStudy> caseStore = new ConcurrentHashMap<>();
    private final Map<Long, CourseLesson> courseStore = new ConcurrentHashMap<>();
    private final Map<Long, AiPromptTemplate> aiPromptStore = new ConcurrentHashMap<>();
    private final Map<Long, DivinationRecord> divinationStore = new ConcurrentHashMap<>();

    private final AtomicLong trigramId = new AtomicLong(9);
    private final AtomicLong hexagramId = new AtomicLong(65);
    private final AtomicLong caseId = new AtomicLong(5);
    private final AtomicLong courseId = new AtomicLong(9);
    private final AtomicLong promptId = new AtomicLong(2);
    private final AtomicLong recordId = new AtomicLong(1);

    @PostConstruct
    public void init() {
        YijingContentLibrary.trigrams().forEach(item -> trigramStore.put(item.id(), item));
        YijingContentLibrary.hexagrams().forEach(item -> hexagramStore.put(item.id(), item));
        lineStore.putAll(YijingContentLibrary.hexagramLines());
        YijingContentLibrary.cases().forEach(item -> caseStore.put(item.id(), item));
        YijingContentLibrary.courses().forEach(item -> courseStore.put(item.id(), item));
        YijingContentLibrary.prompts().forEach(item -> aiPromptStore.put(item.id(), item));
    }

    public List<Trigram> findAllTrigrams() {
        return trigramStore.values().stream()
                .sorted(Comparator.comparing(Trigram::sortOrder))
                .toList();
    }

    public Trigram findTrigram(Long id) {
        return trigramStore.get(id);
    }

    public Trigram saveTrigram(Trigram trigram) {
        Long id = trigram.id() == null ? trigramId.getAndIncrement() : trigram.id();
        Trigram saved = new Trigram(
                id,
                trigram.name(),
                trigram.symbol(),
                trigram.nature(),
                trigram.element(),
                trigram.attribute(),
                trigram.humanMeaning(),
                trigram.modernScenario(),
                trigram.plainExplanation(),
                trigram.suitableProblems(),
                trigram.commonPitfall(),
                trigram.relatedHexagramIds(),
                trigram.sortOrder() == null ? 99 : trigram.sortOrder()
        );
        trigramStore.put(id, saved);
        return saved;
    }

    public void deleteTrigram(Long id) {
        trigramStore.remove(id);
    }

    public List<Hexagram> findAllHexagrams() {
        return hexagramStore.values().stream()
                .sorted(Comparator.comparing(Hexagram::sequenceNo))
                .toList();
    }

    public Hexagram findHexagram(Long id) {
        return hexagramStore.get(id);
    }

    public Hexagram saveHexagram(Hexagram hexagram) {
        Long id = hexagram.id() == null ? hexagramId.getAndIncrement() : hexagram.id();
        Hexagram saved = new Hexagram(
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
        );
        hexagramStore.put(id, saved);
        return saved;
    }

    public void deleteHexagram(Long id) {
        hexagramStore.remove(id);
        lineStore.remove(id);
    }

    public List<HexagramLine> findHexagramLines(Long hexagramIdValue) {
        return lineStore.getOrDefault(hexagramIdValue, List.of());
    }

    public List<CaseStudy> findAllCases() {
        return caseStore.values().stream()
                .sorted(Comparator.comparing(CaseStudy::sortOrder))
                .toList();
    }

    public CaseStudy findCase(Long id) {
        return caseStore.get(id);
    }

    public CaseStudy saveCase(CaseStudy caseStudy) {
        Long id = caseStudy.id() == null ? caseId.getAndIncrement() : caseStudy.id();
        CaseStudy saved = new CaseStudy(
                id,
                caseStudy.title(),
                caseStudy.category(),
                caseStudy.background(),
                caseStudy.hexagramId(),
                caseStudy.analysis(),
                caseStudy.modernAnalysis(),
                caseStudy.advice(),
                caseStudy.sortOrder() == null ? 99 : caseStudy.sortOrder(),
                caseStudy.published() == null || caseStudy.published()
        );
        caseStore.put(id, saved);
        return saved;
    }

    public void deleteCase(Long id) {
        caseStore.remove(id);
    }

    public List<CourseLesson> findAllCourses() {
        return courseStore.values().stream()
                .sorted(Comparator.comparing(CourseLesson::orderNo))
                .toList();
    }

    public CourseLesson findCourse(Long id) {
        return courseStore.get(id);
    }

    public CourseLesson saveCourse(CourseLesson courseLesson) {
        Long id = courseLesson.id() == null ? courseId.getAndIncrement() : courseLesson.id();
        CourseLesson saved = new CourseLesson(
                id,
                courseLesson.orderNo(),
                courseLesson.title(),
                courseLesson.coreConcept(),
                courseLesson.plainExplanation(),
                courseLesson.diagram(),
                courseLesson.example(),
                courseLesson.quizQuestions()
        );
        courseStore.put(id, saved);
        return saved;
    }

    public void deleteCourse(Long id) {
        courseStore.remove(id);
    }

    public List<AiPromptTemplate> findAllPrompts() {
        return aiPromptStore.values().stream()
                .sorted(Comparator.comparing(AiPromptTemplate::id))
                .toList();
    }

    public AiPromptTemplate findPrompt(Long id) {
        return aiPromptStore.get(id);
    }

    public AiPromptTemplate savePrompt(AiPromptTemplate prompt) {
        Long id = prompt.id() == null ? promptId.getAndIncrement() : prompt.id();
        AiPromptTemplate saved = new AiPromptTemplate(
                id,
                prompt.name(),
                prompt.purpose(),
                prompt.content(),
                prompt.enabled() == null || prompt.enabled()
        );
        aiPromptStore.put(id, saved);
        return saved;
    }

    public void deletePrompt(Long id) {
        aiPromptStore.remove(id);
    }

    public DivinationRecord saveRecord(DivinationRecord record) {
        if (record.getId() == null) {
            record.setId(recordId.getAndIncrement());
            record.setCreatedAt(LocalDateTime.now());
        }
        divinationStore.put(record.getId(), record);
        return record;
    }

    public List<DivinationRecord> findAllRecords() {
        return divinationStore.values().stream()
                .sorted(Comparator.comparing(DivinationRecord::getCreatedAt).reversed())
                .toList();
    }

    public DivinationRecord findRecord(Long id) {
        return divinationStore.get(id);
    }
}
