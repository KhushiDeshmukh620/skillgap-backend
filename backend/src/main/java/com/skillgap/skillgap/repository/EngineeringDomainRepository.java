package com.skillgap.skillgap.repository;

import com.skillgap.skillgap.entity.EngineeringDomain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EngineeringDomainRepository
        extends JpaRepository<EngineeringDomain, Long> {

    Optional<EngineeringDomain> findByName(String name);

    boolean existsByName(String name);

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);
}