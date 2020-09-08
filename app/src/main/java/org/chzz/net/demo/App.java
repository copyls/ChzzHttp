package org.chzz.net.demo;

import android.app.Application;

import org.chzz.net.Chzz;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化所有配置
        Chzz.init(this)
                .withApiHost("http://39.97.122.129/")
                .configure();
    }
}
