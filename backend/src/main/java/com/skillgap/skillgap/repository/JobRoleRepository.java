
package com.skillgap.skillgap.repository;

import com.skillgap.skillgap.dto.JobRoleResponse;
import com.skillgap.skillgap.entity.JobRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRoleRepository
        extends JpaRepository<JobRole, Long> {

    // Get all job roles
    @Query("""
        SELECT new com.skillgap.skillgap.dto.JobRoleResponse(
            j.id, j.name
        )
        FROM JobRole j
        ORDER BY j.name
        """)
    List<JobRoleResponse> findAllJobRoleResponses();

    // Get job roles by engineering domain
    @Query("""
        SELECT new com.skillgap.skillgap.dto.JobRoleResponse(
            j.id, j.name
        )
        FROM JobRole j
        WHERE j.engineeringDomain.id = :domainId
        ORDER BY j.name
        """)

    List<JobRoleResponse> findJobRolesByDomainId(
            @Param("domainId") Long domainId
    );

    // Check whether a domain has assigned job roles
    boolean existsByEngineeringDomainId(Long domainId);
    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);
    boolean existsByRequiredSkills_Id(Long skillId);
}
