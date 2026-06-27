package com.example.yijing.service;

import com.example.yijing.model.AiPromptTemplate;
import com.example.yijing.model.CaseStudy;
import com.example.yijing.model.CourseLesson;
import com.example.yijing.model.Hexagram;
import com.example.yijing.model.HexagramLine;
import com.example.yijing.model.Trigram;
import com.example.yijing.vo.HexagramDetailView;
import com.example.yijing.vo.HexagramSummary;

import java.util.List;

public interface ContentService {
    List<Trigram> listTrigrams();

    Trigram getTrigram(Long id);

    Trigram saveTrigram(Trigram trigram);

    void deleteTrigram(Long id);

    List<HexagramSummary> listHexagrams(String keyword);

    HexagramDetailView getHexagramDetail(Long id);

    List<HexagramLine> listHexagramLines(Long hexagramId);

    Hexagram saveHexagram(Hexagram hexagram);

    void deleteHexagram(Long id);

    List<CaseStudy> listCases(String category);

    CaseStudy getCase(Long id);

    CaseStudy saveCase(CaseStudy caseStudy);

    void deleteCase(Long id);

    List<CourseLesson> listCourses();

    CourseLesson getCourse(Long id);

    CourseLesson saveCourse(CourseLesson courseLesson);

    void deleteCourse(Long id);

    List<AiPromptTemplate> listPrompts();

    AiPromptTemplate getPrompt(Long id);

    AiPromptTemplate savePrompt(AiPromptTemplate promptTemplate);

    void deletePrompt(Long id);
}

