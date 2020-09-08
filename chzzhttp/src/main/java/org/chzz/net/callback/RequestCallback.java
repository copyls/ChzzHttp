package org.chzz.net.callback;

import android.os.Handler;
import android.widget.Toast;


import com.google.gson.Gson;

import org.chzz.net.Chzz;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by copy on 2017/8/6.
 * Description:
 * User: copy
 * Date: 2017-08-06
 * Time: 下午3:30
 */
public class RequestCallback implements Callback<String> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IDisposeData IDisposeData;
    private final IError ERROR;
    private static final Handler handler = new Handler();

    public RequestCallback(IRequest request, IDisposeData IDisposeData, ISuccess success, IFailure failure, IError error) {
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.IDisposeData = IDisposeData;
        this.ERROR = error;

    }


    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (REQUEST != null) {
            REQUEST.onRequestEnd(response.code(), response.message());
        }
        if (response.isSuccessful()) {
            if (call.isExecuted()) {
                //单处理成功
                String msg = "请设置回调接口";
                if (SUCCESS != null) {
                    try {
                        if (SUCCESS.getTClass().getName().equals("java.lang.String")) {
                            SUCCESS.onSuccess(response.body());
                        } else {
                            SUCCESS.onSuccess(new Gson().fromJson(response.body(), SUCCESS.getTClass()));
                        }
                    } catch (Exception e) {
                        Toast.makeText(Chzz.getApplication(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                    msg = null;
                }
                //统一处理
                if (IDisposeData != null) {
                    try {
                        if (IDisposeData.getTClass().getName().equals("java.lang.String")) {
                            IDisposeData.onSuccess(response.body());
                        } else {
                            IDisposeData.onSuccess(new Gson().fromJson(response.body(), IDisposeData.getTClass()));
                        }
                    } catch (Exception e) {
                        Toast.makeText(Chzz.getApplication(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                    msg = null;
                }
                if (msg != null) {
                    Toast.makeText(Chzz.getApplication(), msg, Toast.LENGTH_SHORT).show();
                }

            }
        } else {
            if (ERROR != null) {
                ERROR.onError(response.code(), response.message());
            }

            if (IDisposeData != null) {
                IDisposeData.onFailure(response.code(), response.message());
            }
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {

        if (FAILURE != null) {
            FAILURE.onFailure();
        }
        if (REQUEST != null) {
            REQUEST.onRequestEnd(-200, t.getLocalizedMessage());
        }
        if (IDisposeData != null) {
            IDisposeData.onFailure(-200, t.getLocalizedMessage());
        }

    }

}
