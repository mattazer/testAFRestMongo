package com.test.testmarc.logtime;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Aspect permettant de logguer le début, la fin et le temps d'execution d'une méthode (voir annotation @LogExecutionTime)
 */
@Aspect
@Component
public class LogTimeAspect {

    @Around("@annotation(com.test.testmarc.logtime.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        final String method_name=joinPoint.getSignature().getName();
        final long start = System.currentTimeMillis();
        System.out.println(method_name +" method start");

        final Object proceed = joinPoint.proceed();

        System.out.println(method_name +" method end");
        final long executionTime = System.currentTimeMillis() - start;
        System.out.println(method_name + " executed in " + executionTime + "ms");

        return proceed;
    }

}