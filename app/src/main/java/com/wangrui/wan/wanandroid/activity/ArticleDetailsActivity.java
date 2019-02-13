package com.wangrui.wan.wanandroid.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.wangrui.wan.wanandroid.R;

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
        webView.loadUrl(link);
        textView.setText(title);
    }
}
