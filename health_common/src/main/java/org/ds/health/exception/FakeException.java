package org.ds.health.exception;

import java.io.Serializable;

/**
 * @author :Deshan
 * @description:
 * @date :Created in 2021/1/8 20:44
 * @version: 1.0
 */
public class FakeException extends RuntimeException implements Serializable {
    public FakeException(String message){
        super(message);
    }
}
