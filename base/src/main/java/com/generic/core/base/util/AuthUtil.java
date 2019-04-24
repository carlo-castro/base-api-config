package com.generic.core.base.util;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PublicKey;

/**
 * The type Auth util.
 */
@Log4j2
public class AuthUtil {

    /**
     * Encrypt password with rsa string.
     *
     * @param password the password
     * @param filePath the file path
     * @return the string
     */
    public static String encryptPasswordWithRSA( String password, String filePath ) {
        String result = "";
        PublicKey key = null;
        try {
            if ( StringUtils.isNotEmpty( filePath ) ) {
                key = RSAUtil.getPublicKey( filePath );
                result = RSAUtil.encrypt( password, key );
            }
        } catch ( IOException e ) {
            log.debug( "Error with encrypting with the password" );
            log.error( "Error occurred on the file path or on the file itself: \n {} " + e );
        } catch ( GeneralSecurityException e ) {
            log.debug( "Error with encrypting with the password" );
            log.error( "Error occurred while encrypting password: \n {}", e );
        }
        return result;
    }

}