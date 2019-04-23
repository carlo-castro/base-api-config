package com.generic.core.base.util;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by linhnm on 19/06/2017.
 */
public class RSAUtil {

    private static final String ALGORITHM = "RSA";

    private static String getKey( String filename ) throws IOException {
        // Read key from file
        String strKeyPEM = "";
        BufferedReader br = new BufferedReader( new FileReader( filename ) );
        String line;
        while ( ( line = br.readLine( ) ) != null ) strKeyPEM += line + "\n";
        br.close( );
        return strKeyPEM;
    }

    /**
     * Gets public key.
     *
     * @param filename the filename
     * @return the public key
     * @throws IOException              the io exception
     * @throws GeneralSecurityException the general security exception
     */
    public static RSAPublicKey getPublicKey( String filename ) throws IOException, GeneralSecurityException {
        String publicKeyPEM = getKey( filename );
        return getPublicKeyFromString( publicKeyPEM );
    }

    /**
     * Gets public key from string.
     *
     * @param key the key
     * @return the public key from string
     * @throws IOException              the io exception
     * @throws GeneralSecurityException the general security exception
     */
    public static RSAPublicKey getPublicKeyFromString( String key ) throws IOException, GeneralSecurityException {
        String publicKeyPEM = key;
        publicKeyPEM = publicKeyPEM
                .replace( "-----BEGIN PUBLIC KEY-----\n", "" )
                .replace( "-----BEGIN PUBLIC KEY-----", "" )
                .replace( "-----END PUBLIC KEY-----", "" );
        byte[] encoded = Base64.decodeBase64( publicKeyPEM );
        KeyFactory kf = KeyFactory.getInstance( "RSA" );
        RSAPublicKey pubKey = ( RSAPublicKey ) kf.generatePublic( new X509EncodedKeySpec( encoded ) );
        return pubKey;
    }

    /**
     * Encrypt string.
     *
     * @param rawText   the raw text
     * @param publicKey the public key
     * @return the string
     * @throws IOException              the io exception
     * @throws GeneralSecurityException the general security exception
     */
    public static String encrypt( String rawText, PublicKey publicKey ) throws IOException, GeneralSecurityException {
        Cipher cipher = Cipher.getInstance( "RSA" );
        cipher.init( Cipher.ENCRYPT_MODE, publicKey );
        return Base64.encodeBase64String( cipher.doFinal( rawText.getBytes( ) ) );
    }

    /**
     * Decrypt string.
     *
     * @param cipherText the cipher text
     * @param privateKey the private key
     * @return the string
     * @throws IOException              the io exception
     * @throws GeneralSecurityException the general security exception
     */
    public static String decrypt( String cipherText,
                                  PrivateKey privateKey ) throws IOException, GeneralSecurityException {
        Cipher cipher = Cipher.getInstance( "RSA" );
        cipher.init( Cipher.DECRYPT_MODE, privateKey );
        return new String( cipher.doFinal( Base64.decodeBase64( cipherText ) ), "UTF-8" );
    }

}
