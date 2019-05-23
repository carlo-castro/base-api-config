package com.generic.logger.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Log request.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogRequest {

    private String requestMessage;
    private String responseMessage;
    private String uri;
    private String method;
    private Integer connectionStatus;
    private String serviceUri;

    /**
     * Instantiates a new Log request.1
     *
     * @param requestMessage   the request message
     * @param responseMessage  the response message
     * @param uri              the uri
     * @param method           the method
     * @param connectionStatus the connection status
     */
    public LogRequest( String requestMessage, String responseMessage, String uri,
                       String method, String connectionStatus ) {
        this.requestMessage = requestMessage;
        this.responseMessage = responseMessage;
        this.uri = uri;
        this.method = method;
        this.connectionStatus = Integer.parseInt( connectionStatus );
    }

}
