package ru.gb.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
//@Order(1) this annotation allow to create ordered queue of aspects if needed
public class ConsoleLoggerAspect {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ConsoleLoggerAspect.class);

    @Before("@annotation(ru.gb.aop.annotation.TrackUserAction)")
    public Object trackUserAction(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String methodName = methodSignature.getMethod().getName();
        LOGGER.info("-".repeat(60));
        LOGGER.info("Method \"{}\" successfully called", methodName);
        LOGGER.info("Returning type: {}",method.getGenericReturnType().getTypeName());
        LOGGER.info("-".repeat(60));

        return joinPoint;
    }
}