package com.generic.core.logging.interceptors;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;
import com.generic.core.base.util.MapperUtil;
import com.generic.core.logging.client.LoggingApiClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.util.StreamUtils.copyToByteArray;

/**
 * The type Client interceptor.
 */
@Log4j2
public class ClientInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept( HttpRequest request, byte[] body,
                                         ClientHttpRequestExecution execution ) throws IOException {
        ClientHttpResponse response = execution.execute( request, body );

        String responseBody;
        try {
            responseBody = new String( copyToByteArray( response.getBody( ) ) );
        } catch ( IOException ex ) {
            log.debug( "Error in getting the response body" );
            log.error( "e: ", ex );
            responseBody = ex.getLocalizedMessage( );
        }

        String requestMsg;
        String decryptBody = new String( body, UTF_8 );

        if ( request.getMethodValue( ).equals( "POST" ) ) {
            Map< String, Object > decryptMap = MapperUtil.jsonToObject( decryptBody, Map.class );
            requestMsg = MapperUtil.objectToJson( decryptMap );
        } else {
            Map< String, Object > result = new HashMap<>( );
            String[] splitDecryptBody;

            if ( request.getURI( ).getQuery( ) != null ) {
                splitDecryptBody = request.getURI( ).getQuery( ).split( "&" );

                for ( String val : splitDecryptBody ) {
                    if ( val.contains( "=" ) )
                        result.put( val.substring( 0, val.indexOf( "=" ) ), val.substring( val.indexOf( "=" ) + 1 ) );
                }
            }
            requestMsg = MapperUtil.objectToJson( !result.isEmpty( ) ? result : decryptBody );
        }

        log( !StringUtils.isEmpty( requestMsg ) ? requestMsg : "{}",
                request.getMethodValue( ), request.getURI( ).getPath( ),
                String.valueOf( response.getRawStatusCode( ) ), responseBody );

        return response;
    }

    /**
     * Log.
     *
     * @param requestMsg  the request msg
     * @param method      the method
     * @param uri         the uri
     * @param connStatus  the conn status
     * @param responseMsg the response msg
     */
    @Async
    public void log( String requestMsg, String method, String uri, String connStatus, String responseMsg ) {
        LoggingApiClient.sendLog( requestMsg, method, uri, connStatus, responseMsg );
    }

}