package com.lilu.apptool.http.exception;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.io.NotSerializableException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * Description:
 *
 * @author lilu0916 on 2021/7/1
 * No one knows this better than me
 */
public class ApiException extends Exception {


    private final int code;
    private String displayMessage;

    private String message;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
        this.message = throwable.getMessage();
    }

    public int getCode() {
        return code;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String msg) {
        this.displayMessage = msg + "(code:" + code + ")";
    }


    public static ApiException handleException(Throwable throwable) {
        ApiException ex;
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            ex = new ApiException(httpException, httpException.code());
            ex.message = httpException.getMessage();
            return ex;
        } else if (throwable instanceof JsonParseException
                || throwable instanceof JSONException
                || throwable instanceof JsonSyntaxException
                || throwable instanceof JsonSerializer
                || throwable instanceof NotSerializableException
                || throwable instanceof ParseException) {
            ex = new ApiException(throwable, ERROR.PARSE_ERROR);
            ex.message = "解析错误";
            return ex;
        } else if (throwable instanceof ClassCastException) {
            ex = new ApiException(throwable, ERROR.CAST_ERROR);
            ex.message = "类型转换错误";
            return ex;
        } else if (throwable instanceof ConnectException) {
            ex = new ApiException(throwable, ERROR.NETWORK_ERROR);
            ex.message = "连接失败";
            return ex;
        } else if (throwable instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ApiException(throwable, ERROR.SSL_ERROR);
            ex.message = "证书验证失败";
            return ex;
        } else if (throwable instanceof ConnectTimeoutException) {
            ex = new ApiException(throwable, ERROR.TIMEOUT_ERROR);
            ex.message = "连接超时";
            return ex;
        } else if (throwable instanceof java.net.SocketTimeoutException) {
            ex = new ApiException(throwable, ERROR.TIMEOUT_ERROR);
            ex.message = "连接超时";
            return ex;
        } else if (throwable instanceof UnknownHostException) {
            ex = new ApiException(throwable, ERROR.UNKNOWN_HOST);
            ex.message = "无法解析该域名";
            return ex;
        } else if (throwable instanceof NullPointerException) {
            ex = new ApiException(throwable, ERROR.NULL_POINTER_EXCEPTION);
            ex.message = "NullPointerException";
            return ex;
        } else {
            ex = new ApiException(throwable, ERROR.UNKNOWN);
            ex.message = "未知错误";
            return ex;
        }
    }

    @Override
    public String getMessage() {
        return message;
    }


    /**
     * 约定异常
     */
    public static class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORK_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1004;

        /**
         * 连接超时
         */
        public static final int TIMEOUT_ERROR = 1005;

        /**
         * 调用错误
         */
        public static final int INVOKE_ERROR = 1006;
        /**
         * 类转换错误
         */
        public static final int CAST_ERROR = 1007;
        /**
         * 请求取消
         */
        public static final int REQUEST_CANCEL = 1008;
        /**
         * 未知主机错误
         */
        public static final int UNKNOWN_HOST = 1009;

        /**
         * 空指针错误
         */
        public static final int NULL_POINTER_EXCEPTION = 1010;
    }
}
