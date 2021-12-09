package com.github.music.synchronization.service.config;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "com.github.music.synchronization.service.db.entity")
@EnableJpaRepositories(basePackages = "com.github.music.synchronization.service.db.repository")
@ComponentScan(basePackages = "com.github.music.synchronization.service")
public class ServiceConfig {

}
