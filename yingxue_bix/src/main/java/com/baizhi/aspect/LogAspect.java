package com.baizhi.aspect;


import com.baizhi.annotation.AddLog;
import com.baizhi.entity.Log;
import com.baizhi.service.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Configuration  //配置类交给工厂管理
@Aspect     //该类是一个切面类
public class LogAspect {

    private static final Logger log1 = LoggerFactory.getLogger(LogAspect.class);
    @Resource
    HttpServletRequest request;

    @Resource
    LogService logService;

    @Around("@annotation(com.baizhi.annotation.AddLog)")
    public Object AddLog(ProceedingJoinPoint proceedingJoinPoint){
        System.out.println("---------进入环绕通知-------");
        //谁  时间 操作  成功

        //操作的方法名
        String nethodName = proceedingJoinPoint.getSignature().getName();
        //获取方法的Signature对象
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        //获取方法
        Method method = signature.getMethod();
        //获取方法上的注解
        AddLog addLog = method.getAnnotation(AddLog.class);
        //获取注解上的属性值
        String value = addLog.value();

        String status=null;
        Object result=null;
        //放行通知执行目标方法
        try {
            System.out.println("环绕通知执行目标方法前======");
            //放行通知执行目标方法
            result = proceedingJoinPoint.proceed();
            status="Success(执行成功)";
        } catch (Throwable e) {
            e.printStackTrace();
            status="Error(执行失败)";
            System.out.println("目标方法出现异常======");
            //抛出异常
            throw new RuntimeException(e.getMessage());
        } finally {
            Log logs = new Log(null, "xiaoh", new Date(), nethodName+"( "+value+" )", status);
            log1.info("管理员操作日志：{}",logs);
            //日志数据入库
            logService.add(logs);
        }
        //System.out.println("result"+result);
        return result;

    }
}
