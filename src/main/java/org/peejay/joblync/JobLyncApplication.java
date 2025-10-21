package org.peejay.joblync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.peejay.joblync.data.repository")
@EnableConfigurationProperties
public class JobLyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobLyncApplication.class, args);
    }

}