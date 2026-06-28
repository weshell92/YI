package com.example.yijing.service.impl;

import com.example.yijing.model.AiPromptTemplate;
import com.example.yijing.model.CaseStudy;
import com.example.yijing.model.CourseLesson;
import com.example.yijing.model.Hexagram;
import com.example.yijing.model.HexagramLine;
import com.example.yijing.model.Trigram;
import com.example.yijing.repository.InMemoryContentRepository;
import com.example.yijing.service.ContentService;
import com.example.yijing.vo.HexagramDetailView;
import com.example.yijing.vo.HexagramSummary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
public class ContentServiceImpl implements ContentService {

    private final InMemoryContentRepository repository;

    public ContentServiceImpl(InMemoryContentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Trigram> listTrigrams() {
        return repository.findAllTrigrams();
    }

    @Override
    public Trigram getTrigram(Long id) {
        return repository.findTrigram(id);
    }

    @Override
    public Trigram saveTrigram(Trigram trigram) {
        return repository.saveTrigram(trigram);
    }

    @Override
    public void deleteTrigram(Long id) {
        repository.deleteTrigram(id);
    }

    @Override
    public List<HexagramSummary> listHexagrams(String keyword) {
        String query = keyword == null ? "" : keyword.trim().toLowerCase(Locale.ROOT);
        return repository.findAllHexagrams().stream()
                .filter(item -> !StringUtils.hasText(query) || matchesQuery(item, query))
                .map(this::toSummary)
                .toList();
    }

    @Override
    public HexagramDetailView getHexagramDetail(Long id) {
        Hexagram hexagram = repository.findHexagram(id);
        if (hexagram == null) {
            return null;
        }
        List<HexagramSummary> related = hexagram.relatedHexagramIds() == null ? List.of() :
                hexagram.relatedHexagramIds().stream()
                        .map(repository::findHexagram)
                        .filter(Objects::nonNull)
                        .map(this::toSummary)
                        .toList();
        return new HexagramDetailView(
                hexagram,
                repository.findTrigram(hexagram.upperTrigramId()),
                repository.findTrigram(hexagram.lowerTrigramId()),
                repository.findHexagramLines(id),
                related
        );
    }

    @Override
    public List<HexagramLine> listHexagramLines(Long hexagramId) {
        return repository.findHexagramLines(hexagramId);
    }

    @Override
    public Hexagram saveHexagram(Hexagram hexagram) {
        return repository.saveHexagram(hexagram);
    }

    @Override
    public void deleteHexagram(Long id) {
        repository.deleteHexagram(id);
    }

    @Override
    public List<CaseStudy> listCases(String category) {
        return repository.findAllCases().stream()
                .filter(item -> !StringUtils.hasText(category) || item.category().equalsIgnoreCase(category))
                .toList();
    }

    @Override
    public CaseStudy getCase(Long id) {
        return repository.findCase(id);
    }

    @Override
    public CaseStudy saveCase(CaseStudy caseStudy) {
        return repository.saveCase(caseStudy);
    }

    @Override
    public void deleteCase(Long id) {
        repository.deleteCase(id);
    }

    @Override
    public List<CourseLesson> listCourses() {
        return repository.findAllCourses();
    }

    @Override
    public CourseLesson getCourse(Long id) {
        return repository.findCourse(id);
    }

    @Override
    public CourseLesson saveCourse(CourseLesson courseLesson) {
        return repository.saveCourse(courseLesson);
    }

    @Override
    public void deleteCourse(Long id) {
        repository.deleteCourse(id);
    }

    @Override
    public List<AiPromptTemplate> listPrompts() {
        return repository.findAllPrompts();
    }

    @Override
    public AiPromptTemplate getPrompt(Long id) {
        return repository.findPrompt(id);
    }

    @Override
    public AiPromptTemplate savePrompt(AiPromptTemplate promptTemplate) {
        return repository.savePrompt(promptTemplate);
    }

    @Override
    public void deletePrompt(Long id) {
        repository.deletePrompt(id);
    }

    private HexagramSummary toSummary(Hexagram item) {
        return new HexagramSummary(
                item.id(),
                item.sequenceNo(),
                item.name(),
                item.hexagramSymbol(),
                item.hexagramCardTitle(),
                item.structure(),
                item.keywords(),
                item.shortDescription()
        );
    }

    private boolean matchesQuery(Hexagram item, String query) {
        return item.name().toLowerCase(Locale.ROOT).contains(query)
                || item.structure().toLowerCase(Locale.ROOT).contains(query)
                || item.shortDescription().toLowerCase(Locale.ROOT).contains(query)
                || item.keywords().stream().anyMatch(keyword -> keyword.toLowerCase(Locale.ROOT).contains(query));
    }
}
