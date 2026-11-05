package com.skillgap.skillgap.controller;

import com.skillgap.skillgap.ai.AiSkillExtractionService;
import com.skillgap.skillgap.ai.ExtractedSkillsResponse;
import com.skillgap.skillgap.ai.NormalizedSkillsResponse;
import com.skillgap.skillgap.ai.ResumeSkillGapResponse;
import com.skillgap.skillgap.service.ResumeService;
import com.skillgap.skillgap.service.ResumeSkillGapService;
import com.skillgap.skillgap.service.SkillNormalizationService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    private final ResumeService resumeService;
    private final AiSkillExtractionService aiSkillExtractionService;
    private final SkillNormalizationService skillNormalizationService;
    private final ResumeSkillGapService resumeSkillGapService;

    public ResumeController(
            ResumeService resumeService,
            AiSkillExtractionService aiSkillExtractionService,
            SkillNormalizationService skillNormalizationService,
            ResumeSkillGapService resumeSkillGapService) {

        this.resumeService = resumeService;
        this.aiSkillExtractionService = aiSkillExtractionService;
        this.skillNormalizationService = skillNormalizationService;
        this.resumeSkillGapService = resumeSkillGapService;
    }

    @PostMapping(
            value = "/extract",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public Map<String, String> extractResumeText(
            @RequestParam("file") MultipartFile file) {

        String extractedText =
                resumeService.extractTextFromPdf(file);

        return Map.of(
                "message", "Resume text extracted successfully",
                "text", extractedText
        );
    }

    @PostMapping(
            value = "/analyze",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public NormalizedSkillsResponse analyzeResume(
            @RequestParam("file") MultipartFile file) {

        String extractedText =
                resumeService.extractTextFromPdf(file);

        ExtractedSkillsResponse extractedSkills =
                aiSkillExtractionService.extractSkills(extractedText);

        return skillNormalizationService.normalizeSkills(
                extractedSkills.getSkills()
        );
    }

    @PostMapping(
            value = "/skill-gap/{jobRoleId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResumeSkillGapResponse analyzeResumeSkillGap(
            @PathVariable Long jobRoleId,
            @RequestParam("file") MultipartFile file) {

        // 1. Extract resume text
        String extractedText =
                resumeService.extractTextFromPdf(file);

        // 2. AI extracts technical skills
        ExtractedSkillsResponse extractedSkills =
                aiSkillExtractionService.extractSkills(extractedText);

        // 3. Match AI skills with database
        NormalizedSkillsResponse normalizedSkills =
                skillNormalizationService.normalizeSkills(
                        extractedSkills.getSkills()
                );

        // 4. Compare resume skills with target job role
        return resumeSkillGapService.calculateResumeSkillGap(
                jobRoleId,
                normalizedSkills
        );
    }
}