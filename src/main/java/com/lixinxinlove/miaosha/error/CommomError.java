package com.lixinxinlove.miaosha.error;

/**
 * 统一错误接口
 */
public interface CommomError {

    int getErrCode();

    String getErrMsg();

    CommomError setErrMsg(String errMsg);


}
