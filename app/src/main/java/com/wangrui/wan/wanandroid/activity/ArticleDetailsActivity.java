package com.wangrui.wan.wanandroid.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.wangrui.wan.wanandroid.R;

import java.net.URL;

public class ArticleDetailsActivity extends AppCompatActivity {

    private String link;
    private String title;
    private WebView webView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);
        Intent intent = getIntent();
        link = intent.getStringExtra("link");
        title = intent.getStringExtra("title");
        webView = findViewById(R.id.webView);
        textView = findViewById(R.id.title);
        WebSettings settings = webView.getSettings();
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        webView.loadUrl(link);
        webView.setWebViewClient(new WebViewClient(){ //设置加载页面不打开浏览器
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //view.loadUrl(request.getUrl());
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        textView.setText(title);
    }
}
