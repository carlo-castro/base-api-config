package com.generic.core.base.config;

import com.generic.core.base.constants.ErrorCode;
import com.generic.core.logging.client.LoggingApiClient;
import com.generic.core.logging.controller.LoggingController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import com.generic.core.base.client.ApiClient;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * The type Base initializer config.
 */
@EnableAsync
@Configuration
@EnableConfigurationProperties
@Import( { ErrorCode.class, RestTemplateConfig.class, SwaggerConfig.class, MonitoringConfig.class } )
public class BaseInitializerConfig {
    @Resource
    private RestTemplate restTemplateConf;

    @Value( "${constants.logging.uri}" )
    private String loggingUri;

    /**
     * Logging controller logging controller.
     *
     * @return the logging controller
     */
    @Bean
    public LoggingController loggingController( ) {
        return new LoggingController( );
    }

    /**
     * Init.
     */
    @PostConstruct
    public void init( ) {
        LoggingApiClient.init( new RestTemplate( ), loggingUri );
        ApiClient.setRestTemplate( restTemplateConf );
    }

}
