package org.chzz.net;

import android.content.Context;

import org.chzz.net.callback.IDisposeData;
import org.chzz.net.callback.IError;
import org.chzz.net.callback.IFailure;
import org.chzz.net.callback.IRequest;
import org.chzz.net.callback.ISuccess;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by copy on 2017/8/6.
 * Description:
 * User: copy
 * Date: 2017-08-06
 * Time: 下午3:00
 */
public class RestClientBuilder {
    //请求接口地址
    private String mUrl;
    //请求参数  Map<>形式
    private static Map<String, Object> PARAMS;
    //开始或结束回调
    private IRequest mRequest;
    //请求成功回调
    private ISuccess mSuccess;
    //请求失败回调
    private IFailure mFailure;
    //请求错误回调
    private IError mError;
    //原形参数 body
    private RequestBody mBody;
    //上下文
    private Context context;
    //加载图标样式
    //上传的文件
    private File file;
    private IDisposeData IDisposeData;

    public RestClientBuilder() {

    }

    public final RestClientBuilder params(Map<String, Object> params) {
        PARAMS = new HashMap<>();
        PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key, Object value) {

        if (null == PARAMS) {
            PARAMS = new HashMap<>();
        }
        this.PARAMS.put(key, value);
        return this;
    }

    public final RestClientBuilder file(File file) {

        this.file = file;
        return this;
    }

    public final RestClientBuilder file(String file) {
        this.file = new File(file);
        return this;
    }

    public final RestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);

        return this;
    }

    public final RestClientBuilder onRequest(IRequest request) {
        this.mRequest = request;
        return this;
    }

    public final RestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder disposeData(IDisposeData IDisposeData) {
        this.IDisposeData = IDisposeData;
        return this;
    }

    public final RestClientBuilder success(ISuccess success) {
        this.mSuccess = success;
        return this;
    }

    public final RestClientBuilder failure(IFailure IFailure) {
        this.mFailure = IFailure;
        return this;
    }

    public final RestClientBuilder loader(Context context) {
        this.context = context;

        return this;
    }

    public final RestClientBuilder error(IError error) {
        this.mError = error;
        return this;
    }


    private Map<String, Object> checkParama() {
        return PARAMS;
    }

    public final CHZZClient build() {
        return new CHZZClient(mUrl, PARAMS, IDisposeData, mRequest, mSuccess, mFailure, mError, mBody, file, context);
    }

}
