package com.generic.core.base.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.HashMap;
import java.util.Map;

import static com.generic.core.base.constants.ErrorCode.getErrorMessage;
import static com.generic.utils.MapperUtil.objectToJson;
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
        setOk( ).addData( data );
    }

    private EndpointResult setOk( ) {
        responseCode = "0";
        setResponseMessage( responseCode );
        return this;
    }

    /**
     * Add error.
     *
     * @param responseCode the response code
     * @return the endpoint result
     */
    public EndpointResult addError( String responseCode ) {
        this.responseCode = responseCode;
        this.setResponseMessage( responseCode );
        return this;
    }

    /**
     * Add error.
     *
     * @param responseCode the response code
     * @param params       the params
     * @return the endpoint result
     */
    public EndpointResult addError( String responseCode, String... params ) {
        this.responseCode = responseCode;
        setResponseMessage( responseCode );
        this.responseMessage = String.format( this.responseMessage, ( Object[] ) params );
        return this;
    }

    /**
     * Sets response message.
     *
     * @param responseCode the response code
     */
    private void setResponseMessage( String responseCode ) {
        this.responseMessage = getErrorMessage( responseCode );
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
     * @return the endpoint result
     */
    @JsonIgnore
    public EndpointResult addData( String key, Object value ) {
        if ( value != null ) {
            Map< String, Object > map = new HashMap< String, Object >( );
            map.put( key, value );
            data = ( T ) map;
        }
        return this;
    }

    /**
     * Add data.
     *
     * @param data the data
     * @return the endpoint result
     */
    @JsonIgnore
    public EndpointResult addData( T data ) {
        this.data = data;
        return this;
    }

    /**
     * To json string string.
     *
     * @param isDataOnly the is data only
     * @return the string
     */
    @JsonIgnore
    public String toJsonString( boolean isDataOnly ) {
        return objectToJson( isDataOnly ? data : this );
    }

}
