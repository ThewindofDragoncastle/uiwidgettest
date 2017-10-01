package com.example.uiwidgettest.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.uiwidgettest.R;
public class FirstActivity extends BaseActivity {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);//返回一个MenInflater对象 对象中的方法（a,b）
        //a代表是通过哪一资源我们创建菜单 b：我们创建的菜单项目添加到哪一个菜单
        return true;//true代表可见。
    }
public boolean onOptionsItemSelected(MenuItem item)
{
    if(item.getItemId()==R.id.add_item)//获得字符串当你点击的时候
        Toast.makeText(this,"你乱点到了添加键",Toast.LENGTH_SHORT).show();//显示出来
    else if(item.getItemId()==R.id.remove_item)
        Toast.makeText(this,"你乱点到了删除键",Toast.LENGTH_SHORT).show();
    else if(item.getItemId()==R.id.Mywind_item)
        Toast.makeText(this,"我是幻想之风。",Toast.LENGTH_SHORT).show();
    else if(item.getItemId()==R.id.open_baidu)
    {
        Intent intent=new Intent(Intent.ACTION_VIEW);//这是一个Android内置的动作，其常量值为Intent.ACTION_VIEW
        intent.setData(Uri.parse("http://www.baidu.com"));//Uri将网址字符串解析为一个Uri对象 setData将数据传递出去
        startActivity(intent);
        }
    return true;
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);
        Log.d("第一个活动","得到的id是："+getTaskId());
        Button button1=(Button)findViewById(R.id.button_11);
        button1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Toast.makeText(FirstActivity.this, "你是李准！", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(FirstActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });
        Button button2=(Button)findViewById(R.id.button_12);
        button2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
               finish();
            }
        });
        Button button3=(Button)findViewById(R.id.button_13);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("com.example.activitytest.ACTION_START");
                startActivity(intent);
            }
        });
        Button b4=(Button)findViewById(R.id.button_14);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data="活动1的数据传递到活动2";
                Intent intent=new Intent(FirstActivity.this,SecondActivity.class);
                //前往第二个类
                intent.putExtra("getActivity1Data",data);//参数（a,b）代表键和值
                startActivity(intent);
            }
        });

        Button button5=(Button)findViewById(R.id.button_15);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(FirstActivity.this,SecondActivity.class);
                //启动活动的上下文+启动的活动
                startActivityForResult(it,1);
                //启动活动并且发送请求码而且请求销毁返回数据
            }
        });
        //接收返回码与请求码对比
        Button button6=(Button)findViewById(R.id.button_16);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(FirstActivity.this,FirstActivity.class);
                startActivity(it);
            }
        });
        Button button7=(Button)findViewById(R.id.button_17);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(FirstActivity.this,FourActivity.class);
                startActivity(it);
            }
        });
        Button button8=(Button)findViewById(R.id.button_18);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(FirstActivity.this,FiveActivity.class);
                startActivity(it);
            }
        });
        Button button9=(Button)findViewById(R.id.button_19);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(FirstActivity.this,SixActivity.class);
                startActivity(it);
            }
        });
    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data)//请求码 返回码 暂存的数据
    {
        switch (requestCode) {
            case 1:
                if(resultCode==RESULT_OK)
                {
            String rd = data.getStringExtra("datareturn");
            Log.d("FirstActivity", rd);
                    System.out.println("FirstActivity"+rd);
        }break;
            default:
    }
    }

}
