package com.lilu.apptool.http.exception;

/**
 * Description:
 *
 * @author lilu0916 on 2021/7/1
 * No one knows this better than me
 */
public class ServerException extends RuntimeException {

    private int errCode;
    private String message;

    public ServerException(int errCode, String msg) {
        super(msg);
        this.errCode = errCode;
        this.message = msg;
    }

    public int getErrCode() {
        return errCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
