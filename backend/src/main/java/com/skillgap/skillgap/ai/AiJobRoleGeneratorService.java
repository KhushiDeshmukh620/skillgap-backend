
package com.skillgap.skillgap.ai;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.ObjectNode;

import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@Service
public class AiJobRoleGeneratorService {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public AiJobRoleGeneratorService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:11434")
                .build();
    }

    public JsonNode generateJobRole(String roleName) {

        if (roleName == null || roleName.isBlank()) {
            throw new IllegalArgumentException(
                    "Job role name cannot be empty"
            );
        }

        String cleanRoleName = roleName.trim();

        String prompt = """
                You are an expert technical recruiter, industry
                specialist, software architect, and career advisor.

                Generate a comprehensive, realistic, role-specific
                technical job profile.

                JOB ROLE:
                %s

                IMPORTANT:
                Generate one professional competency profile that
                describes the skills needed to perform this job role.

                Do not ask for or assume a target experience level.
                Do not generate separate fresher and experienced profiles.

                Required proficiency represents the level of knowledge
                needed to perform the associated job responsibilities.
                It does not represent the candidate's current ability.

                ==================================================
                1. UNDERSTAND THE JOB ROLE
                ==================================================

                First identify:

                - Main responsibilities of the job.
                - Day-to-day technical activities.
                - Tools and technologies commonly used.
                - Technical knowledge required to perform the work.
                - Important supporting competencies.

                Build the skill list from these responsibilities.

                Do not generate the same generic skill list for
                every job role.

                ==================================================
                2. ROLE-SPECIFIC SKILL SELECTION
                ==================================================

                Select all important skills genuinely needed to
                perform the requested job.

                Consider relevant areas such as:

                - Programming languages.
                - Backend development.
                - Frontend development.
                - Frameworks and libraries.
                - Databases and data persistence.
                - API development and integration.
                - Software testing.
                - Version control.
                - DevOps and deployment.
                - Cloud computing.
                - Networking and security.
                - Data analysis and visualization.
                - Operating systems.
                - Monitoring and troubleshooting.
                - Relevant professional tools.

                Include only areas relevant to the requested role.

                Do not add unrelated technologies just to make
                the profile look comprehensive.

                ==================================================
                3. FULL-STACK DEVELOPMENT COVERAGE
                ==================================================

                For full-stack development roles, consider:

                - Programming language.
                - Backend development.
                - Frontend development.
                - Frontend frameworks.
                - Database management.
                - REST API development.
                - ORM and data access.
                - Testing and debugging.
                - Version control.
                - Deployment fundamentals.

                For Java Full Stack Developer roles, evaluate
                relevant technologies such as:

                - Java.
                - Spring Boot.
                - HTML.
                - CSS.
                - JavaScript.
                - React or another suitable frontend framework.
                - SQL and relational databases.
                - MySQL or another suitable database.
                - Hibernate or JPA.
                - REST APIs.
                - Git.
                - JUnit.

                This is a reference list, not a mandatory checklist.

                Select technologies based on the actual job role.
                Do not apply these requirements to unrelated roles.

                ==================================================
                4. DYNAMIC NUMBER OF SKILLS
                ==================================================

                There is NO fixed skill count.

                Decide the number of skills according to the
                responsibilities and technical complexity of
                the requested job role.

                Include every important competency needed to
                perform the job properly.

                Include foundational, core, and relevant supporting
                skills where appropriate.

                Do not:
                - Force the output to contain 8, 10, or 12 skills.
                - Add unnecessary skills to increase the count.
                - Omit important skills to reduce the count.
                - Repeat the same competency using different names.

                Every skill must have a distinct purpose.

                ==================================================
                5. REQUIRED PROFICIENCY
                ==================================================

                Use only:

                BEGINNER
                INTERMEDIATE
                ADVANCED

                BEGINNER:
                Foundational knowledge sufficient for introductory
                tasks and supervised work.

                INTERMEDIATE:
                Practical ability to use the skill independently
                for normal job responsibilities.

                ADVANCED:
                Deep expertise needed for complex work, architecture,
                optimization, leadership, or technical ownership.

                Assign the level according to the actual responsibility
                associated with the skill.

                Do not mark every skill ADVANCED.

                Do not mark every skill BEGINNER.

                A skill should have a realistic proficiency level
                for performing the job responsibility.

                ==================================================
                6. SKILL PRIORITY
                ==================================================

                Use exactly one of these values:

                ESSENTIAL
                IMPORTANT
                OPTIONAL

                ESSENTIAL:
                A core competency needed to perform the job.

                IMPORTANT:
                A strongly relevant supporting competency.

                OPTIONAL:
                An additional advantage that is useful but not
                generally required for the main responsibilities.

                Assign priority based on actual industry relevance.

                Do not mark every skill ESSENTIAL.

                Do not downgrade a genuinely essential skill.

                ==================================================
                7. SKILL RELEVANCE EXPLANATION
                ==================================================

                Every skill must have a field named
                relevanceExplanation.

                Explain specifically why the skill is needed.

                Describe what the professional uses the skill
                to accomplish in this job role.

                Avoid generic explanations.

                ==================================================
                8. SKILL CATEGORY
                ==================================================

                Every skill must have an appropriate category.

                Examples:

                Programming
                Backend Development
                Frontend Development
                Database
                Testing
                DevOps
                Cloud Computing
                Networking
                Cybersecurity
                Data Engineering
                Data Analysis
                Operating Systems
                Version Control
                Monitoring & Observability
                IT Operations

                Select the most suitable category.

                Never use "AI Generated" as a category.

                ==================================================
                9. THREE LEARNING RESOURCES PER SKILL
                ==================================================

                EVERY skill MUST contain exactly THREE learning
                resource objects.

                Each resource must contain:

                - title
                - resourceType
                - resourceUrl

                Use these resource types only:

                Documentation
                Tutorial
                YouTube

                Resource requirements:

                RESOURCE 1:
                Prefer official documentation or an official
                learning guide for the skill.

                RESOURCE 2:
                Prefer a practical tutorial or structured
                beginner-to-intermediate learning guide.

                RESOURCE 3:
                Prefer a different practical learning resource,
                course, or relevant educational video.

                The three resources must be distinct.

                Every resource must directly teach the skill
                to which it belongs.

                Do not repeat the same URL for all three resources.

                Prefer reliable sources such as official technology
                documentation and recognized learning platforms.

                Examples of appropriate sources include:

                - Oracle Java documentation.
                - Spring official guides.
                - React documentation.
                - MDN Web Docs.
                - PostgreSQL documentation.
                - MySQL documentation.
                - Git documentation.
                - Microsoft Learn.
                - AWS documentation.

                These are examples only. Choose sources relevant
                to the actual skill.

                URL ACCURACY IS IMPORTANT:

                - Never invent a website URL.
                - Never invent a YouTube video ID.
                - Never create a fake course link.
                - Do not use a search-results URL as a direct resource.
                - If you cannot confidently identify an exact resource
                  URL, use an empty string for resourceUrl.
                - Do not claim that a URL has been verified.

                Still return exactly THREE resource objects
                for every skill, even when a URL is empty.

                ==================================================
                10. DUPLICATE PREVENTION
                ==================================================

                Do not include duplicate skills.

                Combine overlapping competencies when they represent
                the same practical ability.

                For example, do not separately list two differently
                named versions of the same Spring Boot MVC competency.

                Keep genuinely different skills separate.

                ==================================================
                11. FINAL QUALITY CHECK
                ==================================================

                Before producing JSON, check:

                - All important job responsibilities are covered.
                - Skills are specific to the requested role.
                - No irrelevant technologies are included.
                - No duplicate skills are included.
                - Every skill has a category.
                - Every skill has a valid proficiency.
                - Every skill has a valid priority.
                - Every skill has a specific relevance explanation.
                - Every skill contains exactly three resources.
                - Every resource has a title, type, and URL field.
                - Resource types use only the permitted values.
                - No URLs have been invented.

                ==================================================
                12. REQUIRED JSON OUTPUT
                ==================================================

                Return ONLY valid JSON using this structure:

                {
                  "roleName": "%s",
                  "description": "A concise description of the job role and its responsibilities.",
                  "requiredSkills": [
                    {
                      "skillName": "Example Skill",
                      "category": "Relevant Category",
                      "requiredLevel": "INTERMEDIATE",
                      "priority": "ESSENTIAL",
                      "relevanceExplanation": "Explains how this skill is used in the actual job.",
                      "learningResources": [
                        {
                          "title": "Official documentation",
                          "resourceType": "Documentation",
                          "resourceUrl": "https://example.com"
                        },
                        {
                          "title": "Practical learning tutorial",
                          "resourceType": "Tutorial",
                          "resourceUrl": ""
                        },
                        {
                          "title": "Educational video",
                          "resourceType": "YouTube",
                          "resourceUrl": ""
                        }
                      ]
                    }
                  ]
                }

                OUTPUT RULES:

                - Return a JSON object, not a JSON array.
                - Include roleName, description, and requiredSkills.
                - Every skill must contain all six skill fields.
                - Every skill must contain exactly three resources.
                - Every resource must contain all three resource fields.
                - Use only the permitted enum values.
                - Return valid JSON only.
                - Do not include Markdown or explanations outside JSON.

                /no_think
                """.formatted(
                cleanRoleName,
                cleanRoleName
        );

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
                    "No response received from Ollama"
            );
        }

        try {
            String aiResponse = response.get("response")
                    .toString()
                    .trim();

            JsonNode result = objectMapper.readTree(aiResponse);

            if (!(result instanceof ObjectNode resultObject)
                    || !result.has("roleName")
                    || !result.has("description")
                    || !result.get("description").isTextual()
                    || !result.has("requiredSkills")
                    || !result.get("requiredSkills").isArray()) {

                throw new RuntimeException(
                        "Invalid AI job role response"
                );
            }

            // Keep the requested role name consistent.
            resultObject.put("roleName", cleanRoleName);

            // Remove any old experience-level property.
            resultObject.remove("experienceLevel");

            normalizeCategories(result);
            removeDuplicateSkills(result);
            validateGeneratedSkills(result);

            return result;

        } catch (Exception exception) {
            throw new RuntimeException(
                    "Unable to process AI-generated job role: "
                            + exception.getMessage(),
                    exception
            );
        }
    }

    private void normalizeCategories(JsonNode result) {

        for (JsonNode skill : result.get("requiredSkills")) {

            if (!(skill instanceof ObjectNode skillObject)) {
                continue;
            }

            String category = skill.path("category")
                    .asText("")
                    .trim();

            if (!category.isBlank()
                    && !category.equalsIgnoreCase("AI Generated")) {
                continue;
            }

            String name = skill.path("skillName")
                    .asText("")
                    .toLowerCase(Locale.ROOT);

            String detectedCategory;

            if (name.matches(".*(linux|windows|operating system).*")) {
                detectedCategory = "Operating Systems";
            } else if (name.matches(".*(network|tcp|dns).*")) {
                detectedCategory = "Networking";
            } else if (name.matches(".*(security|devsecops).*")) {
                detectedCategory = "Cybersecurity";
            } else if (name.matches(".*(cloud|aws|azure).*")) {
                detectedCategory = "Cloud Computing";
            } else if (name.matches(".*(script|python|programming|java).*")) {
                detectedCategory = "Programming";
            } else if (name.matches(".*(database|sql|mysql|postgres).*")) {
                detectedCategory = "Database";
            } else if (name.matches(".*(monitor|logging|observability).*")) {
                detectedCategory = "Monitoring & Observability";
            } else if (name.matches(".*(incident|troubleshoot).*")) {
                detectedCategory = "IT Operations";
            } else if (name.matches(".*(docker|container).*")) {
                detectedCategory = "Containerization";
            } else if (name.matches(".*(git|version control).*")) {
                detectedCategory = "Version Control";
            } else if (name.matches(".*(ci/cd|pipeline|deployment|terraform|automation).*")) {
                detectedCategory = "DevOps";
            } else {
                detectedCategory = "Technical Skills";
            }

            skillObject.put("category", detectedCategory);
        }
    }

    private void removeDuplicateSkills(JsonNode result) {

        if (!(result.get("requiredSkills") instanceof ArrayNode skills)) {
            return;
        }

        Set<String> seen = new HashSet<>();
        ArrayNode uniqueSkills = objectMapper.createArrayNode();

        for (JsonNode skill : skills) {

            String skillName = skill.path("skillName")
                    .asText("")
                    .trim();

            if (skillName.isBlank()) {
                continue;
            }

            String normalizedName = skillName
                    .toLowerCase(Locale.ROOT)
                    .replaceAll("[^a-z0-9]+", " ")
                    .trim();

            if (seen.add(normalizedName)) {
                uniqueSkills.add(skill);
            }
        }

        if (result instanceof ObjectNode resultObject) {
            resultObject.set("requiredSkills", uniqueSkills);
        }
    }

    private void validateGeneratedSkills(JsonNode result) {

        ArrayNode skills = (ArrayNode) result.get("requiredSkills");

        if (skills.isEmpty()) {
            throw new RuntimeException(
                    "AI did not generate any required skills."
            );
        }

        for (JsonNode skill : skills) {

            String skillName = skill.path("skillName").asText("").trim();
            String category = skill.path("category").asText("").trim();
            String level = skill.path("requiredLevel").asText("").trim();
            String priority = skill.path("priority").asText("").trim();
            String relevance = skill.path("relevanceExplanation")
                    .asText("")
                    .trim();

            if (skillName.isBlank()
                    || category.isBlank()
                    || relevance.isBlank()) {
                throw new RuntimeException(
                        "A generated skill is missing its name, category, "
                                + "or relevance explanation."
                );
            }

            if (!level.equals("BEGINNER")
                    && !level.equals("INTERMEDIATE")
                    && !level.equals("ADVANCED")) {
                throw new RuntimeException(
                        "Invalid proficiency for skill: " + skillName
                );
            }

            if (!priority.equals("ESSENTIAL")
                    && !priority.equals("IMPORTANT")
                    && !priority.equals("OPTIONAL")) {
                throw new RuntimeException(
                        "Invalid priority for skill: " + skillName
                );
            }

            JsonNode resources = skill.get("learningResources");

            if (!(resources instanceof ArrayNode resourceArray)
                    || resourceArray.size() != 3) {
                throw new RuntimeException(
                        "Skill '" + skillName
                                + "' must contain exactly three learning resources. "
                                + "Please generate the role again."
                );
            }

            for (JsonNode resource : resourceArray) {

                String title = resource.path("title").asText("").trim();
                String type = resource.path("resourceType").asText("").trim();
                String url = resource.path("resourceUrl").asText("").trim();

                if (title.isBlank()) {
                    throw new RuntimeException(
                            "A learning resource title is missing for skill: "
                                    + skillName
                    );
                }

                if (!type.equals("Documentation")
                        && !type.equals("Tutorial")
                        && !type.equals("YouTube")) {
                    throw new RuntimeException(
                            "Invalid learning resource type for skill: "
                                    + skillName
                    );
                }

                if (!url.isBlank()
                        && !url.matches("^https?://\\S+$")) {
                    throw new RuntimeException(
                            "Invalid learning resource URL for skill: "
                                    + skillName
                    );
                }
            }
        }
    }
}
