package com.skillgap.skillgap.service;

import com.skillgap.skillgap.ai.NormalizedSkillsResponse;
import com.skillgap.skillgap.entity.Skill;
import com.skillgap.skillgap.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SkillNormalizationService {

    private final SkillRepository skillRepository;

    public SkillNormalizationService(
            SkillRepository skillRepository) {

        this.skillRepository = skillRepository;
    }

    public NormalizedSkillsResponse normalizeSkills(
            List<String> extractedSkills) {

        List<Skill> databaseSkills =
                skillRepository.findAll();

        List<String> matchedSkills =
                new ArrayList<>();

        List<String> otherExtractedSkills =
                new ArrayList<>();

        for (String extractedSkill : extractedSkills) {

            String normalizedExtracted =
                    normalizeName(extractedSkill);

            Skill matchedDatabaseSkill = null;

            for (Skill databaseSkill : databaseSkills) {

                String normalizedDatabase =
                        normalizeName(databaseSkill.getName());

                if (normalizedExtracted.equals(
                        normalizedDatabase)) {

                    matchedDatabaseSkill =
                            databaseSkill;

                    break;
                }
            }

            if (matchedDatabaseSkill != null) {

                if (!matchedSkills.contains(
                        matchedDatabaseSkill.getName())) {

                    matchedSkills.add(
                            matchedDatabaseSkill.getName());
                }

            } else {

                if (!otherExtractedSkills.contains(
                        extractedSkill)) {

                    otherExtractedSkills.add(
                            extractedSkill);
                }
            }
        }

        return new NormalizedSkillsResponse(
                matchedSkills,
                otherExtractedSkills
        );
    }

    private String normalizeName(String skillName) {

        if (skillName == null) {
            return "";
        }

        String normalized =
                skillName.toLowerCase()
                        .replace(".", "")
                        .replace("-", "")
                        .replace(" ", "")
                        .trim();

        return switch (normalized) {

            case "corejava" -> "java";

            case "springbootframework" ->
                    "springboot";

            case "reactjs", "reactjavascript" ->
                    "react";

            default -> normalized;
        };
    }
}