package com.astolfo.robotservice.infrastructure.common.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Aspect
@Component
public class ServiceAspect {

    @Pointcut("execution(* com.astolfo.robotservice.domain.service.*.*(..))")
    public void serviceMethods() {}

    @Around("serviceMethods()")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("<<<<<<<<<< service");
        log.info("robot service: ");

        log.info("Method Signature: {}", joinPoint.getSignature());
        log.info("Arguments: {}", Arrays.toString(joinPoint.getArgs()));

        Object returnResult = joinPoint.proceed();

        if (!(returnResult instanceof Mono<?> future)) {

            log.info("service return: ");
            log.info("Return value: {}", returnResult);
            log.info("service >>>>>>>>>>");

            return returnResult;
        }

        return future
                .doOnSuccess(value -> {
                    log.info("service return: ");
                    log.info("Return value: {}", value);
                    log.info("service >>>>>>>>>>");
                })
                .doOnError(throwable -> {
                    log.info("service return: ");
                    log.error("An error occurred during method execution: ", throwable);
                    log.info("service >>>>>>>>>>");
                });
    }
}
