package com.astolfo.robotservice.robot.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Aspect
@Component
public class ListenerAspect {

    @Pointcut("execution(* com.astolfo.robotservice.robot.domain.*.listener.*.*(..))")
    public void listenerMethods() {}

    @Around("listenerMethods()")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("----------------------------------------begin");
        log.info("robot listener: ");

        log.info("Method Signature: {}", joinPoint.getSignature());
        log.info("Arguments: {}", List.of(joinPoint.getArgs()));

        Object result = joinPoint.proceed();

        log.info("Return value: {}", result);

        log.info("-----------------------------------------end");

        return result;
    }

}
