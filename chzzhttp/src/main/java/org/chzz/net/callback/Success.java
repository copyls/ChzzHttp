package org.chzz.net.callback;

import java.lang.reflect.ParameterizedType;

/**
 * Created by copy on 2017/8/8.
 */

public abstract class Success<T> implements ISuccess<T> {


    @Override
    public Class<T> getTClass()
    {
        Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }

}
