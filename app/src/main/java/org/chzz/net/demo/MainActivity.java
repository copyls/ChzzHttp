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
        params.put("username", "copy");
        params.put("password", "123456");
        CHZZClient.builder()
                .params(params)
                .url("/userLogin")
                .disposeData(dataListener)
                .build()
                .get();

    }

    private IDisposeData dataListener = new DisposeDataImpl<UserEntity>() {
        @Override
        public void onSuccess(UserEntity entity) {

        }

        @Override
        public void onFailure(int code, String msg) {

        }
    };
}
