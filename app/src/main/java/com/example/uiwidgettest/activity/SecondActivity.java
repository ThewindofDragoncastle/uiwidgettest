package com.example.uiwidgettest.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.uiwidgettest.R;
public class SecondActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);
        Button b1=(Button)findViewById(R.id.button_21);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:13258200962"));
                startActivity(intent);
            }
        });
        Intent it=getIntent();//获得启动这个Intent对象
        String data=it.getStringExtra("getActivity1Data");
        System.out.println("第二个活动："+data);
        Button b2=(Button)findViewById(R.id.button_22);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回数据
                Intent it=new Intent();
                it.putExtra("datareturn","销毁时返回的数据");//将键与值暂存在intent中
                setResult(RESULT_OK,it);//前面请求返回结果 这里设置结果
                finish();
            }
        });
    }
}

