package com.skillgap.skillgap.ai;

import com.skillgap.skillgap.dto.LearningRoadmapItem;
import com.skillgap.skillgap.dto.SkillRecommendation;

import java.util.List;

public class ResumeSkillGapResponse {

    private String targetRole;
    private int totalRequiredSkills;
    private int matchedSkillsCount;
    private double matchPercentage;

    private List<String> matchedSkills;
    private List<String> missingSkills;
    private List<String> otherExtractedSkills;

    private List<SkillRecommendation> recommendations;
    private List<LearningRoadmapItem> learningRoadmap;

    public ResumeSkillGapResponse() {
    }

    public ResumeSkillGapResponse(
            String targetRole,
            int totalRequiredSkills,
            int matchedSkillsCount,
            double matchPercentage,
            List<String> matchedSkills,
            List<String> missingSkills,
            List<String> otherExtractedSkills,
            List<SkillRecommendation> recommendations,
            List<LearningRoadmapItem> learningRoadmap) {

        this.targetRole = targetRole;
        this.totalRequiredSkills = totalRequiredSkills;
        this.matchedSkillsCount = matchedSkillsCount;
        this.matchPercentage = matchPercentage;
        this.matchedSkills = matchedSkills;
        this.missingSkills = missingSkills;
        this.otherExtractedSkills = otherExtractedSkills;
        this.recommendations = recommendations;
        this.learningRoadmap = learningRoadmap;
    }

    public String getTargetRole() {
        return targetRole;
    }

    public void setTargetRole(String targetRole) {
        this.targetRole = targetRole;
    }

    public int getTotalRequiredSkills() {
        return totalRequiredSkills;
    }

    public void setTotalRequiredSkills(int totalRequiredSkills) {
        this.totalRequiredSkills = totalRequiredSkills;
    }

    public int getMatchedSkillsCount() {
        return matchedSkillsCount;
    }

    public void setMatchedSkillsCount(int matchedSkillsCount) {
        this.matchedSkillsCount = matchedSkillsCount;
    }

    public double getMatchPercentage() {
        return matchPercentage;
    }

    public void setMatchPercentage(double matchPercentage) {
        this.matchPercentage = matchPercentage;
    }

    public List<String> getMatchedSkills() {
        return matchedSkills;
    }

    public void setMatchedSkills(List<String> matchedSkills) {
        this.matchedSkills = matchedSkills;
    }

    public List<String> getMissingSkills() {
        return missingSkills;
    }

    public void setMissingSkills(List<String> missingSkills) {
        this.missingSkills = missingSkills;
    }

    public List<String> getOtherExtractedSkills() {
        return otherExtractedSkills;
    }

    public void setOtherExtractedSkills(
            List<String> otherExtractedSkills) {
        this.otherExtractedSkills = otherExtractedSkills;
    }

    public List<SkillRecommendation> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(
            List<SkillRecommendation> recommendations) {
        this.recommendations = recommendations;
    }

    public List<LearningRoadmapItem> getLearningRoadmap() {
        return learningRoadmap;
    }

    public void setLearningRoadmap(
            List<LearningRoadmapItem> learningRoadmap) {
        this.learningRoadmap = learningRoadmap;
    }
}