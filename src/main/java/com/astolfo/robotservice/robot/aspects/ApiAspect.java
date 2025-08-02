package com.astolfo.robotservice.robot.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class ApiAspect {

    @Pointcut("execution(* com.astolfo.robotservice.robot.domain.*.api.*.*(..))")
    public void apiMethods() {}

    @Around("apiMethods()")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("----------------------------------------begin robot api");
        log.info("robot api: ");

        log.info("Method Signature: {}", joinPoint.getSignature());
        log.info("Arguments: {}", Arrays.toString(joinPoint.getArgs()));

        Object result = joinPoint.proceed();

        log.info("robot api return: ");
        log.info("Return value: {}", result);

        log.info("----------------------------------------end robot api");

        return result;
    }
}
