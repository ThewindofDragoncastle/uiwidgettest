package com.example.uiwidgettest.filesave;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uiwidgettest.R;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

public class LitepalOSQL extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_litepal_osql);
        Button createTable=(Button)findViewById(R.id.CreateTable);
        Button addData1=(Button)findViewById(R.id.addData1);
        Button deletedata2=(Button)findViewById(R.id.deleteData2);
        Button updatedata2=(Button)findViewById(R.id.updateData2);
        final EditText energy=(EditText)findViewById(R.id.te11);
        final EditText height=(EditText)findViewById(R.id.te12);
        final EditText sex=(EditText)findViewById(R.id.te13);
        final EditText title=(EditText)findViewById(R.id.te14);
        final TextView textView=(TextView) findViewById(R.id.display1);
        textView.setText(getIntent().getStringExtra("FileSaveIntegration.AllHerodata"));
        energy.setText("99");
        height.setText("180");
        sex.setText("男");
        title.setText("虎痴");
        createTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.getDatabase();
                Toast.makeText(getBaseContext(),"数据库创建成功！ ",Toast.LENGTH_SHORT).show();
                Log.d("LitepalOSQL","数据库创建成功。");
            }
        });
        addData1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HeroData herodata=new HeroData();
                try {
                    herodata.setEnergy(Integer.valueOf(energy.getText().toString()));
                    herodata.setSex(sex.getText().toString());
                    herodata.setHeight(Integer.valueOf(height.getText().toString()));
                    herodata.setTitle(title.getText().toString());
                    herodata.save();
                    Toast.makeText(getBaseContext(),"添加数据成功",Toast.LENGTH_SHORT).show();
                } catch (Exception e)
                {
                    Log.d("LitepalSQL:","存在不合法数据");
                    Toast.makeText(getBaseContext(),"存在不合法数据",Toast.LENGTH_SHORT).show();
                }
            }
        });
        deletedata2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupport.deleteAll(HeroData.class,"title=? and height=?","虎痴","180");
            }
        });
        updatedata2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              HeroData heroData=new HeroData();
                heroData.setSex("女");
                heroData.updateAll("sex=?","男");
            }
        });
    }
}
