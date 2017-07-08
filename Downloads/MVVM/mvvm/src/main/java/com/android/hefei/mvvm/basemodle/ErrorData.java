package com.android.hefei.mvvm.basemodle;

/**
 * Created by hefei on 17/6/4.
 * <p>
 * 加载数据异步回调的错误信息
 */

public class ErrorData {

    private int code;
    private String msg;

    public ErrorData() {
    }

    public ErrorData(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ErrorData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
