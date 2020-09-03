package pers.mihao.toolset.config.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import pers.mihao.toolset.util.RespHelper;

import java.lang.reflect.Method;

/**
 * 验证参数的aop
 */
@Component
@Aspect
public class BindingResultAspect {

    Logger log = LoggerFactory.getLogger(this.getClass());
    /**
     * 拦截所有的下级目录的controller
     */
    @Pointcut("execution(public * pers.mihao.toolset.*.controller.*.*(..))")
    public void BindingResult() {
    }

    @Around("BindingResult()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        log.info("http请求start: method:{}, args:{} ", method.getName(), args);
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult result = (BindingResult) arg;
                if (result.hasErrors()) {
                    FieldError fieldError = result.getFieldError();
                    if(fieldError!=null){
                        return RespHelper.parsErrResJson(fieldError.getDefaultMessage());
                    }else{
                        return RespHelper.parsErrResJson();
                    }
                }
            }
        }
        Object res = joinPoint.proceed();
        log.info("http请求end method:{}, res:{}", method.getName(), res.toString());
        return res;
    }
}
