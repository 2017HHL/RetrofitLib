package net.huansi.retrofit.utils;

import net.huansi.retrofit.listener.WebResponseListener;
import net.huansi.retrofit.listener.WebResponseListener.WebResponseError;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;


public abstract class DefaultObserver<T> implements Observer<T> {
    /**
     * 请求网络失败原因
     */
    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
    }
    private WebResponseListener webResponseListener;

    public DefaultObserver(WebResponseListener webResponseListener) {
        this.webResponseListener = webResponseListener;
    }

    @Override
    public void onNext(T response) {
        onSuccess(response);
        onFinish();
    }

    @Override
    public void onError(Throwable e) {
            //HTTP错误
        if (e instanceof HttpException) {
            onException(ExceptionReason.BAD_NETWORK);
            //连接错误
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {
            onException(ExceptionReason.CONNECT_ERROR);
            //连接超时
        } else if (e instanceof InterruptedIOException) {
            onException(ExceptionReason.CONNECT_TIMEOUT);
            //解析错误
        } else if (e instanceof JSONException
                || e instanceof ParseException) {
            onException(ExceptionReason.PARSE_ERROR);
        } else {
            onException(ExceptionReason.UNKNOWN_ERROR);
        }
        onFinish();
    }

    /**
     * 请求成功
     * @param response 服务器返回的数据
     */
    public abstract void onSuccess(T response);

    protected void onFinish(){}

    /**
     * 请求异常
     *
     */
    private void onException(ExceptionReason reason) {
        switch (reason) {
            case CONNECT_ERROR:
                setError("网络连接失败");
                break;
            case CONNECT_TIMEOUT:
                setError("连接超时");
                break;
            case BAD_NETWORK:
                setError("服务器异常");
                break;
            case PARSE_ERROR:
                setError("解析服务器响应数据失败");
                break;
            case UNKNOWN_ERROR:
            default:
                setError("服务器异常");
                break;
        }
    }
    private void setError(String error){
        if (webResponseListener !=null)webResponseListener.error(new WebResponseError(error));
    }


    @Override
    public void onSubscribe(Disposable d) {}


    @Override
    public void onComplete() {}



}

