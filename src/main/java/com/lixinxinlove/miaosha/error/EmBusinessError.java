package com.lixinxinlove.miaosha.error;

public enum EmBusinessError implements CommonError {


    PARAMETER_VALIDATION_ERROR(10001, "参数不合法"),

    UNKNOWN_ERROR(10002, "未知错误"),

    USER_NOT_EXIST(20001, "用户不存在");


    private int code;
    private String errMsg;


    EmBusinessError(int code, String errMsg) {
        this.code = code;
        this.errMsg = errMsg;
    }


    @Override
    public int getErrCode() {
        return this.code;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        return null;
    }
}
