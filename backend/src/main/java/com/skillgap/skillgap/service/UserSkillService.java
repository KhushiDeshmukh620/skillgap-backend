package com.skillgap.skillgap.service;

import com.skillgap.skillgap.entity.ProficiencyLevel;
import com.skillgap.skillgap.entity.Skill;
import com.skillgap.skillgap.entity.User;
import com.skillgap.skillgap.entity.UserSkill;
import com.skillgap.skillgap.repository.SkillRepository;
import com.skillgap.skillgap.repository.UserRepository;
import com.skillgap.skillgap.repository.UserSkillRepository;
import org.springframework.stereotype.Service;
import com.skillgap.skillgap.dto.UserSkillResponse;

import java.util.List;

@Service
public class UserSkillService {

    private final UserSkillRepository userSkillRepository;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;

    public UserSkillService(
            UserSkillRepository userSkillRepository,
            UserRepository userRepository,
            SkillRepository skillRepository) {

        this.userSkillRepository = userSkillRepository;
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
    }

    public UserSkillResponse addUserSkill(
            Long userId,
            Long skillId,
            ProficiencyLevel proficiencyLevel) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        UserSkill userSkill = userSkillRepository
                .findByUserIdAndSkillId(userId, skillId)
                .orElseGet(UserSkill::new);

        userSkill.setUser(user);
        userSkill.setSkill(skill);
        userSkill.setProficiencyLevel(proficiencyLevel);

        UserSkill savedUserSkill = userSkillRepository.save(userSkill);

        return new UserSkillResponse(
                savedUserSkill.getId(),
                savedUserSkill.getSkill().getId(),
                savedUserSkill.getSkill().getName(),
                savedUserSkill.getSkill().getCategory(),
                savedUserSkill.getProficiencyLevel()
        );
    }

    public List<UserSkillResponse> getUserSkills(Long userId) {

        return userSkillRepository.findByUserId(userId)
                .stream()
                .map(userSkill -> new UserSkillResponse(
                        userSkill.getId(),
                        userSkill.getSkill().getId(),
                        userSkill.getSkill().getName(),
                        userSkill.getSkill().getCategory(),
                        userSkill.getProficiencyLevel()
                ))
                .toList();
    }


    public void deleteUserSkill(Long userId, Long skillId) {

        UserSkill userSkill = userSkillRepository
                .findByUserIdAndSkillId(userId, skillId)
                .orElseThrow(() ->
                        new org.springframework.web.server.ResponseStatusException(
                                org.springframework.http.HttpStatus.NOT_FOUND,
                                "Skill not found in your profile"
                        )
                );

        userSkillRepository.delete(userSkill);
    }

}