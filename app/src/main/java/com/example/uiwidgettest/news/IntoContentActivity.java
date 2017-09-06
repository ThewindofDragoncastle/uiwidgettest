package com.example.uiwidgettest.news;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.uiwidgettest.R;

public class IntoContentActivity extends AppCompatActivity {
//将双页布局的右边布局的碎片引入活动


    public static void actionStart(Context context, String title, String content)
    {
        Intent intent=new Intent(context,IntoContentActivity.class);
        intent.putExtra("new_title",title);//传递参数
        intent.putExtra("new_content",content);
        System.out.println("已经传递参数");
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onlytitlefragment);
        System.out.println("单页面视图建立成功");
        String newstitle=getIntent().getStringExtra("new_title");
        String newscontent=getIntent().getStringExtra("new_content");
        Content contentFragment=
                (Content) getSupportFragmentManager().findFragmentById(R.id.content_fragment);
        contentFragment.refresh(newstitle,newscontent);
    }
}
