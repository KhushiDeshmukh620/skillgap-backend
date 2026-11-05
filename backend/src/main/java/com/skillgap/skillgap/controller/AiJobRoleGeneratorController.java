package com.skillgap.skillgap.controller;

import com.skillgap.skillgap.ai.AiJobRoleGeneratorService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tools.jackson.databind.JsonNode;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/ai-job-roles")
public class AiJobRoleGeneratorController {

    private final AiJobRoleGeneratorService aiService;

    public AiJobRoleGeneratorController(
            AiJobRoleGeneratorService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/generate")
    public ResponseEntity<JsonNode> generate(
            @RequestBody Map<String, String> request) {

        String roleName = request.get("roleName");

        if (roleName == null || roleName.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        JsonNode result = aiService.generateJobRole(roleName.trim());

        return ResponseEntity.ok(result);
    }
}