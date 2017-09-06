package com.example.uiwidgettest.byinternet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.uiwidgettest.R;

public class InternetIntergration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_intergration);
        final Button Intowebview=(Button)findViewById(R.id.Intowebview);
        Intowebview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InternetIntergration.this,ByWebView.class);
                startActivity(intent);
            }
        });
        final Button IntoHTMLdata=(Button)findViewById(R.id.IntoURLCONNHTMLdata);
        IntoHTMLdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InternetIntergration.this,HTMLdata.class);
                startActivity(intent);
            }
        });
        final Button IntoOKHttpHTMLdata=(Button)findViewById(R.id.IntoOKHttpHTMLdata);
        IntoOKHttpHTMLdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InternetIntergration.this,OKHTTPGetData.class);
                startActivity(intent);
            }
        });
        final Button Intopullmethod=(Button)findViewById(R.id.Intopullmethod);
        Intopullmethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InternetIntergration.this,PullMethod.class);
                startActivity(intent);
            }
        });
        final Button Testchat=(Button)findViewById(R.id.Testchat);
        Testchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InternetIntergration.this,chatfromserver.class);
                startActivity(intent);
            }
        });
        final Button IntoSAXmethod=(Button)findViewById(R.id.IntoSAXmethod);
        IntoSAXmethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InternetIntergration.this,SaxParse.class);
                startActivity(intent);
            }
        });
        final Button IntoJSONobjectmethod=(Button)findViewById(R.id.IntoJSONobjectmethod);
        IntoJSONobjectmethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InternetIntergration.this,JSONobjectparse.class);
                startActivity(intent);
            }
        });
        final Button IntoGSONmethod=(Button)findViewById(R.id.IntoGSONmethod);
        IntoGSONmethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InternetIntergration.this,GSONparse.class);
                startActivity(intent);
            }
        });
    }
}
