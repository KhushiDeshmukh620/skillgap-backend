package com.skillgap.skillgap.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "job_role_skill_levels")
public class JobRoleSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_role_id", nullable = false)
    private JobRole jobRole;

    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProficiencyLevel requiredLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SkillPriority priority = SkillPriority.IMPORTANT;

    @Column(length = 1000)
    private String relevanceExplanation;

    public JobRoleSkill() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JobRole getJobRole() {
        return jobRole;
    }

    public void setJobRole(JobRole jobRole) {
        this.jobRole = jobRole;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public ProficiencyLevel getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(ProficiencyLevel requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    public SkillPriority getPriority() {
        return priority;
    }

    public void setPriority(SkillPriority priority) {
        this.priority = priority;
    }

    public String getRelevanceExplanation() {
        return relevanceExplanation;
    }

    public void setRelevanceExplanation(String relevanceExplanation) {
        this.relevanceExplanation = relevanceExplanation;
    }
}