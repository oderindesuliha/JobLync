package org.peejay.joblync.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/register", "/api/users/login", "/api/job-postings/**", "/api/auth/**").permitAll()
                        .requestMatchers(
                                "/api/users/**",
                                "/api/admin/**",
                                "/api/employees/**",
                                "/api/internal-job-postings/**",
                                "/api/performance-reviews/**",
                                "/api/career-development-plans/**",
                                "/api/succession-plans/**",
                                "/api/skills/**",
                                "/api/user-skills/**",
                                "/api/learning-modules/**",
                                "/api/user-learning-progress/**"
                        ).hasAnyRole("ADMIN", "HR_MANAGER", "EMPLOYEE")
                        .requestMatchers(
                                "/api/internal-job-postings",
                                "/api/internal-job-postings/**",
                                "/api/performance-reviews",
                                "/api/performance-reviews/**",
                                "/api/career-development-plans",
                                "/api/career-development-plans/**",
                                "/api/succession-plans",
                                "/api/succession-plans/**",
                                "/api/skills",
                                "/api/skills/**",
                                "/api/learning-modules",
                                "/api/learning-modules/**",
                                "/api/employees",
                                "/api/employees/**"
                        ).hasAnyRole("ADMIN", "HR_MANAGER")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}