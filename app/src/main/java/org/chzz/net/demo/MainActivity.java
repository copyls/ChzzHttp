package org.chzz.net.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsVideo;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.chzz.net.CHZZClient;
import org.chzz.net.callback.DisposeDataImpl;
import org.chzz.net.callback.IDisposeData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    WebView webView;
    String BaseUrl = "https://api.sigujx.com/?url="; //vip 1
    // String BaseUrl = "https://api.927jx.com/vip/?url=" ; //vip 2
    // String BaseUrl = "https://cdn.yangju.vip/kc/?url="  ;//vip 3
    // String BaseUrl = "https://jx.618g.com/?url=" ; //vip 5
    // String BaseUrl = "https://jsap.attakids.com/?url="; //vip 6
    String url = "http://m.iqiyi.com/v_28hcnk7vbz8.html?social_platform=link&p1=1_11_115";
    TextView iVloading;
    Button but_1, but_2, but_3, but_4, but_video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iVloading = findViewById(R.id.iv_loading);
        webView = findViewById(R.id.forum_context);
        but_1 = findViewById(R.id.but_1);
        but_2 = findViewById(R.id.but_2);
        but_3 = findViewById(R.id.but_3);
        but_4 = findViewById(R.id.but_4);
        but_video = findViewById(R.id.but_video);
        but_1.setOnClickListener(this);
        but_2.setOnClickListener(this);
        but_video.setOnClickListener(this);
        but_3.setOnClickListener(this);
        but_4.setOnClickListener(this);
        //mWebView.loadUrl("https://jsap.attakids.com/?url=https://www.iqiyi.com/v_18de8nylgnw.html?vfm=m_103_txsp");
      //  initView();
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
//
//        AgentWeb mAgentWeb  =AgentWeb.with(this)
//                .setAgentWebParent((LinearLayout) webVIew, new LinearLayout.LayoutParams(-1, -1))
//                .useDefaultIndicator()
//                .createAgentWeb()
//                .ready()
//                .go("https://jsap.attakids.com/?url=https://www.iqiyi.com/v_18de8nylgnw.html?vfm=m_103_txsp");
//
//        //mAgentWeb.getAgentWebSettings().getWebSettings().setUserAgentString("User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_0) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11");





    }

    private IDisposeData dataListener = new DisposeDataImpl<User>() {
        @Override
        public void onSuccess(User entity) {

            Log.i("CHZZ", "------" + entity.emsg);
        }

        @Override
        public void onFailure(int code, String msg) {
            Log.i("CHZZ", msg);
        }
    };

    public void initView() {
        //非wifi情况下，主动下载x5内核
        QbSdk.setDownloadWithoutWifi(true);
        CookieManager.getInstance().removeAllCookies(null);
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Toast.makeText(MainActivity.this, arg0 + "", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCoreInitFinished() {

            }
        };

        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
//        webView.getSettings().setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webView.getSettings().setBuiltInZoomControls(false); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webView.getSettings().setDisplayZoomControls(true); //隐藏原生的缩放控件
        webView.getSettings().setBlockNetworkImage(false);//解决图片不显示
        webView.getSettings().setLoadsImagesAutomatically(true); //支持自动加载图片
        webView.getSettings().setDefaultTextEncodingName("utf-8");//设置编码格式
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        webView.getSettings().setAppCachePath(appCachePath);
        webView.getSettings().setAllowFileAccess(true);    // 可以读取文件缓存
        webView.getSettings().setAppCacheEnabled(true);    //开启H5(APPCache)缓存功能
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setMixedContentMode(WebSettings.LOAD_NORMAL);     // https下访问http资源

        webView.setDrawingCacheEnabled(true);
        // JS的扩展，暂时不介绍，可自行百度
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        //webView.getSettings().setUserAgentString("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50");


        webView.setWebViewClient(new WebViewClient() {


            @Override
            public void onPageFinished(final WebView webView, String s) {
                super.onPageFinished(webView, s);
//                webView.loadUrl("javascript:window.local_obj.showSource('<head>'+"
//                        + "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
//                if (!firstLink) {//必须加上，不然会不断刷新WebView界面
//                    firstLink = true;
//                    /**拿掉标题的主要代码   开始**/
//
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Document doc = null;
//                            try {
//                                doc = Jsoup.connect(url).get();
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            try {
//
//                                String titleString = doc.getElementsByTag("title").text();
//
//                                doc.title("好好看");
//                                System.out.println("*******" + titleString);
//
//                                /**拼凑要显示H5  开始**/
//                                String html = doc.toString();
//                                final String finalDoc = html;
//                                MainActivity.this.runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        webView.loadDataWithBaseURL(url, finalDoc, "text/html", "utf-8", null); //只要有这句就得声明firstLink，不然一直刷新界面
//
//                                        Log.i("CHZZ", "loadDataWithBaseURL");
//                                    }
//                                });
//                            } catch (Exception e) {
//
//                            }
//                        }
//                    }).start();
//
//
//                    /**拿掉标题的主要代码   开始**/
//                }
            }


            @Override
            public void onPageStarted(final WebView view, final String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
//                Log.i("CHZZ", url);
//                if (url.contains("p.pstatp")) {
//                    try {
//                        return new WebResourceResponse("image/png", "utf-8", MainActivity.this.getAssets().open("bg.png"));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                if (url.contains("loading.jpg")) {
//                    try {
//                        handler.sendEmptyMessageDelayed(0, 500);
//                        return new WebResourceResponse("image/png", "utf-8", MainActivity.this.getAssets().open("loading.jpeg"));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                if (url.contains(".mp4")) {
//                    loadUrl();
//                }
                return super.shouldInterceptRequest(view, url);
            }

        });
        loadUrl();

    }

    private void loadUrl() {
        iVloading.setVisibility(View.VISIBLE);
        webView.loadUrl(BaseUrl + url);
        Log.i("CHZZ", "---BaseUrl + url---" + BaseUrl + url);
    }

    boolean firstLink = false;
    Handler handler = new Handler() {

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            iVloading.setVisibility(View.GONE);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_1:
                url = "https://m.v.qq.com/play.html?vid=t00329rbass&cid=rjae621myqca41h";
                break;
            case R.id.but_2:
                url = "http://m.iqiyi.com/v_19wkgr3ec3g.html?social_platform=link&p1=1_11_115";
                break;
            case R.id.but_3:
                url = "http://m.iqiyi.com/v_28hcnk7vbz8.html?social_platform=link&p1=1_11_115";
                break;
            case R.id.but_4:
                url = "https://v.youku.com/v_show/id_XNDkzODMxMTk4MA==.html";
                break;
            case R.id.but_video:
                startActivity(new Intent(this,VideoPlayActivity.class));
                break;
        }
        loadUrl();
    }
}
