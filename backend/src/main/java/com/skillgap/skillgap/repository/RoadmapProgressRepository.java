
package com.skillgap.skillgap.repository;

import com.skillgap.skillgap.entity.RoadmapProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RoadmapProgressRepository
        extends JpaRepository<RoadmapProgress, Long> {

    List<RoadmapProgress> findByUserIdAndJobRoleId(
            Long userId,
            Long jobRoleId
    );

    Optional<RoadmapProgress> findByUserIdAndJobRoleIdAndSkillId(
            Long userId,
            Long jobRoleId,
            Long skillId
    );
}
