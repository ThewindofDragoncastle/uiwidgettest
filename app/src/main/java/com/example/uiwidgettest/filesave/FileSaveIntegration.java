package com.example.uiwidgettest.filesave;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.uiwidgettest.R;

import org.litepal.crud.DataSupport;

import java.util.List;

public class FileSaveIntegration extends AppCompatActivity {
    CreateTable createTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filesaveintegration);
        Button button=(Button)findViewById(R.id.directSQL);
        Button button2=(Button)findViewById(R.id.LitepalSQL);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FileSaveIntegration.this,LitepalOSQL.class);
                List<HeroData> heroDatas= DataSupport.findAll(HeroData.class);
                StringBuffer sb=new StringBuffer("");
                for (HeroData herodata:heroDatas
                     ) {
                    sb.append(herodata.getEnergy()
                            +"\n"+herodata.getHeight()+"\n"
                            +herodata.getTitle()+"\n"+herodata.getSex());
                }//全部查询
                //部分查询

                Log.d("FileSaveIntegration",sb.toString());
                StringBuffer sbf=new StringBuffer("");
                HeroData herodatalist=DataSupport.findLast(HeroData.class);
                if(herodatalist!=null) {
                    sbf.append("最后一条数据("+herodatalist.getId() +")：体力："+ herodatalist.getEnergy()
                            + "\n身高：" + herodatalist.getHeight() + "\n称号："
                            + herodatalist.getTitle() + "\n性别: " + herodatalist.getSex());
                    intent.putExtra("FileSaveIntegration.AllHerodata", sbf.toString());
                }
                startActivity(intent);
            }
        });

        createTable=new CreateTable(this,"hero.db",null,3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FileSaveIntegration.this,FileSQLSaveNotLitepal.class);
                StringBuffer sb=new StringBuffer("");
                SQLiteDatabase sqLiteDatabase=createTable.getWritableDatabase();
                try {
                    Cursor cursor = sqLiteDatabase.query("hero", null, null, null, null, null, null);
                    Log.d("获取光标对象成功！", "");
                    if (cursor.moveToFirst())//如果移到首相成功
                    {
                        int i = 0;
                        while (true) {
                            String name1 = cursor.getString(cursor.getColumnIndex("name"));
                            int force1 = cursor.getInt(cursor.getColumnIndex("force"));
                            int wit1 = cursor.getInt(cursor.getColumnIndex("wit"));
                            int virtue1 = cursor.getInt(cursor.getColumnIndex("virtue"));
                            i++;
                            sb.replace(0, sb.length(), "最后一列：第" + cursor.getInt(cursor.getColumnIndex("id")) + "列" + "姓名：" + name1 + "\n武力：" + force1 +
                                    "\n智慧:" + wit1 + "\ni=" + i);
//                        Toast.makeText(getBaseContext(),i+"姓名："+name1+"\n武力："+force1+
//                                "\n智慧:"+wit1,Toast.LENGTH_SHORT).show();
                            Log.d("获取光标对象成功！", i + "姓名：" + name1 + "\n武力：" + force1 +
                                    "\n智慧:" + wit1);
                            if (!cursor.moveToNext())
                                break;

                        }

                    }
                    cursor.close();
                    intent.putExtra("sqldata", sb.toString());
                }catch (Exception e)
                {
                    Toast.makeText(getBaseContext(),"最后一列数据不存在",Toast.LENGTH_SHORT).show();
                }
                startActivity(intent);
            }

        });

    }
}
