
package com.skillgap.skillgap;

import com.skillgap.skillgap.entity.LearningResource;
import com.skillgap.skillgap.entity.Skill;
import com.skillgap.skillgap.repository.LearningResourceRepository;
import com.skillgap.skillgap.repository.SkillRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class LearningResourceSeeder implements CommandLineRunner {

    private final SkillRepository skillRepository;
    private final LearningResourceRepository resourceRepository;

    public LearningResourceSeeder(
            SkillRepository skillRepository,
            LearningResourceRepository resourceRepository) {
        this.skillRepository = skillRepository;
        this.resourceRepository = resourceRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {

        // ==========================================
        // PYTHON
        // ==========================================
        add("Python", "Python Official Documentation",
                "https://docs.python.org/3/", "Documentation", 1);
        add("Python", "Python Tutorial",
                "https://www.w3schools.com/python/", "Tutorial", 2);
        add("Python", "Python YouTube Tutorials",
                youtube("Python Programming Tutorial"), "YouTube", 3);
        add("Python", "Python Google Search",
                google("Python Programming Tutorial for Beginners"), "Google Search", 4);

        // ==========================================
        // DATA STRUCTURES AND ALGORITHMS
        // ==========================================
        add("Data Structures & Algorithms", "GeeksforGeeks DSA",
                "https://www.geeksforgeeks.org/data-structures/", "Tutorial", 1);
        add("Data Structures & Algorithms", "VisuAlgo",
                "https://visualgo.net/", "Interactive", 2);
        add("Data Structures & Algorithms", "DSA YouTube Tutorials",
                youtube("Data Structures and Algorithms Tutorial"), "YouTube", 3);
        add("Data Structures & Algorithms", "DSA Google Search",
                google("Data Structures and Algorithms for Beginners"), "Google Search", 4);

        // ==========================================
        // OBJECT-ORIENTED PROGRAMMING
        // ==========================================
        add("Object-Oriented Programming", "Python Classes Documentation",
                "https://docs.python.org/3/tutorial/classes.html", "Documentation", 1);
        add("Object-Oriented Programming", "Real Python OOP Tutorial",
                "https://realpython.com/python3-object-oriented-programming/", "Tutorial", 2);
        add("Object-Oriented Programming", "OOP YouTube Tutorials",
                youtube("Object Oriented Programming Python"), "YouTube", 3);
        add("Object-Oriented Programming", "OOP Google Search",
                google("Object Oriented Programming Python Tutorial"), "Google Search", 4);
        add("Object-Oriented Programming", "Java Object Oriented Programming",
                "https://dev.java/learn/classes-objects/", "Documentation", 5);
        add("Object-Oriented Programming", "Java OOP Concepts Tutorial",
                youtube("Java OOP Concepts Classes Objects Inheritance Polymorphism"), "YouTube", 6);

        // ==========================================
        // SQL
        // ==========================================
        add("SQL", "SQL for Beginners",
                "https://www.w3schools.com/sql/", "Tutorial", 1);
        add("SQL", "PostgreSQL Documentation",
                "https://www.postgresql.org/docs/", "Documentation", 2);
        add("SQL", "SQL for Data Analysis YouTube",
                youtube("SQL for Data Analysis"), "YouTube", 3);
        add("SQL", "SQL Tutorial for Beginners Google Search",
                google("SQL Tutorial for Beginners"), "Google Search", 4);

        // ==========================================
        // GIT
        // ==========================================
        add("Git", "Git Official Documentation",
                "https://git-scm.com/doc", "Documentation", 1);
        add("Git", "Git Tutorial for Beginners",
                "https://www.git-tutorial.com/", "Tutorial", 2);
        add("Git", "Git YouTube Tutorials",
                youtube("Git Full Course for Beginners"), "YouTube", 3);
        add("Git", "Git Google Search",
                google("Git Tutorial for Beginners"), "Google Search", 4);

        // ==========================================
        // REST APIs
        // ==========================================
        add("REST APIs", "FastAPI Official Tutorial",
                "https://fastapi.tiangolo.com/tutorial/", "Documentation", 1);
        add("REST APIs", "MDN HTTP Documentation",
                "https://developer.mozilla.org/en-US/docs/Web/HTTP", "Documentation", 2);
        add("REST APIs", "REST API YouTube Tutorials",
                youtube("REST API Tutorial for Beginners"), "YouTube", 3);
        add("REST APIs", "REST API Google Search",
                google("REST API Tutorial for Beginners"), "Google Search", 4);
        add("REST APIs", "REST API Design Best Practices",
                "https://restfulapi.net/", "Tutorial", 5);
        add("REST APIs", "Spring Boot REST API Tutorial",
                youtube("Spring Boot REST API Tutorial Java"), "YouTube", 6);

        // ==========================================
        // PYTEST
        // ==========================================
        add("pytest", "pytest Official Documentation",
                "https://docs.pytest.org/", "Documentation", 1);
        add("pytest", "Real Python pytest Tutorial",
                "https://realpython.com/pytest-python-testing/", "Tutorial", 2);
        add("pytest", "pytest YouTube Tutorials",
                youtube("Python pytest Tutorial"), "YouTube", 3);
        add("pytest", "pytest Google Search",
                google("pytest Tutorial for Beginners"), "Google Search", 4);

        // ==========================================
        // DEBUGGING
        // ==========================================
        add("Debugging", "Python Debugger Documentation",
                "https://docs.python.org/3/library/pdb.html", "Documentation", 1);
        add("Debugging", "VS Code Python Debugging",
                "https://code.visualstudio.com/docs/python/debugging", "Tutorial", 2);
        add("Debugging", "Python Debugging YouTube Tutorials",
                youtube("Python Debugging Tutorial"), "YouTube", 3);
        add("Debugging", "Python Debugging Google Search",
                google("Python Debugging Techniques for Beginners"), "Google Search", 4);

        // ==========================================
        // EXCEPTION HANDLING
        // ==========================================
        add("Exception Handling", "Python Errors and Exceptions",
                "https://docs.python.org/3/tutorial/errors.html", "Documentation", 1);
        add("Exception Handling", "Real Python Exception Handling",
                "https://realpython.com/python-exceptions/", "Tutorial", 2);
        add("Exception Handling", "Exception Handling YouTube Tutorials",
                youtube("Python Exception Handling Tutorial"), "YouTube", 3);
        add("Exception Handling", "Exception Handling Google Search",
                google("Python Exception Handling Tutorial"), "Google Search", 4);
        add("Exception Handling", "Java Exceptions Official Tutorial",
                "https://docs.oracle.com/javase/tutorial/essential/exceptions/", "Documentation", 5);
        add("Exception Handling", "Java Exception Handling Tutorial",
                youtube("Java Exception Handling Tutorial"), "YouTube", 6);

        // ==========================================
        // PYTHON LIBRARIES
        // ==========================================
        add("Python Libraries", "Python Standard Library",
                "https://docs.python.org/3/library/", "Documentation", 1);
        add("Python Libraries", "NumPy Documentation",
                "https://numpy.org/doc/stable/", "Documentation", 2);
        add("Python Libraries", "Python Libraries YouTube Tutorials",
                youtube("Popular Python Libraries Tutorial"), "YouTube", 3);
        add("Python Libraries", "Python Libraries Google Search",
                google("Best Python Libraries for Beginners"), "Google Search", 4);

        // ==========================================
        // JAVA
        // ==========================================
        add("Java", "Java Official Documentation",
                "https://docs.oracle.com/en/java/", "Documentation", 1);
        add("Java", "Dev Java Learning Tutorials",
                "https://dev.java/learn/", "Tutorial", 2);
        add("Java", "Java Programming YouTube Course",
                youtube("Java Programming Full Course for Beginners"), "YouTube", 3);
        add("Java", "Java Programming Practice",
                google("Java programming practice problems for beginners"), "Google Search", 4);

        // ==========================================
        // SPRING BOOT
        // ==========================================
        add("Spring Boot", "Spring Boot Official Documentation",
                "https://docs.spring.io/spring-boot/index.html", "Documentation", 1);
        add("Spring Boot", "Spring Guides",
                "https://spring.io/guides", "Tutorial", 2);
        add("Spring Boot", "Spring Boot REST API Course",
                youtube("Spring Boot REST API Full Course Java"), "YouTube", 3);
        add("Spring Boot", "Spring Boot Beginner Tutorial",
                google("Spring Boot tutorial for beginners"), "Google Search", 4);

        // ==========================================
        // HIBERNATE
        // ==========================================
        add("Hibernate", "Hibernate Official Documentation",
                "https://hibernate.org/orm/documentation/", "Documentation", 1);
        add("Hibernate", "Hibernate ORM Getting Started",
                "https://hibernate.org/orm/documentation/getting-started/", "Tutorial", 2);
        add("Hibernate", "Hibernate Java Tutorials",
                youtube("Hibernate ORM Java Tutorial"), "YouTube", 3);
        add("Hibernate", "Hibernate Google Search",
                google("Hibernate ORM Java beginner tutorial"), "Google Search", 4);

        // ==========================================
        // JDBC
        // ==========================================
        add("JDBC", "Oracle JDBC Basics",
                "https://docs.oracle.com/javase/tutorial/jdbc/basics/", "Documentation", 1);
        add("JDBC", "JDBC Java Tutorial",
                youtube("JDBC Java Tutorial for Beginners"), "YouTube", 2);
        add("JDBC", "JDBC Database Connectivity Tutorial",
                google("Java JDBC database connectivity tutorial"), "Google Search", 3);

        // ==========================================
        // JUNIT
        // ==========================================
        add("JUnit", "JUnit 5 Official User Guide",
                "https://junit.org/junit5/docs/current/user-guide/", "Documentation", 1);
        add("JUnit", "JUnit 5 Getting Started",
                "https://junit.org/junit5/docs/current/user-guide/#overview-getting-started", "Tutorial", 2);
        add("JUnit", "JUnit Java Testing Tutorial",
                youtube("JUnit 5 Java Unit Testing Tutorial"), "YouTube", 3);
        add("JUnit", "JUnit Testing Google Search",
                google("JUnit 5 testing examples for beginners"), "Google Search", 4);

        // ==========================================
        // MULTITHREADING
        // ==========================================
        add("Multithreading", "Java Concurrency Documentation",
                "https://docs.oracle.com/javase/tutorial/essential/concurrency/", "Documentation", 1);
        add("Multithreading", "Java Concurrency API",
                "https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/package-summary.html", "Documentation", 2);
        add("Multithreading", "Java Multithreading Tutorial",
                youtube("Java Multithreading Tutorial for Beginners"), "YouTube", 3);
        add("Multithreading", "Java Concurrency Google Search",
                google("Java multithreading and concurrency examples"), "Google Search", 4);

        // ==========================================
        // MAVEN
        // ==========================================
        add("Maven", "Apache Maven Official Documentation",
                "https://maven.apache.org/guides/", "Documentation", 1);
        add("Maven", "Maven Getting Started Guide",
                "https://maven.apache.org/guides/getting-started/", "Tutorial", 2);
        add("Maven", "Maven Java Tutorial",
                youtube("Apache Maven Java Tutorial for Beginners"), "YouTube", 3);
        add("Maven", "Maven Google Search",
                google("Apache Maven tutorial for beginners"), "Google Search", 4);

        // ==========================================
        // HTML
        // ==========================================
        add("HTML", "MDN HTML Learning Guide",
                "https://developer.mozilla.org/en-US/docs/Learn_web_development/Core/Structuring_content", "Documentation", 1);
        add("HTML", "W3Schools HTML Tutorial",
                "https://www.w3schools.com/html/", "Tutorial", 2);
        add("HTML", "HTML YouTube Course",
                youtube("HTML Full Course for Beginners"), "YouTube", 3);

        // ==========================================
        // CSS
        // ==========================================
        add("CSS", "MDN CSS Documentation",
                "https://developer.mozilla.org/en-US/docs/Web/CSS", "Documentation", 1);
        add("CSS", "W3Schools CSS Tutorial",
                "https://www.w3schools.com/css/", "Tutorial", 2);
        add("CSS", "CSS YouTube Course",
                youtube("CSS Full Course for Beginners"), "YouTube", 3);

        // ==========================================
        // JAVASCRIPT
        // ==========================================
        add("JavaScript", "MDN JavaScript Guide",
                "https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide", "Documentation", 1);
        add("JavaScript", "JavaScript.info Modern Tutorial",
                "https://javascript.info/", "Tutorial", 2);
        add("JavaScript", "JavaScript YouTube Course",
                youtube("JavaScript Full Course for Beginners"), "YouTube", 3);
        add("JavaScript", "JavaScript Practice",
                google("JavaScript coding exercises for beginners"), "Google Search", 4);

        // ==========================================
        // REACT
        // ==========================================
        add("React", "React Official Documentation",
                "https://react.dev/learn", "Documentation", 1);
        add("React", "React Tutorial for Beginners",
                youtube("React JS Full Course for Beginners"), "YouTube", 2);
        add("React", "React Practice Projects",
                google("React beginner projects with source code"), "Google Search", 3);

        // ==========================================
        // TYPESCRIPT
        // ==========================================
        add("TypeScript", "TypeScript Official Documentation",
                "https://www.typescriptlang.org/docs/", "Documentation", 1);
        add("TypeScript", "TypeScript Handbook",
                "https://www.typescriptlang.org/docs/handbook/intro.html", "Tutorial", 2);
        add("TypeScript", "TypeScript YouTube Course",
                youtube("TypeScript Full Course for Beginners"), "YouTube", 3);
        add("TypeScript", "TypeScript Practice",
                google("TypeScript beginner coding exercises"), "Google Search", 4);

        // ==========================================
        // RESPONSIVE WEB DESIGN
        // ==========================================
        add("Responsive Web Design", "MDN Responsive Design Guide",
                "https://developer.mozilla.org/en-US/docs/Learn_web_development/Core/CSS_layout/Responsive_Design", "Documentation", 1);
        add("Responsive Web Design", "web.dev Responsive Design",
                "https://web.dev/learn/design/", "Tutorial", 2);
        add("Responsive Web Design", "Responsive Design YouTube Tutorials",
                youtube("Responsive Web Design HTML CSS Tutorial"), "YouTube", 3);
        add("Responsive Web Design", "Responsive Design Google Search",
                google("Responsive web design projects for beginners"), "Google Search", 4);

        // ==========================================
        // WEB ACCESSIBILITY
        // ==========================================
        add("Web Accessibility", "W3C Web Accessibility Fundamentals",
                "https://www.w3.org/WAI/fundamentals/", "Documentation", 1);
        add("Web Accessibility", "MDN Accessibility Guide",
                "https://developer.mozilla.org/en-US/docs/Web/Accessibility", "Tutorial", 2);
        add("Web Accessibility", "Web Accessibility YouTube Tutorials",
                youtube("Web Accessibility WCAG Tutorial"), "YouTube", 3);
        add("Web Accessibility", "Web Accessibility Google Search",
                google("Web accessibility WCAG beginner guide"), "Google Search", 4);

        // ==========================================
        // BROWSER DEVELOPER TOOLS
        // ==========================================
        add("Browser Developer Tools", "Chrome DevTools Documentation",
                "https://developer.chrome.com/docs/devtools/", "Documentation", 1);
        add("Browser Developer Tools", "Firefox Developer Tools",
                "https://firefox-source-docs.mozilla.org/devtools-user/", "Documentation", 2);
        add("Browser Developer Tools", "Chrome DevTools YouTube Tutorials",
                youtube("Chrome Developer Tools tutorial for beginners"), "YouTube", 3);
        add("Browser Developer Tools", "Browser Debugging Google Search",
                google("Browser developer tools debugging tutorial"), "Google Search", 4);

        // ==========================================
        // UI/UX FUNDAMENTALS
        // ==========================================
        add("UI/UX Fundamentals", "Google UX Design Resources",
                "https://grow.google/certificates/ux-design/", "Tutorial", 1);
        add("UI/UX Fundamentals", "Nielsen Norman Group UX Articles",
                "https://www.nngroup.com/articles/", "Documentation", 2);
        add("UI/UX Fundamentals", "UI UX Fundamentals YouTube Tutorials",
                youtube("UI UX design fundamentals for beginners"), "YouTube", 3);
        add("UI/UX Fundamentals", "UI UX Google Search",
                google("UI UX design principles for beginners"), "Google Search", 4);

        // ==========================================
        // FRONTEND TESTING
        // ==========================================
        add("Frontend Testing", "Vitest Official Documentation",
                "https://vitest.dev/guide/", "Documentation", 1);
        add("Frontend Testing", "React Testing Library Documentation",
                "https://testing-library.com/docs/react-testing-library/intro/", "Documentation", 2);
        add("Frontend Testing", "Frontend Testing YouTube Tutorials",
                youtube("Frontend testing React Vitest tutorial"), "YouTube", 3);
        add("Frontend Testing", "Frontend Testing Google Search",
                google("Frontend testing beginner guide"), "Google Search", 4);


        // ==========================================
        // DATABASE DESIGN
        // ==========================================
        add("Database Design", "Database Design Fundamentals",
                "https://www.ibm.com/think/topics/database-design", "Documentation", 1);
        add("Database Design", "Database Normalization Tutorial",
                youtube("Database Normalization 1NF 2NF 3NF Tutorial"), "YouTube", 2);
        add("Database Design", "Database Design Practice",
                google("Relational database design examples for beginners"), "Google Search", 3);

        // ==========================================
        // SPRING SECURITY
        // ==========================================
        add("Spring Security", "Spring Security Official Documentation",
                "https://docs.spring.io/spring-security/reference/", "Documentation", 1);
        add("Spring Security", "Spring Security Java Tutorial",
                youtube("Spring Security Spring Boot Tutorial for Beginners"), "YouTube", 2);
        add("Spring Security", "Spring Security Learning Guide",
                google("Spring Security authentication authorization tutorial"), "Google Search", 3);

        // ==========================================
        // CACHING
        // ==========================================
        add("Caching", "Spring Boot Caching Documentation",
                "https://docs.spring.io/spring-boot/reference/io/caching.html", "Documentation", 1);
        add("Caching", "Java and Spring Boot Caching Tutorial",
                youtube("Spring Boot caching tutorial Java"), "YouTube", 2);
        add("Caching", "Caching Fundamentals",
                google("Caching concepts cache eviction cache invalidation tutorial"), "Google Search", 3);

        // ==========================================
        // MICROSERVICES
        // ==========================================
        add("Microservices", "Microservices Architecture Guide",
                "https://microservices.io/", "Documentation", 1);
        add("Microservices", "Spring Microservices Tutorial",
                youtube("Spring Boot Microservices tutorial for beginners"), "YouTube", 2);
        add("Microservices", "Microservices Design Patterns",
                google("Microservices architecture design patterns tutorial"), "Google Search", 3);

        // ==========================================
        // AUTHENTICATION AND AUTHORIZATION
        // ==========================================
        add("Authentication and Authorization", "Spring Security Authentication Guide",
                "https://docs.spring.io/spring-security/reference/servlet/authentication/index.html", "Documentation", 1);
        add("Authentication and Authorization", "OAuth 2.0 Official Guide",
                "https://oauth.net/2/", "Documentation", 2);
        add("Authentication and Authorization", "JWT Authentication Tutorial",
                youtube("Spring Boot JWT Authentication Authorization tutorial"), "YouTube", 3);
        add("Authentication and Authorization", "Authentication and Authorization Fundamentals",
                google("Authentication vs authorization RBAC JWT tutorial"), "Google Search", 4);


// ==========================================
// KOTLIN
// ==========================================
        add("Kotlin", "Kotlin Official Documentation",
                "https://kotlinlang.org/docs/home.html", "Documentation", 1);
        add("Kotlin", "Kotlin Basic Syntax",
                "https://kotlinlang.org/docs/basic-syntax.html", "Tutorial", 2);
        add("Kotlin", "Kotlin Android YouTube Tutorials",
                youtube("Kotlin programming for Android beginners"), "YouTube", 3);
        add("Kotlin", "Kotlin Practice Exercises",
                google("Kotlin programming practice exercises"), "Google Search", 4);

// ==========================================
// ANDROID SDK
// ==========================================
        add("Android SDK", "Android Developers SDK Guide",
                "https://developer.android.com/tools", "Documentation", 1);
        add("Android SDK", "Android SDK Fundamentals",
                youtube("Android SDK tutorial for beginners"), "YouTube", 2);
        add("Android SDK", "Android SDK Learning Guide",
                google("Android SDK components tutorial"), "Google Search", 3);

// ==========================================
// ANDROID STUDIO
// ==========================================
        add("Android Studio", "Android Studio Official Documentation",
                "https://developer.android.com/studio/intro", "Documentation", 1);
        add("Android Studio", "Android Studio User Guide",
                "https://developer.android.com/studio/intro", "Tutorial", 2);
        add("Android Studio", "Android Studio YouTube Tutorial",
                youtube("Android Studio tutorial for beginners"), "YouTube", 3);

// ==========================================
// XML
// ==========================================
        add("XML", "W3Schools XML Tutorial",
                "https://www.w3schools.com/xml/", "Tutorial", 1);
        add("XML", "MDN XML Introduction",
                "https://developer.mozilla.org/en-US/docs/Web/XML", "Documentation", 2);
        add("XML", "XML Android Layout Tutorial",
                youtube("Android XML layout tutorial for beginners"), "YouTube", 3);

// ==========================================
// JETPACK COMPOSE
// ==========================================
        add("Jetpack Compose", "Jetpack Compose Official Documentation",
                "https://developer.android.com/develop/ui/compose", "Documentation", 1);
        add("Jetpack Compose", "Compose Basics Tutorial",
                "https://developer.android.com/develop/ui/compose/tutorial", "Tutorial", 2);
        add("Jetpack Compose", "Jetpack Compose YouTube Course",
                youtube("Jetpack Compose full course for beginners"), "YouTube", 3);

// ==========================================
// ROOM DATABASE
// ==========================================
        add("Room Database", "Android Room Persistence Library",
                "https://developer.android.com/training/data-storage/room", "Documentation", 1);
        add("Room Database", "Room Database Android Tutorial",
                youtube("Android Room database Kotlin tutorial"), "YouTube", 2);
        add("Room Database", "Room Database Learning Guide",
                google("Android Room database Kotlin examples"), "Google Search", 3);

// ==========================================
// MATERIAL DESIGN
// ==========================================
        add("Material Design", "Material Design Official Guidelines",
                "https://m3.material.io/", "Documentation", 1);
        add("Material Design", "Material Design for Android",
                "https://developer.android.com/develop/ui/compose/designsystems/material3", "Tutorial", 2);
        add("Material Design", "Material Design YouTube Tutorials",
                youtube("Material Design 3 Android Compose tutorial"), "YouTube", 3);

// ==========================================
// FIREBASE
// ==========================================
        add("Firebase", "Firebase Android Documentation",
                "https://firebase.google.com/docs/android/setup", "Documentation", 1);
        add("Firebase", "Firebase Android Learning Guide",
                "https://firebase.google.com/docs", "Tutorial", 2);
        add("Firebase", "Firebase Android YouTube Tutorials",
                youtube("Firebase Android Kotlin tutorial"), "YouTube", 3);

// ==========================================
// APP TESTING
// ==========================================
        add("App Testing", "Android Testing Fundamentals",
                "https://developer.android.com/training/testing/fundamentals", "Documentation", 1);
        add("App Testing", "Android Testing Codelabs",
                "https://developer.android.com/codelabs", "Tutorial", 2);
        add("App Testing", "Android App Testing YouTube Tutorials",
                youtube("Android app testing Kotlin tutorial"), "YouTube", 3);

// ==========================================
// COROUTINES
// ==========================================
        add("Coroutines", "Kotlin Coroutines Official Guide",
                "https://kotlinlang.org/docs/coroutines-guide.html", "Documentation", 1);
        add("Coroutines", "Android Kotlin Coroutines Guide",
                "https://developer.android.com/kotlin/coroutines", "Tutorial", 2);
        add("Coroutines", "Kotlin Coroutines YouTube Tutorial",
                youtube("Kotlin coroutines Android tutorial for beginners"), "YouTube", 3);


// ==========================================
// SOFTWARE DEVELOPMENT LIFE CYCLE (SDLC)
// ==========================================
        add("Software Development Life Cycle", "SDLC Official Overview",
                "https://www.ibm.com/think/topics/software-development-life-cycle", "Documentation", 1);
        add("Software Development Life Cycle", "SDLC Models Tutorial",
                youtube("Software Development Life Cycle SDLC models tutorial"), "YouTube", 2);
        add("Software Development Life Cycle", "SDLC Google Search",
                google("SDLC phases waterfall agile spiral model"), "Google Search", 3);

// ==========================================
// UNIT TESTING
// ==========================================
        add("Unit Testing", "JUnit 5 Official User Guide",
                "https://junit.org/junit5/docs/current/user-guide/", "Documentation", 1);
        add("Unit Testing", "Unit Testing Fundamentals",
                "https://martinfowler.com/articles/practical-test-pyramid.html", "Tutorial", 2);
        add("Unit Testing", "Java Unit Testing Tutorial",
                youtube("Java unit testing JUnit Mockito tutorial"), "YouTube", 3);
        add("Unit Testing", "Unit Testing Google Search",
                google("unit testing principles examples for beginners"), "Google Search", 4);

// ==========================================
// OPERATING SYSTEMS
// ==========================================
        add("Operating Systems", "Operating Systems Concepts",
                "https://pages.cs.wisc.edu/~remzi/OSTEP/", "Documentation", 1);
        add("Operating Systems", "Operating Systems Tutorial",
                youtube("Operating Systems full course for beginners"), "YouTube", 2);
        add("Operating Systems", "Operating Systems Google Search",
                google("operating systems processes threads memory management tutorial"), "Google Search", 3);

// ==========================================
// COMPUTER NETWORKS
// ==========================================
        add("Computer Networks", "Computer Networking Guide",
                "https://developer.mozilla.org/en-US/docs/Web/HTTP", "Documentation", 1);
        add("Computer Networks", "Computer Networks Full Course",
                youtube("Computer Networks full course for beginners"), "YouTube", 2);
        add("Computer Networks", "Computer Networks Google Search",
                google("computer networks OSI TCP IP DNS HTTP tutorial"), "Google Search", 3);

// ==========================================
// DESIGN PATTERNS
// ==========================================
        add("Design Patterns", "Refactoring Guru Design Patterns",
                "https://refactoring.guru/design-patterns", "Tutorial", 1);
        add("Design Patterns", "Java Design Patterns Repository",
                "https://github.com/iluwatar/java-design-patterns", "Documentation", 2);
        add("Design Patterns", "Java Design Patterns YouTube Tutorials",
                youtube("Java design patterns for beginners"), "YouTube", 3);
        add("Design Patterns", "Design Patterns Google Search",
                google("Java design patterns practical examples"), "Google Search", 4);

// ==========================================
// AGILE METHODOLOGY
// ==========================================
        add("Agile Methodology", "Agile Manifesto",
                "https://agilemanifesto.org/", "Documentation", 1);
        add("Agile Methodology", "Scrum Guide",
                "https://scrumguides.org/scrum-guide.html", "Documentation", 2);
        add("Agile Methodology", "Agile Methodology YouTube Tutorials",
                youtube("Agile methodology Scrum tutorial for beginners"), "YouTube", 3);
        add("Agile Methodology", "Agile Google Search",
                google("Agile methodology Scrum sprint retrospective tutorial"), "Google Search", 4);


        // ==========================================
        // CSIT DOMAIN — AI AND MACHINE LEARNING
        // ==========================================

        // Mathematics for Machine Learning
        add("Mathematics for Machine Learning", "Mathematics for Machine Learning - Mathematics for ML",
                "https://mml-book.github.io/", "Documentation", 1);
        add("Mathematics for Machine Learning", "Khan Academy Linear Algebra",
                "https://www.khanacademy.org/math/linear-algebra", "Tutorial", 2);
        add("Mathematics for Machine Learning", "Mathematics for Machine Learning Video Tutorials",
                youtube("Mathematics for Machine Learning linear algebra calculus"), "YouTube", 3);
        add("Mathematics for Machine Learning", "Mathematics for Machine Learning Practice",
                google("mathematics for machine learning practice exercises"), "Google Search", 4);

        // Statistics
        add("Statistics", "OpenStax Introductory Statistics",
                "https://openstax.org/details/books/introductory-statistics", "Documentation", 1);
        add("Statistics", "Khan Academy Statistics",
                "https://www.khanacademy.org/math/statistics-probability", "Tutorial", 2);
        add("Statistics", "Statistics for Machine Learning Tutorials",
                youtube("statistics for machine learning beginners"), "YouTube", 3);
        add("Statistics", "Statistics Practice Problems",
                google("statistics probability practice problems with solutions"), "Google Search", 4);

        // Machine Learning
        add("Machine Learning", "Google Machine Learning Crash Course",
                "https://developers.google.com/machine-learning/crash-course", "Documentation", 1);
        add("Machine Learning", "Machine Learning Andrew Ng Course",
                "https://www.coursera.org/specializations/machine-learning-introduction", "Tutorial", 2);
        add("Machine Learning", "Machine Learning Beginner Full Course",
                youtube("machine learning full course for beginners"), "YouTube", 3);
        add("Machine Learning", "Machine Learning Practice Projects",
                google("beginner machine learning projects datasets"), "Google Search", 4);

        // Deep Learning
        add("Deep Learning", "Deep Learning Book",
                "https://www.deeplearningbook.org/", "Documentation", 1);
        add("Deep Learning", "PyTorch Tutorials",
                "https://pytorch.org/tutorials/", "Tutorial", 2);
        add("Deep Learning", "Deep Learning Beginner Course",
                youtube("deep learning neural networks full course for beginners"), "YouTube", 3);
        add("Deep Learning", "Deep Learning Practice Projects",
                google("deep learning beginner projects neural networks"), "Google Search", 4);

        // NumPy and Pandas
        add("NumPy and Pandas", "NumPy Official Documentation",
                "https://numpy.org/doc/stable/user/absolute_beginners.html", "Documentation", 1);
        add("NumPy and Pandas", "Pandas Getting Started",
                "https://pandas.pydata.org/docs/getting_started/index.html", "Tutorial", 2);
        add("NumPy and Pandas", "NumPy and Pandas Beginner Course",
                youtube("NumPy Pandas full course for beginners"), "YouTube", 3);
        add("NumPy and Pandas", "NumPy Pandas Practice",
                google("NumPy Pandas data analysis practice exercises"), "Google Search", 4);

        // Scikit-learn
        add("Scikit-learn", "Scikit-learn Official User Guide",
                "https://scikit-learn.org/stable/user_guide.html", "Documentation", 1);
        add("Scikit-learn", "Scikit-learn Getting Started",
                "https://scikit-learn.org/stable/getting_started.html", "Tutorial", 2);
        add("Scikit-learn", "Scikit-learn Beginner Tutorials",
                youtube("Scikit learn machine learning tutorial for beginners"), "YouTube", 3);
        add("Scikit-learn", "Scikit-learn Practice Projects",
                google("scikit learn beginner projects classification regression"), "Google Search", 4);

        // Model Evaluation
        add("Model Evaluation", "Scikit-learn Model Evaluation Guide",
                "https://scikit-learn.org/stable/modules/model_evaluation.html", "Documentation", 1);
        add("Model Evaluation", "Google Machine Learning Classification Metrics",
                "https://developers.google.com/machine-learning/crash-course/classification/accuracy-precision-recall", "Tutorial", 2);
        add("Model Evaluation", "Model Evaluation Metrics Tutorials",
                youtube("machine learning model evaluation precision recall F1 score"), "YouTube", 3);
        add("Model Evaluation", "Model Evaluation Practice",
                google("machine learning model evaluation practice confusion matrix"), "Google Search", 4);


        // ==========================================
        // CSIT DOMAIN — IT OPERATIONS
        // ==========================================

        // IT Service Management
        add("IT Service Management", "Atlassian ITSM Guide",
                "https://www.atlassian.com/itsm", "Documentation", 1);
        add("IT Service Management", "IBM IT Service Management",
                "https://www.ibm.com/think/topics/it-service-management", "Tutorial", 2);
        add("IT Service Management", "ITSM and ITIL Beginner Tutorials",
                youtube("IT service management ITIL tutorial for beginners"), "YouTube", 3);
        add("IT Service Management", "ITSM Practice and Case Studies",
                google("IT service management ITIL practical examples"), "Google Search", 4);

        // Monitoring Tools
        add("Monitoring Tools", "Prometheus Official Documentation",
                "https://prometheus.io/docs/introduction/overview/", "Documentation", 1);
        add("Monitoring Tools", "Grafana Getting Started",
                "https://grafana.com/docs/grafana/latest/getting-started/", "Tutorial", 2);
        add("Monitoring Tools", "Server Monitoring Tools Tutorials",
                youtube("Prometheus Grafana monitoring tutorial for beginners"), "YouTube", 3);
        add("Monitoring Tools", "Monitoring Tools Practical Guide",
                google("server application monitoring tools practical tutorial"), "Google Search", 4);


        // ==========================================
        // CSIT DOMAIN — CLOUD AND NETWORKING
        // ==========================================

        // AWS
        add("AWS", "AWS Official Documentation",
                "https://docs.aws.amazon.com/", "Documentation", 1);
        add("AWS", "AWS Skill Builder",
                "https://skillbuilder.aws/", "Tutorial", 2);
        add("AWS", "AWS Cloud Practitioner Beginner Course",
                youtube("AWS cloud practitioner full course for beginners"), "YouTube", 3);
        add("AWS", "AWS Hands-on Labs",
                google("AWS hands on labs beginner projects"), "Google Search", 4);

        // Azure
        add("Azure", "Microsoft Azure Documentation",
                "https://learn.microsoft.com/en-us/azure/", "Documentation", 1);
        add("Azure", "Microsoft Learn Azure Fundamentals",
                "https://learn.microsoft.com/en-us/training/azure/", "Tutorial", 2);
        add("Azure", "Azure Fundamentals Beginner Course",
                youtube("Microsoft Azure fundamentals full course for beginners"), "YouTube", 3);
        add("Azure", "Azure Hands-on Practice",
                google("Microsoft Azure beginner hands on labs"), "Google Search", 4);

        // Cloud Computing
        add("Cloud Computing", "NIST Cloud Computing Definition",
                "https://www.nist.gov/publications/nist-definition-cloud-computing", "Documentation", 1);
        add("Cloud Computing", "AWS Cloud Computing Fundamentals",
                "https://aws.amazon.com/what-is-cloud-computing/", "Tutorial", 2);
        add("Cloud Computing", "Cloud Computing Beginner Course",
                youtube("cloud computing full course for beginners"), "YouTube", 3);
        add("Cloud Computing", "Cloud Computing Practical Projects",
                google("cloud computing beginner practical projects"), "Google Search", 4);

        // Cloud Service Provisioning
        add("Cloud Service Provisioning", "AWS EC2 User Guide",
                "https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/concepts.html", "Documentation", 1);
        add("Cloud Service Provisioning", "Azure Resource Manager Documentation",
                "https://learn.microsoft.com/en-us/azure/azure-resource-manager/management/overview", "Tutorial", 2);
        add("Cloud Service Provisioning", "Cloud Resource Provisioning Tutorials",
                youtube("AWS Azure cloud resource provisioning tutorial"), "YouTube", 3);
        add("Cloud Service Provisioning", "Cloud Provisioning Practice",
                google("cloud infrastructure provisioning hands on exercises"), "Google Search", 4);

        // IaC (Infrastructure as Code)
        add("IaC (Infrastructure as Code)", "Terraform Official Documentation",
                "https://developer.hashicorp.com/terraform/docs", "Documentation", 1);
        add("IaC (Infrastructure as Code)", "Terraform Getting Started",
                "https://developer.hashicorp.com/terraform/tutorials", "Tutorial", 2);
        add("IaC (Infrastructure as Code)", "Infrastructure as Code Beginner Course",
                youtube("Terraform infrastructure as code full course"), "YouTube", 3);
        add("IaC (Infrastructure as Code)", "Terraform Hands-on Projects",
                google("Terraform infrastructure as code beginner projects"), "Google Search", 4);

        // IAM
        add("IAM", "AWS IAM Documentation",
                "https://docs.aws.amazon.com/IAM/latest/UserGuide/introduction.html", "Documentation", 1);
        add("IAM", "Microsoft Entra ID Documentation",
                "https://learn.microsoft.com/en-us/entra/identity/", "Tutorial", 2);
        add("IAM", "Identity and Access Management Tutorials",
                youtube("IAM identity access management tutorial for beginners"), "YouTube", 3);
        add("IAM", "IAM Practical Exercises",
                google("AWS IAM users roles policies hands on labs"), "Google Search", 4);

        // Kubernetes
        add("Kubernetes", "Kubernetes Official Documentation",
                "https://kubernetes.io/docs/home/", "Documentation", 1);
        add("Kubernetes", "Kubernetes Basics Interactive Tutorial",
                "https://kubernetes.io/docs/tutorials/kubernetes-basics/", "Tutorial", 2);
        add("Kubernetes", "Kubernetes Beginner Course",
                youtube("Kubernetes full course for beginners"), "YouTube", 3);
        add("Kubernetes", "Kubernetes Hands-on Labs",
                google("Kubernetes beginner hands on labs exercises"), "Google Search", 4);

        // Network Troubleshooting
        add("Network Troubleshooting", "Cisco Troubleshooting Resources",
                "https://www.cisco.com/c/en/us/support/index.html", "Documentation", 1);
        add("Network Troubleshooting", "Cloudflare Learning Center Networking",
                "https://www.cloudflare.com/learning/network-layer/what-is-a-computer-network/", "Tutorial", 2);
        add("Network Troubleshooting", "Network Troubleshooting Tutorials",
                youtube("network troubleshooting ping traceroute nslookup tutorial"), "YouTube", 3);
        add("Network Troubleshooting", "Network Troubleshooting Practice",
                google("network troubleshooting practical lab exercises"), "Google Search", 4);

        // Networking Fundamentals
        add("Networking Fundamentals", "Cisco Networking Basics",
                "https://www.cisco.com/site/us/en/learn/training-certifications/training/index.html", "Documentation", 1);
        add("Networking Fundamentals", "Cloudflare Networking Learning Center",
                "https://www.cloudflare.com/learning/network-layer/", "Tutorial", 2);
        add("Networking Fundamentals", "Computer Networking Beginner Course",
                youtube("computer networking full course OSI TCP IP for beginners"), "YouTube", 3);
        add("Networking Fundamentals", "Networking Practice Labs",
                google("networking fundamentals practical lab exercises"), "Google Search", 4);

        // Virtualization
        add("Virtualization", "VMware Virtualization Documentation",
                "https://docs.vmware.com/", "Documentation", 1);
        add("Virtualization", "Microsoft Hyper-V Documentation",
                "https://learn.microsoft.com/en-us/windows-server/virtualization/hyper-v/", "Tutorial", 2);
        add("Virtualization", "Virtualization Beginner Tutorials",
                youtube("virtualization virtual machines hypervisor tutorial for beginners"), "YouTube", 3);
        add("Virtualization", "Virtualization Practical Exercises",
                google("virtualization virtual machine hands on lab exercises"), "Google Search", 4);


        // ==========================================
        // CSIT DOMAIN — COMPUTER ENGINEERING
        // ==========================================

        // C Programming
        add("C Programming", "GNU C Language Documentation",
                "https://gcc.gnu.org/onlinedocs/", "Documentation", 1);
        add("C Programming", "Learn C Programming",
                "https://www.learn-c.org/", "Tutorial", 2);
        add("C Programming", "C Programming Beginner Course",
                youtube("C programming full course for beginners"), "YouTube", 3);
        add("C Programming", "C Programming Practice Problems",
                google("C programming beginner coding exercises"), "Google Search", 4);

        // Computer Organization and Architecture
        add("Computer Organization and Architecture", "NPTEL Computer Architecture",
                "https://nptel.ac.in/courses/106/102/106102157/", "Documentation", 1);
        add("Computer Organization and Architecture", "Computer Architecture Tutorials",
                google("computer organization architecture tutorial CPU memory instruction cycle"), "Tutorial", 2);
        add("Computer Organization and Architecture", "Computer Organization Full Course",
                youtube("computer organization and architecture full course"), "YouTube", 3);
        add("Computer Organization and Architecture", "Computer Architecture Practice",
                google("computer organization architecture practice questions"), "Google Search", 4);

        // Digital Electronics
        add("Digital Electronics", "All About Circuits Digital Textbook",
                "https://www.allaboutcircuits.com/textbook/digital/", "Documentation", 1);
        add("Digital Electronics", "Electronics Tutorials Digital Circuits",
                "https://www.electronics-tutorials.ws/category/digital", "Tutorial", 2);
        add("Digital Electronics", "Digital Electronics Beginner Course",
                youtube("digital electronics full course logic gates flip flops"), "YouTube", 3);
        add("Digital Electronics", "Digital Electronics Practice",
                google("digital electronics logic gates practice problems"), "Google Search", 4);

        // Microprocessors
        add("Microprocessors", "Intel Developer Documentation",
                "https://www.intel.com/content/www/us/en/developer/overview.html", "Documentation", 1);
        add("Microprocessors", "Microprocessor Fundamentals",
                google("microprocessor architecture 8085 8086 tutorial"), "Tutorial", 2);
        add("Microprocessors", "Microprocessors Beginner Course",
                youtube("microprocessor 8085 8086 full course for beginners"), "YouTube", 3);
        add("Microprocessors", "Microprocessor Practice Questions",
                google("microprocessor 8085 8086 practice problems"), "Google Search", 4);

        // Programming Fundamentals
        add("Programming Fundamentals", "CS50 Introduction to Programming",
                "https://cs50.harvard.edu/x/", "Documentation", 1);
        add("Programming Fundamentals", "MIT Introduction to Computer Science",
                "https://ocw.mit.edu/courses/6-100l-introduction-to-computer-science-and-programming-using-python-fall-2022/", "Tutorial", 2);
        add("Programming Fundamentals", "Programming Fundamentals Beginner Course",
                youtube("programming fundamentals full course for beginners"), "YouTube", 3);
        add("Programming Fundamentals", "Programming Logic Practice",
                google("programming fundamentals flowcharts algorithms practice problems"), "Google Search", 4);


        // ==========================================
        // CSIT DOMAIN — SECURITY AND OPERATING SYSTEMS
        // ==========================================

        // Cybersecurity Fundamentals
        add("Cybersecurity Fundamentals", "NIST Cybersecurity Framework",
                "https://www.nist.gov/cyberframework", "Documentation", 1);
        add("Cybersecurity Fundamentals", "Cisco Introduction to Cybersecurity",
                "https://skillsforall.com/course/introduction-to-cybersecurity", "Tutorial", 2);
        add("Cybersecurity Fundamentals", "Cybersecurity Beginner Course",
                youtube("cybersecurity full course for beginners"), "YouTube", 3);
        add("Cybersecurity Fundamentals", "Cybersecurity Practice Labs",
                google("cybersecurity beginner defensive security labs"), "Google Search", 4);

        // Windows Server
        add("Windows Server", "Microsoft Windows Server Documentation",
                "https://learn.microsoft.com/en-us/windows-server/", "Documentation", 1);
        add("Windows Server", "Windows Server Getting Started",
                "https://learn.microsoft.com/en-us/windows-server/get-started/", "Tutorial", 2);
        add("Windows Server", "Windows Server Administration Course",
                youtube("Windows Server administration full course for beginners"), "YouTube", 3);
        add("Windows Server", "Windows Server Administration Practice",
                google("Windows Server administration beginner lab exercises"), "Google Search", 4);


        // ==========================================
        // CSIT DOMAIN — IT SUPPORT
        // ==========================================

        // Hardware Troubleshooting
        add("Hardware Troubleshooting", "CompTIA Troubleshooting Resources",
                "https://www.comptia.org/certifications/a", "Documentation", 1);
        add("Hardware Troubleshooting", "Dell Computer Troubleshooting Guide",
                "https://www.dell.com/support/home/en-in", "Tutorial", 2);
        add("Hardware Troubleshooting", "Computer Hardware Troubleshooting Tutorials",
                youtube("computer hardware troubleshooting full course for beginners"), "YouTube", 3);
        add("Hardware Troubleshooting", "Hardware Troubleshooting Practice",
                google("computer hardware troubleshooting practical case studies"), "Google Search", 4);


        // ==========================================
        // CSIT DOMAIN — BACKEND DEVELOPMENT
        // ==========================================

        // FastAPI
        add("FastAPI", "FastAPI Official Documentation",
                "https://fastapi.tiangolo.com/", "Documentation", 1);
        add("FastAPI", "FastAPI Tutorial",
                "https://fastapi.tiangolo.com/tutorial/", "Tutorial", 2);
        add("FastAPI", "FastAPI Beginner Course",
                youtube("FastAPI full course for beginners Python"), "YouTube", 3);
        add("FastAPI", "FastAPI Practice Projects",
                google("FastAPI beginner REST API projects"), "Google Search", 4);



// ==========================================
// AUTOMATIC RESOURCE GENERATION FOR ALL SKILLS
// ==========================================

        List<Skill> allSkills = skillRepository.findAll();

        for (Skill skill : allSkills) {

            String skillName = skill.getName();

            String[] titles = {
                    skillName + " Official Documentation",
                    skillName + " Beginner Tutorial",
                    skillName + " YouTube Tutorials",
                    skillName + " Practical Learning Resources"
            };

            String[] urls = {
                    google(skillName + " official documentation"),
                    google(skillName + " tutorial for beginners"),
                    youtube(skillName + " tutorial"),
                    google(skillName + " practical examples and exercises")
            };

            String[] types = {
                    "Documentation",
                    "Tutorial",
                    "YouTube",
                    "Google Search"
            };

            int order = 1;

            for (int i = 0; i < titles.length; i++) {

                if (!resourceRepository.existsBySkillIdAndTitleIgnoreCase(
                        skill.getId(), titles[i])) {

                    long currentCount =
                            resourceRepository.countBySkillId(skill.getId());

                    if (currentCount >= 4) {
                        break;
                    }

                    add(skillName, titles[i], urls[i], types[i],
                            (int) currentCount + 1);
                }
            }
        }

        System.out.println("Learning resources seeding completed.");



    }

    private void add(
            String skillName,
            String title,
            String url,
            String resourceType,
            int order) {

        Skill skill = skillRepository.findByNameIgnoreCase(skillName)
                .orElse(null);

        if (skill == null) {
            System.out.println("Skill not found: " + skillName);
            return;
        }

        if (resourceRepository.existsBySkillIdAndTitleIgnoreCase(
                skill.getId(), title)) {
            return;
        }

        LearningResource resource = new LearningResource();
        resource.setSkill(skill);
        resource.setTitle(title);
        resource.setResourceUrl(url);
        resource.setResourceType(resourceType);
        resource.setLearningOrder(order);

        resourceRepository.save(resource);
    }

    private String youtube(String query) {
        return "https://www.youtube.com/results?search_query="
                + query.trim().replace(" ", "+");
    }

    private String google(String query) {
        return "https://www.google.com/search?q="
                + query.trim().replace(" ", "+");
    }
}
