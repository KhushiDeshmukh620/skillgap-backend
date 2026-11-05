package com.skillgap.skillgap.ai;

import java.util.List;

public class NormalizedSkillsResponse {

    private List<String> matchedSkills;
    private List<String> otherExtractedSkills;

    public NormalizedSkillsResponse() {
    }

    public NormalizedSkillsResponse(
            List<String> matchedSkills,
            List<String> otherExtractedSkills) {

        this.matchedSkills = matchedSkills;
        this.otherExtractedSkills = otherExtractedSkills;
    }

    public List<String> getMatchedSkills() {
        return matchedSkills;
    }

    public void setMatchedSkills(List<String> matchedSkills) {
        this.matchedSkills = matchedSkills;
    }

    public List<String> getOtherExtractedSkills() {
        return otherExtractedSkills;
    }

    public void setOtherExtractedSkills(
            List<String> otherExtractedSkills) {

        this.otherExtractedSkills = otherExtractedSkills;
    }
}