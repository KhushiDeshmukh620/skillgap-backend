
package com.skillgap.skillgap.service;

import com.skillgap.skillgap.entity.LearningResource;
import com.skillgap.skillgap.entity.Skill;
import com.skillgap.skillgap.repository.LearningResourceRepository;
import com.skillgap.skillgap.repository.SkillRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LearningResourceService {

    private final LearningResourceRepository learningResourceRepository;
    private final SkillRepository skillRepository;

    public LearningResourceService(
            LearningResourceRepository learningResourceRepository,
            SkillRepository skillRepository) {

        this.learningResourceRepository = learningResourceRepository;
        this.skillRepository = skillRepository;
    }

    /*
     * ADD LEARNING RESOURCE
     */
    public LearningResource addResource(
            Long skillId,
            LearningResource resource) {

        if (resource == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Learning resource is required"
            );
        }

        // Validate URL using shared validator.
        String validUrl =
                LearningResourceUrlValidator.requireValid(
                        resource.getResourceUrl()
                );

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Skill not found"
                ));

        resource.setId(null);
        resource.setSkill(skill);
        resource.setResourceUrl(validUrl);

        return learningResourceRepository.save(resource);
    }

    /*
     * GET LEARNING RESOURCES BY SKILL
     */
    public List<LearningResource> getResourcesBySkill(
            Long skillId) {

        return learningResourceRepository
                .findBySkillIdOrderByLearningOrderAsc(skillId);
    }

    /*
     * UPDATE LEARNING RESOURCE
     */
    public LearningResource updateResource(
            Long resourceId,
            LearningResource updatedResource) {

        if (updatedResource == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Learning resource is required"
            );
        }

        // Validate URL using shared validator.
        String validUrl =
                LearningResourceUrlValidator.requireValid(
                        updatedResource.getResourceUrl()
                );

        LearningResource existingResource =
                learningResourceRepository.findById(resourceId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Learning resource not found"
                        ));

        existingResource.setTitle(
                updatedResource.getTitle()
        );

        existingResource.setResourceUrl(validUrl);

        existingResource.setResourceType(
                updatedResource.getResourceType()
        );

        existingResource.setLearningOrder(
                updatedResource.getLearningOrder()
        );

        // Preserve the existing skill relationship.
        return learningResourceRepository.save(existingResource);
    }

    /*
     * DELETE LEARNING RESOURCE
     */
    public void deleteResource(Long resourceId) {

        LearningResource existingResource =
                learningResourceRepository.findById(resourceId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Learning resource not found"
                        ));

        learningResourceRepository.delete(existingResource);
    }
}
