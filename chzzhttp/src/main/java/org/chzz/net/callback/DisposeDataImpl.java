package org.chzz.net.callback;

public abstract class DisposeDataImpl<T>  implements IDisposeData<T>  {
    public T t;
    @Override
    public T getEntity() {
        return t;
    }

}
