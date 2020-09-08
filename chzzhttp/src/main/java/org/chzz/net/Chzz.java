package org.chzz.net;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by copy on 2017/8/6.
 * Description:
 * User: copy
 * Date: 2017-08-06
 * Time: 上午9:56
 */
public final class Chzz {

    /**
     * 初始化Chzz配置
     * @param context
     * @return
     */
    public static Configurator init(Context context) {
        getConfigurations().put(ConfigType.APPLICATION_CONTEXY.name(), context.getApplicationContext());
        return Configurator.getInstance();

    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    /**
     * 通过key来获取配置文件值
     * @param key 获取内容key
     * @param <T> 返回的内容
     * @return
     */
    public static <T> T getConfiguration(Enum<ConfigType> key) {
        return getConfigurator().getConfiguration(key);
    }

    /**
     * 获取所有的配置的HasMap
     * @return
     */
    public static HashMap<String, Object> getConfigurations() {
        return Configurator.getInstance().getChzzConfigs();
    }

    /**
     * 获取全局Application
     * @return
     */
    public  static Context getApplication(){
        return (Context) getConfigurations().get(ConfigType.APPLICATION_CONTEXY.name());
    }


}
