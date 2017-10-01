package com.example.uiwidgettest.materialdesign;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestOptions;
import com.example.uiwidgettest.MyApplication;
import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;

import java.io.File;

public class Hero_Activity extends AppCompatActivity {
private static final String HERO_NAME="hero_textview";
    private static final String HERO_IMAGE="hero_image";
    private HeroMessagedatabasehelper heroMessagedatabasehelper=new HeroMessagedatabasehelper(MyApplication.getContext(),"MyHero",null,2);
    private SQLiteDatabase sqLiteDatabase=heroMessagedatabasehelper.getReadableDatabase();
    ImageView imageView;private static final int IMAGEUSEURL=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_);
        Intent intent=getIntent();
        int image=intent.getIntExtra(HERO_IMAGE,0);
        String name=intent.getStringExtra(HERO_NAME);
        MyLog.i("Hero Activity","姓名："+name);
        imageView=(ImageView)findViewById(R.id.heroImageView);
        if(!sqLiteDatabase.query("HeroMessage",null,null,null,null,null,null).moveToNext()){
            //不能查询到数据就建立数据库
            InputDatatoDatabase inputDatatoDatabase = new InputDatatoDatabase();
            inputDatatoDatabase.Inputdata();
            MyLog.d("Hero ACtivity:","数据库不存在！");
        }
        final NestedScrollView nestedScrollView=(NestedScrollView)findViewById(R.id.NestedScrollView);
        FloatingActionButton actionButton=(FloatingActionButton)findViewById(R.id.hero_floatactionbutton);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final int dx=nestedScrollView.getScrollX();
               final int dy=nestedScrollView.getScrollY();
                nestedScrollView.scrollTo(dx+500,dy+500);
                MyLog.d("Hero Activity","当前滚动位置水平坐标:"+dx+"\n"+"当前滚动位置水平坐标:"+dy);
                Snackbar.make(v,"文字文字下滑成功！",Snackbar.LENGTH_SHORT).setAction("返回", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nestedScrollView.scrollTo(dx,dy);
                    }
                }).show();
            }
        });
//        if(image!=IMAGEUSEURL)//不等于初始值
//        {
//            try {
//                Glide.with(this).load(image).into(imageView);
//            }
//            catch (Exception e)
//            {
//                MyLog.e("Hero Activity:","图片加载出错");
//            }
//
//        }
//      else
 {
     Hero hero=(Hero)intent.getSerializableExtra("hero");
     File file=new File( Environment.getExternalStorageDirectory()+File.separator
             +"开发专用文件夹"+File.separator+"app使用图片"+hero.getUrl().toString().substring(hero.getUrl().toString().lastIndexOf("/")));
            try {
            RequestOptions options=new RequestOptions().placeholder(R.drawable.loading)
                    .error(R.drawable.noload);
                Glide.with(this).load(file).apply(options).into(imageView);
        }catch (Exception e)
            {
                MyLog.e("Hero Activity:","图片加载出错");
            }

        }
        TextView textView=(TextView)findViewById(R.id.hero_textview);
        CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collToolbar);
        collapsingToolbarLayout.setTitle(name);
        collapsingToolbarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"这是"+"大萌新！",Toast.LENGTH_SHORT).show();
            }
        });
        Toolbar toolbar=(Toolbar)findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        }
        textView.setText(genename(name));
    }
    private String genename(String name)
    {
        StringBuffer sb=new StringBuffer("");
       Cursor cursor=sqLiteDatabase.query("HeroMessage",null,"name=?",new String[]{name},null,null,null);
        MyLog.d("Hero Activity","数据查询成功！");
        if(cursor.moveToFirst())
        {
            do{
                sb.append(cursor.getString(cursor.getColumnIndex("narrative")));
            }
            while (cursor.moveToNext());
            cursor.close();
        }
        return sb.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
