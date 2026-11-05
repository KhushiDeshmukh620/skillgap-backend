
package com.skillgap.skillgap.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "job_roles")
public class JobRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    // Each job role belongs to one engineering domain
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "engineering_domain_id")
    private EngineeringDomain engineeringDomain;

    // Existing required skills relationship
    @ManyToMany
    @JoinTable(
            name = "job_role_skills",
            joinColumns = @JoinColumn(name = "job_role_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> requiredSkills = new HashSet<>();

    public JobRole() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EngineeringDomain getEngineeringDomain() {
        return engineeringDomain;
    }

    public void setEngineeringDomain(
            EngineeringDomain engineeringDomain) {
        this.engineeringDomain = engineeringDomain;
    }

    public Set<Skill> getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(Set<Skill> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }
}
