package com.astolfo.robotservice.infrastructure.common.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Aspect
@Component
public class ListenerAspect {

    @Pointcut("execution(* com.astolfo.robotservice.domain.listener.*.*(..))")
    public void listenerMethods() {}

    @Around("listenerMethods()")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("<<<<<<<<<< listener");
        log.info("listener: ");

        log.info("Method Signature: {}", joinPoint.getSignature());
        log.info("Arguments: {}", Arrays.toString(joinPoint.getArgs()));

        Object returnResult = joinPoint.proceed();

        if (!(returnResult instanceof CompletableFuture<?> future)) {
            return returnResult;
        }

        return future.whenComplete((result, exception) -> {
            log.info("listener return: ");

            if (Objects.nonNull(exception)) {
                log.error("An error occurred during method execution: ", exception);
            } else {
                log.info("Return value: {}", result);
            }

            log.info("listener >>>>>>>>>>");
        });
    }
}
