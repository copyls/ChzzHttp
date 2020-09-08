package org.chzz.net.callback;

import java.lang.reflect.ParameterizedType;

public abstract class DisposeDataImpl<T>  implements IDisposeData<T>  {
    @Override
    public Class<T> getTClass()
    {
        Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }
}
