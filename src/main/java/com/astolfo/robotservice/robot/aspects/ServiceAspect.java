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
public class ServiceAspect {

    @Pointcut("execution(* com.astolfo.robotservice.robot.domain.*.service.*.*(..))")
    public void serviceMethods() {}

    @Around("serviceMethods()")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("----------------------------------------begin robot service");
        log.info("robot service: ");

        log.info("Method Signature: {}", joinPoint.getSignature());
        log.info("Arguments: {}", Arrays.toString(joinPoint.getArgs()));

        Object result = joinPoint.proceed();

        log.info("robot service return: ");
        log.info("Return value: {}", result);

        log.info("----------------------------------------end robot service");
        log.info("\n");

        return result;
    }

}
