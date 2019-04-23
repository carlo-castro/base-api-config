package com.generic.core.logging.aspect;

import com.generic.core.logging.client.LoggingApiClient;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.generic.core.base.util.MapperUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Logging aspect.
 */
public class LoggingAspect {

    /**
     * Log when execute object.
     *
     * @param joinPoint the join point
     * @return the object
     * @throws Throwable the throwable
     */
    public Object logWhenExecute( ProceedingJoinPoint joinPoint ) throws Throwable {

        HttpServletRequest request = ( ( ServletRequestAttributes ) RequestContextHolder
                .currentRequestAttributes( ) ).getRequest( );

        Object responseObj = joinPoint.proceed( );
        String requestMsg = MapperUtil.objectToJson( !ArrayUtils.isEmpty( joinPoint.getArgs( ) ) ?
                joinPoint.getArgs( )[ 0 ] : "{}" );
        String uri = request.getRequestURI( );
        String method = request.getMethod( );
        log( requestMsg, method, uri, MapperUtil.objectToJson( responseObj ) );

        return responseObj;

    }

    /**
     * Log.
     *
     * @param requestMsg  the request msg
     * @param method      the method
     * @param uri         the uri
     * @param responseMsg the response msg
     */
    @Async
    public void log( String requestMsg, String method, String uri, String responseMsg ) {

        LoggingApiClient.sendLog( requestMsg, method, uri, "200", responseMsg );
    }

}
