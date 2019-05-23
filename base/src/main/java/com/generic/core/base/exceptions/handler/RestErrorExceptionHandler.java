package com.generic.core.base.exceptions.handler;

import com.generic.core.base.exceptions.RestExceptionError;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

/**
 * The type Rest error exception.
 */
@Log4j2
@Component
public class RestErrorExceptionHandler extends DefaultResponseErrorHandler {

//    @Override
//    public boolean hasError( ClientHttpResponse response ) throws IOException {
//        return ( response.getStatusCode( ).series( ) == HttpStatus.Series.CLIENT_ERROR ||
//                response.getStatusCode( ).series( ) == HttpStatus.Series.SERVER_ERROR );
//    }

    @Override
    public void handleError( ClientHttpResponse response ) throws IOException {
        if ( response.getStatusCode( ).series( ) == HttpStatus.Series.CLIENT_ERROR )
            throw new RestExceptionError( "An error occurred on Exchange" );
        else if ( response.getStatusCode( ).series( ) == HttpStatus.Series.CLIENT_ERROR ) {
            // handle CLIENT_ERROR
            if ( response.getStatusCode( ) == HttpStatus.NOT_FOUND )
                throw new RestExceptionError( "Site cannot be found" );

        }

    }

//    @Override
//    public void handleError( URI url, HttpMethod method, ClientHttpResponse response ) throws IOException {
//        if ( response.getStatusCode( ).series( ) == HttpStatus.Series.CLIENT_ERROR )
//            throw new RestExceptionError( "An error occurred on Exchange" );
//        else if ( response.getStatusCode( ).series( ) == HttpStatus.Series.CLIENT_ERROR ) {
//            // handle CLIENT_ERROR
//            if ( response.getStatusCode( ) == HttpStatus.NOT_FOUND )
//                throw new RestExceptionError( "Site cannot be found" );
//
//        }
//    }

}
