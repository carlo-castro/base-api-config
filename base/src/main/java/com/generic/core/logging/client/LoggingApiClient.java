package com.generic.core.logging.client;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Logging api client.
 */
@Log4j2
public class LoggingApiClient {

    private static RestTemplate restTemplate;
    private static String loggerUri;
    private static String serviceUri;

    /**
     * Init.
     *
     * @param restTemplate the rest template
     * @param loggerUri    the logger uri
     */
    public static void init( RestTemplate restTemplate, String loggerUri ) {
        LoggingApiClient.restTemplate = restTemplate;
        LoggingApiClient.loggerUri = loggerUri;
    }

    /**
     * Sets service uri.
     *
     * @param serviceUri the service uri
     */
    public static void setServiceUri( String serviceUri ) {
        LoggingApiClient.serviceUri = serviceUri;
    }

    /**
     * Send log.
     *
     * @param reqMsg     the req msg
     * @param method     the method
     * @param uri        the uri
     * @param connStatus the conn status
     * @param respMsg    the resp msg
     */
    public static void sendLog( String reqMsg, String method, String uri, String connStatus, String respMsg ) {
        Map< String, String > params = new HashMap<>( );

        HttpHeaders headers = new HttpHeaders( );
        headers.setContentType( MediaType.APPLICATION_JSON );

        params.put( "requestMessage", reqMsg );
        params.put( "responseMessage", respMsg );
        params.put( "uri", uri );
        params.put( "method", method );
        params.put( "connectionStatus", connStatus );
        params.put( "serviceUri", serviceUri );

        HttpEntity< MultiValueMap< String, String > > request = new HttpEntity( params, headers );

        try {
            restTemplate.postForEntity( loggerUri, request, ResponseEntity.class );
        } catch ( HttpServerErrorException e ) {
            log.debug( "Error in sending Logs" );
            log.error( "e: {}", e );
        }
    }

}
