package com.generic.core.base.config;

import com.generic.core.base.exceptions.RestErrorException;
import com.generic.core.logging.interceptors.ClientInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * The type Rest template config.
 */
@Configuration
public class RestTemplateConfig {

    /**
     * Rest template rest template.
     *
     * @return the rest template
     */
    @Bean
    public RestTemplate restTemplate( ) {
        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(
                new SimpleClientHttpRequestFactory( ) );

        RestTemplate restTemplate = new RestTemplate( factory );
        restTemplate.setErrorHandler( new RestErrorException( ) );
        restTemplate.setInterceptors( Collections.singletonList( new ClientInterceptor( ) ) );
        return restTemplate;
    }

}
