package xyz.firstmeet.lblog.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import xyz.firstmeet.lblog.object.Msg;

public class GlobalExceptionHandler {

    /**
     * 处理JWTVerificationException异常
     */
    @ExceptionHandler(JWTVerificationException.class)
    public Msg jWTVerificationExceptionHandler() {
        return Msg.makeMsg(Msg.CODE_FAIL, Msg.MSG_FAIL, null);
    }
}
