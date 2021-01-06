package org.ds.health.controller;

import org.ds.health.entity.Result;
import org.ds.health.exception.HealthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HealExceptionAdvice {
    
    /**
     *  info:  打印日志，记录流程性的内容
     *  debug: 记录一些重要的数据 id, orderId, userId
     *  error: 记录异常的堆栈信息，代替e.printStackTrace();
     */
    private static final Logger log = LoggerFactory.getLogger(HealExceptionAdvice.class);

    /**
    * @description: 业务异常处理
    * @author: Deshan
    * @date: 2021/1/6 19:33
    * @param: [he]
    * @return: org.ds.health.entity.Result
    */
    @ExceptionHandler(HealthException.class)
    public Result handleHealthException(HealthException he){
        return new Result(false, he.getMessage());
    }

    /**
    * @description: 处理未知异常
    * @author: Deshan
    * @date: 2021/1/6 19:32
    * @param: [e]
    * @return: org.ds.health.entity.Result
    */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        log.error("发生异常",e);
        return new Result(false, "发生未知错误，操作失败，请联系管理员");
    }
}