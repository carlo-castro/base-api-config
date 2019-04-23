package com.generic.core.base.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

/**
 * The type Rest error exception.
 */
@Log4j2
@ControllerAdvice
@Order( HIGHEST_PRECEDENCE )
public class RestErrorException implements ResponseErrorHandler {

    @Override
    public void handleError( URI url, HttpMethod method, ClientHttpResponse response ) {
        try {
            log.trace( "Handle Error URL1: {}", response.getStatusText( ) );
        } catch ( IOException e ) {
            log.debug( "An Error occured on Exchanged" );
            log.error( e.getMessage( ), e );
        }
    }

    @Override
    public boolean hasError( ClientHttpResponse response ) {
        try {
            log.trace( "Has Error URL: {}", response.getStatusText( ) );
        } catch ( IOException e ) {
            log.debug( "An Error occured on Exchanged" );
            log.error( e.getMessage( ), e );
        }
        return false;
    }

    @Override
    public void handleError( ClientHttpResponse response ) {
        try {
            log.trace( "Handle Error URL2: {}", response.getStatusText( ) );
        } catch ( IOException e ) {
            log.debug( "An Error occured on Exchanged" );
            log.error( e.getMessage( ), e );
        }
    }

}
