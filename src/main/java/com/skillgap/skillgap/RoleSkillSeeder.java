
package com.skillgap.skillgap;

import com.skillgap.skillgap.entity.JobRole;
import com.skillgap.skillgap.entity.JobRoleSkill;
import com.skillgap.skillgap.entity.ProficiencyLevel;
import com.skillgap.skillgap.entity.Skill;
import com.skillgap.skillgap.entity.SkillPriority;

import com.skillgap.skillgap.repository.JobRoleRepository;
import com.skillgap.skillgap.repository.JobRoleSkillRepository;
import com.skillgap.skillgap.repository.SkillRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RoleSkillSeeder implements CommandLineRunner {

    private final JobRoleRepository jobRoleRepository;
    private final JobRoleSkillRepository jobRoleSkillRepository;
    private final SkillRepository skillRepository;

    public RoleSkillSeeder(
            JobRoleRepository jobRoleRepository,
            JobRoleSkillRepository jobRoleSkillRepository,
            SkillRepository skillRepository) {

        this.jobRoleRepository = jobRoleRepository;
        this.jobRoleSkillRepository = jobRoleSkillRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {

        // 1. Python Developer - preserve existing requirements
        add("Python Developer", "Python", "Programming",
                ProficiencyLevel.ADVANCED, SkillPriority.ESSENTIAL,
                "Core language used to build Python applications.");

        add("Python Developer", "Data Structures & Algorithms", "Programming",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Helps build efficient and scalable solutions.");

        add("Python Developer", "Object-Oriented Programming", "Programming",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Supports reusable and maintainable application design.");

        add("Python Developer", "SQL", "Database",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Used to query relational databases.");

        add("Python Developer", "Git", "Development Tools",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Used for source control and collaboration.");

        add("Python Developer", "REST APIs", "Backend Development",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Used to build and consume web services.");

        add("Python Developer", "pytest", "Testing",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Used to write and execute Python tests.");

        add("Python Developer", "Debugging", "Programming",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Helps identify and fix application errors.");

        add("Python Developer", "Exception Handling", "Programming",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Helps handle runtime errors safely.");

        add("Python Developer", "Python Libraries", "Programming",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Supports development using Python packages.");

        // 2. Java Developer
        add("Java Developer", "Java", "Programming",
                ProficiencyLevel.ADVANCED, SkillPriority.ESSENTIAL,
                "Core language for Java application development.");

        add("Java Developer", "Object-Oriented Programming", "Programming",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Understand classes, objects and OOP principles.");

        add("Java Developer", "Data Structures & Algorithms", "Programming",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Important for efficient coding and interviews.");

        add("Java Developer", "SQL", "Database",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Used to query and manage relational databases.");

        add("Java Developer", "Spring Boot", "Backend Development",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Used to develop Java backend applications.");

        add("Java Developer", "REST APIs", "Backend Development",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Used to design and consume HTTP services.");

        add("Java Developer", "Hibernate", "Backend Development",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Provides object-relational mapping.");

        add("Java Developer", "Git", "Development Tools",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Supports version control and collaboration.");

        add("Java Developer", "Exception Handling", "Programming",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Handles application errors safely.");

        add("Java Developer", "JDBC", "Database",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Connects Java applications with databases.");

        add("Java Developer", "JUnit", "Testing",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Used to write Java unit tests.");

        add("Java Developer", "Multithreading", "Programming",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Supports concurrent program execution.");

        add("Java Developer", "Maven", "Development Tools",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Manages dependencies and application builds.");

        // 3. Full Stack Developer
        add("Full Stack Developer", "HTML", "Frontend",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Structures web application pages.");

        add("Full Stack Developer", "CSS", "Frontend",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Styles responsive web interfaces.");

        add("Full Stack Developer", "JavaScript", "Frontend",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Adds interactive frontend functionality.");

        add("Full Stack Developer", "React", "Frontend",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Builds reusable user interface components.");

        add("Full Stack Developer", "Java", "Programming",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Provides backend application development.");

        add("Full Stack Developer", "Spring Boot", "Backend Development",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Builds backend services.");

        add("Full Stack Developer", "REST APIs", "Backend Development",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Connects frontend and backend applications.");

        add("Full Stack Developer", "SQL", "Database",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Manages application data.");

        add("Full Stack Developer", "Git", "Development Tools",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Supports source control.");

        add("Full Stack Developer", "Hibernate", "Backend Development",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Maps Java objects to database tables.");

        add("Full Stack Developer", "Maven", "Development Tools",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Manages project dependencies.");

        add("Full Stack Developer", "JUnit", "Testing",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Tests backend application logic.");

        // 4. Frontend Developer
        add("Frontend Developer", "HTML", "Frontend",
                ProficiencyLevel.ADVANCED, SkillPriority.ESSENTIAL,
                "Defines the structure of web pages.");

        add("Frontend Developer", "CSS", "Frontend",
                ProficiencyLevel.ADVANCED, SkillPriority.ESSENTIAL,
                "Creates responsive and attractive interfaces.");

        add("Frontend Developer", "JavaScript", "Frontend",
                ProficiencyLevel.ADVANCED, SkillPriority.ESSENTIAL,
                "Provides interactive web functionality.");

        add("Frontend Developer", "React", "Frontend",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Builds component-based user interfaces.");

        add("Frontend Developer", "TypeScript", "Frontend",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Adds static typing to JavaScript.");

        add("Frontend Developer", "Responsive Web Design", "Frontend",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Adapts interfaces to different screen sizes.");

        add("Frontend Developer", "REST APIs", "Backend Development",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Connects interfaces with backend services.");

        add("Frontend Developer", "Git", "Development Tools",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Tracks source code changes.");

        add("Frontend Developer", "Web Accessibility", "Frontend",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Makes applications usable by diverse users.");

        add("Frontend Developer", "Browser Developer Tools", "Development Tools",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Helps debug and inspect web applications.");

        add("Frontend Developer", "UI/UX Fundamentals", "Design",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Improves usability and interface quality.");

        add("Frontend Developer", "Frontend Testing", "Testing",
                ProficiencyLevel.BEGINNER, SkillPriority.OPTIONAL,
                "Checks frontend components and interactions.");

        // 5. Backend Developer
        add("Backend Developer", "Java", "Programming",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Builds backend application logic.");

        add("Backend Developer", "Spring Boot", "Backend Development",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Develops backend services.");

        add("Backend Developer", "REST APIs", "Backend Development",
                ProficiencyLevel.ADVANCED, SkillPriority.ESSENTIAL,
                "Designs reliable application interfaces.");

        add("Backend Developer", "SQL", "Database",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Stores and retrieves application data.");

        add("Backend Developer", "Hibernate", "Backend Development",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Supports database persistence.");

        add("Backend Developer", "Database Design", "Database",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Creates structured and efficient databases.");

        add("Backend Developer", "Git", "Development Tools",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Manages source code versions.");

        add("Backend Developer", "Maven", "Development Tools",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Manages Java dependencies and builds.");

        add("Backend Developer", "Spring Security", "Security",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Secures backend applications.");

        add("Backend Developer", "JUnit", "Testing",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Tests backend business logic.");

        add("Backend Developer", "Docker", "DevOps",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Packages applications into containers.");

        add("Backend Developer", "Caching", "Backend Development",
                ProficiencyLevel.BEGINNER, SkillPriority.OPTIONAL,
                "Improves application response times.");

        add("Backend Developer", "Microservices", "Backend Development",
                ProficiencyLevel.BEGINNER, SkillPriority.OPTIONAL,
                "Supports independently deployable services.");

        add("Backend Developer", "Authentication and Authorization", "Security",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Controls application access and permissions.");

        // 6. Android Developer
        add("Android Developer", "Kotlin", "Programming",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Modern language for Android development.");

        add("Android Developer", "Java", "Programming",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Supports Android application development.");

        add("Android Developer", "Android SDK", "Mobile Development",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Provides tools for Android applications.");

        add("Android Developer", "Android Studio", "Development Tools",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Primary IDE for Android development.");

        add("Android Developer", "Jetpack Compose", "Mobile Development",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Builds modern Android interfaces.");

        add("Android Developer", "XML", "Mobile Development",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Defines traditional Android layouts.");

        add("Android Developer", "REST APIs", "Backend Development",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Connects mobile applications to servers.");

        add("Android Developer", "Room Database", "Database",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Stores structured local application data.");

        add("Android Developer", "Git", "Development Tools",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Manages application source code.");

        add("Android Developer", "Material Design", "UI Design",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Creates consistent mobile interfaces.");

        add("Android Developer", "App Testing", "Testing",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Checks application quality and reliability.");

        add("Android Developer", "Firebase", "Backend Development",
                ProficiencyLevel.BEGINNER, SkillPriority.OPTIONAL,
                "Provides common mobile backend services.");

        add("Android Developer", "Coroutines", "Programming",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Handles asynchronous operations in Kotlin.");

        // 7. Software Engineer
        add("Software Engineer", "Programming Fundamentals", "Programming",
                ProficiencyLevel.ADVANCED, SkillPriority.ESSENTIAL,
                "Provides a foundation for software development.");

        add("Software Engineer", "Data Structures & Algorithms", "Programming",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Supports efficient problem solving.");

        add("Software Engineer", "Object-Oriented Programming", "Programming",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Supports maintainable software design.");

        add("Software Engineer", "Java", "Programming",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "One commonly used software development language.");

        add("Software Engineer", "Python", "Programming",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Supports scripting and application development.");

        add("Software Engineer", "SQL", "Database",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Manages structured application data.");

        add("Software Engineer", "Git", "Development Tools",
                ProficiencyLevel.BEGINNER, SkillPriority.ESSENTIAL,
                "Supports collaborative source control.");

        add("Software Engineer", "Software Development Life Cycle", "Software Engineering",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Explains software planning, development and delivery.");

        add("Software Engineer", "Debugging", "Programming",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Finds and fixes software defects.");

        add("Software Engineer", "Unit Testing", "Testing",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Checks individual software components.");

        add("Software Engineer", "Operating Systems", "Computer Science",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Explains processes, memory and system resources.");

        add("Software Engineer", "Computer Networks", "Computer Science",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Explains communication between computer systems.");

        add("Software Engineer", "Design Patterns", "Software Engineering",
                ProficiencyLevel.BEGINNER, SkillPriority.OPTIONAL,
                "Provides reusable solutions to common design problems.");

        add("Software Engineer", "Agile Methodology", "Software Engineering",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Supports iterative team-based software development.");


        // =====================================================
        // DOMAIN 1: COMPUTER SCIENCE AND INFORMATION TECHNOLOGY
        // Industry-ready fresher-level skill requirements
        // =====================================================

        // 8. AI / Machine Learning Engineer
        add("AI / Machine Learning Engineer", "Python", "Programming",
                ProficiencyLevel.ADVANCED, SkillPriority.ESSENTIAL,
                "Primary programming language for AI and machine learning development.");

        add("AI / Machine Learning Engineer", "Mathematics for Machine Learning", "Mathematics",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Provides foundations in linear algebra, calculus and probability.");

        add("AI / Machine Learning Engineer", "Statistics", "Mathematics",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Supports data analysis, model evaluation and statistical reasoning.");

        add("AI / Machine Learning Engineer", "Data Structures & Algorithms", "Programming",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Supports efficient data processing and algorithm implementation.");

        add("AI / Machine Learning Engineer", "SQL", "Database",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Retrieves and manages structured datasets.");

        add("AI / Machine Learning Engineer", "Machine Learning", "Artificial Intelligence",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Builds predictive models using supervised and unsupervised learning.");

        add("AI / Machine Learning Engineer", "Deep Learning", "Artificial Intelligence",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Introduces neural networks and deep learning architectures.");

        add("AI / Machine Learning Engineer", "Data Preprocessing", "Data Science",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Cleans and transforms data before model training.");

        add("AI / Machine Learning Engineer", "NumPy and Pandas", "Data Science",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Supports numerical computation and dataset manipulation.");

        add("AI / Machine Learning Engineer", "Scikit-learn", "Artificial Intelligence",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Implements and evaluates traditional machine learning models.");

        add("AI / Machine Learning Engineer", "Git", "Development Tools",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Tracks code changes and supports team collaboration.");

        add("AI / Machine Learning Engineer", "Model Evaluation", "Artificial Intelligence",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Measures model performance using suitable evaluation metrics.");


        // 9. Application Support Engineer
        add("Application Support Engineer", "Operating Systems", "Computer Science",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Supports application troubleshooting across operating environments.");

        add("Application Support Engineer", "SQL", "Database",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Investigates application data and database-related issues.");

        add("Application Support Engineer", "Computer Networks", "Networking",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Helps diagnose connectivity and communication problems.");

        add("Application Support Engineer", "Linux", "Operating Systems",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Supports command-line troubleshooting and server operations.");

        add("Application Support Engineer", "Troubleshooting", "IT Operations",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Identifies root causes and resolves application incidents.");

        add("Application Support Engineer", "REST APIs", "Backend Development",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Helps investigate API failures and integration issues.");

        add("Application Support Engineer", "Java", "Programming",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Helps understand application logic and runtime errors.");

        add("Application Support Engineer", "Log Analysis", "IT Operations",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Examines application logs to identify errors and failures.");

        add("Application Support Engineer", "IT Service Management", "IT Operations",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Introduces incident, problem and change management practices.");

        add("Application Support Engineer", "Monitoring Tools", "IT Operations",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Monitors application health, availability and performance.");

        add("Application Support Engineer", "Git", "Development Tools",
                ProficiencyLevel.BEGINNER, SkillPriority.OPTIONAL,
                "Provides basic source-code version control knowledge.");


        // 10. Cloud Support Engineer
        add("Cloud Support Engineer", "Cloud Computing", "Cloud",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Explains cloud infrastructure, services and deployment models.");

        add("Cloud Support Engineer", "AWS", "Cloud",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Provides practical knowledge of commonly used cloud services.");

        add("Cloud Support Engineer", "Linux", "Operating Systems",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Supports cloud server administration and troubleshooting.");

        add("Cloud Support Engineer", "Computer Networks", "Networking",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Supports cloud connectivity, routing and network troubleshooting.");

        add("Cloud Support Engineer", "Networking Fundamentals", "Networking",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Covers IP addressing, DNS, TCP/IP and network communication.");

        add("Cloud Support Engineer", "Virtualization", "Cloud",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Explains virtual machines and virtual infrastructure.");

        add("Cloud Support Engineer", "IAM", "Cloud Security",
                ProficiencyLevel.BEGINNER, SkillPriority.ESSENTIAL,
                "Manages cloud identities, roles and access permissions.");

        add("Cloud Support Engineer", "Cloud Monitoring", "Cloud",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Monitors cloud resource health and service availability.");

        add("Cloud Support Engineer", "Troubleshooting", "IT Operations",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Diagnoses infrastructure and cloud service issues.");

        add("Cloud Support Engineer", "Python", "Programming",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Supports basic automation and cloud administration scripts.");

        add("Cloud Support Engineer", "Git", "Development Tools",
                ProficiencyLevel.BEGINNER, SkillPriority.OPTIONAL,
                "Tracks scripts and configuration changes.");


        // 11. Computer Engineer
        add("Computer Engineer", "Programming Fundamentals", "Programming",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Builds a foundation for developing and understanding computer systems.");

        add("Computer Engineer", "Data Structures & Algorithms", "Programming",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Supports efficient problem solving and software implementation.");

        add("Computer Engineer", "Computer Organization and Architecture", "Computer Science",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Explains processor architecture, memory and instruction execution.");

        add("Computer Engineer", "Operating Systems", "Computer Science",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Explains process management, memory and system resources.");

        add("Computer Engineer", "Computer Networks", "Networking",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Explains network communication and protocols.");

        add("Computer Engineer", "Digital Electronics", "Electronics",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Provides knowledge of logic gates and digital circuits.");

        add("Computer Engineer", "Microprocessors", "Computer Engineering",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Introduces processor architecture and instruction sets.");

        add("Computer Engineer", "C Programming", "Programming",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Supports low-level programming and system fundamentals.");

        add("Computer Engineer", "SQL", "Database",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Provides basic relational database querying knowledge.");

        add("Computer Engineer", "Linux", "Operating Systems",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Introduces Linux commands and system administration.");

        add("Computer Engineer", "Git", "Development Tools",
                ProficiencyLevel.BEGINNER, SkillPriority.OPTIONAL,
                "Supports version control and collaborative development.");


        // 12. Information Technology Engineer
        add("Information Technology Engineer", "Networking Fundamentals", "Networking",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Covers network architecture, IP addressing and communication.");

        add("Information Technology Engineer", "Operating Systems", "Computer Science",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Supports operating system administration and troubleshooting.");

        add("Information Technology Engineer", "Linux", "Operating Systems",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Provides command-line and server management skills.");

        add("Information Technology Engineer", "Windows Server", "Operating Systems",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Introduces enterprise Windows server administration.");

        add("Information Technology Engineer", "SQL", "Database",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Supports database querying and data management.");

        add("Information Technology Engineer", "Cybersecurity Fundamentals", "Security",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Introduces secure IT operations and common security threats.");

        add("Information Technology Engineer", "Cloud Computing", "Cloud",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Introduces cloud platforms and infrastructure services.");

        add("Information Technology Engineer", "IT Service Management", "IT Operations",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Supports incident and service request management.");

        add("Information Technology Engineer", "Troubleshooting", "IT Operations",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Resolves hardware, software and network-related problems.");

        add("Information Technology Engineer", "Python", "Programming",
                ProficiencyLevel.BEGINNER, SkillPriority.OPTIONAL,
                "Supports basic scripting and IT task automation.");

        add("Information Technology Engineer", "Git", "Development Tools",
                ProficiencyLevel.BEGINNER, SkillPriority.OPTIONAL,
                "Provides basic source control knowledge.");


        // 13. IT Engineer
        add("IT Engineer", "Computer Networks", "Networking",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Supports network configuration and troubleshooting.");

        add("IT Engineer", "Operating Systems", "Computer Science",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Supports system administration and operating system management.");

        add("IT Engineer", "Linux", "Operating Systems",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Provides Linux administration and command-line skills.");

        add("IT Engineer", "Windows Server", "Operating Systems",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Supports basic enterprise server administration.");

        add("IT Engineer", "Networking Fundamentals", "Networking",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Covers TCP/IP, DNS, DHCP and network protocols.");

        add("IT Engineer", "Hardware Troubleshooting", "IT Support",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Diagnoses and resolves computer hardware issues.");

        add("IT Engineer", "Cybersecurity Fundamentals", "Security",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Supports secure system and network operations.");

        add("IT Engineer", "Cloud Computing", "Cloud",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Introduces cloud infrastructure and services.");

        add("IT Engineer", "IT Service Management", "IT Operations",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Supports IT incident and service management.");

        add("IT Engineer", "Troubleshooting", "IT Operations",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Provides a structured approach to diagnosing technical issues.");

        add("IT Engineer", "Python", "Programming",
                ProficiencyLevel.BEGINNER, SkillPriority.OPTIONAL,
                "Supports basic scripting and repetitive task automation.");


        // 14. Java Full Stack Developer
        // Existing proficiency records are preserved by add().
        add("Java Full Stack Developer", "Java", "Programming",
                ProficiencyLevel.ADVANCED, SkillPriority.ESSENTIAL,
                "Core backend programming language for enterprise applications.");

        add("Java Full Stack Developer", "Spring Boot", "Backend Development",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Builds production-ready Java backend services.");

        add("Java Full Stack Developer", "HTML", "Frontend",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Structures accessible web application interfaces.");

        add("Java Full Stack Developer", "CSS", "Frontend",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Creates responsive and consistent web interfaces.");

        add("Java Full Stack Developer", "JavaScript", "Frontend",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Implements interactive frontend functionality.");

        add("Java Full Stack Developer", "React", "Frontend",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Builds reusable frontend components.");

        add("Java Full Stack Developer", "REST APIs", "Backend Development",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Connects frontend applications with backend services.");

        add("Java Full Stack Developer", "SQL", "Database",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Stores and retrieves relational application data.");

        add("Java Full Stack Developer", "Hibernate", "Backend Development",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Provides object-relational mapping for Java applications.");

        add("Java Full Stack Developer", "Git", "Development Tools",
                ProficiencyLevel.BEGINNER, SkillPriority.ESSENTIAL,
                "Supports version control and team collaboration.");

        add("Java Full Stack Developer", "Maven", "Development Tools",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Manages Java project dependencies and builds.");

        add("Java Full Stack Developer", "JUnit", "Testing",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Tests backend business logic and application components.");

        add("Java Full Stack Developer", "Responsive Web Design", "Frontend",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Ensures web interfaces adapt to different screen sizes.");

        add("Java Full Stack Developer", "Spring Security", "Security",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Introduces authentication and authorization for Java applications.");

        add("Java Full Stack Developer", "Database Design", "Database",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Supports structured and efficient relational database design.");

        add("Java Full Stack Developer", "Debugging", "Programming",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Identifies and fixes frontend and backend defects.");


        // 15. Python Backend Developer
        add("Python Backend Developer", "Python", "Programming",
                ProficiencyLevel.ADVANCED, SkillPriority.ESSENTIAL,
                "Core language for backend application development.");

        add("Python Backend Developer", "Object-Oriented Programming", "Programming",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Supports modular and maintainable backend application design.");

        add("Python Backend Developer", "Data Structures & Algorithms", "Programming",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Supports efficient backend logic and problem solving.");

        add("Python Backend Developer", "REST APIs", "Backend Development",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Builds HTTP interfaces for client-server communication.");

        add("Python Backend Developer", "SQL", "Database",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.ESSENTIAL,
                "Manages and retrieves relational application data.");

        add("Python Backend Developer", "Database Design", "Database",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Designs structured and normalized database schemas.");

        add("Python Backend Developer", "Django", "Backend Development",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Provides a framework for building Python web applications.");

        add("Python Backend Developer", "FastAPI", "Backend Development",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Builds modern Python APIs with type-based validation.");

        add("Python Backend Developer", "Git", "Development Tools",
                ProficiencyLevel.BEGINNER, SkillPriority.ESSENTIAL,
                "Tracks code changes and supports collaboration.");

        add("Python Backend Developer", "pytest", "Testing",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Tests backend functions and application behavior.");

        add("Python Backend Developer", "Authentication and Authorization", "Security",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Secures APIs and controls user access.");

        add("Python Backend Developer", "Docker", "DevOps",
                ProficiencyLevel.BEGINNER, SkillPriority.IMPORTANT,
                "Packages backend applications into portable containers.");

        add("Python Backend Developer", "Debugging", "Programming",
                ProficiencyLevel.INTERMEDIATE, SkillPriority.IMPORTANT,
                "Finds and resolves backend application defects.");

        add("Python Backend Developer", "Caching", "Backend Development",
                ProficiencyLevel.BEGINNER, SkillPriority.OPTIONAL,
                "Improves backend response times and reduces repeated data access.");


// ==========================================
// ELECTRONICS AND TELECOMMUNICATION ENGINEERING
// ==========================================

// 1. Electronics and Telecommunication Engineer
        add("Electronics and Telecommunication Engineer", new String[][]{
                {"Analog Electronics", "Electronics", "INTERMEDIATE", "ESSENTIAL", "Understand analog electronic circuits and components."},
                {"Digital Electronics", "Electronics", "INTERMEDIATE", "ESSENTIAL", "Understand digital logic and electronic systems."},
                {"Electronic Devices and Circuits", "Electronics", "INTERMEDIATE", "ESSENTIAL", "Analyze electronic devices and circuit behavior."},
                {"Circuit Analysis", "Electronics", "INTERMEDIATE", "ESSENTIAL", "Analyze electrical and electronic circuits."},
                {"Microprocessors", "Embedded Systems", "BEGINNER", "IMPORTANT", "Understand microprocessor architecture and operation."},
                {"Microcontrollers", "Embedded Systems", "BEGINNER", "IMPORTANT", "Understand microcontroller architecture and peripherals."},
                {"Communication Systems", "Communication", "INTERMEDIATE", "ESSENTIAL", "Understand analog and digital communication systems."},
                {"Signals and Systems", "Communication", "BEGINNER", "IMPORTANT", "Understand signals and system behavior."},
                {"C Programming", "Programming", "INTERMEDIATE", "IMPORTANT", "Write programs for electronics applications."},
                {"MATLAB", "Tools", "BEGINNER", "IMPORTANT", "Use MATLAB for engineering analysis and simulation."},
                {"PCB Design", "Electronics", "BEGINNER", "IMPORTANT", "Design and understand printed circuit boards."},
                {"Testing and Troubleshooting", "Testing", "INTERMEDIATE", "IMPORTANT", "Test circuits and diagnose hardware problems."}
        });

// 2. Communication Engineer
        add("Communication Engineer", new String[][]{
                {"Communication Systems", "Communication", "INTERMEDIATE", "ESSENTIAL", "Understand communication system components and operation."},
                {"Digital Communication", "Communication", "INTERMEDIATE", "ESSENTIAL", "Understand digital modulation and transmission techniques."},
                {"Analog Communication", "Communication", "BEGINNER", "IMPORTANT", "Understand analog modulation and communication techniques."},
                {"Signals and Systems", "Communication", "INTERMEDIATE", "ESSENTIAL", "Analyze signals and linear systems."},
                {"Digital Signal Processing", "Communication", "BEGINNER", "IMPORTANT", "Understand digital signal processing techniques."},
                {"Wireless Communication", "Communication", "INTERMEDIATE", "ESSENTIAL", "Understand wireless communication principles."},
                {"RF Fundamentals", "Electronics", "BEGINNER", "IMPORTANT", "Understand radio frequency fundamentals."},
                {"Networking Fundamentals", "Networking", "BEGINNER", "IMPORTANT", "Understand basic computer networking concepts."},
                {"MATLAB", "Tools", "BEGINNER", "IMPORTANT", "Simulate and analyze communication systems."},
                {"Python", "Programming", "BEGINNER", "OPTIONAL", "Use Python for basic technical analysis."},
                {"Antenna Fundamentals", "Electronics", "BEGINNER", "IMPORTANT", "Understand antenna characteristics and operation."}
        });

// 3. RF Engineer
        add("RF Engineer", new String[][]{
                {"RF Fundamentals", "Electronics", "INTERMEDIATE", "ESSENTIAL", "Understand radio frequency principles and components."},
                {"Electromagnetic Theory", "Electronics", "INTERMEDIATE", "ESSENTIAL", "Understand electromagnetic fields and wave propagation."},
                {"Antenna Design", "Electronics", "INTERMEDIATE", "ESSENTIAL", "Understand antenna design and performance."},
                {"Microwave Engineering", "Electronics", "BEGINNER", "IMPORTANT", "Understand microwave components and systems."},
                {"Transmission Lines", "Electronics", "INTERMEDIATE", "ESSENTIAL", "Analyze transmission lines and signal propagation."},
                {"RF Circuit Design", "Electronics", "BEGINNER", "IMPORTANT", "Understand RF circuit design principles."},
                {"Network Analysis", "Electronics", "INTERMEDIATE", "ESSENTIAL", "Analyze electronic networks and circuits."},
                {"Signal Processing", "Communication", "BEGINNER", "IMPORTANT", "Understand signal processing fundamentals."},
                {"MATLAB", "Tools", "BEGINNER", "IMPORTANT", "Use MATLAB for RF analysis and simulation."},
                {"Circuit Simulation", "Tools", "BEGINNER", "IMPORTANT", "Simulate and analyze electronic circuits."},
                {"Testing and Troubleshooting", "Testing", "INTERMEDIATE", "IMPORTANT", "Test RF circuits and troubleshoot faults."}
        });

// 4. Telecom Network Engineer
        add("Telecom Network Engineer", new String[][]{
                {"Networking Fundamentals", "Networking", "INTERMEDIATE", "ESSENTIAL", "Understand networking concepts and architectures."},
                {"TCP/IP", "Networking", "INTERMEDIATE", "ESSENTIAL", "Understand TCP/IP protocols and communication."},
                {"Routing and Switching", "Networking", "INTERMEDIATE", "ESSENTIAL", "Configure and understand routing and switching."},
                {"Wireless Communication", "Communication", "INTERMEDIATE", "ESSENTIAL", "Understand wireless communication technologies."},
                {"Mobile Communication", "Communication", "INTERMEDIATE", "ESSENTIAL", "Understand mobile network architecture and operation."},
                {"5G Fundamentals", "Communication", "BEGINNER", "IMPORTANT", "Understand basic 5G network concepts."},
                {"Network Troubleshooting", "Networking", "INTERMEDIATE", "ESSENTIAL", "Identify and resolve network connectivity issues."},
                {"Network Monitoring", "Networking", "BEGINNER", "IMPORTANT", "Monitor network performance and availability."},
                {"Linux", "Operating Systems", "BEGINNER", "IMPORTANT", "Use Linux commands for network operations."},
                {"Network Security", "Security", "BEGINNER", "IMPORTANT", "Understand basic network security practices."},
                {"Telecom Protocols", "Networking", "BEGINNER", "IMPORTANT", "Understand protocols used in telecom networks."}
        });

// 5. Embedded Electronics Engineer
        add("Embedded Electronics Engineer", new String[][]{
                {"C Programming", "Programming", "INTERMEDIATE", "ESSENTIAL", "Write programs for embedded applications."},
                {"Embedded C", "Programming", "INTERMEDIATE", "ESSENTIAL", "Develop firmware using Embedded C."},
                {"Microcontrollers", "Embedded Systems", "INTERMEDIATE", "ESSENTIAL", "Program microcontrollers and interface peripherals."},
                {"Microprocessors", "Embedded Systems", "BEGINNER", "IMPORTANT", "Understand microprocessor architecture."},
                {"Digital Electronics", "Electronics", "INTERMEDIATE", "ESSENTIAL", "Understand digital logic and hardware circuits."},
                {"Embedded Systems", "Embedded Systems", "INTERMEDIATE", "ESSENTIAL", "Understand embedded hardware and software integration."},
                {"UART, SPI and I2C", "Embedded Systems", "BEGINNER", "IMPORTANT", "Understand common embedded communication interfaces."},
                {"RTOS Fundamentals", "Embedded Systems", "BEGINNER", "IMPORTANT", "Understand real-time operating system concepts."},
                {"PCB Design", "Electronics", "BEGINNER", "IMPORTANT", "Understand printed circuit board design."},
                {"Hardware Debugging", "Testing", "BEGINNER", "IMPORTANT", "Debug embedded hardware and identify faults."},
                {"Linux", "Operating Systems", "BEGINNER", "OPTIONAL", "Understand basic Linux operations for embedded development."}
        });



// ==========================================
// DOMAIN 3: ELECTRICAL ENGINEERING
// Industry-ready fresher-level skill requirements
// ==========================================

// 1. Electrical Design Engineer
        add("Electrical Design Engineer", new String[][]{
                {"Electrical Circuit Analysis", "Electrical", "INTERMEDIATE", "ESSENTIAL", "Analyze electrical circuits and calculate voltage, current and power."},
                {"Electrical Machines", "Electrical", "INTERMEDIATE", "ESSENTIAL", "Understand transformers, motors and generators."},
                {"Power Systems", "Electrical", "INTERMEDIATE", "ESSENTIAL", "Understand generation, transmission and distribution of electrical power."},
                {"Electrical Drawing", "Design", "INTERMEDIATE", "ESSENTIAL", "Read and prepare electrical engineering drawings."},
                {"AutoCAD", "Design Tools", "BEGINNER", "IMPORTANT", "Prepare electrical layouts and technical drawings."},
                {"Electrical Wiring", "Electrical", "INTERMEDIATE", "ESSENTIAL", "Understand wiring methods and electrical connections."},
                {"Power Distribution", "Electrical", "INTERMEDIATE", "ESSENTIAL", "Understand electrical distribution systems and components."},
                {"Electrical Safety", "Safety", "INTERMEDIATE", "ESSENTIAL", "Apply electrical safety standards and precautions."},
                {"MATLAB", "Engineering Tools", "BEGINNER", "IMPORTANT", "Perform basic electrical calculations and simulations."},
                {"Testing and Troubleshooting", "Testing", "INTERMEDIATE", "IMPORTANT", "Test electrical systems and diagnose faults."},
                {"PLC Fundamentals", "Automation", "BEGINNER", "IMPORTANT", "Understand programmable logic controller fundamentals."},
                {"Technical Documentation", "Documentation", "BEGINNER", "IMPORTANT", "Prepare electrical reports and technical documentation."}
        });

// 2. Power Electronics Engineer
        add("Power Electronics Engineer", new String[][]{
                {"Power Electronics", "Electrical", "INTERMEDIATE", "ESSENTIAL", "Understand power electronic converters and applications."},
                {"Power Semiconductor Devices", "Electronics", "INTERMEDIATE", "ESSENTIAL", "Understand diodes, MOSFETs, IGBTs and thyristors."},
                {"Analog Electronics", "Electronics", "INTERMEDIATE", "ESSENTIAL", "Understand analog circuits used in power electronic systems."},
                {"Circuit Analysis", "Electrical", "INTERMEDIATE", "ESSENTIAL", "Analyze electrical circuits and their behavior."},
                {"DC-DC Converters", "Power Electronics", "INTERMEDIATE", "ESSENTIAL", "Understand buck, boost and buck-boost converter operation."},
                {"Inverters", "Power Electronics", "INTERMEDIATE", "ESSENTIAL", "Understand DC-to-AC conversion and inverter operation."},
                {"Electrical Machines", "Electrical", "BEGINNER", "IMPORTANT", "Understand electrical machine fundamentals."},
                {"Control Systems", "Automation", "INTERMEDIATE", "ESSENTIAL", "Understand feedback and control of power electronic systems."},
                {"MATLAB and Simulink", "Engineering Tools", "BEGINNER", "IMPORTANT", "Simulate power electronic circuits and systems."},
                {"PCB Design", "Electronics", "BEGINNER", "IMPORTANT", "Understand printed circuit board design."},
                {"Testing and Troubleshooting", "Testing", "INTERMEDIATE", "IMPORTANT", "Test power circuits and identify faults."},
                {"Electrical Safety", "Safety", "INTERMEDIATE", "IMPORTANT", "Follow electrical safety procedures during testing."}
        });

// 3. Electrical Maintenance Engineer
        add("Electrical Maintenance Engineer", new String[][]{
                {"Electrical Machines", "Electrical", "INTERMEDIATE", "ESSENTIAL", "Maintain motors, transformers and generators."},
                {"Electrical Circuit Analysis", "Electrical", "INTERMEDIATE", "ESSENTIAL", "Understand and diagnose electrical circuits."},
                {"Power Systems", "Electrical", "INTERMEDIATE", "ESSENTIAL", "Understand industrial electrical power systems."},
                {"Electrical Maintenance", "Maintenance", "INTERMEDIATE", "ESSENTIAL", "Perform preventive and corrective electrical maintenance."},
                {"Electrical Testing", "Testing", "INTERMEDIATE", "ESSENTIAL", "Perform electrical testing and inspections."},
                {"Electrical Safety", "Safety", "INTERMEDIATE", "ESSENTIAL", "Follow electrical safety procedures and standards."},
                {"Motors and Drives", "Electrical", "INTERMEDIATE", "ESSENTIAL", "Understand industrial motors and drive systems."},
                {"PLC Fundamentals", "Automation", "BEGINNER", "IMPORTANT", "Understand PLC-based industrial automation."},
                {"Troubleshooting", "Maintenance", "INTERMEDIATE", "ESSENTIAL", "Identify and resolve electrical equipment faults."},
                {"Preventive Maintenance", "Maintenance", "INTERMEDIATE", "IMPORTANT", "Plan and perform routine equipment maintenance."},
                {"AutoCAD", "Design Tools", "BEGINNER", "OPTIONAL", "Read basic electrical maintenance drawings."},
                {"Technical Documentation", "Documentation", "BEGINNER", "IMPORTANT", "Maintain inspection and maintenance records."}
        });

// 4. Control Systems Engineer
        add("Control Systems Engineer", new String[][]{
                {"Control Systems", "Automation", "INTERMEDIATE", "ESSENTIAL", "Understand open-loop and closed-loop control systems."},
                {"Signals and Systems", "Control Engineering", "INTERMEDIATE", "ESSENTIAL", "Analyze continuous-time and discrete-time signals."},
                {"Feedback Control", "Control Engineering", "INTERMEDIATE", "ESSENTIAL", "Understand feedback principles and system stability."},
                {"Mathematics for Engineering", "Mathematics", "INTERMEDIATE", "ESSENTIAL", "Apply differential equations and mathematical methods."},
                {"MATLAB and Simulink", "Engineering Tools", "INTERMEDIATE", "ESSENTIAL", "Model and simulate control systems."},
                {"PLC Fundamentals", "Automation", "INTERMEDIATE", "ESSENTIAL", "Understand PLC programming and industrial control."},
                {"PID Controllers", "Control Engineering", "INTERMEDIATE", "ESSENTIAL", "Understand proportional, integral and derivative control."},
                {"Industrial Automation", "Automation", "BEGINNER", "IMPORTANT", "Understand automated industrial processes."},
                {"Sensors and Actuators", "Instrumentation", "INTERMEDIATE", "ESSENTIAL", "Understand sensing and actuation in control systems."},
                {"SCADA", "Automation", "BEGINNER", "IMPORTANT", "Understand supervisory control and data acquisition."},
                {"C Programming", "Programming", "BEGINNER", "IMPORTANT", "Develop basic programs for control applications."},
                {"Testing and Troubleshooting", "Testing", "INTERMEDIATE", "IMPORTANT", "Test control systems and diagnose faults."}
        });

// 5. Instrumentation Engineer
        add("Instrumentation Engineer", new String[][]{
                {"Sensors and Transducers", "Instrumentation", "INTERMEDIATE", "ESSENTIAL", "Understand sensors and transducers used in measurement systems."},
                {"Industrial Instrumentation", "Instrumentation", "INTERMEDIATE", "ESSENTIAL", "Understand industrial measurement and instrumentation systems."},
                {"Measurement and Instrumentation", "Instrumentation", "INTERMEDIATE", "ESSENTIAL", "Measure physical quantities using suitable instruments."},
                {"Control Systems", "Automation", "INTERMEDIATE", "ESSENTIAL", "Understand feedback and process control systems."},
                {"PLC Fundamentals", "Automation", "INTERMEDIATE", "ESSENTIAL", "Understand PLC architecture and basic programming."},
                {"SCADA", "Automation", "BEGINNER", "IMPORTANT", "Monitor and supervise industrial processes."},
                {"Process Control", "Instrumentation", "INTERMEDIATE", "ESSENTIAL", "Understand industrial process measurement and control."},
                {"Calibration", "Instrumentation", "INTERMEDIATE", "ESSENTIAL", "Calibrate instruments and verify measurement accuracy."},
                {"Electrical Circuit Analysis", "Electrical", "BEGINNER", "IMPORTANT", "Understand electrical measurement circuits."},
                {"Industrial Communication Protocols", "Networking", "BEGINNER", "IMPORTANT", "Understand communication between industrial devices."},
                {"MATLAB", "Engineering Tools", "BEGINNER", "OPTIONAL", "Perform basic engineering calculations and analysis."},
                {"Testing and Troubleshooting", "Testing", "INTERMEDIATE", "IMPORTANT", "Test instruments and troubleshoot measurement systems."}
        });




        // ==========================================
// DOMAIN 4: MECHANICAL ENGINEERING
// ==========================================

// 1. Mechanical Engineer
        add("Mechanical Engineer", new String[][]{
                {"Engineering Mechanics", "Mechanical", "INTERMEDIATE", "ESSENTIAL", "Understand forces, motion and mechanical systems."},
                {"Thermodynamics", "Mechanical", "INTERMEDIATE", "ESSENTIAL", "Understand energy, heat and work."},
                {"Fluid Mechanics", "Mechanical", "INTERMEDIATE", "ESSENTIAL", "Analyze fluid behavior and flow."},
                {"Strength of Materials", "Mechanical", "INTERMEDIATE", "ESSENTIAL", "Evaluate stress, strain and deformation."},
                {"Machine Design", "Design", "INTERMEDIATE", "ESSENTIAL", "Design reliable mechanical components."},
                {"Manufacturing Processes", "Manufacturing", "INTERMEDIATE", "ESSENTIAL", "Understand common manufacturing methods."},
                {"Engineering Drawing", "Design", "INTERMEDIATE", "ESSENTIAL", "Interpret and prepare engineering drawings."},
                {"AutoCAD", "Design Tools", "BEGINNER", "IMPORTANT", "Create and edit technical drawings."},
                {"SolidWorks", "Design Tools", "BEGINNER", "IMPORTANT", "Create 3D mechanical models."},
                {"Material Science", "Materials", "INTERMEDIATE", "IMPORTANT", "Understand engineering material properties."},
                {"Quality Control", "Quality", "BEGINNER", "IMPORTANT", "Inspect and maintain product quality."},
                {"Technical Documentation", "Documentation", "BEGINNER", "IMPORTANT", "Prepare engineering reports and records."}
        });

// 2. Thermal Engineer
        add("Thermal Engineer", new String[][]{
                {"Thermodynamics", "Mechanical", "ADVANCED", "ESSENTIAL", "Analyze energy conversion and thermal cycles."},
                {"Heat Transfer", "Thermal", "ADVANCED", "ESSENTIAL", "Analyze conduction, convection and radiation."},
                {"Fluid Mechanics", "Mechanical", "INTERMEDIATE", "ESSENTIAL", "Understand fluid flow and pressure."},
                {"Thermal Engineering", "Thermal", "ADVANCED", "ESSENTIAL", "Analyze thermal systems and equipment."},
                {"Refrigeration and Air Conditioning", "Thermal", "INTERMEDIATE", "ESSENTIAL", "Understand cooling and refrigeration systems."},
                {"Power Plant Engineering", "Energy", "INTERMEDIATE", "ESSENTIAL", "Understand power generation systems."},
                {"Computational Fluid Dynamics", "Simulation", "BEGINNER", "IMPORTANT", "Simulate fluid and heat flow."},
                {"ANSYS", "Simulation", "BEGINNER", "IMPORTANT", "Perform engineering simulation and analysis."},
                {"MATLAB", "Engineering Tools", "BEGINNER", "IMPORTANT", "Perform engineering calculations."},
                {"Energy Auditing", "Energy", "BEGINNER", "IMPORTANT", "Evaluate energy consumption and efficiency."},
                {"Technical Documentation", "Documentation", "BEGINNER", "IMPORTANT", "Prepare thermal engineering reports."},
                {"Testing and Troubleshooting", "Testing", "INTERMEDIATE", "IMPORTANT", "Test and diagnose thermal equipment."}
        });

// 3. HVAC Engineer
        add("HVAC Engineer", new String[][]{
                {"HVAC Systems", "HVAC", "ADVANCED", "ESSENTIAL", "Understand heating, ventilation and air conditioning."},
                {"Refrigeration and Air Conditioning", "HVAC", "INTERMEDIATE", "ESSENTIAL", "Understand refrigeration and cooling cycles."},
                {"Heat Transfer", "Thermal", "INTERMEDIATE", "ESSENTIAL", "Calculate heat loads and transfer."},
                {"Fluid Mechanics", "Mechanical", "INTERMEDIATE", "ESSENTIAL", "Understand airflow and fluid movement."},
                {"HVAC Load Calculation", "HVAC", "INTERMEDIATE", "ESSENTIAL", "Calculate heating and cooling requirements."},
                {"Duct Design", "HVAC", "INTERMEDIATE", "ESSENTIAL", "Design HVAC duct distribution."},
                {"AutoCAD", "Design Tools", "BEGINNER", "IMPORTANT", "Prepare HVAC layout drawings."},
                {"Revit MEP", "Design Tools", "BEGINNER", "IMPORTANT", "Create building-services models."},
                {"Electrical Fundamentals", "Electrical", "BEGINNER", "IMPORTANT", "Understand electrical HVAC components."},
                {"Building Management Systems", "Automation", "BEGINNER", "IMPORTANT", "Understand automated building controls."},
                {"Energy Efficiency", "Energy", "INTERMEDIATE", "IMPORTANT", "Improve HVAC energy performance."},
                {"Technical Documentation", "Documentation", "BEGINNER", "IMPORTANT", "Prepare HVAC project documentation."}
        });

// 4. Design and Development Engineer
        add("Design and Development Engineer", new String[][]{
                {"Engineering Mechanics", "Mechanical", "INTERMEDIATE", "ESSENTIAL", "Apply mechanics to product design."},
                {"Machine Design", "Design", "ADVANCED", "ESSENTIAL", "Design mechanical components and assemblies."},
                {"Strength of Materials", "Mechanical", "INTERMEDIATE", "ESSENTIAL", "Evaluate component strength and durability."},
                {"Engineering Drawing", "Design", "INTERMEDIATE", "ESSENTIAL", "Prepare and interpret technical drawings."},
                {"SolidWorks", "Design Tools", "INTERMEDIATE", "ESSENTIAL", "Develop 3D mechanical models."},
                {"AutoCAD", "Design Tools", "BEGINNER", "IMPORTANT", "Prepare detailed engineering drawings."},
                {"Product Design", "Design", "INTERMEDIATE", "ESSENTIAL", "Develop functional product concepts."},
                {"Material Science", "Materials", "INTERMEDIATE", "IMPORTANT", "Select suitable engineering materials."},
                {"Finite Element Analysis", "Simulation", "BEGINNER", "IMPORTANT", "Analyze structural performance."},
                {"ANSYS", "Simulation", "BEGINNER", "IMPORTANT", "Perform product simulations."},
                {"Manufacturing Processes", "Manufacturing", "INTERMEDIATE", "IMPORTANT", "Design for manufacturability."},
                {"Technical Documentation", "Documentation", "BEGINNER", "IMPORTANT", "Maintain design specifications and reports."}
        });

// 5. Maintenance Engineer
        add("Maintenance Engineer", new String[][]{
                {"Mechanical Maintenance", "Maintenance", "INTERMEDIATE", "ESSENTIAL", "Maintain mechanical equipment and systems."},
                {"Engineering Mechanics", "Mechanical", "INTERMEDIATE", "ESSENTIAL", "Understand mechanical forces and motion."},
                {"Thermodynamics", "Mechanical", "BEGINNER", "IMPORTANT", "Understand thermal equipment operation."},
                {"Fluid Mechanics", "Mechanical", "INTERMEDIATE", "IMPORTANT", "Understand pumps and fluid systems."},
                {"Machine Design", "Design", "BEGINNER", "IMPORTANT", "Understand mechanical components."},
                {"Preventive Maintenance", "Maintenance", "INTERMEDIATE", "ESSENTIAL", "Plan routine equipment maintenance."},
                {"Predictive Maintenance", "Maintenance", "BEGINNER", "IMPORTANT", "Identify possible equipment failures."},
                {"Troubleshooting", "Maintenance", "INTERMEDIATE", "ESSENTIAL", "Diagnose and resolve equipment faults."},
                {"Hydraulics and Pneumatics", "Mechanical", "INTERMEDIATE", "ESSENTIAL", "Maintain hydraulic and pneumatic systems."},
                {"Industrial Safety", "Safety", "INTERMEDIATE", "ESSENTIAL", "Follow industrial maintenance safety practices."},
                {"Quality Control", "Quality", "BEGINNER", "IMPORTANT", "Inspect equipment and maintenance quality."},
                {"Technical Documentation", "Documentation", "BEGINNER", "IMPORTANT", "Maintain maintenance records and reports."}
        });



        // ==========================================
// DOMAIN 5: CIVIL ENGINEERING
// ==========================================

// 1. Geotechnical Engineer
        add("Geotechnical Engineer", new String[][]{
                {"Soil Mechanics", "Civil", "ADVANCED", "ESSENTIAL", "Analyze soil behavior and engineering properties."},
                {"Foundation Engineering", "Civil", "INTERMEDIATE", "ESSENTIAL", "Design safe and stable foundations."},
                {"Engineering Geology", "Civil", "INTERMEDIATE", "ESSENTIAL", "Understand geological conditions of construction sites."},
                {"Rock Mechanics", "Civil", "INTERMEDIATE", "IMPORTANT", "Analyze rock behavior and stability."},
                {"Geotechnical Investigation", "Civil", "INTERMEDIATE", "ESSENTIAL", "Conduct subsurface site investigations."},
                {"Slope Stability Analysis", "Civil", "INTERMEDIATE", "ESSENTIAL", "Evaluate slope failure risks."},
                {"Earth Retaining Structures", "Civil", "INTERMEDIATE", "IMPORTANT", "Understand retaining wall design."},
                {"Soil Testing", "Testing", "INTERMEDIATE", "ESSENTIAL", "Perform laboratory and field soil tests."},
                {"AutoCAD", "Design Tools", "BEGINNER", "IMPORTANT", "Prepare engineering drawings."},
                {"PLAXIS", "Simulation", "BEGINNER", "IMPORTANT", "Perform geotechnical numerical analysis."},
                {"Site Investigation", "Civil", "INTERMEDIATE", "IMPORTANT", "Assess construction site conditions."},
                {"Technical Documentation", "Documentation", "BEGINNER", "IMPORTANT", "Prepare geotechnical investigation reports."}
        });

// 2. Transportation Engineer
        add("Transportation Engineer", new String[][]{
                {"Transportation Engineering", "Civil", "ADVANCED", "ESSENTIAL", "Plan and design transportation systems."},
                {"Highway Engineering", "Civil", "INTERMEDIATE", "ESSENTIAL", "Understand highway planning and design."},
                {"Traffic Engineering", "Civil", "INTERMEDIATE", "ESSENTIAL", "Analyze traffic flow and road safety."},
                {"Pavement Design", "Civil", "INTERMEDIATE", "ESSENTIAL", "Design durable road pavements."},
                {"Surveying", "Civil", "INTERMEDIATE", "ESSENTIAL", "Understand land measurement and surveying."},
                {"Geometric Road Design", "Civil", "INTERMEDIATE", "ESSENTIAL", "Design road geometry and alignment."},
                {"Traffic Analysis", "Analysis", "INTERMEDIATE", "IMPORTANT", "Evaluate traffic conditions and capacity."},
                {"AutoCAD", "Design Tools", "BEGINNER", "IMPORTANT", "Prepare road design drawings."},
                {"Civil 3D", "Design Tools", "BEGINNER", "IMPORTANT", "Develop road and infrastructure models."},
                {"GIS", "Technology", "BEGINNER", "IMPORTANT", "Analyze geographical transportation data."},
                {"Project Management", "Management", "BEGINNER", "IMPORTANT", "Coordinate transportation projects."},
                {"Technical Documentation", "Documentation", "BEGINNER", "IMPORTANT", "Prepare transportation project reports."}
        });

// 3. Environmental Engineer
        add("Environmental Engineer", new String[][]{
                {"Environmental Engineering", "Environmental", "ADVANCED", "ESSENTIAL", "Apply engineering to environmental protection."},
                {"Environmental Science", "Environmental", "INTERMEDIATE", "ESSENTIAL", "Understand environmental systems and impacts."},
                {"Water and Wastewater Treatment", "Environmental", "INTERMEDIATE", "ESSENTIAL", "Understand water treatment processes."},
                {"Air Pollution Control", "Environmental", "INTERMEDIATE", "ESSENTIAL", "Analyze and control air pollution."},
                {"Solid Waste Management", "Environmental", "INTERMEDIATE", "ESSENTIAL", "Manage solid waste safely."},
                {"Environmental Impact Assessment", "Environmental", "INTERMEDIATE", "ESSENTIAL", "Evaluate project environmental impacts."},
                {"Water Quality Analysis", "Environmental", "INTERMEDIATE", "IMPORTANT", "Assess water quality parameters."},
                {"Environmental Monitoring", "Environmental", "INTERMEDIATE", "IMPORTANT", "Monitor environmental conditions."},
                {"GIS", "Technology", "BEGINNER", "IMPORTANT", "Analyze environmental geographical data."},
                {"Environmental Regulations", "Compliance", "INTERMEDIATE", "IMPORTANT", "Understand environmental compliance requirements."},
                {"Laboratory Testing", "Testing", "BEGINNER", "IMPORTANT", "Perform environmental sample testing."},
                {"Technical Documentation", "Documentation", "BEGINNER", "IMPORTANT", "Prepare environmental reports."}
        });

// 4. Water Resources Engineer
        add("Water Resources Engineer", new String[][]{
                {"Hydrology", "Civil", "ADVANCED", "ESSENTIAL", "Analyze rainfall, runoff and water cycles."},
                {"Hydraulics", "Civil", "INTERMEDIATE", "ESSENTIAL", "Analyze water flow in systems."},
                {"Water Resources Engineering", "Civil", "ADVANCED", "ESSENTIAL", "Plan and manage water resources."},
                {"Irrigation Engineering", "Civil", "INTERMEDIATE", "ESSENTIAL", "Design irrigation systems."},
                {"Open Channel Flow", "Civil", "INTERMEDIATE", "ESSENTIAL", "Analyze flow in open channels."},
                {"Groundwater Engineering", "Civil", "INTERMEDIATE", "IMPORTANT", "Understand groundwater systems."},
                {"Flood Management", "Water Management", "INTERMEDIATE", "ESSENTIAL", "Assess and manage flood risks."},
                {"Water Supply Engineering", "Civil", "INTERMEDIATE", "IMPORTANT", "Design water supply systems."},
                {"GIS", "Technology", "BEGINNER", "IMPORTANT", "Map and analyze water resources."},
                {"HEC-RAS", "Simulation", "BEGINNER", "IMPORTANT", "Model river hydraulics and floodplains."},
                {"AutoCAD", "Design Tools", "BEGINNER", "IMPORTANT", "Prepare water infrastructure drawings."},
                {"Technical Documentation", "Documentation", "BEGINNER", "IMPORTANT", "Prepare water resources reports."}
        });

// 5. Survey Engineer
        add("Survey Engineer", new String[][]{
                {"Surveying", "Civil", "ADVANCED", "ESSENTIAL", "Measure land and establish survey control."},
                {"Engineering Surveying", "Civil", "INTERMEDIATE", "ESSENTIAL", "Perform construction and engineering surveys."},
                {"Total Station", "Surveying Tools", "INTERMEDIATE", "ESSENTIAL", "Measure angles and distances accurately."},
                {"GPS and GNSS", "Surveying Tools", "INTERMEDIATE", "ESSENTIAL", "Collect accurate geographic coordinates."},
                {"Leveling", "Surveying", "INTERMEDIATE", "ESSENTIAL", "Determine elevation differences."},
                {"Topographic Surveying", "Surveying", "INTERMEDIATE", "ESSENTIAL", "Prepare terrain and land surveys."},
                {"GIS", "Technology", "BEGINNER", "IMPORTANT", "Manage spatial information."},
                {"AutoCAD", "Design Tools", "BEGINNER", "IMPORTANT", "Prepare survey drawings."},
                {"Civil 3D", "Design Tools", "BEGINNER", "IMPORTANT", "Process survey data and surfaces."},
                {"Remote Sensing", "Technology", "BEGINNER", "IMPORTANT", "Interpret satellite and aerial imagery."},
                {"Land Measurement", "Surveying", "INTERMEDIATE", "IMPORTANT", "Calculate areas and boundaries."},
                {"Technical Documentation", "Documentation", "BEGINNER", "IMPORTANT", "Prepare survey reports and records."}
        });



        // ==========================================
// DOMAIN 6: SOFTWARE DEVELOPMENT
// ==========================================

// 1. Frontend Developer
        add("Frontend Developer", new String[][]{
                {"HTML", "Frontend", "ADVANCED", "ESSENTIAL", "Build semantic web page structures."},
                {"CSS", "Frontend", "ADVANCED", "ESSENTIAL", "Style responsive and accessible interfaces."},
                {"JavaScript", "Programming", "ADVANCED", "ESSENTIAL", "Build interactive browser applications."},
                {"React", "Frontend", "INTERMEDIATE", "ESSENTIAL", "Build reusable component-based interfaces."},
                {"TypeScript", "Programming", "BEGINNER", "IMPORTANT", "Write type-safe JavaScript applications."},
                {"Responsive Web Design", "Frontend", "INTERMEDIATE", "ESSENTIAL", "Create layouts for different screen sizes."},
                {"REST API Integration", "Backend", "INTERMEDIATE", "ESSENTIAL", "Connect frontend applications to APIs."},
                {"Git and GitHub", "Tools", "INTERMEDIATE", "IMPORTANT", "Manage source code and collaboration."},
                {"Node.js", "Backend", "BEGINNER", "IMPORTANT", "Understand JavaScript runtime environments."},
                {"Web Accessibility", "Frontend", "BEGINNER", "IMPORTANT", "Build accessible user interfaces."},
                {"Testing", "Software Testing", "BEGINNER", "IMPORTANT", "Test frontend functionality."},
                {"Debugging", "Development Tools", "INTERMEDIATE", "IMPORTANT", "Identify and resolve application errors."}
        });

// 2. Java Developer
        add("Java Developer", new String[][]{
                {"Core Java", "Programming", "ADVANCED", "ESSENTIAL", "Apply Java language fundamentals."},
                {"Object Oriented Programming", "Programming", "ADVANCED", "ESSENTIAL", "Apply encapsulation, inheritance and polymorphism."},
                {"Collections Framework", "Programming", "INTERMEDIATE", "ESSENTIAL", "Work with Java collection types."},
                {"Exception Handling", "Programming", "INTERMEDIATE", "ESSENTIAL", "Handle application errors safely."},
                {"Multithreading", "Programming", "INTERMEDIATE", "IMPORTANT", "Develop concurrent Java applications."},
                {"JDBC", "Database", "INTERMEDIATE", "ESSENTIAL", "Connect Java applications to databases."},
                {"Spring Boot", "Backend", "INTERMEDIATE", "ESSENTIAL", "Build Java backend applications."},
                {"Hibernate", "Backend", "INTERMEDIATE", "IMPORTANT", "Map Java objects to database tables."},
                {"SQL", "Database", "INTERMEDIATE", "ESSENTIAL", "Query and manage relational databases."},
                {"REST API Development", "Backend", "INTERMEDIATE", "ESSENTIAL", "Build RESTful web services."},
                {"Maven", "Build Tools", "BEGINNER", "IMPORTANT", "Manage Java project dependencies."},
                {"JUnit", "Testing", "BEGINNER", "IMPORTANT", "Write automated Java tests."}
        });

// 3. Python Developer
        add("Python Developer", new String[][]{
                {"Python Programming", "Programming", "ADVANCED", "ESSENTIAL", "Develop applications using Python."},
                {"Object Oriented Programming", "Programming", "INTERMEDIATE", "ESSENTIAL", "Apply object-oriented programming principles."},
                {"Python Data Structures", "Programming", "INTERMEDIATE", "ESSENTIAL", "Work with lists, dictionaries and sets."},
                {"Exception Handling", "Programming", "INTERMEDIATE", "ESSENTIAL", "Handle runtime errors effectively."},
                {"SQL", "Database", "INTERMEDIATE", "ESSENTIAL", "Query and manage databases."},
                {"Django", "Backend", "BEGINNER", "IMPORTANT", "Build Python web applications."},
                {"Flask", "Backend", "BEGINNER", "IMPORTANT", "Develop lightweight web services."},
                {"REST API Development", "Backend", "INTERMEDIATE", "ESSENTIAL", "Create API endpoints."},
                {"Git and GitHub", "Tools", "BEGINNER", "IMPORTANT", "Manage code and collaboration."},
                {"Unit Testing", "Testing", "BEGINNER", "IMPORTANT", "Test Python application behavior."},
                {"Linux Fundamentals", "Operating Systems", "BEGINNER", "OPTIONAL", "Work with Linux environments."},
                {"Debugging", "Development Tools", "INTERMEDIATE", "IMPORTANT", "Find and fix Python errors."}
        });

// 4. Full Stack Developer
        add("Full Stack Developer", new String[][]{
                {"HTML", "Frontend", "INTERMEDIATE", "ESSENTIAL", "Build web page structures."},
                {"CSS", "Frontend", "INTERMEDIATE", "ESSENTIAL", "Style responsive web interfaces."},
                {"JavaScript", "Programming", "ADVANCED", "ESSENTIAL", "Build interactive applications."},
                {"React", "Frontend", "INTERMEDIATE", "ESSENTIAL", "Develop frontend components."},
                {"Java", "Programming", "INTERMEDIATE", "ESSENTIAL", "Build application logic using Java."},
                {"Spring Boot", "Backend", "INTERMEDIATE", "ESSENTIAL", "Develop backend services."},
                {"REST API Development", "Backend", "INTERMEDIATE", "ESSENTIAL", "Connect frontend and backend."},
                {"SQL", "Database", "INTERMEDIATE", "ESSENTIAL", "Store and retrieve application data."},
                {"Database Design", "Database", "INTERMEDIATE", "IMPORTANT", "Design relational database structures."},
                {"Git and GitHub", "Tools", "INTERMEDIATE", "IMPORTANT", "Manage application source code."},
                {"Maven", "Build Tools", "BEGINNER", "IMPORTANT", "Manage project dependencies."},
                {"Deployment", "DevOps", "BEGINNER", "IMPORTANT", "Deploy full stack applications."}
        });

// 5. Backend Developer
        add("Backend Developer", new String[][]{
                {"Java", "Programming", "INTERMEDIATE", "ESSENTIAL", "Develop backend application logic."},
                {"Spring Boot", "Backend", "ADVANCED", "ESSENTIAL", "Build production-ready backend services."},
                {"REST API Development", "Backend", "ADVANCED", "ESSENTIAL", "Design and implement REST APIs."},
                {"SQL", "Database", "INTERMEDIATE", "ESSENTIAL", "Query relational databases."},
                {"Database Design", "Database", "INTERMEDIATE", "ESSENTIAL", "Design efficient database schemas."},
                {"Hibernate", "Backend", "INTERMEDIATE", "IMPORTANT", "Implement object-relational mapping."},
                {"Authentication and Authorization", "Security", "INTERMEDIATE", "ESSENTIAL", "Secure application access."},
                {"Microservices", "Architecture", "BEGINNER", "IMPORTANT", "Understand distributed service architecture."},
                {"Git and GitHub", "Tools", "INTERMEDIATE", "IMPORTANT", "Manage source code."},
                {"Maven", "Build Tools", "BEGINNER", "IMPORTANT", "Manage dependencies and builds."},
                {"JUnit", "Testing", "BEGINNER", "IMPORTANT", "Write backend unit tests."},
                {"Docker", "DevOps", "BEGINNER", "IMPORTANT", "Package applications into containers."}
        });

// 6. Android Developer
        add("Android Developer", new String[][]{
                {"Java", "Programming", "INTERMEDIATE", "ESSENTIAL", "Develop Android application logic."},
                {"Kotlin", "Programming", "INTERMEDIATE", "ESSENTIAL", "Build modern Android applications."},
                {"Android SDK", "Mobile Development", "ADVANCED", "ESSENTIAL", "Use Android development tools."},
                {"Android Studio", "Development Tools", "INTERMEDIATE", "ESSENTIAL", "Develop and debug Android apps."},
                {"XML", "Frontend", "INTERMEDIATE", "ESSENTIAL", "Design Android application layouts."},
                {"Jetpack Compose", "Mobile Development", "BEGINNER", "IMPORTANT", "Build declarative Android interfaces."},
                {"SQLite", "Database", "INTERMEDIATE", "ESSENTIAL", "Store local application data."},
                {"REST API Integration", "Backend", "INTERMEDIATE", "ESSENTIAL", "Connect mobile applications to APIs."},
                {"Firebase", "Backend", "BEGINNER", "IMPORTANT", "Use backend services for mobile apps."},
                {"Git and GitHub", "Tools", "BEGINNER", "IMPORTANT", "Manage application source code."},
                {"Android Testing", "Testing", "BEGINNER", "IMPORTANT", "Test Android application behavior."},
                {"Material Design", "UI Design", "BEGINNER", "IMPORTANT", "Create consistent Android interfaces."}
        });

// 7. Software Engineer
        add("Software Engineer", new String[][]{
                {"Data Structures and Algorithms", "Computer Science", "ADVANCED", "ESSENTIAL", "Solve computational problems efficiently."},
                {"Object Oriented Programming", "Programming", "ADVANCED", "ESSENTIAL", "Design reusable software components."},
                {"Java", "Programming", "INTERMEDIATE", "ESSENTIAL", "Develop software using Java."},
                {"Python", "Programming", "BEGINNER", "IMPORTANT", "Use Python for software development."},
                {"SQL", "Database", "INTERMEDIATE", "ESSENTIAL", "Manage and query data."},
                {"Software Development Life Cycle", "Software Engineering", "INTERMEDIATE", "ESSENTIAL", "Understand software development stages."},
                {"Software Design Patterns", "Architecture", "INTERMEDIATE", "IMPORTANT", "Apply reusable software design solutions."},
                {"Git and GitHub", "Tools", "INTERMEDIATE", "ESSENTIAL", "Manage source code and teamwork."},
                {"REST API Development", "Backend", "BEGINNER", "IMPORTANT", "Understand service communication."},
                {"Unit Testing", "Testing", "INTERMEDIATE", "ESSENTIAL", "Verify software components."},
                {"Debugging", "Development Tools", "INTERMEDIATE", "ESSENTIAL", "Identify and resolve software defects."},
                {"Agile Methodology", "Project Management", "BEGINNER", "IMPORTANT", "Work in iterative development teams."}
        });



        // ==========================================
// DOMAIN 7: AI & DATA
// ==========================================

// 1. AI Engineer
        add("AI Engineer", new String[][]{
                {"Python", "Programming", "ADVANCED", "ESSENTIAL", "Develop AI applications using Python."},
                {"Data Structures and Algorithms", "Computer Science", "INTERMEDIATE", "IMPORTANT", "Build efficient AI solutions."},
                {"Linear Algebra", "Mathematics", "INTERMEDIATE", "ESSENTIAL", "Understand mathematical foundations of AI."},
                {"Statistics", "Mathematics", "INTERMEDIATE", "ESSENTIAL", "Apply statistical methods to AI problems."},
                {"Machine Learning", "AI", "ADVANCED", "ESSENTIAL", "Build predictive learning models."},
                {"Deep Learning", "AI", "INTERMEDIATE", "ESSENTIAL", "Develop neural network models."},
                {"TensorFlow", "AI Tools", "INTERMEDIATE", "IMPORTANT", "Build and train AI models."},
                {"PyTorch", "AI Tools", "BEGINNER", "IMPORTANT", "Develop deep learning applications."},
                {"Natural Language Processing", "AI", "INTERMEDIATE", "IMPORTANT", "Process and understand human language."},
                {"Computer Vision", "AI", "BEGINNER", "IMPORTANT", "Build image and video understanding systems."},
                {"REST API Development", "Backend", "INTERMEDIATE", "IMPORTANT", "Expose AI models through APIs."},
                {"Git and GitHub", "Tools", "INTERMEDIATE", "IMPORTANT", "Manage AI project source code."}
        });

// 2. Machine Learning Engineer
        add("Machine Learning Engineer", new String[][]{
                {"Python", "Programming", "ADVANCED", "ESSENTIAL", "Develop machine learning applications."},
                {"Statistics", "Mathematics", "ADVANCED", "ESSENTIAL", "Apply statistical learning methods."},
                {"Linear Algebra", "Mathematics", "INTERMEDIATE", "ESSENTIAL", "Understand vector and matrix operations."},
                {"Probability", "Mathematics", "INTERMEDIATE", "ESSENTIAL", "Understand probabilistic models."},
                {"Machine Learning", "AI", "ADVANCED", "ESSENTIAL", "Train and evaluate ML models."},
                {"Deep Learning", "AI", "INTERMEDIATE", "ESSENTIAL", "Build neural network architectures."},
                {"Scikit-learn", "AI Tools", "INTERMEDIATE", "ESSENTIAL", "Implement machine learning algorithms."},
                {"TensorFlow", "AI Tools", "INTERMEDIATE", "IMPORTANT", "Develop deep learning models."},
                {"PyTorch", "AI Tools", "BEGINNER", "IMPORTANT", "Train neural networks."},
                {"SQL", "Database", "INTERMEDIATE", "IMPORTANT", "Retrieve and prepare training data."},
                {"MLOps", "DevOps", "BEGINNER", "IMPORTANT", "Manage ML model deployment and lifecycle."},
                {"Git and GitHub", "Tools", "INTERMEDIATE", "IMPORTANT", "Track machine learning code changes."}
        });

// 3. Data Scientist
        add("Data Scientist", new String[][]{
                {"Python", "Programming", "ADVANCED", "ESSENTIAL", "Analyze data and build models."},
                {"Statistics", "Mathematics", "ADVANCED", "ESSENTIAL", "Perform statistical analysis."},
                {"Probability", "Mathematics", "INTERMEDIATE", "ESSENTIAL", "Apply probability in data analysis."},
                {"Linear Algebra", "Mathematics", "INTERMEDIATE", "IMPORTANT", "Understand mathematical data representations."},
                {"Machine Learning", "AI", "INTERMEDIATE", "ESSENTIAL", "Build predictive models."},
                {"SQL", "Database", "ADVANCED", "ESSENTIAL", "Extract and manipulate datasets."},
                {"Pandas", "Data Analysis", "INTERMEDIATE", "ESSENTIAL", "Clean and analyze structured data."},
                {"NumPy", "Data Analysis", "INTERMEDIATE", "IMPORTANT", "Perform numerical computations."},
                {"Data Visualization", "Data Analysis", "INTERMEDIATE", "ESSENTIAL", "Communicate insights through visualizations."},
                {"Scikit-learn", "AI Tools", "INTERMEDIATE", "IMPORTANT", "Develop predictive models."},
                {"Deep Learning", "AI", "BEGINNER", "OPTIONAL", "Understand neural network methods."},
                {"Jupyter Notebook", "Tools", "INTERMEDIATE", "IMPORTANT", "Perform interactive data experiments."}
        });

// 4. Data Analyst
        add("Data Analyst", new String[][]{
                {"SQL", "Database", "ADVANCED", "ESSENTIAL", "Query and analyze business data."},
                {"Python", "Programming", "INTERMEDIATE", "IMPORTANT", "Automate data analysis tasks."},
                {"Microsoft Excel", "Data Analysis", "ADVANCED", "ESSENTIAL", "Analyze and organize spreadsheets."},
                {"Statistics", "Mathematics", "INTERMEDIATE", "ESSENTIAL", "Interpret data using statistical methods."},
                {"Data Cleaning", "Data Analysis", "INTERMEDIATE", "ESSENTIAL", "Prepare accurate datasets."},
                {"Data Visualization", "Data Analysis", "INTERMEDIATE", "ESSENTIAL", "Present data insights visually."},
                {"Power BI", "BI Tools", "INTERMEDIATE", "ESSENTIAL", "Create interactive business dashboards."},
                {"Tableau", "BI Tools", "BEGINNER", "IMPORTANT", "Build analytical dashboards."},
                {"Pandas", "Data Analysis", "INTERMEDIATE", "IMPORTANT", "Manipulate data using Python."},
                {"NumPy", "Data Analysis", "BEGINNER", "IMPORTANT", "Perform numerical analysis."},
                {"Business Intelligence", "Analytics", "INTERMEDIATE", "IMPORTANT", "Support data-driven decisions."},
                {"Communication Skills", "Professional Skills", "INTERMEDIATE", "IMPORTANT", "Present findings clearly."}
        });

// 5. NLP Engineer
        add("NLP Engineer", new String[][]{
                {"Python", "Programming", "ADVANCED", "ESSENTIAL", "Develop language processing applications."},
                {"Natural Language Processing", "AI", "ADVANCED", "ESSENTIAL", "Process and understand human language."},
                {"Machine Learning", "AI", "INTERMEDIATE", "ESSENTIAL", "Build language classification models."},
                {"Deep Learning", "AI", "INTERMEDIATE", "ESSENTIAL", "Develop neural language models."},
                {"Statistics", "Mathematics", "INTERMEDIATE", "IMPORTANT", "Apply statistical methods to language data."},
                {"Text Processing", "NLP", "ADVANCED", "ESSENTIAL", "Clean and transform textual data."},
                {"Natural Language Toolkit (NLTK)", "NLP Tools", "INTERMEDIATE", "ESSENTIAL", "Perform text processing tasks."},
                {"spaCy", "NLP Tools", "INTERMEDIATE", "IMPORTANT", "Build industrial NLP pipelines."},
                {"Transformers", "AI Tools", "INTERMEDIATE", "ESSENTIAL", "Use transformer-based language models."},
                {"PyTorch", "AI Tools", "BEGINNER", "IMPORTANT", "Train neural NLP models."},
                {"TensorFlow", "AI Tools", "BEGINNER", "OPTIONAL", "Develop neural language applications."},
                {"REST API Development", "Backend", "BEGINNER", "IMPORTANT", "Expose NLP models through APIs."}
        });

// 6. Data Engineer
        add("Data Engineer", new String[][]{
                {"Python", "Programming", "ADVANCED", "ESSENTIAL", "Build data processing pipelines."},
                {"SQL", "Database", "ADVANCED", "ESSENTIAL", "Query and transform large datasets."},
                {"Database Design", "Database", "INTERMEDIATE", "ESSENTIAL", "Design efficient data structures."},
                {"ETL Processes", "Data Engineering", "INTERMEDIATE", "ESSENTIAL", "Extract, transform and load data."},
                {"Data Warehousing", "Data Engineering", "INTERMEDIATE", "ESSENTIAL", "Design analytical data warehouses."},
                {"Apache Spark", "Big Data", "INTERMEDIATE", "ESSENTIAL", "Process large-scale datasets."},
                {"Apache Kafka", "Big Data", "BEGINNER", "IMPORTANT", "Build real-time data pipelines."},
                {"Hadoop", "Big Data", "BEGINNER", "IMPORTANT", "Understand distributed data processing."},
                {"Linux Fundamentals", "Operating Systems", "INTERMEDIATE", "IMPORTANT", "Work with data infrastructure."},
                {"Cloud Computing", "Cloud", "BEGINNER", "IMPORTANT", "Understand cloud data platforms."},
                {"Docker", "DevOps", "BEGINNER", "IMPORTANT", "Containerize data applications."},
                {"Git and GitHub", "Tools", "INTERMEDIATE", "IMPORTANT", "Manage data engineering code."}
        });


        // ==========================================
// DOMAIN 8: CLOUD & DEVOPS
// ==========================================

// 1. Cloud Engineer
        add("Cloud Engineer", new String[][]{
                {"Cloud Computing", "Cloud", "ADVANCED", "ESSENTIAL", "Design and manage cloud infrastructure."},
                {"AWS", "Cloud Platforms", "INTERMEDIATE", "ESSENTIAL", "Deploy applications using AWS services."},
                {"Microsoft Azure", "Cloud Platforms", "BEGINNER", "IMPORTANT", "Understand Azure cloud services."},
                {"Google Cloud Platform", "Cloud Platforms", "BEGINNER", "IMPORTANT", "Work with Google Cloud services."},
                {"Linux Fundamentals", "Operating Systems", "INTERMEDIATE", "ESSENTIAL", "Manage cloud servers using Linux."},
                {"Networking Fundamentals", "Networking", "INTERMEDIATE", "ESSENTIAL", "Understand cloud networking concepts."},
                {"Docker", "DevOps", "INTERMEDIATE", "ESSENTIAL", "Containerize cloud applications."},
                {"Kubernetes", "DevOps", "BEGINNER", "IMPORTANT", "Orchestrate containerized applications."},
                {"Terraform", "Infrastructure as Code", "INTERMEDIATE", "ESSENTIAL", "Automate cloud infrastructure provisioning."},
                {"Python", "Programming", "INTERMEDIATE", "IMPORTANT", "Automate cloud administration tasks."},
                {"Git and GitHub", "Tools", "INTERMEDIATE", "IMPORTANT", "Manage infrastructure code and changes."},
                {"Monitoring and Logging", "Cloud Operations", "INTERMEDIATE", "IMPORTANT", "Monitor cloud infrastructure performance."}
        });

// 2. DevOps Engineer
        add("DevOps Engineer", new String[][]{
                {"Linux Fundamentals", "Operating Systems", "ADVANCED", "ESSENTIAL", "Manage and troubleshoot Linux systems."},
                {"Git and GitHub", "Tools", "ADVANCED", "ESSENTIAL", "Manage source code and collaboration."},
                {"Docker", "DevOps", "ADVANCED", "ESSENTIAL", "Build and manage application containers."},
                {"Kubernetes", "DevOps", "INTERMEDIATE", "ESSENTIAL", "Orchestrate containerized applications."},
                {"CI/CD", "DevOps", "ADVANCED", "ESSENTIAL", "Automate software integration and deployment."},
                {"Jenkins", "DevOps Tools", "INTERMEDIATE", "ESSENTIAL", "Build automated delivery pipelines."},
                {"AWS", "Cloud Platforms", "INTERMEDIATE", "IMPORTANT", "Deploy applications on AWS."},
                {"Terraform", "Infrastructure as Code", "INTERMEDIATE", "ESSENTIAL", "Automate infrastructure management."},
                {"Ansible", "Configuration Management", "INTERMEDIATE", "IMPORTANT", "Automate server configuration."},
                {"Python", "Programming", "INTERMEDIATE", "IMPORTANT", "Write automation scripts."},
                {"Networking Fundamentals", "Networking", "INTERMEDIATE", "IMPORTANT", "Understand network communication."},
                {"Monitoring and Logging", "DevOps", "INTERMEDIATE", "ESSENTIAL", "Monitor application and system health."}
        });

// 3. AWS Engineer
        add("AWS Engineer", new String[][]{
                {"AWS", "Cloud Platforms", "ADVANCED", "ESSENTIAL", "Design and manage AWS infrastructure."},
                {"Amazon EC2", "AWS Services", "INTERMEDIATE", "ESSENTIAL", "Provision and manage virtual servers."},
                {"Amazon S3", "AWS Services", "INTERMEDIATE", "ESSENTIAL", "Manage scalable cloud storage."},
                {"AWS IAM", "AWS Security", "INTERMEDIATE", "ESSENTIAL", "Manage identities and access permissions."},
                {"Amazon VPC", "Networking", "INTERMEDIATE", "ESSENTIAL", "Configure private cloud networks."},
                {"AWS Lambda", "AWS Services", "BEGINNER", "IMPORTANT", "Build serverless applications."},
                {"Amazon RDS", "AWS Services", "INTERMEDIATE", "IMPORTANT", "Manage relational cloud databases."},
                {"CloudFormation", "Infrastructure as Code", "INTERMEDIATE", "IMPORTANT", "Automate AWS infrastructure deployment."},
                {"Docker", "DevOps", "INTERMEDIATE", "IMPORTANT", "Containerize applications for deployment."},
                {"Linux Fundamentals", "Operating Systems", "INTERMEDIATE", "ESSENTIAL", "Manage AWS Linux instances."},
                {"Python", "Programming", "INTERMEDIATE", "IMPORTANT", "Automate AWS operations."},
                {"Monitoring and Logging", "Cloud Operations", "INTERMEDIATE", "ESSENTIAL", "Monitor AWS resources and performance."}
        });

// 4. Site Reliability Engineer
        add("Site Reliability Engineer", new String[][]{
                {"Linux Fundamentals", "Operating Systems", "ADVANCED", "ESSENTIAL", "Troubleshoot production Linux systems."},
                {"Networking Fundamentals", "Networking", "ADVANCED", "ESSENTIAL", "Diagnose network and connectivity issues."},
                {"Python", "Programming", "INTERMEDIATE", "ESSENTIAL", "Automate operational tasks."},
                {"Bash Scripting", "Scripting", "INTERMEDIATE", "ESSENTIAL", "Automate system administration."},
                {"Kubernetes", "DevOps", "ADVANCED", "ESSENTIAL", "Manage reliable containerized systems."},
                {"Docker", "DevOps", "INTERMEDIATE", "ESSENTIAL", "Manage application containers."},
                {"AWS", "Cloud Platforms", "INTERMEDIATE", "IMPORTANT", "Operate cloud-based infrastructure."},
                {"CI/CD", "DevOps", "INTERMEDIATE", "ESSENTIAL", "Automate reliable software releases."},
                {"Monitoring and Logging", "Cloud Operations", "ADVANCED", "ESSENTIAL", "Monitor system health and performance."},
                {"Prometheus", "Monitoring Tools", "INTERMEDIATE", "ESSENTIAL", "Collect and monitor system metrics."},
                {"Grafana", "Monitoring Tools", "INTERMEDIATE", "IMPORTANT", "Visualize infrastructure metrics."},
                {"Incident Management", "Site Reliability", "INTERMEDIATE", "ESSENTIAL", "Respond to and resolve production incidents."}
        });




        // ==========================================
// DOMAIN 9: SECURITY & NETWORKING
// ==========================================

// 1. Cybersecurity Analyst
        add("Cybersecurity Analyst", new String[][]{
                {"Networking Fundamentals", "Networking", "ADVANCED", "ESSENTIAL", "Understand network protocols and communication."},
                {"Linux Fundamentals", "Operating Systems", "INTERMEDIATE", "ESSENTIAL", "Analyze and manage Linux systems."},
                {"Windows Administration", "Operating Systems", "INTERMEDIATE", "IMPORTANT", "Manage and secure Windows environments."},
                {"Cybersecurity Fundamentals", "Cybersecurity", "ADVANCED", "ESSENTIAL", "Understand security principles and threats."},
                {"Network Security", "Cybersecurity", "ADVANCED", "ESSENTIAL", "Protect networks from security threats."},
                {"Vulnerability Assessment", "Cybersecurity", "INTERMEDIATE", "ESSENTIAL", "Identify and assess system vulnerabilities."},
                {"SIEM Tools", "Security Tools", "INTERMEDIATE", "ESSENTIAL", "Analyze security events and alerts."},
                {"Incident Response", "Cybersecurity", "INTERMEDIATE", "ESSENTIAL", "Investigate and respond to security incidents."},
                {"Cryptography", "Cybersecurity", "INTERMEDIATE", "IMPORTANT", "Understand encryption and secure communication."},
                {"Python", "Programming", "BEGINNER", "IMPORTANT", "Automate security analysis tasks."},
                {"Firewalls", "Network Security", "INTERMEDIATE", "IMPORTANT", "Configure and manage network firewalls."},
                {"Risk Assessment", "Cybersecurity", "INTERMEDIATE", "IMPORTANT", "Evaluate security risks and controls."}
        });

// 2. Ethical Hacker
        add("Ethical Hacker", new String[][]{
                {"Networking Fundamentals", "Networking", "ADVANCED", "ESSENTIAL", "Understand network architecture and protocols."},
                {"Linux Fundamentals", "Operating Systems", "ADVANCED", "ESSENTIAL", "Use Linux for authorized security testing."},
                {"Cybersecurity Fundamentals", "Cybersecurity", "ADVANCED", "ESSENTIAL", "Understand security concepts and vulnerabilities."},
                {"Penetration Testing", "Cybersecurity", "ADVANCED", "ESSENTIAL", "Perform authorized security assessments."},
                {"Web Application Security", "Cybersecurity", "ADVANCED", "ESSENTIAL", "Identify web application security weaknesses."},
                {"OWASP Top 10", "Web Security", "ADVANCED", "ESSENTIAL", "Understand common web application risks."},
                {"Kali Linux", "Security Tools", "INTERMEDIATE", "ESSENTIAL", "Use security testing tools in authorized labs."},
                {"Burp Suite", "Security Tools", "INTERMEDIATE", "ESSENTIAL", "Assess web application security."},
                {"Nmap", "Security Tools", "INTERMEDIATE", "IMPORTANT", "Discover hosts and services on authorized networks."},
                {"Python", "Programming", "INTERMEDIATE", "IMPORTANT", "Automate security testing tasks."},
                {"Cryptography", "Cybersecurity", "INTERMEDIATE", "IMPORTANT", "Understand encryption and cryptographic methods."},
                {"Vulnerability Assessment", "Cybersecurity", "ADVANCED", "ESSENTIAL", "Identify and prioritize security weaknesses."}
        });

// 3. SOC Analyst
        add("SOC Analyst", new String[][]{
                {"Cybersecurity Fundamentals", "Cybersecurity", "ADVANCED", "ESSENTIAL", "Understand threats and security operations."},
                {"Networking Fundamentals", "Networking", "INTERMEDIATE", "ESSENTIAL", "Analyze network traffic and protocols."},
                {"Linux Fundamentals", "Operating Systems", "INTERMEDIATE", "IMPORTANT", "Investigate Linux system activity."},
                {"Windows Administration", "Operating Systems", "INTERMEDIATE", "IMPORTANT", "Analyze Windows logs and events."},
                {"SIEM Tools", "Security Tools", "ADVANCED", "ESSENTIAL", "Monitor and investigate security events."},
                {"Incident Response", "Cybersecurity", "ADVANCED", "ESSENTIAL", "Handle and investigate security incidents."},
                {"Log Analysis", "Security Operations", "ADVANCED", "ESSENTIAL", "Analyze system and security logs."},
                {"Threat Intelligence", "Cybersecurity", "INTERMEDIATE", "ESSENTIAL", "Identify and understand emerging threats."},
                {"Vulnerability Assessment", "Cybersecurity", "INTERMEDIATE", "IMPORTANT", "Identify and prioritize vulnerabilities."},
                {"Endpoint Detection and Response", "Security Tools", "INTERMEDIATE", "ESSENTIAL", "Detect suspicious endpoint activity."},
                {"Python", "Programming", "BEGINNER", "IMPORTANT", "Automate security monitoring tasks."},
                {"Firewalls", "Network Security", "INTERMEDIATE", "IMPORTANT", "Monitor and analyze firewall activity."}
        });

// 4. Network Engineer
        add("Network Engineer", new String[][]{
                {"Networking Fundamentals", "Networking", "ADVANCED", "ESSENTIAL", "Understand network architecture and protocols."},
                {"TCP/IP", "Networking", "ADVANCED", "ESSENTIAL", "Configure and troubleshoot TCP/IP communication."},
                {"Routing and Switching", "Networking", "ADVANCED", "ESSENTIAL", "Configure routers and network switches."},
                {"Cisco Networking", "Networking Tools", "INTERMEDIATE", "ESSENTIAL", "Manage Cisco networking equipment."},
                {"Subnetting", "Networking", "ADVANCED", "ESSENTIAL", "Design and manage IP addressing."},
                {"VLAN Configuration", "Networking", "INTERMEDIATE", "ESSENTIAL", "Configure virtual local area networks."},
                {"Network Security", "Cybersecurity", "INTERMEDIATE", "ESSENTIAL", "Secure network infrastructure."},
                {"Firewalls", "Network Security", "INTERMEDIATE", "ESSENTIAL", "Configure and manage firewall policies."},
                {"Wireless Networking", "Networking", "INTERMEDIATE", "IMPORTANT", "Configure and troubleshoot wireless networks."},
                {"Linux Fundamentals", "Operating Systems", "INTERMEDIATE", "IMPORTANT", "Manage network services on Linux."},
                {"Network Monitoring", "Networking Tools", "INTERMEDIATE", "ESSENTIAL", "Monitor network availability and performance."},
                {"Troubleshooting", "Networking", "ADVANCED", "ESSENTIAL", "Diagnose and resolve network problems."}
        });


        // ==========================================
// DOMAIN 10: OTHER CS ROLES
// ==========================================

// 1. Database Administrator
        add("Database Administrator", new String[][]{
                {"SQL", "Database", "ADVANCED", "ESSENTIAL", "Query and manage relational databases."},
                {"Database Design", "Database", "ADVANCED", "ESSENTIAL", "Design efficient database schemas."},
                {"MySQL", "Database", "ADVANCED", "ESSENTIAL", "Administer MySQL databases."},
                {"PostgreSQL", "Database", "INTERMEDIATE", "IMPORTANT", "Manage PostgreSQL database systems."},
                {"Oracle Database", "Database", "INTERMEDIATE", "IMPORTANT", "Administer Oracle database environments."},
                {"Database Administration", "Database", "ADVANCED", "ESSENTIAL", "Manage database operations and maintenance."},
                {"Database Backup and Recovery", "Database", "ADVANCED", "ESSENTIAL", "Protect data through backup and recovery."},
                {"Database Performance Tuning", "Database", "INTERMEDIATE", "ESSENTIAL", "Optimize database query performance."},
                {"Database Security", "Database Security", "INTERMEDIATE", "ESSENTIAL", "Protect databases from unauthorized access."},
                {"Indexing and Query Optimization", "Database", "INTERMEDIATE", "IMPORTANT", "Improve database query efficiency."},
                {"Linux Fundamentals", "Operating Systems", "INTERMEDIATE", "IMPORTANT", "Manage database servers on Linux."},
                {"Monitoring and Logging", "Database Operations", "INTERMEDIATE", "IMPORTANT", "Monitor database health and performance."},
                {"Python", "Programming", "BEGINNER", "OPTIONAL", "Automate database administration tasks."}
        });

// 2. QA Engineer
        add("QA Engineer", new String[][]{
                {"Software Testing Fundamentals", "Software Testing", "ADVANCED", "ESSENTIAL", "Understand software testing principles."},
                {"Manual Testing", "Software Testing", "ADVANCED", "ESSENTIAL", "Test applications manually."},
                {"Test Case Design", "Software Testing", "ADVANCED", "ESSENTIAL", "Create effective test cases."},
                {"Bug Tracking", "Software Testing", "INTERMEDIATE", "ESSENTIAL", "Report and track software defects."},
                {"Regression Testing", "Software Testing", "INTERMEDIATE", "ESSENTIAL", "Verify existing functionality after changes."},
                {"Functional Testing", "Software Testing", "INTERMEDIATE", "ESSENTIAL", "Validate application functionality."},
                {"API Testing", "Software Testing", "INTERMEDIATE", "IMPORTANT", "Validate application programming interfaces."},
                {"SQL", "Database", "INTERMEDIATE", "IMPORTANT", "Verify application data in databases."},
                {"Agile Methodology", "Software Development", "INTERMEDIATE", "IMPORTANT", "Participate in Agile development workflows."},
                {"SDLC", "Software Development", "INTERMEDIATE", "ESSENTIAL", "Understand the software development lifecycle."},
                {"JIRA", "Testing Tools", "INTERMEDIATE", "IMPORTANT", "Manage testing tasks and defects."},
                {"Test Documentation", "Software Testing", "INTERMEDIATE", "ESSENTIAL", "Document testing activities and results."}
        });

// 3. Automation Test Engineer
        add("Automation Test Engineer", new String[][]{
                {"Java", "Programming", "ADVANCED", "ESSENTIAL", "Develop automated testing scripts."},
                {"Python", "Programming", "INTERMEDIATE", "IMPORTANT", "Write automation testing scripts."},
                {"Selenium", "Automation Testing", "ADVANCED", "ESSENTIAL", "Automate browser-based application testing."},
                {"TestNG", "Automation Testing", "INTERMEDIATE", "ESSENTIAL", "Organize and execute automated test cases."},
                {"JUnit", "Automation Testing", "INTERMEDIATE", "IMPORTANT", "Write and run automated unit tests."},
                {"API Testing", "Software Testing", "INTERMEDIATE", "ESSENTIAL", "Automate API validation."},
                {"Postman", "Testing Tools", "INTERMEDIATE", "IMPORTANT", "Test and validate REST APIs."},
                {"REST API", "Backend", "INTERMEDIATE", "IMPORTANT", "Understand API requests and responses."},
                {"Maven", "Build Tools", "INTERMEDIATE", "IMPORTANT", "Manage Java project dependencies."},
                {"Git and GitHub", "Tools", "INTERMEDIATE", "ESSENTIAL", "Manage automation testing code."},
                {"CI/CD", "DevOps", "INTERMEDIATE", "IMPORTANT", "Integrate automated tests into pipelines."},
                {"SQL", "Database", "INTERMEDIATE", "IMPORTANT", "Validate database records during testing."},
                {"Test Case Design", "Software Testing", "INTERMEDIATE", "ESSENTIAL", "Design reliable automated test cases."}
        });

// 4. UI/UX Designer
        add("UI/UX Designer", new String[][]{
                {"UI Design Principles", "Design", "ADVANCED", "ESSENTIAL", "Create visually consistent user interfaces."},
                {"UX Design Principles", "Design", "ADVANCED", "ESSENTIAL", "Design useful and intuitive user experiences."},
                {"Figma", "Design Tools", "ADVANCED", "ESSENTIAL", "Create interface designs and prototypes."},
                {"Wireframing", "UX Design", "INTERMEDIATE", "ESSENTIAL", "Plan application layouts and structures."},
                {"Prototyping", "UX Design", "INTERMEDIATE", "ESSENTIAL", "Create interactive design prototypes."},
                {"User Research", "UX Design", "INTERMEDIATE", "ESSENTIAL", "Understand user needs and behavior."},
                {"Usability Testing", "UX Design", "INTERMEDIATE", "ESSENTIAL", "Evaluate interface usability."},
                {"Responsive Design", "UI Design", "INTERMEDIATE", "ESSENTIAL", "Design interfaces for different screen sizes."},
                {"Typography", "Visual Design", "INTERMEDIATE", "IMPORTANT", "Use readable and consistent typography."},
                {"Color Theory", "Visual Design", "INTERMEDIATE", "IMPORTANT", "Apply effective color combinations."},
                {"Design Systems", "UI Design", "INTERMEDIATE", "IMPORTANT", "Maintain reusable interface components."},
                {"Accessibility", "UX Design", "INTERMEDIATE", "ESSENTIAL", "Design accessible digital experiences."}
        });

// 5. Blockchain Developer
        add("Blockchain Developer", new String[][]{
                {"Blockchain Fundamentals", "Blockchain", "ADVANCED", "ESSENTIAL", "Understand blockchain architecture and concepts."},
                {"Solidity", "Programming", "ADVANCED", "ESSENTIAL", "Develop smart contracts."},
                {"JavaScript", "Programming", "INTERMEDIATE", "ESSENTIAL", "Build blockchain application interfaces."},
                {"Smart Contracts", "Blockchain", "ADVANCED", "ESSENTIAL", "Create and manage smart contract logic."},
                {"Ethereum", "Blockchain Platforms", "INTERMEDIATE", "ESSENTIAL", "Develop applications on Ethereum."},
                {"Web3.js", "Blockchain Tools", "INTERMEDIATE", "IMPORTANT", "Connect applications to blockchain networks."},
                {"Cryptography", "Cybersecurity", "INTERMEDIATE", "ESSENTIAL", "Understand cryptographic blockchain mechanisms."},
                {"Data Structures and Algorithms", "Computer Science", "INTERMEDIATE", "IMPORTANT", "Build efficient blockchain solutions."},
                {"Truffle Suite", "Blockchain Tools", "BEGINNER", "IMPORTANT", "Develop and test smart contracts."},
                {"Hardhat", "Blockchain Tools", "INTERMEDIATE", "IMPORTANT", "Compile and test smart contracts."},
                {"Git and GitHub", "Tools", "INTERMEDIATE", "IMPORTANT", "Manage blockchain project source code."},
                {"Node.js", "Backend", "INTERMEDIATE", "IMPORTANT", "Develop blockchain application backends."},
                {"REST API Development", "Backend", "INTERMEDIATE", "IMPORTANT", "Integrate blockchain services with applications."}
        });



        // ==========================================
// DOMAIN 11: MECHANICAL & MANUFACTURING
// ==========================================

// 1. Mechanical Design Engineer
        add("Mechanical Design Engineer", new String[][]{
                {"Engineering Mechanics", "Mechanical Engineering", "ADVANCED", "ESSENTIAL", "Apply mechanics principles to mechanical designs."},
                {"Machine Design", "Mechanical Engineering", "ADVANCED", "ESSENTIAL", "Design reliable mechanical components."},
                {"Engineering Drawing", "Design", "ADVANCED", "ESSENTIAL", "Prepare and interpret engineering drawings."},
                {"AutoCAD", "CAD Tools", "INTERMEDIATE", "IMPORTANT", "Create accurate engineering drawings."},
                {"SolidWorks", "CAD Tools", "ADVANCED", "ESSENTIAL", "Create 3D mechanical models and assemblies."},
                {"CATIA", "CAD Tools", "INTERMEDIATE", "IMPORTANT", "Develop complex mechanical designs."},
                {"Finite Element Analysis", "Simulation", "INTERMEDIATE", "ESSENTIAL", "Analyze structural behavior under loads."},
                {"Material Science", "Mechanical Engineering", "INTERMEDIATE", "ESSENTIAL", "Select materials for mechanical components."},
                {"Manufacturing Processes", "Manufacturing", "INTERMEDIATE", "IMPORTANT", "Understand component manufacturing methods."},
                {"GD&T", "Engineering Design", "INTERMEDIATE", "ESSENTIAL", "Apply geometric dimensioning and tolerancing."},
                {"Thermodynamics", "Mechanical Engineering", "INTERMEDIATE", "IMPORTANT", "Apply thermal principles in design."},
                {"Technical Documentation", "Professional Skills", "INTERMEDIATE", "IMPORTANT", "Prepare design specifications and reports."}
        });

// 2. Production Engineer
        add("Production Engineer", new String[][]{
                {"Manufacturing Processes", "Manufacturing", "ADVANCED", "ESSENTIAL", "Manage industrial manufacturing processes."},
                {"Production Planning", "Manufacturing", "ADVANCED", "ESSENTIAL", "Plan production schedules and resources."},
                {"Lean Manufacturing", "Manufacturing", "INTERMEDIATE", "ESSENTIAL", "Reduce waste and improve production efficiency."},
                {"Quality Control", "Quality Engineering", "INTERMEDIATE", "ESSENTIAL", "Maintain product quality standards."},
                {"Industrial Engineering", "Manufacturing", "INTERMEDIATE", "ESSENTIAL", "Improve industrial operations and workflows."},
                {"Process Optimization", "Manufacturing", "ADVANCED", "ESSENTIAL", "Improve manufacturing process performance."},
                {"Supply Chain Management", "Operations", "INTERMEDIATE", "IMPORTANT", "Coordinate materials and production requirements."},
                {"Inventory Management", "Operations", "INTERMEDIATE", "IMPORTANT", "Manage production inventory effectively."},
                {"Six Sigma", "Quality Engineering", "INTERMEDIATE", "IMPORTANT", "Reduce defects using data-driven methods."},
                {"Work Study", "Industrial Engineering", "INTERMEDIATE", "IMPORTANT", "Improve work methods and productivity."},
                {"AutoCAD", "CAD Tools", "BEGINNER", "OPTIONAL", "Interpret manufacturing layouts and drawings."},
                {"Safety Management", "Industrial Safety", "INTERMEDIATE", "ESSENTIAL", "Maintain safe production environments."}
        });

// 3. Quality Engineer
        add("Quality Engineer", new String[][]{
                {"Quality Management Systems", "Quality Engineering", "ADVANCED", "ESSENTIAL", "Implement and maintain quality systems."},
                {"Quality Control", "Quality Engineering", "ADVANCED", "ESSENTIAL", "Inspect products and control defects."},
                {"Quality Assurance", "Quality Engineering", "ADVANCED", "ESSENTIAL", "Ensure products meet quality requirements."},
                {"Statistical Process Control", "Quality Engineering", "INTERMEDIATE", "ESSENTIAL", "Monitor manufacturing process variation."},
                {"Six Sigma", "Quality Engineering", "INTERMEDIATE", "ESSENTIAL", "Improve processes and reduce defects."},
                {"Root Cause Analysis", "Quality Engineering", "ADVANCED", "ESSENTIAL", "Identify causes of quality problems."},
                {"ISO 9001", "Quality Standards", "INTERMEDIATE", "ESSENTIAL", "Apply quality management standards."},
                {"Failure Mode and Effects Analysis", "Quality Engineering", "INTERMEDIATE", "IMPORTANT", "Identify and reduce potential failures."},
                {"Metrology", "Measurement", "INTERMEDIATE", "ESSENTIAL", "Perform accurate dimensional measurements."},
                {"Inspection Techniques", "Quality Engineering", "INTERMEDIATE", "ESSENTIAL", "Inspect components against specifications."},
                {"Engineering Drawing", "Design", "INTERMEDIATE", "IMPORTANT", "Interpret technical drawings and tolerances."},
                {"Technical Documentation", "Professional Skills", "INTERMEDIATE", "IMPORTANT", "Prepare inspection reports and records."}
        });

// 4. Automotive Engineer
        add("Automotive Engineer", new String[][]{
                {"Automotive Engineering Fundamentals", "Automotive Engineering", "ADVANCED", "ESSENTIAL", "Understand vehicle systems and engineering."},
                {"Internal Combustion Engines", "Automotive Engineering", "ADVANCED", "ESSENTIAL", "Understand engine design and operation."},
                {"Vehicle Dynamics", "Automotive Engineering", "INTERMEDIATE", "ESSENTIAL", "Analyze vehicle motion and handling."},
                {"Automotive Design", "Mechanical Engineering", "INTERMEDIATE", "ESSENTIAL", "Design automotive components and systems."},
                {"Thermodynamics", "Mechanical Engineering", "INTERMEDIATE", "ESSENTIAL", "Apply thermal engineering principles."},
                {"Fluid Mechanics", "Mechanical Engineering", "INTERMEDIATE", "IMPORTANT", "Understand fluid behavior in vehicle systems."},
                {"Machine Design", "Mechanical Engineering", "INTERMEDIATE", "ESSENTIAL", "Design mechanical vehicle components."},
                {"CAD Software", "CAD Tools", "INTERMEDIATE", "ESSENTIAL", "Create automotive component models."},
                {"Finite Element Analysis", "Simulation", "INTERMEDIATE", "IMPORTANT", "Evaluate structural performance."},
                {"Automotive Electronics", "Electronics", "INTERMEDIATE", "IMPORTANT", "Understand electronic vehicle systems."},
                {"Manufacturing Processes", "Manufacturing", "INTERMEDIATE", "IMPORTANT", "Understand automotive production methods."},
                {"Automotive Safety Standards", "Automotive Engineering", "INTERMEDIATE", "ESSENTIAL", "Apply vehicle safety requirements."}
        });

// 5. CAD Engineer
        add("CAD Engineer", new String[][]{
                {"Computer-Aided Design", "CAD Tools", "ADVANCED", "ESSENTIAL", "Create accurate computer-aided engineering designs."},
                {"AutoCAD", "CAD Tools", "ADVANCED", "ESSENTIAL", "Prepare detailed 2D engineering drawings."},
                {"SolidWorks", "CAD Tools", "ADVANCED", "ESSENTIAL", "Develop 3D parts and assemblies."},
                {"CATIA", "CAD Tools", "INTERMEDIATE", "IMPORTANT", "Create complex 3D engineering models."},
                {"Creo Parametric", "CAD Tools", "INTERMEDIATE", "IMPORTANT", "Develop parametric mechanical models."},
                {"Engineering Drawing", "Design", "ADVANCED", "ESSENTIAL", "Interpret and prepare technical drawings."},
                {"GD&T", "Engineering Design", "INTERMEDIATE", "ESSENTIAL", "Apply geometric tolerances to components."},
                {"3D Modeling", "CAD Tools", "ADVANCED", "ESSENTIAL", "Create detailed three-dimensional models."},
                {"Surface Modeling", "CAD Tools", "INTERMEDIATE", "IMPORTANT", "Design complex component surfaces."},
                {"Assembly Design", "CAD Tools", "INTERMEDIATE", "ESSENTIAL", "Create and manage mechanical assemblies."},
                {"Material Science", "Mechanical Engineering", "BEGINNER", "IMPORTANT", "Understand engineering material properties."},
                {"Technical Documentation", "Professional Skills", "INTERMEDIATE", "IMPORTANT", "Prepare CAD drawings and design documentation."}
        });




        // ==========================================
// DOMAIN 12: CIVIL & CONSTRUCTION
// ==========================================

// 1. Civil Engineer
        add("Civil Engineer", new String[][]{
                {"Engineering Mechanics", "Civil Engineering", "ADVANCED", "ESSENTIAL", "Apply mechanics principles to civil structures."},
                {"Structural Analysis", "Civil Engineering", "ADVANCED", "ESSENTIAL", "Analyze forces and behavior in structures."},
                {"Surveying", "Civil Engineering", "INTERMEDIATE", "ESSENTIAL", "Measure and map construction sites."},
                {"Building Materials", "Civil Engineering", "INTERMEDIATE", "ESSENTIAL", "Select suitable construction materials."},
                {"Concrete Technology", "Civil Engineering", "INTERMEDIATE", "ESSENTIAL", "Understand concrete properties and applications."},
                {"Geotechnical Engineering", "Civil Engineering", "INTERMEDIATE", "ESSENTIAL", "Analyze soil properties and foundations."},
                {"Fluid Mechanics", "Civil Engineering", "INTERMEDIATE", "IMPORTANT", "Apply fluid principles to civil projects."},
                {"AutoCAD", "CAD Tools", "INTERMEDIATE", "ESSENTIAL", "Prepare civil engineering drawings."},
                {"Construction Management", "Construction", "INTERMEDIATE", "ESSENTIAL", "Plan and manage construction activities."},
                {"Estimation and Costing", "Construction", "INTERMEDIATE", "IMPORTANT", "Estimate construction quantities and costs."},
                {"Environmental Engineering", "Civil Engineering", "BEGINNER", "IMPORTANT", "Understand environmental considerations in projects."},
                {"Civil Engineering Standards", "Engineering Standards", "INTERMEDIATE", "ESSENTIAL", "Apply relevant civil engineering codes."}
        });

// 2. Structural Engineer
        add("Structural Engineer", new String[][]{
                {"Structural Analysis", "Civil Engineering", "ADVANCED", "ESSENTIAL", "Analyze loads and structural behavior."},
                {"Structural Design", "Civil Engineering", "ADVANCED", "ESSENTIAL", "Design safe and efficient structures."},
                {"Engineering Mechanics", "Civil Engineering", "ADVANCED", "ESSENTIAL", "Apply mechanics to structural systems."},
                {"Strength of Materials", "Mechanical and Civil Engineering", "ADVANCED", "ESSENTIAL", "Evaluate stress and deformation."},
                {"Reinforced Cement Concrete Design", "Structural Engineering", "ADVANCED", "ESSENTIAL", "Design reinforced concrete structures."},
                {"Steel Structure Design", "Structural Engineering", "INTERMEDIATE", "ESSENTIAL", "Design structural steel members."},
                {"AutoCAD", "CAD Tools", "INTERMEDIATE", "IMPORTANT", "Prepare structural engineering drawings."},
                {"STAAD.Pro", "Structural Analysis Tools", "INTERMEDIATE", "ESSENTIAL", "Analyze structural systems using software."},
                {"ETABS", "Structural Analysis Tools", "INTERMEDIATE", "ESSENTIAL", "Model and analyze building structures."},
                {"Geotechnical Engineering", "Civil Engineering", "INTERMEDIATE", "IMPORTANT", "Understand soil and foundation behavior."},
                {"Earthquake Engineering", "Structural Engineering", "INTERMEDIATE", "IMPORTANT", "Design structures for seismic forces."},
                {"Structural Design Codes", "Engineering Standards", "INTERMEDIATE", "ESSENTIAL", "Apply structural design standards and codes."}
        });

// 3. Site Engineer
        add("Site Engineer", new String[][]{
                {"Construction Management", "Construction", "ADVANCED", "ESSENTIAL", "Coordinate daily construction activities."},
                {"Site Supervision", "Construction", "ADVANCED", "ESSENTIAL", "Supervise construction work at project sites."},
                {"Engineering Drawing", "Design", "INTERMEDIATE", "ESSENTIAL", "Read and interpret construction drawings."},
                {"Surveying", "Civil Engineering", "INTERMEDIATE", "ESSENTIAL", "Perform site measurements and layout."},
                {"Building Materials", "Civil Engineering", "INTERMEDIATE", "ESSENTIAL", "Check construction material suitability."},
                {"Concrete Technology", "Civil Engineering", "INTERMEDIATE", "ESSENTIAL", "Monitor concrete mixing and placement."},
                {"Quality Control", "Quality Engineering", "INTERMEDIATE", "ESSENTIAL", "Maintain construction quality standards."},
                {"Construction Safety", "Construction", "ADVANCED", "ESSENTIAL", "Maintain safe construction site practices."},
                {"Estimation and Costing", "Construction", "INTERMEDIATE", "IMPORTANT", "Estimate materials and project costs."},
                {"AutoCAD", "CAD Tools", "INTERMEDIATE", "IMPORTANT", "Review and interpret construction drawings."},
                {"Project Scheduling", "Project Management", "INTERMEDIATE", "IMPORTANT", "Plan and track construction schedules."},
                {"Construction Documentation", "Construction", "INTERMEDIATE", "IMPORTANT", "Maintain site records and reports."}
        });

// 4. Quantity Surveyor
        add("Quantity Surveyor", new String[][]{
                {"Quantity Estimation", "Quantity Surveying", "ADVANCED", "ESSENTIAL", "Calculate quantities of construction work."},
                {"Estimation and Costing", "Construction", "ADVANCED", "ESSENTIAL", "Prepare accurate project cost estimates."},
                {"Bill of Quantities", "Quantity Surveying", "ADVANCED", "ESSENTIAL", "Prepare bills of quantities for projects."},
                {"Rate Analysis", "Quantity Surveying", "INTERMEDIATE", "ESSENTIAL", "Calculate construction item rates."},
                {"Construction Contracts", "Construction", "INTERMEDIATE", "ESSENTIAL", "Understand construction contract requirements."},
                {"AutoCAD", "CAD Tools", "INTERMEDIATE", "IMPORTANT", "Read construction drawings for measurements."},
                {"Construction Materials", "Civil Engineering", "INTERMEDIATE", "IMPORTANT", "Understand material specifications and costs."},
                {"Project Scheduling", "Project Management", "INTERMEDIATE", "IMPORTANT", "Track project timelines and progress."},
                {"Cost Control", "Quantity Surveying", "ADVANCED", "ESSENTIAL", "Monitor and control construction expenditure."},
                {"Microsoft Excel", "Software Tools", "ADVANCED", "ESSENTIAL", "Prepare cost calculations and reports."},
                {"Construction Management", "Construction", "INTERMEDIATE", "IMPORTANT", "Understand construction project workflows."},
                {"Tendering and Procurement", "Construction", "INTERMEDIATE", "ESSENTIAL", "Prepare tenders and procurement documents."}
        });

// 5. BIM Engineer
        add("BIM Engineer", new String[][]{
                {"Building Information Modeling", "BIM", "ADVANCED", "ESSENTIAL", "Create and manage building information models."},
                {"Autodesk Revit", "BIM Tools", "ADVANCED", "ESSENTIAL", "Develop detailed BIM building models."},
                {"AutoCAD", "CAD Tools", "INTERMEDIATE", "ESSENTIAL", "Prepare and interpret technical drawings."},
                {"Navisworks", "BIM Tools", "INTERMEDIATE", "ESSENTIAL", "Coordinate models and detect clashes."},
                {"3D Modeling", "Design", "INTERMEDIATE", "ESSENTIAL", "Create three-dimensional building models."},
                {"BIM Coordination", "BIM", "ADVANCED", "ESSENTIAL", "Coordinate models across engineering disciplines."},
                {"Clash Detection", "BIM", "INTERMEDIATE", "ESSENTIAL", "Identify conflicts between building systems."},
                {"Construction Documentation", "Construction", "INTERMEDIATE", "IMPORTANT", "Prepare coordinated construction documentation."},
                {"Structural Analysis", "Civil Engineering", "INTERMEDIATE", "IMPORTANT", "Understand structural model requirements."},
                {"MEP Systems", "Building Services", "INTERMEDIATE", "IMPORTANT", "Understand mechanical electrical and plumbing systems."},
                {"BIM Standards", "BIM", "INTERMEDIATE", "ESSENTIAL", "Follow BIM standards and workflows."},
                {"Project Coordination", "Project Management", "INTERMEDIATE", "IMPORTANT", "Coordinate project information and deliverables."}
        });


        // 43. Electrical Engineer
        add("Electrical Engineer", new String[][]{
                {"Electrical Circuit Analysis", "Electrical", "ADVANCED", "ESSENTIAL", "Analyze AC and DC electrical circuits."},
                {"Electrical Machines", "Electrical", "ADVANCED", "ESSENTIAL", "Understand motors, generators, and transformers."},
                {"Power Systems", "Electrical", "INTERMEDIATE", "ESSENTIAL", "Understand electrical power generation and distribution."},
                {"Electrical Measurements", "Electrical", "INTERMEDIATE", "IMPORTANT", "Use instruments to measure electrical parameters."},
                {"Electrical Wiring", "Electrical", "INTERMEDIATE", "IMPORTANT", "Understand electrical wiring and installation practices."},
                {"Control Systems", "Electrical", "INTERMEDIATE", "IMPORTANT", "Understand feedback and control mechanisms."},
                {"Power Electronics", "Electrical", "INTERMEDIATE", "IMPORTANT", "Understand converters, inverters, and power devices."},
                {"Electrical Safety", "Electrical", "INTERMEDIATE", "ESSENTIAL", "Follow electrical safety standards and procedures."},
                {"Electrical CAD", "Electrical", "BEGINNER", "IMPORTANT", "Create and interpret electrical design drawings."},
                {"PLC Programming", "Automation", "BEGINNER", "IMPORTANT", "Develop basic programmable logic controller programs."},
                {"MATLAB", "Software", "INTERMEDIATE", "IMPORTANT", "Simulate and analyze electrical engineering systems."},
                {"Troubleshooting", "Maintenance", "INTERMEDIATE", "ESSENTIAL", "Identify and resolve electrical system faults."}
        });

// 44. Electronics Engineer
        add("Electronics Engineer", new String[][]{
                {"Analog Electronics", "Electronics", "ADVANCED", "ESSENTIAL", "Design and analyze analog electronic circuits."},
                {"Digital Electronics", "Electronics", "ADVANCED", "ESSENTIAL", "Understand digital logic and circuit design."},
                {"Electronic Circuit Design", "Electronics", "ADVANCED", "ESSENTIAL", "Design and test electronic circuits."},
                {"Microprocessors", "Embedded", "INTERMEDIATE", "ESSENTIAL", "Understand microprocessor architecture and interfacing."},
                {"Microcontrollers", "Embedded", "INTERMEDIATE", "ESSENTIAL", "Program and interface microcontroller systems."},
                {"Electronic Devices", "Electronics", "INTERMEDIATE", "ESSENTIAL", "Understand diodes, transistors, and semiconductor devices."},
                {"PCB Design", "Electronics", "INTERMEDIATE", "IMPORTANT", "Design printed circuit board layouts."},
                {"Signal Processing", "Electronics", "INTERMEDIATE", "IMPORTANT", "Process and analyze electronic signals."},
                {"Communication Systems", "Communication", "INTERMEDIATE", "IMPORTANT", "Understand analog and digital communication systems."},
                {"C Programming", "Programming", "INTERMEDIATE", "IMPORTANT", "Write programs for electronic and embedded systems."},
                {"Electronic Testing", "Testing", "INTERMEDIATE", "IMPORTANT", "Test and validate electronic circuits."},
                {"Circuit Simulation", "Electronics", "BEGINNER", "IMPORTANT", "Simulate electronic circuits using design tools."}
        });

// 45. VLSI Engineer
        add("VLSI Engineer", new String[][]{
                {"Digital Logic Design", "VLSI", "ADVANCED", "ESSENTIAL", "Design combinational and sequential logic circuits."},
                {"Verilog", "HDL", "ADVANCED", "ESSENTIAL", "Describe digital hardware using Verilog."},
                {"SystemVerilog", "HDL", "INTERMEDIATE", "ESSENTIAL", "Use SystemVerilog for design and verification."},
                {"CMOS Technology", "Semiconductor", "ADVANCED", "ESSENTIAL", "Understand CMOS transistor operation and fabrication."},
                {"VLSI Design", "VLSI", "ADVANCED", "ESSENTIAL", "Understand integrated circuit design methodologies."},
                {"Static Timing Analysis", "VLSI", "INTERMEDIATE", "ESSENTIAL", "Analyze timing constraints in digital circuits."},
                {"RTL Design", "VLSI", "INTERMEDIATE", "ESSENTIAL", "Develop register-transfer-level hardware designs."},
                {"Functional Verification", "Verification", "INTERMEDIATE", "IMPORTANT", "Verify digital designs against specifications."},
                {"ASIC Design", "VLSI", "INTERMEDIATE", "IMPORTANT", "Understand application-specific integrated circuit design."},
                {"FPGA Design", "Hardware", "INTERMEDIATE", "IMPORTANT", "Implement digital designs on FPGA devices."},
                {"Semiconductor Physics", "Semiconductor", "INTERMEDIATE", "IMPORTANT", "Understand semiconductor materials and device behavior."},
                {"EDA Tools", "VLSI", "INTERMEDIATE", "IMPORTANT", "Use electronic design automation tools."}
        });

// 46. Embedded Systems Engineer
        add("Embedded Systems Engineer", new String[][]{
                {"Embedded C", "Programming", "ADVANCED", "ESSENTIAL", "Write low-level C programs for embedded devices."},
                {"C Programming", "Programming", "ADVANCED", "ESSENTIAL", "Develop efficient programs using the C language."},
                {"Microcontrollers", "Embedded", "ADVANCED", "ESSENTIAL", "Configure and program microcontroller peripherals."},
                {"Microprocessors", "Embedded", "INTERMEDIATE", "ESSENTIAL", "Understand processor architecture and interfacing."},
                {"RTOS", "Embedded", "INTERMEDIATE", "ESSENTIAL", "Develop real-time applications using an RTOS."},
                {"Embedded Linux", "Operating Systems", "INTERMEDIATE", "IMPORTANT", "Work with Linux-based embedded platforms."},
                {"Device Drivers", "Embedded", "INTERMEDIATE", "IMPORTANT", "Develop and debug hardware device drivers."},
                {"UART", "Communication", "INTERMEDIATE", "IMPORTANT", "Implement serial communication using UART."},
                {"SPI", "Communication", "INTERMEDIATE", "IMPORTANT", "Implement Serial Peripheral Interface communication."},
                {"I2C", "Communication", "INTERMEDIATE", "IMPORTANT", "Implement I2C device communication."},
                {"PCB Design", "Electronics", "BEGINNER", "IMPORTANT", "Understand and review embedded hardware layouts."},
                {"Debugging", "Software", "INTERMEDIATE", "ESSENTIAL", "Debug embedded firmware and hardware interactions."}
        });

// 47. Power Systems Engineer
        add("Power Systems Engineer", new String[][]{
                {"Power System Analysis", "Power Systems", "ADVANCED", "ESSENTIAL", "Analyze electrical power system performance."},
                {"Power Generation", "Power Systems", "ADVANCED", "ESSENTIAL", "Understand conventional and renewable power generation."},
                {"Power Transmission", "Power Systems", "ADVANCED", "ESSENTIAL", "Understand high-voltage power transmission systems."},
                {"Power Distribution", "Power Systems", "INTERMEDIATE", "ESSENTIAL", "Design and analyze electrical distribution networks."},
                {"Load Flow Analysis", "Power Systems", "INTERMEDIATE", "ESSENTIAL", "Calculate voltage, current, and power flow."},
                {"Fault Analysis", "Power Systems", "INTERMEDIATE", "ESSENTIAL", "Analyze short-circuit and system fault conditions."},
                {"Power System Protection", "Power Systems", "INTERMEDIATE", "ESSENTIAL", "Understand relays and protection schemes."},
                {"Electrical Machines", "Electrical", "INTERMEDIATE", "IMPORTANT", "Understand generators, motors, and transformers."},
                {"High Voltage Engineering", "Electrical", "INTERMEDIATE", "IMPORTANT", "Understand high-voltage equipment and insulation."},
                {"Renewable Energy Systems", "Energy", "INTERMEDIATE", "IMPORTANT", "Integrate renewable energy into power systems."},
                {"MATLAB", "Software", "INTERMEDIATE", "IMPORTANT", "Model and simulate power system networks."},
                {"Smart Grid Technology", "Power Systems", "BEGINNER", "IMPORTANT", "Understand modern intelligent electricity networks."}
        });


        // Systems Engineer (ID: 50)
        add("Systems Engineer", new String[][]{
                {"Linux Administration", "Operating Systems", "ADVANCED", "ESSENTIAL",
                        "Manage Linux servers, users, permissions, and system services."},
                {"Windows Server Administration", "Operating Systems", "INTERMEDIATE", "ESSENTIAL",
                        "Configure and maintain Windows Server environments."},
                {"Networking Fundamentals", "Networking", "INTERMEDIATE", "ESSENTIAL",
                        "Understand IP addressing, DNS, DHCP, and network connectivity."},
                {"System Administration", "IT Infrastructure", "ADVANCED", "ESSENTIAL",
                        "Manage operating systems, users, services, and infrastructure."},
                {"Shell Scripting", "Programming", "INTERMEDIATE", "IMPORTANT",
                        "Automate system administration tasks using shell scripts."},
                {"Virtualization", "Infrastructure", "INTERMEDIATE", "IMPORTANT",
                        "Manage virtual machines and virtualization environments."},
                {"Server Management", "IT Infrastructure", "INTERMEDIATE", "ESSENTIAL",
                        "Monitor and maintain server infrastructure."},
                {"System Monitoring", "IT Operations", "INTERMEDIATE", "IMPORTANT",
                        "Monitor system performance, availability, and resource usage."},
                {"Troubleshooting", "IT Operations", "ADVANCED", "ESSENTIAL",
                        "Diagnose and resolve hardware, software, and system issues."},
                {"Active Directory", "IT Infrastructure", "INTERMEDIATE", "IMPORTANT",
                        "Manage users, groups, and policies using Active Directory."},
                {"Backup and Recovery", "IT Infrastructure", "INTERMEDIATE", "IMPORTANT",
                        "Implement backup strategies and disaster recovery procedures."},
                {"Cloud Computing Fundamentals", "Cloud", "BEGINNER", "IMPORTANT",
                        "Understand cloud infrastructure and basic cloud services."}
        });
        System.out.println("======================================");
        System.out.println("Software Development role skills processed.");
        System.out.println("======================================");
    }

    private void add(
            String roleName,
            String skillName,
            String category,
            ProficiencyLevel requiredLevel,
            SkillPriority priority,
            String explanation) {

        JobRole jobRole = jobRoleRepository.findAll()
                .stream()
                .filter(role -> role.getName().equalsIgnoreCase(roleName))
                .findFirst()
                .orElse(null);

        if (jobRole == null) {
            System.out.println("Job role not found: " + roleName);
            return;
        }

        Skill skill = skillRepository.findByNameIgnoreCase(skillName)
                .orElseGet(() -> {
                    Skill newSkill = new Skill();
                    newSkill.setName(skillName);
                    newSkill.setCategory(category);
                    return skillRepository.save(newSkill);
                });

        // Synchronize the many-to-many relationship.
        jobRole.getRequiredSkills().add(skill);

        // Create only missing proficiency records.
        JobRoleSkill roleSkill = jobRoleSkillRepository
                .findByJobRoleIdAndSkillId(jobRole.getId(), skill.getId())
                .orElse(null);

        if (roleSkill == null) {
            roleSkill = new JobRoleSkill();
            roleSkill.setJobRole(jobRole);
            roleSkill.setSkill(skill);
            roleSkill.setRequiredLevel(requiredLevel);
            roleSkill.setPriority(priority);
            roleSkill.setRelevanceExplanation(explanation);

            jobRoleSkillRepository.save(roleSkill);
        }

        // Save the many-to-many relationship.
        jobRoleRepository.save(jobRole);
    }

    private void add(String roleName, String[][] skills) {

        for (String[] skill : skills) {

            add(
                    roleName,
                    skill[0], // Skill name
                    skill[1], // Category
                    ProficiencyLevel.valueOf(skill[2]), // Proficiency
                    SkillPriority.valueOf(skill[3]),    // Priority
                    skill[4]  // Explanation
            );
        }
    }
}
