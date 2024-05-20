package com.projcet.tstorycopyproject.global.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class PerformanceAspect {
    @Around("execution(* com.projcet.tstorycopyproject.domain..controller.*.*(..)) || @annotation(com.projcet.tstorycopyproject.global.aspect.TimeCheck)")
    public Object measureClassMethodExecutionTime(org.aspectj.lang.ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object returnValue = joinPoint.proceed();
        long totalTime = System.currentTimeMillis() - startTime;

        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        log.info("[" + className + "." + methodName + "] took " + totalTime + " ms.");

        return returnValue;
    }
}
