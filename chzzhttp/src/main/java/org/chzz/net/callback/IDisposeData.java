package org.chzz.net.callback;

public interface IDisposeData<T> {

    Class<T> getTClass();

    /**
     * 请求成功回调事件处理
     */
    void onSuccess(T entity);

    /**
     * 请求失败回调事件处理
     */
    void onFailure(int code, String msg);
}
