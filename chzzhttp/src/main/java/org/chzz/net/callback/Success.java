package org.chzz.net.callback;

/**
 * Created by copy on 2017/8/8.
 */

public abstract class Success<T> implements ISuccess<T> {
    public T t;

    /**
     * 默认为String
     */
    public Success() {
    }

    /**
     * 传入对像
     *
     * @param t
     */
    public Success(T t) {
        this.t = t;
    }

    @Override
    public T getEntity() {
        return t;
    }
}
