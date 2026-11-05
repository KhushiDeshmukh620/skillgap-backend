package com.skillgap.skillgap.ai;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AiSkillExtractionService {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public AiSkillExtractionService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:11434")
                .build();
    }

    public ExtractedSkillsResponse extractSkills(String resumeText) {

        if (resumeText == null || resumeText.isBlank()) {
            throw new RuntimeException("Resume text cannot be empty");
        }

        String prompt = """
                Extract technical skills explicitly present in the resume.

                Include:
                - programming languages
                - frameworks
                - databases
                - web technologies
                - developer tools
                - cloud/platform technologies
                - important technical concepts

                Do not include:
                - names
                - email addresses
                - phone numbers
                - education
                - dates
                - company names
                - soft skills

                Normalize obvious variations:
                - Core Java -> Java
                - Basic SQL -> SQL
                - Vs Code -> VS Code

                Return ONLY valid JSON in exactly this structure:

                {
                  "skills": [
                    "Java",
                    "Spring Boot",
                    "MySQL"
                  ]
                }

                Do not explain anything.
                Do not include markdown.
                Do not include reasoning.

                Resume:
                ----------------
                %s
                ----------------

                /no_think
                """.formatted(resumeText);

        Map<String, Object> requestBody = Map.of(
                "model", "qwen3:4b",
                "prompt", prompt,
                "stream", false,
                "think", false,
                "format", "json"
        );

        Map<?, ?> response = restClient.post()
                .uri("/api/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .body(Map.class);

        if (response == null || response.get("response") == null) {
            throw new RuntimeException(
                    "No response received from local AI"
            );
        }

        String aiResponse =
                response.get("response").toString().trim();

        try {

            JsonNode root =
                    objectMapper.readTree(aiResponse);

            JsonNode skillsNode =
                    root.get("skills");

            if (skillsNode == null || !skillsNode.isArray()) {
                throw new RuntimeException(
                        "AI response does not contain a skills list"
                );
            }

            List<String> skills = new ArrayList<>();

            for (JsonNode skillNode : skillsNode) {

                String skill =
                        skillNode.asText().trim();

                if (!skill.isBlank()
                        && !skills.contains(skill)) {

                    skills.add(skill);
                }
            }

            return new ExtractedSkillsResponse(skills);

        } catch (Exception exception) {

            throw new RuntimeException(
                    "Could not understand local AI response"
            );
        }
    }
}