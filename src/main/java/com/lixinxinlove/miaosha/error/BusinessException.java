package com.lixinxinlove.miaosha.error;

/**
 * 统一异常处理  包装器 业务异常类实现
 */
public class BusinessException extends Exception implements CommomError {

    private CommomError commomError;

    public BusinessException(CommomError commomError) {
        super();
        this.commomError = commomError;
    }

    public BusinessException(CommomError commomError, String errMsg) {
        super();
        this.commomError = commomError;
        this.commomError.setErrMsg(errMsg);
    }


    @Override
    public int getErrCode() {
        return commomError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return commomError.getErrMsg();
    }

    @Override
    public CommomError setErrMsg(String errMsg) {
        return commomError.setErrMsg(errMsg);
    }
}
