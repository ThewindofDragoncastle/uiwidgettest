package com.example.uiwidgettest.myreview.broadcast;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;

import java.net.MalformedURLException;
import java.net.URL;

public class WebView extends AppCompatActivity implements View.OnClickListener {
      Button forward;
      Button back;
    android.webkit.WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Intent intent=getIntent();
        webView=(android.webkit.WebView)findViewById(R.id.ReviewWebview);
        forward=(Button)findViewById(R.id.webtoforward);
        forward.setOnClickListener(this);
        back=(Button)findViewById(R.id.webtoback);
        back.setOnClickListener(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(intent.getData()+"");
        MyLog.d("网页浏览：",intent.getData()+"");
        }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.webtoback:
                if(webView.canGoBack()) {
                    webView.goBack();
                }
                break;
            case R.id.webtoforward:
                if(webView.canGoForward())
                webView.goForward();
                break;

        }
    }
boolean Isback=false;
    @Override
    public void onBackPressed() {
        if(!Isback)
        {
            Toast.makeText(this,"再次按退出可成功退出",Toast.LENGTH_SHORT).show();
            Isback=true;
        }
        else {
            //被摧毁时在这里返回结果
            Intent intent=new Intent();
            intent.putExtra("result","我马玄黄，终不见人影成双。");
            setResult(1,intent);
            super.onBackPressed();
        }

    }


}
