
package com.skillgap.skillgap.config;

import com.skillgap.skillgap.security.JwtAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter) {

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())

                .exceptionHandling(exception -> exception

                        .authenticationEntryPoint(
                                (request, response, authException) -> {

                                    response.setStatus(
                                            HttpStatus.UNAUTHORIZED.value()
                                    );

                                    response.setContentType(
                                            "application/json"
                                    );

                                    response.getWriter().write(
                                            "{\"message\":\"Unauthorized\"}"
                                    );
                                }
                        )

                        .accessDeniedHandler(
                                (request, response, accessDeniedException) -> {

                                    response.setStatus(
                                            HttpStatus.FORBIDDEN.value()
                                    );

                                    response.setContentType(
                                            "application/json"
                                    );

                                    response.getWriter().write(
                                            "{\"message\":\"Forbidden\"}"
                                    );
                                }
                        )
                )

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // Public login
                        .requestMatchers(
                                "/api/users/login"
                        ).permitAll()

                        // Public registration
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/users"
                        ).permitAll()

                        // Authenticated users can view job roles
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/job-roles",
                                "/api/job-roles/domain/*"
                        ).authenticated()

                        // ADMIN: Create job roles and assign skills
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/job-roles",
                                "/api/job-roles/*/skills/*"
                        ).hasRole("ADMIN")

                        // ADMIN: Update job roles
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/job-roles/*"
                        ).hasRole("ADMIN")

                        // ADMIN: Delete job roles
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/job-roles/*"
                        ).hasRole("ADMIN")

                        // Only ADMIN can view detailed job role management data
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/job-roles/admin"
                        ).hasRole("ADMIN")

                        // Authenticated users can view domains
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/engineering-domains",
                                "/api/engineering-domains/**"
                        ).authenticated()

                        // ADMIN: Create domains
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/engineering-domains"
                        ).hasRole("ADMIN")

                        // ADMIN: Update domains
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/engineering-domains/**"
                        ).hasRole("ADMIN")

                        // ADMIN: Delete domains
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/engineering-domains/**"
                        ).hasRole("ADMIN")

                                // Logged-in users can view skills
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/skills",
                                        "/api/skills/**"
                                ).authenticated()

                                // Only ADMIN can add skills
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/skills"
                                ).hasRole("ADMIN")

                                // Only ADMIN can edit skills
                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/api/skills/**"
                                ).hasRole("ADMIN")

                        // Only ADMIN can delete skills
                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/api/skills/**"
                                ).hasRole("ADMIN")

                                // ========================================
// JOB ROLE SKILL & PROFICIENCY MANAGEMENT
// ========================================

// Logged-in users can view required skills
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/job-role-skills/*"
                                ).authenticated()

// Only ADMIN can add or update required skills
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/job-role-skills/*/*/*"
                                ).hasRole("ADMIN")

// Only ADMIN can remove required skills
                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/api/job-role-skills/*/*"
                                ).hasRole("ADMIN")


                                // ========================================
                                // LEARNING RESOURCE SECURITY
                                // ========================================

                                // Students and admins can view resources
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/resources/**"
                                ).authenticated()

                                // Only admins can create resources
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/resources/**"
                                ).hasRole("ADMIN")

                                // Only admins can update resources
                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/api/resources/**"
                                ).hasRole("ADMIN")

                                // Only admins can delete resources
                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/api/resources/**"
                                ).hasRole("ADMIN")



                                // ========================================
                                // AI JOB ROLE GENERATION AND APPROVAL
                                // ========================================

                                // Only ADMIN can access all admin APIs
                                .requestMatchers("/api/admin/**").hasRole("ADMIN")

// All remaining endpoints require login
                                .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
