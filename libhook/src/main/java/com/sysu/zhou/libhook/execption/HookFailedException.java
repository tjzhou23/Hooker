package com.sysu.zhou.libhook.execption;

/**
 * @author zhousysu
 */
public class HookFailedException extends Exception {
    public HookFailedException(String message) {
        super(message);
    }

    public HookFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public HookFailedException(Throwable cause) {
        super(cause);
    }
}
