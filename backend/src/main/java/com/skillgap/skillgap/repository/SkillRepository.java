
package com.skillgap.skillgap.repository;

import com.skillgap.skillgap.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    // Check if a skill name already exists
    boolean existsByNameIgnoreCase(String name);

    // Check duplicate names while editing a skill
    boolean existsByNameIgnoreCaseAndIdNot(
            String name,
            Long id
    );

    // Find an existing skill without case sensitivity
    Optional<Skill> findByNameIgnoreCase(String name);
}
