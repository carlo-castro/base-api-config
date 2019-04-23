package com.generic.core.base.constants;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Error code.
 */
@Data
@Component
@ConfigurationProperties( prefix = "constants.code" )
public class ErrorCode {

    private Map< String, String > error = new HashMap<>( );
    private static Map< String, String > errorStatic = new HashMap<>( );

    /**
     * Init.
     */
    @PostConstruct
    public void init( ) {
        errorStatic.putAll( error );
    }

    /**
     * Gets error message.
     *
     * @param errorCode the error code
     * @return the error message
     */
    public static String getErrorMessage( String errorCode ) {
        return errorStatic.get( errorCode );
    }

}