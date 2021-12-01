package com.github.music.synchronization.app.config;

import com.github.music.synchronization.app.config.swagger.SwaggerConfig;
import com.github.music.synchronization.rest.RestConfig;
import com.github.music.synchronization.service.config.ServiceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SwaggerConfig.class, RestConfig.class, ServiceConfig.class})
public class ApplicationConfiguration {
}
