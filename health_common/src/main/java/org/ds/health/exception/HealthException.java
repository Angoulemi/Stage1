package org.ds.health.exception;

import java.io.Serializable;

/**
 * @description: 自定义异常
 * 区分系统与自定义的异常
 * 终止已经不符合业务逻辑的代码
 * @author: Deshan
 * @date: 2021/1/6 19:28
 */
public class HealthException extends RuntimeException implements Serializable {
    public HealthException(String message) {
        super(message);
    }
}