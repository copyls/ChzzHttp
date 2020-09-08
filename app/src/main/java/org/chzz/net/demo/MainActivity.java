package org.chzz.net.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.chzz.net.CHZZClient;
import org.chzz.net.callback.DisposeDataImpl;
import org.chzz.net.callback.IDisposeData;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testGet();
    }


    private void testGet() {


        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mb", "18734924592");
        params.put("pwd", "999999q");
        CHZZClient.builder()
                .params(params)
                .url("module_voice/login_phone")
                .disposeData(dataListener)
                .build()
                .get();

    }

    private IDisposeData dataListener = new DisposeDataImpl<User>() {
        @Override
        public void onSuccess(User entity) {

            Log.i("CHZZ","------"+entity.emsg);
        }

        @Override
        public void onFailure(int code, String msg) {
            Log.i("CHZZ",msg);
        }
    };
}
