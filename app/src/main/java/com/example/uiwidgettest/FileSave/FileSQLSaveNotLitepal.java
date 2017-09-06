package com.example.uiwidgettest.FileSave;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.database.DatabaseUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uiwidgettest.R;

public class FileSQLSaveNotLitepal extends AppCompatActivity {
    CreateTable createTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.derectsql);
        Button button=(Button)findViewById(R.id.CreateTable);
        createTable=new CreateTable(this,"hero.db",null,3);
        StringBuffer sb=new StringBuffer("");
       final SQLiteDatabase sqLiteDatabase=createTable.getWritableDatabase();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             createTable.getWritableDatabase();
            }
        });
        Button button1=(Button)findViewById(R.id.addData);
        final EditText textView1=(EditText)findViewById(R.id.te1);
        final EditText textView2=(EditText)findViewById(R.id.te2);
        final EditText textView3=(EditText)findViewById(R.id.te3);
        final EditText textView4=(EditText)findViewById(R.id.te4);
        final TextView textView5=(TextView)findViewById(R.id.display);
        textView5.setText(getIntent().getStringExtra("sqldata"));
        textView1.setText("刘备（执行语句exec插入）");
        textView2.setText("76");
        textView3.setText("65");
        textView4.setText("99");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=textView1.getText().toString();
//                try {
//                    byte[] val = new byte[name.length()];
//                    val = name.getBytes("utf-8");
//                    name = new String(val, "utf-8");
//                    Log.d("name的值：",name);
//                }catch (UnsupportedEncodingException e)
//                {
//                    Log.d("不能解析的编码！","");
//                }
                int force=-1;
                int wit=-1;
                int virtue=-1;

               try
               {
                   force=Integer.valueOf(textView2.getText().toString());
                   wit=Integer.valueOf(textView3.getText().toString());
                   virtue=Integer.valueOf(textView4.getText().toString());
               }
               catch (Exception e)
               {
                   Log.d("存在不合法数据","");
                   Toast.makeText(getBaseContext(),"存在不合法数据",Toast.LENGTH_SHORT).show();
               }

                ContentValues contextValues=new ContentValues();
                contextValues.put("name","刘备（values组装）");
                contextValues.put("wit",wit);
                contextValues.put("force",force);
                contextValues.put("virtue",virtue);
                sqLiteDatabase.insert("hero",null,contextValues);
                contextValues.clear();
                Toast.makeText(getBaseContext(),"values插入数据成功",Toast.LENGTH_SHORT).show();
                if(wit!=-1&&force!=-1&&virtue!=-1) {
                    sqLiteDatabase.execSQL("insert into hero (name,force,wit,virtue) values(?,?,?,?)",new String[]
                            {name,""+force,""+wit,""+virtue});
                    Log.d("插入成功！", "");
                    Toast.makeText(getBaseContext(),"exec插入数据成功",Toast.LENGTH_SHORT).show();
                }

            }
        });
        Button button2=(Button)findViewById(R.id.deleteData);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}
