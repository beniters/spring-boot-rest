package com.ninjaone.backendinterviewproject;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("com.ninjaone.backendinterviewproject.repository")
@EnableTransactionManagement
public class DatabaseConfiguration {
}
