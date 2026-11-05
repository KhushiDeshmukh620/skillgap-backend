package com.skillgap.skillgap.controller;

import com.skillgap.skillgap.entity.EngineeringDomain;
import com.skillgap.skillgap.service.EngineeringDomainService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/engineering-domains")
public class EngineeringDomainController {

    private final EngineeringDomainService engineeringDomainService;

    public EngineeringDomainController(
            EngineeringDomainService engineeringDomainService) {

        this.engineeringDomainService = engineeringDomainService;
    }


    // Get all engineering domains
    @GetMapping
    public List<EngineeringDomain> getAllDomains() {

        return engineeringDomainService.getAllDomains();
    }


    // Get one engineering domain
    @GetMapping("/{id}")
    public EngineeringDomain getDomainById(
            @PathVariable Long id) {

        return engineeringDomainService.getDomainById(id);
    }


    // Add new engineering domain
    @PostMapping
    public EngineeringDomain addDomain(
            @RequestBody EngineeringDomain engineeringDomain) {

        return engineeringDomainService.addDomain(
                engineeringDomain
        );
    }


    // Update engineering domain
    @PutMapping("/{id}")
    public EngineeringDomain updateDomain(
            @PathVariable Long id,
            @RequestBody EngineeringDomain engineeringDomain) {

        return engineeringDomainService.updateDomain(
                id,
                engineeringDomain
        );
    }


    // Delete engineering domain
    @DeleteMapping("/{id}")
    public void deleteDomain(
            @PathVariable Long id) {

        engineeringDomainService.deleteDomain(id);
    }
}