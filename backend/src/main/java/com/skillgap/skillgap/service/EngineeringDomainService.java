
package com.skillgap.skillgap.service;

import com.skillgap.skillgap.entity.EngineeringDomain;
import com.skillgap.skillgap.repository.EngineeringDomainRepository;
import com.skillgap.skillgap.repository.JobRoleRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EngineeringDomainService {

    private final EngineeringDomainRepository engineeringDomainRepository;
    private final JobRoleRepository jobRoleRepository;

    public EngineeringDomainService(
            EngineeringDomainRepository engineeringDomainRepository,
            JobRoleRepository jobRoleRepository) {

        this.engineeringDomainRepository = engineeringDomainRepository;
        this.jobRoleRepository = jobRoleRepository;
    }

    // Get all engineering domains
    @Transactional(readOnly = true)
    public List<EngineeringDomain> getAllDomains() {
        return engineeringDomainRepository.findAll();
    }

    // Get engineering domain by ID
    @Transactional(readOnly = true)
    public EngineeringDomain getDomainById(Long id) {

        return engineeringDomainRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Engineering domain not found."
                        )
                );
    }

    // Validate domain name
    private String validateName(String name) {

        if (name == null || name.trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Engineering domain name is required."
            );
        }

        String cleanedName = name.trim();

        if (cleanedName.length() > 255) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Engineering domain name is too long."
            );
        }

        return cleanedName;
    }

    // Add new engineering domain
    @Transactional
    public EngineeringDomain addDomain(
            EngineeringDomain engineeringDomain) {

        String name = validateName(engineeringDomain.getName());

        if (engineeringDomainRepository
                .existsByNameIgnoreCase(name)) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Engineering domain already exists."
            );
        }

        engineeringDomain.setName(name);

        return engineeringDomainRepository.save(
                engineeringDomain
        );
    }

    // Update engineering domain
    @Transactional
    public EngineeringDomain updateDomain(
            Long id,
            EngineeringDomain updatedDomain) {

        EngineeringDomain existingDomain = getDomainById(id);

        String newName = validateName(updatedDomain.getName());

        boolean duplicate =
                engineeringDomainRepository
                        .existsByNameIgnoreCaseAndIdNot(
                                newName,
                                id
                        );

        if (duplicate) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Another engineering domain already has this name."
            );
        }

        existingDomain.setName(newName);
        existingDomain.setDescription(
                updatedDomain.getDescription()
        );

        return engineeringDomainRepository.save(existingDomain);
    }

    // Delete domain only when no job roles use it
    @Transactional
    public void deleteDomain(Long id) {

        EngineeringDomain domain = getDomainById(id);

        if (jobRoleRepository.existsByEngineeringDomainId(id)) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Cannot delete this domain because job roles are assigned to it."
            );
        }

        engineeringDomainRepository.delete(domain);
    }
}
