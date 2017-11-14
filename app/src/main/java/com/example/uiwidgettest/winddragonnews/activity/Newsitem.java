package com.example.uiwidgettest.winddragonnews.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.uiwidgettest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Newsitem extends AppCompatActivity {
    @BindView(R.id.webview)
    android.webkit.WebView webView;
    @BindView(R.id.toolbarback)
    Button back;
    @OnClick(R.id.toolbarback)
    public void OnclickBack()
    {
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        利用webview加载新闻的url
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsitem);
        ButterKnife.bind(this);
        back.setVisibility(View.VISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        String url=getIntent().getStringExtra("url");
        webView.loadUrl(url);
    }
}
