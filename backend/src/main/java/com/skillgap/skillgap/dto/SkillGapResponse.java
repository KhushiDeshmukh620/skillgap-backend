package com.skillgap.skillgap.dto;

import com.skillgap.skillgap.entity.Skill;

import java.util.List;
import java.util.Set;

public class SkillGapResponse {

    private String userName;
    private String targetRole;
    private int totalRequiredSkills;
    private int matchedSkills;
    private int missingSkills;
    private double matchPercentage;
    private double proficiencyScore;
    private Set<Skill> skillGap;
    private Set<Skill> matchedSkillList;
    private List<SkillRecommendation> recommendations;
    private List<ProficiencyGap> proficiencyGaps;
    private List<LearningRoadmapItem> learningRoadmap;

    public SkillGapResponse() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public int getMatchedSkills() {
        return matchedSkills;
    }

    public void setMatchedSkills(int matchedSkills) {
        this.matchedSkills = matchedSkills;
    }

    public int getMissingSkills() {
        return missingSkills;
    }

    public void setMissingSkills(int missingSkills) {
        this.missingSkills = missingSkills;
    }

    public double getMatchPercentage() {
        return matchPercentage;
    }

    public void setMatchPercentage(double matchPercentage) {
        this.matchPercentage = matchPercentage;
    }

    public double getProficiencyScore() {
        return proficiencyScore;
    }

    public void setProficiencyScore(double proficiencyScore) {
        this.proficiencyScore = proficiencyScore;
    }

    public Set<Skill> getSkillGap() {
        return skillGap;
    }

    public void setSkillGap(Set<Skill> skillGap) {
        this.skillGap = skillGap;
    }

    public Set<Skill> getMatchedSkillList() {
        return matchedSkillList;
    }

    public void setMatchedSkillList(Set<Skill> matchedSkillList) {
        this.matchedSkillList = matchedSkillList;
    }

    public List<SkillRecommendation> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<SkillRecommendation> recommendations) {
        this.recommendations = recommendations;
    }

    public List<ProficiencyGap> getProficiencyGaps() {
        return proficiencyGaps;
    }

    public void setProficiencyGaps(List<ProficiencyGap> proficiencyGaps) {
        this.proficiencyGaps = proficiencyGaps;
    }

    public List<LearningRoadmapItem> getLearningRoadmap() {
        return learningRoadmap;
    }

    public void setLearningRoadmap(List<LearningRoadmapItem> learningRoadmap) {
        this.learningRoadmap = learningRoadmap;
    }
}