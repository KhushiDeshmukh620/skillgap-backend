
package com.skillgap.skillgap.dto;

import com.skillgap.skillgap.entity.ProficiencyLevel;

public record JobRoleSkillResponse(
        Long id,
        Long jobRoleId,
        Long skillId,
        String skillName,
        ProficiencyLevel requiredLevel
) {
}
