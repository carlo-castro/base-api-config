package com.generic.core.base.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import com.generic.core.base.constants.ErrorCode;
import com.generic.core.base.util.MapperUtil;

import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

/**
 * The type Endpoint result.
 *
 * @param <T> the type parameter
 */
@Data
public class EndpointResult< T > {

    private String responseMessage;
    private String responseCode;
    private T data;

    /**
     * Instantiates a new Endpoint result.
     */
    public EndpointResult( ) { setOk( ); }

    /**
     * Instantiates a new Endpoint result.
     *
     * @param data the data
     */
    public EndpointResult( T data ) {
        setOk( );
        addData( data );
    }

    private void setOk( ) {
        responseCode = "0";
        setResponseMessage( responseCode );
    }

    /**
     * Add error.
     *
     * @param responseCode the response code
     */
    public void addError( String responseCode, String... error) {
        this.responseCode = responseCode;
        setResponseMessage( responseCode );
        String.format(this.responseMessage,error);
    }

    /**
     * Sets response message.
     *
     * @param responseCode the response code
     */
    private void setResponseMessage( String responseCode ) {
        this.responseMessage = ErrorCode.getErrorMessage( responseCode );
    }

    /**
     * Is data not null boolean.
     *
     * @return the boolean
     */
    @JsonIgnore
    public boolean isDataNotNull( ) { return data != null; }

    /**
     * Gets data.
     *
     * @return the data
     */
    public T getData( ) { return defaultIfNull( data, null ); }

    /**
     * Has error boolean.
     *
     * @return the boolean
     */
    @JsonIgnore
    public boolean hasError( ) {
        return !responseCode.equals( "0" );
    }

    /**
     * Add data.
     *
     * @param key   the key
     * @param value the value
     */
    @JsonIgnore
    public void addData( String key, Object value ) {
        if ( value != null ) {
            Map< String, Object > map = new HashMap< String, Object >( );
            map.put( key, value );
            data = ( T ) map;
        }
    }

    /**
     * Add data.
     *
     * @param data the data
     */
    @JsonIgnore
    public void addData( T data ) {
        this.data = data;
    }

    /**
     * To json string string.
     *
     * @param isDataOnly the is data only
     * @return the string
     */
    @JsonIgnore
    public String toJsonString( boolean isDataOnly ) {
        return MapperUtil.objectToJson( isDataOnly ? data : this );
    }

}
