package com.generic.core.base.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.List;

/**
 * The type Mapper util.
 */
@Log4j2
public class MapperUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper( );

    /**
     * Json to object t.
     *
     * @param <T>      the type parameter
     * @param json     the json
     * @param classOfT the class of t
     * @return the t
     */
    public static < T > T jsonToObject( String json, Class< T > classOfT ) {
        try {
            return objectMapper.readValue( json, classOfT );
        } catch ( JsonGenerationException var3 ) {
            log.debug( "Error in Parsing the JSON" );
            log.error( "e: ", var3 );
        } catch ( JsonMappingException var4 ) {
            log.debug( "Error in Parsing the JSON" );
            log.error( "e: ", var4 );
        } catch ( IOException var5 ) {
            log.debug( "Error in Parsing the JSON" );
            log.error( "e: ", var5 );
        } catch ( Exception var6 ) {
            log.debug( "Error in Parsing the JSON" );
            log.error( "e: ", var6 );
        }
        return null;
    }

    /**
     * Object to json string.
     *
     * @param obj the obj
     * @return the string
     */
    public static String objectToJson( Object obj ) {
        try {
            return objectMapper.writeValueAsString( obj );
        } catch ( JsonProcessingException var2 ) {
            log.debug( var2.getMessage( ), var2 );
            log.error( "e: ", var2 );
        } catch ( Exception var3 ) {
            log.debug( var3.getMessage( ), var3 );
            log.error( "e: ", var3 );
        }

        return null;
    }

    /**
     * Is json boolean.
     *
     * @param json the json
     * @return the boolean
     */
    public static boolean isJson( String json ) {
        try {
            objectMapper.readTree( json );
            return true;
        } catch ( IOException e ) {
            log.debug( "String is not a json" );
            log.error( "e: ", e );
            return false;
        } catch ( Exception e ) {
            log.debug( "String is not a json" );
            log.error( "e: ", e );
            return false;
        }
    }

    /**
     * Convert list list.
     *
     * @param data  the data
     * @param clazz the clazz
     * @return the list
     */
    public static List convertList( Object data, Class clazz ) {
        try {
            CollectionLikeType collectionLikeType = objectMapper.getTypeFactory( )
                    .constructCollectionLikeType( List.class, clazz );
            return objectMapper.readValue( objectMapper.writeValueAsString( data ), collectionLikeType );
        } catch ( IOException e ) {
            log.debug( "Cannot convert List" );
            log.error( "e: ", e );
        }
        return null;
    }

    /**
     * Object to class t.
     *
     * @param <T>    the type parameter
     * @param object the object
     * @param clazz  the clazz
     * @return the t
     */
    public static < T > T objectToClass( Object object, Class< T > clazz ) {
        return objectMapper.convertValue( object, clazz );
    }

}
