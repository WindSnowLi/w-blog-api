package xyz.firstmeet.lblog.object;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author windSnowLi
 */
public class Msg implements Serializable {
    public final static int CODE_SUCCESS = 20000;
    public final static int CODE_FAIL = -1;
    public final static String MSG_SUCCESS = "请求成功";
    public final static String MSG_FAIL = "请求失败";
    public final static String LOGIN_PASSWORD_ORNUMBER_FAIL = "账户或密码错误";
    /**
     * 返回状态码
     */
    @Getter
    private int code;
    /**
     * 返回信息
     */
    @Getter
    private String message;
    /**
     * 返回信息内容
     */
    @Getter
    private String data;

    public Msg setCode(int code) {
        this.code = code;
        return this;
    }

    public Msg setMessage(String message) {
        this.message = message;
        return this;
    }

    public Msg setData(String data) {
        this.data = data;
        return this;
    }

    public Msg(int code, String message, String data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private Msg(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private Msg() {
    }

    public static Msg makeMsg(int code, String msg, String content) {
        return new Msg(code, msg, content);
    }

    public static String makeJsonMsg(int code, String msg, Object content) {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("code",code);
        jsonObject.put("message",msg);
        jsonObject.put("data",content);
        return jsonObject.toJSONString();
    }

    public static String getFailMsg() {
        return Msg.makeJsonMsg(Msg.CODE_FAIL, Msg.MSG_FAIL, null);
    }

    public static String getSuccessMsg() {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, null);
    }

    public static Msg parseMsg(String msg) {
        return JSONObject.parseObject(msg, Msg.class);
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }


    public static String getSuccessMsg(Object object) {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, MSG_SUCCESS, object);
    }
}
