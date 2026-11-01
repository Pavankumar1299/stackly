package org.example.lbm.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log =
            LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* org.example.lbm.service..*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint)
            throws Throwable {

        String methodName =
                joinPoint.getSignature().toShortString();

        log.info("Started: {}", methodName);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try {
            Object result = joinPoint.proceed();

            stopWatch.stop();

            log.info("Completed: {} in {} ms",
                    methodName, stopWatch.getTotalTimeMillis());

            return result;

        } catch (Throwable exception) {
            if (stopWatch.isRunning()) {
                stopWatch.stop();
            }

            log.error("Failed: {} after {} ms",
                    methodName, stopWatch.getTotalTimeMillis(), exception);

            throw exception;
        }
    }
}