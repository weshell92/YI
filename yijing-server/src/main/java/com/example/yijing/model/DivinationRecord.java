package com.example.yijing.model;

import java.time.LocalDateTime;
import java.util.List;

public class DivinationRecord {
    private Long id;
    private Long userId;
    private String question;
    private String method;
    private Long originalHexagramId;
    private Long changedHexagramId;
    private List<Integer> movingLines;
    private String analysis;
    private String realityExplanation;
    private List<String> riskWarnings;
    private List<String> actionSuggestions;
    private String observationPoints;
    private String reviewResult;
    private LocalDateTime createdAt;

    public DivinationRecord() {
    }

    public DivinationRecord(Long id, Long userId, String question, String method, Long originalHexagramId,
                            Long changedHexagramId, List<Integer> movingLines, String analysis,
                            String realityExplanation, List<String> riskWarnings, List<String> actionSuggestions,
                            String observationPoints, String reviewResult, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.question = question;
        this.method = method;
        this.originalHexagramId = originalHexagramId;
        this.changedHexagramId = changedHexagramId;
        this.movingLines = movingLines;
        this.analysis = analysis;
        this.realityExplanation = realityExplanation;
        this.riskWarnings = riskWarnings;
        this.actionSuggestions = actionSuggestions;
        this.observationPoints = observationPoints;
        this.reviewResult = reviewResult;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Long getOriginalHexagramId() {
        return originalHexagramId;
    }

    public void setOriginalHexagramId(Long originalHexagramId) {
        this.originalHexagramId = originalHexagramId;
    }

    public Long getChangedHexagramId() {
        return changedHexagramId;
    }

    public void setChangedHexagramId(Long changedHexagramId) {
        this.changedHexagramId = changedHexagramId;
    }

    public List<Integer> getMovingLines() {
        return movingLines;
    }

    public void setMovingLines(List<Integer> movingLines) {
        this.movingLines = movingLines;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getRealityExplanation() {
        return realityExplanation;
    }

    public void setRealityExplanation(String realityExplanation) {
        this.realityExplanation = realityExplanation;
    }

    public List<String> getRiskWarnings() {
        return riskWarnings;
    }

    public void setRiskWarnings(List<String> riskWarnings) {
        this.riskWarnings = riskWarnings;
    }

    public List<String> getActionSuggestions() {
        return actionSuggestions;
    }

    public void setActionSuggestions(List<String> actionSuggestions) {
        this.actionSuggestions = actionSuggestions;
    }

    public String getObservationPoints() {
        return observationPoints;
    }

    public void setObservationPoints(String observationPoints) {
        this.observationPoints = observationPoints;
    }

    public String getReviewResult() {
        return reviewResult;
    }

    public void setReviewResult(String reviewResult) {
        this.reviewResult = reviewResult;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

