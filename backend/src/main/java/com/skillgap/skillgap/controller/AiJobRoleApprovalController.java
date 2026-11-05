
package com.skillgap.skillgap.controller;

import com.skillgap.skillgap.service.AiJobRoleApprovalService;
import com.skillgap.skillgap.service.AiJobRoleApprovalService.ApprovalRequest;
import com.skillgap.skillgap.service.AiJobRoleApprovalService.ApprovalResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/ai-job-roles")
public class AiJobRoleApprovalController {

    private final AiJobRoleApprovalService approvalService;

    public AiJobRoleApprovalController(
            AiJobRoleApprovalService approvalService) {

        this.approvalService = approvalService;
    }

    // Create and save a NEW job role.
    @PostMapping("/approve")
    @ResponseStatus(HttpStatus.CREATED)
    public ApprovalResponse approveJobRole(
            @RequestBody ApprovalRequest request) {

        return approvalService.approveJobRole(request);
    }

    // Update the skills of an EXISTING job role.
    @PutMapping("/{jobRoleId}")
    public ApprovalResponse updateExistingJobRole(
            @PathVariable Long jobRoleId,
            @RequestBody ApprovalRequest request) {

        return approvalService.updateExistingJobRole(
                jobRoleId,
                request
        );
    }
}
