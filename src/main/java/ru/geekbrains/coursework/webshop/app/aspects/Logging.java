package ru.geekbrains.coursework.webshop.app.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
/*
private method not support
protected method not support
@Autowired method not support
 */
public class Logging {
    private static final Map<Class<?>, Logger> LOGGERS = new HashMap<>();

    @Pointcut("execution(* ru.geekbrains.coursework.webshop.app.domain.*.*(..))")
    public void allServiceMethods() {
    }

    @Pointcut("execution(* ru.geekbrains.coursework.webshop.app.external..*.*(..))")
    public void allControllerMethods() {
    }

    @After("allServiceMethods() || allControllerMethods()")
    public void loggingPublicMethod(JoinPoint joinPoint) {
        this.getLogger(joinPoint).info(this.getLoggingMessage(joinPoint));
    }

    public String getLoggingMessage(JoinPoint joinPoint) {
        return String.format(
                "run method {%s %s} with param {%s}",
                Modifier.toString(joinPoint.getSignature().getModifiers()),
                joinPoint.getSignature().toShortString(),
                Arrays.toString(joinPoint.getArgs()));
    }


    public Logger getLogger(JoinPoint joinPoint) {
        return this.getLogger(joinPoint.getSourceLocation().getWithinType());
    }

    public Logger getLogger(Class<?> clazz) {
        return LOGGERS.getOrDefault(clazz, LoggerFactory.getLogger(clazz));
    }
}
