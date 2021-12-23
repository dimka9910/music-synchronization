package com.github.music.synchronization.service.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "com.github.music.synchronization.service.db.entity")
@EnableJpaRepositories(basePackages = "com.github.music.synchronization.service.db.repository")
@ComponentScan(basePackages = "com.github.music.synchronization.service")
@Slf4j
public class ServiceConfig {

    @Bean(name = "appRestClient")
    public RestTemplate getRestClient() {
        RestTemplate restClient = new RestTemplate(
                new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));

        restClient.setInterceptors(Collections.singletonList((request, body, execution) -> {
            log.debug("Intercepting...");
            return execution.execute(request, body);
        }));

        return restClient;
    }

}
