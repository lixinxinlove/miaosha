package com.lixinxinlove.miaosha.response;

/**
 * 统一返回类型
 */
public class CommonReturnType {

    private String status;
    private int code;
    private Object data;

    private static CommonReturnType commonReturnType;

    public static CommonReturnType create(Object data) {
        commonReturnType = new CommonReturnType();
        commonReturnType.setData(data);
        commonReturnType.setStatus("success");
        commonReturnType.setCode(1000);
        return commonReturnType;
    }

    public static CommonReturnType error(Object data) {
        commonReturnType = new CommonReturnType();
        commonReturnType.setData(data);
        commonReturnType.setStatus("fail");
        commonReturnType.setCode(4000);
        return commonReturnType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
