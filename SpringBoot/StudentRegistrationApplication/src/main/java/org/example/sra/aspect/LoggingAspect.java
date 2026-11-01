package org.example.sra.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log =
            LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(public * org.example.studentregistration.service..*(..))")
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName = joinPoint.getSignature().toShortString();

        long startTime = System.currentTimeMillis();

        log.info("STARTED: {}", methodName);

        try {
            Object result = joinPoint.proceed();

            long executionTime = System.currentTimeMillis() - startTime;

            log.info("COMPLETED: {} in {} ms", methodName, executionTime);

            return result;

        } catch (Throwable exception) {

            long executionTime = System.currentTimeMillis() - startTime;

            log.error("FAILED: {} after {} ms", methodName, executionTime, exception);

            throw exception;
        }
    }
}
