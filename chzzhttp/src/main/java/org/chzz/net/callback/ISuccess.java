package org.chzz.net.callback;

/**
 * Created by copy on 2017/8/6.
 * Description:
 * User: copy
 * Date: 2017-08-06
 * Time: 下午2:53
 */
public interface ISuccess<T> {
    void onSuccess(T entity);

    T getEntity();
}
