package com.skillgap.skillgap.ai;

import java.util.List;

public class ExtractedSkillsResponse {

    private List<String> skills;

    public ExtractedSkillsResponse() {
    }

    public ExtractedSkillsResponse(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}