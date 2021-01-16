package org.chzz.net.demo;

import android.app.Application;
import android.util.Log;

import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;

import org.chzz.net.Chzz;

import java.util.HashMap;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化所有配置
        Chzz.init(this)
                .withApiHost("http://39.97.122.129/")
                .configure();

        HashMap map = new HashMap();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        QbSdk.initTbsSettings(map);
        //回调接口初始化完成接口回调
        QbSdk.PreInitCallback pcb=new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {

            }
            @Override
            public void onViewInitFinished(boolean b) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。

                Log.i("CHZZ", "---为true表示x5内核加载成功---" + b);
            }
        };

        //x5内核预加载，异步初始化x5 webview所需环境
        QbSdk.initX5Environment(getApplicationContext(), pcb);
    }

}
