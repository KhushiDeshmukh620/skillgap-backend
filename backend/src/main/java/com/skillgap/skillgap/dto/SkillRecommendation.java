package com.skillgap.skillgap.dto;

import com.skillgap.skillgap.entity.LearningResource;
import com.skillgap.skillgap.entity.Skill;

import java.util.List;

public class SkillRecommendation {

    private Skill skill;
    private List<LearningResource> resources;

    public SkillRecommendation() {
    }

    public SkillRecommendation(
            Skill skill,
            List<LearningResource> resources) {
        this.skill = skill;
        this.resources = resources;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public List<LearningResource> getResources() {
        return resources;
    }

    public void setResources(List<LearningResource> resources) {
        this.resources = resources;
    }
}