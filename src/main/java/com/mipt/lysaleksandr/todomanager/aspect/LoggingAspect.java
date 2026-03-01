package com.mipt.lysaleksandr.todomanager.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* com.mipt.lysaleksandr.todomanager.service..*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        System.out.println("Call: " + methodName);
        System.out.println("Args: " + Arrays.toString(args));

        Object result = joinPoint.proceed();

        System.out.println("Result: " + result);

        return result;
    }
}