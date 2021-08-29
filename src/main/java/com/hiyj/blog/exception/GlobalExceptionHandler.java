package com.hiyj.blog.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.hiyj.blog.object.Msg;

public class GlobalExceptionHandler {

    /**
     * 处理JWTVerificationException异常
     */
    @ExceptionHandler(JWTVerificationException.class)
    public Msg jWTVerificationExceptionHandler() {
        return Msg.makeMsg(Msg.CODE_FAIL, Msg.MSG_FAIL, null);
    }
}
