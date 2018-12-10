package cn.innovativest.entertainment.base;

import java.io.Serializable;

/**
 * 服务器返回数据基类
 */
public class BaseRespose<T> implements Serializable {

    private static final long serialVersionUID = 2484868567614623456L;
    /**
     * {
     * "states": 1,
     * "message":"ok",
     * "data": {
     * }
     * }
     */

    private int states;
    private String message;
    private T data;


    public BaseRespose() {
    }

    public BaseRespose(int states, String message, T data) {
        this.states = states;
        this.message = message;
        this.data = data;
    }

    public int getStates() {
        return states;
    }

    public void setStates(int states) {
        this.states = states;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }


    public void setData(T data) {
        this.data = data;
    }
}
