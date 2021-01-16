package org.chzz.net.demo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @ProjectName: ChzzHttp
 * @Package: org.chzz.net.demo
 * @ClassName: VideoPlayActivity
 * @Description:
 * @Author: copy
 * @CreateDate: 2020-11-12 09:49
 * @UpdateUser:
 * @UpdateDate: 2020-11-12 09:49
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */


public class VideoPlayActivity extends AppCompatActivity implements View.OnClickListener {
    private String videoUrl = "https://api.sigujx.com/?url=";
    private X5WebView x5webView;

    Button but_1, but_2, but_3, but_4, but_video;
    private String url = "https://m.v.qq.com/play.html?vid=x00323jocrm&cid=rjae621myqca41h";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        initView();
        but_1 = findViewById(R.id.but_1);
        but_2 = findViewById(R.id.but_2);
        but_3 = findViewById(R.id.but_3);
        but_4 = findViewById(R.id.but_4);
        but_1.setOnClickListener(this);
        but_2.setOnClickListener(this);

        but_3.setOnClickListener(this);
        but_4.setOnClickListener(this);
        startPlay(videoUrl);
    }

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
        }
        loadUrl();
    }

    private void loadUrl() {
        Log.i("CHZZ", "---BaseUrl + url---" + videoUrl + url);
        x5webView.loadUrl(videoUrl + url);
    }

    /**
     * 跳转至此页面
     *
     * @param context
     * @param videoUrl 视频地址
     */
    public static void actionStart(Context context, String videoUrl) {
        Intent intent = new Intent(context, VideoPlayActivity.class);
        intent.putExtra("videoUrl", videoUrl);
        context.startActivity(intent);
    }


    private void initView() {
        x5webView = findViewById(R.id.x5_webview);
    }

    /**
     * 使用自定义webview播放视频
     *
     * @param vedioUrl 视频地址
     */
    private void startPlay(String vedioUrl) {
        loadUrl();
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        x5webView.getView().setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        // x5webView.setWebChromeClient(new WebChromeClient());
        x5webView.setWebViewClient(webViewClient);

    }

    boolean firstLink = false;
    WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(final WebView webView, String s) {
            super.onPageFinished(webView, s);

            /**拿掉标题的主要代码   开始**/
            if (!firstLink) {//必须加上，不然会不断刷新WebView界面
                firstLink = true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Document doc = null;
                        try {
                            doc = Jsoup.connect(videoUrl+url).get();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {

                            String titleString = doc.getElementsByTag("title").text();

                            doc.title("好好看");
                            System.out.println("*******" + titleString);

                            /**拼凑要显示H5  开始**/
                            String html = doc.toString();
                            html = html.replace(" <script src=\"http", "");
                           doc.getElementsByTag("script").last().remove();


                            final String finalDoc = doc.toString();

                            VideoPlayActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    webView.loadDataWithBaseURL(videoUrl+url, finalDoc, "text/html", "utf-8", null); //只要有这句就得声明firstLink，不然一直刷新界面

                                    Log.i("CHZZ", "loadDataWithBaseURL");
                                }
                            });
                        } catch (Exception e) {

                        }
                    }
                }).start();
            }
        }

        @Override
        public void onPageStarted(final WebView view, final String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            Log.i("CHZZ", url);
//            if (url.contains(".png") || url.contains(".gif") || url.contains("jpg") || url.contains("jpge") || url.contains("pstatp.com")) {
//                try {
//                    return new WebResourceResponse("image/png", "utf-8", VideoPlayActivity.this.getAssets().open("bg.png"));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            if (url.contains("loading.jpg")) {
//                try {
//                    //handler.sendEmptyMessageDelayed(0, 500);
//                    return new WebResourceResponse("image/png", "utf-8", VideoPlayActivity.this.getAssets().open("loading.jpeg"));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
            if (url.contains(".mp4")) {
                //loadUrl();
            }
            return super.shouldInterceptRequest(view, url);
        }

    };
}