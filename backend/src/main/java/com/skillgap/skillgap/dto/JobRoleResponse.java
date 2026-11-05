
package com.skillgap.skillgap.dto;

public class JobRoleResponse {

    private Long id;
    private String name;
    private String description;
    private Long engineeringDomainId;
    private String engineeringDomainName;

    public JobRoleResponse() {
    }

    // Keep this constructor for existing repository queries
    public JobRoleResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Constructor for Admin Job Role Management
    public JobRoleResponse(
            Long id,
            String name,
            String description,
            Long engineeringDomainId,
            String engineeringDomainName) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.engineeringDomainId = engineeringDomainId;
        this.engineeringDomainName = engineeringDomainName;
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

    public Long getEngineeringDomainId() {
        return engineeringDomainId;
    }

    public void setEngineeringDomainId(Long engineeringDomainId) {
        this.engineeringDomainId = engineeringDomainId;
    }

    public String getEngineeringDomainName() {
        return engineeringDomainName;
    }

    public void setEngineeringDomainName(String engineeringDomainName) {
        this.engineeringDomainName = engineeringDomainName;
    }
}
