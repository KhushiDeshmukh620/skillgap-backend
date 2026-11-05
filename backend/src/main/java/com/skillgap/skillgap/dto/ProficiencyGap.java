package com.skillgap.skillgap.dto;

import com.skillgap.skillgap.entity.ProficiencyLevel;

public class ProficiencyGap {

    private Long skillId;
    private String skillName;
    private ProficiencyLevel currentLevel;
    private ProficiencyLevel requiredLevel;
    private String status;

    public ProficiencyGap() {
    }

    public ProficiencyGap(
            Long skillId,
            String skillName,
            ProficiencyLevel currentLevel,
            ProficiencyLevel requiredLevel,
            String status) {

        this.skillId = skillId;
        this.skillName = skillName;
        this.currentLevel = currentLevel;
        this.requiredLevel = requiredLevel;
        this.status = status;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public ProficiencyLevel getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(ProficiencyLevel currentLevel) {
        this.currentLevel = currentLevel;
    }

    public ProficiencyLevel getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(ProficiencyLevel requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}