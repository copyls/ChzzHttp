package org.chzz.net;


import org.chzz.net.cookie.CookiesManager;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by copy on 2017/8/6.
 * Description:
 * User: copy
 * Date: 2017-08-06
 * Time: 下午1:50
 */
public class RestCreator {

    public static final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();

    public static RestService getRestService() {
        return RestServiceHolder.REST_SERVICE;
    }

    /**
     * 设置retrofit2
     */
    private static final class RetrofitHolder {
        //服务器的URL
        private static final String BASE_URL = (String) Chzz.getConfigurations().get(ConfigType.API_HOST.name());
        private static final OkHttpClient client = (OkHttpClient) Chzz.getConfigurations().get(ConfigType.OKHTTPCLIENT.name());
        //构建  retrofit2
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client == null ? okHttpHolder.OK_HTTP_CLIENT : client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    /**
     * okHttp设置
     */
    private static final class okHttpHolder {
        //超时时间
        private static final int TIME_OUT = 60;
        //builder形式new OkHttpClient
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        //测试使用的拦截器
        private static final ArrayList<Interceptor> INTERCEPTORS = Chzz.getConfiguration(ConfigType.INTERCEPTORS);

        private static final OkHttpClient OK_HTTP_CLIENT = BUILDER
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .cookieJar(new CookiesManager(Chzz.getApplication()))
                .build();

        //为okHttp增加拦截器
        private static OkHttpClient.Builder addInterceptor() {
            if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty()) {
                for (Interceptor interceptor : INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor);
                }
            }
            return BUILDER;
        }
    }


    private static final class RestServiceHolder {
        private static final RestService REST_SERVICE = RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }
}
