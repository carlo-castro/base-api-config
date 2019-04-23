package com.generic.core.logging.controller;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import com.generic.core.logging.aspect.LoggingAspect;

/**
 * The type Logging controller.
 */
@Aspect
public class LoggingController extends LoggingAspect {

    private final static String POINTLOCAL = "execution(* com.*.controllers.*.*(..))";

    @Override
    @Around( POINTLOCAL )
    public Object logWhenExecute( ProceedingJoinPoint joinPoint ) throws Throwable {
        return super.logWhenExecute( joinPoint );
    }

}
