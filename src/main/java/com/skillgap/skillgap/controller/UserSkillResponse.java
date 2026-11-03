package com.skillgap.skillgap.dto;

import com.skillgap.skillgap.entity.ProficiencyLevel;

public class UserSkillResponse {

    private Long id;
    private Long skillId;
    private String skillName;
    private String category;
    private ProficiencyLevel proficiencyLevel;

    public UserSkillResponse() {
    }

    public UserSkillResponse(
            Long id,
            Long skillId,
            String skillName,
            String category,
            ProficiencyLevel proficiencyLevel) {

        this.id = id;
        this.skillId = skillId;
        this.skillName = skillName;
        this.category = category;
        this.proficiencyLevel = proficiencyLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ProficiencyLevel getProficiencyLevel() {
        return proficiencyLevel;
    }

    public void setProficiencyLevel(ProficiencyLevel proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }
}