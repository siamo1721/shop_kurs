package com.example.kurs.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class ServiceLoggingAspect {

    @Pointcut("execution(* com.example.kurs.service..*(..))")
    public void serviceMethods() {}

    @Before("serviceMethods()")
    public void logBeforeMethod(JoinPoint joinPoint) {
        log.info("Executing service method: " + joinPoint.getSignature().getName());
        log.info("Arguments: " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        log.info("Method executed: " + joinPoint.getSignature().getName());
        log.info("Return value: " + result);
    }
}
