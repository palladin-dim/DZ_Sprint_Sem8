package ru.gb.timesheet.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class RecoverAspect {

    @Around("@annotation(ru.gb.timesheet.aspect.Recover)")
    public Object handleRecoverableMethod(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Recover recoverAnnotation = method.getAnnotation(Recover.class);
        Class<?>[] noRecoverFor = recoverAnnotation.noRecoverFor();

        try {
            return pjp.proceed();
        } catch (Throwable ex) {
            for (Class<?> exceptionClass : noRecoverFor) {
                if (exceptionClass.isAssignableFrom(ex.getClass())) {
                    throw ex;
                }
            }

            log.error("Recovering {}#{} after exception [{}: {}]",
                    pjp.getTarget().getClass().getSimpleName(),
                    pjp.getSignature().getName(),
                    ex.getClass().getName(),
                    ex.getMessage());

            return getDefaultValue(signature.getReturnType());
        }
    }

    private Object getDefaultValue(Class<?> returnType) {
        if (returnType.isPrimitive()) {
            if (returnType.equals(boolean.class)) {
                return false;
            } else if (returnType.equals(char.class)) {
                return '\u0000';
            } else {
                return 0;
            }
        }
        return null;
    }
}
