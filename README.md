#   ChzzHttp库



    ChzzHttp库 把Retrofit2进行再次封装，支持链式调用，方便调用。

    项目地址：""

    项目依赖：implementation''

**方便之处**

- 链式调用，一点到底
- 动态切换请求基地址，人性化使用
- 自定义数据数类型，方便不同格式切换



###   一、使用方法

在application中进行全局初始化以及添加全局相关配置

1. 最简单用法

        //初始化所有配置， 默认使用的是okhttp3
        Chzz.init(this)
        .withApiHost("http://www.baidu.com")
        .configure();


2. 详细配置，可以自定义okhttp

        OkHttpClient client = new OkHttpClient.Builder()
            //增加拦截器
            .addNetworkInterceptor(new StethoInterceptor())
            //是否重定向
            .followRedirects(false)
             //超时时间
            .connectTimeout(30, TimeUnit.SECONDS)
             //cookie管理
            .cookieJar(new CookiesManager(this))
            .build();

            //初始化所有配置
            Chzz.init(this)
                .withApiHost(ConstantValues.BASE_URL)
                .withOkHttpClient(client)
                .configure();


### 二、请求方法的使用

1、get请求

    Map<String, Object> params = new HashMap<String, Object>();
    params.put("username", "copy");
    params.put("password", "123456");
    CHZZClient.builder()
            .params(params)
            .url("/userLogin")
            .disposeData(dataListener)
            .build()
            .get();


2 、post请求和get请求基本一样，只要把git改为post即可


    Map<String, Object> params = new HashMap<String, Object>();
    params.put("username", "copy");
    params.put("password", "123456");
    CHZZClient.builder()
            .params(params)
            .url("/userLogin")
            .disposeData(dataListener)
            .build()
            .post();
3、其他方法一样使用


###   三、处理返回的数据

    自动解析成bean

    private IDisposeData dataListener = new DisposeDataImpl<UserEntity>() {
        @Override
        public void onSuccess(UserEntity entity) {

        }
        @Override
        public void onFailure(int code, String msg) {

        }
    };

    需要保存原始String时，把bean改为 String
        private IDisposeData dataListener = new DisposeDataImpl<String>() {
        @Override
        public void onSuccess(String entity) {

        }
        @Override
        public void onFailure(int code, String msg) {

        }
    };


### 上传



    ....




### 下载


    ....




### Licenses

     Copyright 2017 qpg

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.