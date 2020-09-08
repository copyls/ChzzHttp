package org.chzz.net.callback;

/**
 * Created by copy on 2017/8/6.
 * Description:  请求开始AND结束回调接口
 * User: copy
 * Date: 2017-08-06
 * Time: 下午2:55
 */
public interface IRequest {

    void onRequestStart();
    void onRequestEnd(int code, String msg);
}
