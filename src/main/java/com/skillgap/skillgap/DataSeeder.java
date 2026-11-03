
package com.skillgap.skillgap;

import com.skillgap.skillgap.entity.EngineeringDomain;
import com.skillgap.skillgap.entity.JobRole;
import com.skillgap.skillgap.repository.EngineeringDomainRepository;
import com.skillgap.skillgap.repository.JobRoleRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class DataSeeder implements CommandLineRunner {

    private final EngineeringDomainRepository domainRepository;
    private final JobRoleRepository jobRoleRepository;

    public DataSeeder(
            EngineeringDomainRepository domainRepository,
            JobRoleRepository jobRoleRepository) {
        this.domainRepository = domainRepository;
        this.jobRoleRepository = jobRoleRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {

        Map<String, DomainData> domains = new LinkedHashMap<>();

        // ==========================================
        // 1. COMPUTER SCIENCE AND INFORMATION TECHNOLOGY
        // ==========================================
        domains.put("Computer Science and Information Technology",
                new DomainData(
                        "Computer science, information technology, systems and computing.",
                        new String[]{
                                "Computer Engineer",
                                "IT Engineer",
                                "Systems Engineer",
                                "Information Technology Engineer",
                                "Application Support Engineer"
                        }
                ));

        // ==========================================
        // 2. ELECTRONICS AND TELECOMMUNICATION ENGINEERING
        // ==========================================
        domains.put("Electronics and Telecommunication Engineering",
                new DomainData(
                        "Electronic circuits, communication systems and telecommunications.",
                        new String[]{
                                "Electronics and Telecommunication Engineer",
                                "Communication Engineer",
                                "RF Engineer",
                                "Telecom Network Engineer",
                                "Embedded Electronics Engineer"
                        }
                ));

        // ==========================================
        // 3. ELECTRICAL ENGINEERING
        // ==========================================
        domains.put("Electrical Engineering",
                new DomainData(
                        "Electrical machines, power electronics and control systems.",
                        new String[]{
                                "Electrical Design Engineer",
                                "Power Electronics Engineer",
                                "Electrical Maintenance Engineer",
                                "Control Systems Engineer",
                                "Instrumentation Engineer"
                        }
                ));

        // ==========================================
        // 4. MECHANICAL ENGINEERING
        // ==========================================
        domains.put("Mechanical Engineering",
                new DomainData(
                        "Mechanical systems, thermal engineering and machine design.",
                        new String[]{
                                "Mechanical Engineer",
                                "Thermal Engineer",
                                "HVAC Engineer",
                                "Design and Development Engineer",
                                "Maintenance Engineer"
                        }
                ));

        // ==========================================
        // 5. CIVIL ENGINEERING
        // ==========================================
        domains.put("Civil Engineering",
                new DomainData(
                        "Civil infrastructure, surveying and environmental engineering.",
                        new String[]{
                                "Geotechnical Engineer",
                                "Transportation Engineer",
                                "Environmental Engineer",
                                "Water Resources Engineer",
                                "Survey Engineer"
                        }
                ));

        // ==========================================
        // 6. SOFTWARE DEVELOPMENT
        // ==========================================
        domains.put("Software Development", new DomainData(
                "Software development, programming and application engineering.",
                new String[]{
                        "Java Developer",
                        "Python Developer",
                        "Full Stack Developer",
                        "Frontend Developer",
                        "Backend Developer",
                        "Android Developer",
                        "Software Engineer"
                }
        ));

        // ==========================================
        // 7. AI AND DATA
        // ==========================================
        domains.put("AI & Data", new DomainData(
                "Artificial intelligence, machine learning and data engineering.",
                new String[]{
                        "AI Engineer",
                        "Machine Learning Engineer",
                        "Data Scientist",
                        "Data Analyst",
                        "NLP Engineer",
                        "Data Engineer"
                }
        ));

        // ==========================================
        // 8. CLOUD AND DEVOPS
        // ==========================================
        domains.put("Cloud & DevOps", new DomainData(
                "Cloud infrastructure, deployment and reliability engineering.",
                new String[]{
                        "Cloud Engineer",
                        "DevOps Engineer",
                        "AWS Engineer",
                        "Site Reliability Engineer"
                }
        ));

        // ==========================================
        // 9. SECURITY AND NETWORKING
        // ==========================================
        domains.put("Security & Networking", new DomainData(
                "Cybersecurity, information security and computer networking.",
                new String[]{
                        "Cybersecurity Analyst",
                        "Ethical Hacker",
                        "SOC Analyst",
                        "Network Engineer"
                }
        ));

        // ==========================================
        // 10. OTHER CS ROLES
        // ==========================================
        domains.put("Other CS Roles", new DomainData(
                "Additional computer science and technology career paths.",
                new String[]{
                        "Database Administrator",
                        "QA Engineer",
                        "Automation Test Engineer",
                        "UI/UX Designer",
                        "Blockchain Developer"
                }
        ));

        // ==========================================
        // 11. MECHANICAL AND MANUFACTURING
        // ==========================================
        domains.put("Mechanical & Manufacturing", new DomainData(
                "Mechanical design, manufacturing and production engineering.",
                new String[]{
                        "Mechanical Design Engineer",
                        "Production Engineer",
                        "Quality Engineer",
                        "Automotive Engineer",
                        "CAD Engineer"
                }
        ));

        // ==========================================
        // 12. CIVIL AND CONSTRUCTION
        // ==========================================
        domains.put("Civil & Construction", new DomainData(
                "Civil infrastructure, construction and structural engineering.",
                new String[]{
                        "Civil Engineer",
                        "Structural Engineer",
                        "Site Engineer",
                        "Quantity Surveyor",
                        "BIM Engineer"
                }
        ));

        // ==========================================
        // 13. ELECTRICAL AND ELECTRONICS
        // ==========================================
        domains.put("Electrical & Electronics", new DomainData(
                "Electrical systems, electronics and embedded engineering.",
                new String[]{
                        "Electrical Engineer",
                        "Electronics Engineer",
                        "VLSI Engineer",
                        "Embedded Systems Engineer",
                        "Power Systems Engineer"
                }
        ));

        // ==========================================
        // SAVE DOMAINS AND JOB ROLES
        // ==========================================
        for (Map.Entry<String, DomainData> entry : domains.entrySet()) {

            String domainName = entry.getKey();
            DomainData data = entry.getValue();

            EngineeringDomain domain = domainRepository
                    .findByName(domainName)
                    .orElseGet(() -> {
                        EngineeringDomain newDomain =
                                new EngineeringDomain(
                                        domainName,
                                        data.description
                                );

                        return domainRepository.save(newDomain);
                    });

            for (String roleName : data.roles) {

                if (!jobRoleRepository.existsByNameIgnoreCase(roleName)) {

                    JobRole jobRole = new JobRole();

                    jobRole.setName(roleName);
                    jobRole.setDescription(
                            roleName + " career role in " + domainName + "."
                    );
                    jobRole.setEngineeringDomain(domain);

                    jobRoleRepository.save(jobRole);
                }
            }
        }

        System.out.println("======================================");
        System.out.println("SkillGap domain seeding completed!");
        System.out.println("Total domains processed: " + domains.size());
        System.out.println("======================================");
    }

    private static class DomainData {

        private final String description;
        private final String[] roles;

        private DomainData(String description, String[] roles) {
            this.description = description;
            this.roles = roles;
        }
    }
}
