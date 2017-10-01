package com.example.uiwidgettest.contentprovider;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uiwidgettest.R;

public class ContentProviderIntegration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider_integration);
        final TextView display1=(TextView)findViewById(R.id.CPdisplay);
        final Button callphone = (Button) findViewById(R.id.callphone);
        final Button getContact = (Button) findViewById(R.id.getContact);
        final Button PARTHERO = (Button) findViewById(R.id.PARTHERO);
        final Button ALLHERO = (Button) findViewById(R.id.ALLHERO);
        final Button ALLSEARCHHERODATA = (Button) findViewById(R.id.ALLHERODATA);
        final Button PARTSEARCHHERODATA = (Button) findViewById(R.id.PARTHERODATA);
        final Button Deleteherodata = (Button) findViewById(R.id.Deleteherodata);
        final Button DeleteSearchherodata = (Button) findViewById(R.id.DeleteSearchherodata);
        final Button updateherodata= (Button) findViewById(R.id.updateherodata);
        final Button updateSearchherodata = (Button) findViewById(R.id.updateSearchherodata);
        final EditText loyal=(EditText)findViewById(R.id.loyal);
        final EditText exp=(EditText)findViewById(R.id.exp);
        final EditText deleteorupdatehero=(EditText)findViewById(R.id.updatehero);
        final EditText deleteorupdatesearchhero=(EditText)findViewById(R.id.updateSearchhero);
        deleteorupdatehero.setText("1");
        deleteorupdatesearchhero.setText("1");
        loyal.setText("99");
        exp.setText("99");
        final Button  cpadddata= (Button) findViewById(R.id.cpadddata);
        Deleteherodata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//没有数据则删除所有
                if(deleteorupdatehero.getText().toString().isEmpty())
                {
                    Uri uri=Uri.parse("content://com.example.uiwidgettest.provider/hero");
                    getContentResolver().delete(uri,null,null);
                    Toast.makeText(getBaseContext(),"删除hero表全部数据成功！",Toast.LENGTH_SHORT).show();
                }
                else
                { if (IsInvaild(deleteorupdatehero.getText().toString())) {
                    Uri uri = Uri.parse("content://com.example.uiwidgettest.provider/hero/" +1);
                   try {
                       getContentResolver().delete(uri, "id=?", new String[]{deleteorupdatehero.getText().toString()});
                       Toast.makeText(getBaseContext(),"删除hero表id为："+Integer.valueOf(deleteorupdatehero.getText().toString())+" 数据成功！",Toast.LENGTH_SHORT).show();
                   }catch (Exception e)
                   {
                       Log.d("CPI","删除失败,原因：id不存在。");
                       Toast.makeText(getBaseContext(),"删除失败请检查id是否存在！",Toast.LENGTH_SHORT).show();
                   }

                }
                }
            }
        });
        DeleteSearchherodata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//没有数据则删除所有
                if (deleteorupdatesearchhero.getText().toString().isEmpty()) {
                    Uri uri = Uri.parse("content://com.example.uiwidgettest.provider/Searchherodata");
                    getContentResolver().delete(uri, null, null);
                    Toast.makeText(getBaseContext(), "删除searchhero表全部数据成功！", Toast.LENGTH_SHORT).show();
                } else {
                    if (IsInvaild(deleteorupdatesearchhero.getText().toString())) {
                        Uri uri = Uri.parse("content://com.example.uiwidgettest.provider/Searchherodata/" + 1);
                        try {
                            getContentResolver().delete(uri, "id=?", new String[]{deleteorupdatesearchhero.getText().toString()});
                            Toast.makeText(getBaseContext(), "删除searchhero表id为：" + Integer.valueOf(deleteorupdatesearchhero.getText().toString()) + " 数据成功！", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.d("CPI", "删除失败,原因：id不存在。");
                            Toast.makeText(getBaseContext(), "删除失败请检查id是否存在！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        updateherodata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//没有数据则更新所有
                if(deleteorupdatehero.getText().toString().isEmpty())
                {
                    Uri uri=Uri.parse("content://com.example.uiwidgettest.provider/hero");
                    ContentValues values=new ContentValues();
                    values.put("name","魏延");
                    getContentResolver().update(uri,values,null,null);
                    Toast.makeText(getBaseContext(),"更新hero表全部数据成功！",Toast.LENGTH_SHORT).show();
                }
                else
                { if (IsInvaild(deleteorupdatehero.getText().toString())) {
                    Uri uri = Uri.parse("content://com.example.uiwidgettest.provider/hero/" +1);
                    try {
                        ContentValues values=new ContentValues();
                        values.put("name","魏延");
                        getContentResolver().update(uri,values, "id=?", new String[]{deleteorupdatehero.getText().toString()});
                        Toast.makeText(getBaseContext(),"更新hero表id为："+Integer.valueOf(deleteorupdatehero.getText().toString())+" 数据成功！",Toast.LENGTH_SHORT).show();
                    }catch (Exception e)
                    {
                        Log.d("CPI","删除失败,原因：id不存在。");
                        Toast.makeText(getBaseContext(),"删除失败请检查id是否存在！",Toast.LENGTH_SHORT).show();
                    }

                }
                }
            }
        });
        updateSearchherodata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//没有数据则更新所有
                if(deleteorupdatesearchhero.getText().toString().isEmpty())
                {
                    Uri uri=Uri.parse("content://com.example.uiwidgettest.provider/Searchherodata");
                    ContentValues values=new ContentValues();
                    values.put("loyal","999");
                    getContentResolver().update(uri,values,null,null);
                    Toast.makeText(getBaseContext(),"更新hero表全部数据成功！",Toast.LENGTH_SHORT).show();
                }
                else
                { if (IsInvaild(deleteorupdatehero.getText().toString())) {
                    Uri uri = Uri.parse("content://com.example.uiwidgettest.provider/Searchherodata/" +1);
                    try {
                        ContentValues values=new ContentValues();
                        values.put("loyal","999");
                        getContentResolver().update(uri,values, "id=?", new String[]{deleteorupdatesearchhero.getText().toString()});
                        Toast.makeText(getBaseContext(),"更新hero表id为："+Integer.valueOf(deleteorupdatesearchhero.getText().toString())+" 数据成功！",Toast.LENGTH_SHORT).show();
                    }catch (Exception e)
                    {
                        Log.d("CPI","删除失败,原因：id不存在。");
                        Toast.makeText(getBaseContext(),"删除失败请检查id是否存在！",Toast.LENGTH_SHORT).show();
                    }

                }
                }
            }
        });
        cpadddata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //向Searchherodata表插入数据 并通过自建内容提供器查询
                int loyaldata=-1;
                int expdata=-1;
                if(IsInvaild(loyal.getText().toString())&&IsInvaild(exp.getText().toString()))
                {
                    loyaldata=Integer.valueOf(loyal.getText().toString());
                    expdata=Integer.valueOf(exp.getText().toString());
                    Uri uri=Uri.parse("content://com.example.uiwidgettest.provider/Searchherodata/1");
                    //切记要匹配
                    ContentValues contentValues=new ContentValues();
                    contentValues.put("loyal",loyaldata);
                    contentValues.put("exp",expdata);
                    Uri newUri=getContentResolver().insert(uri,contentValues);
                    String newid=newUri.getPathSegments().get(1);
                    Log.d("CPI:",newid);
                    StringBuffer sb=new StringBuffer("");
                    Cursor cursor=getContentResolver().query(uri,null,null,null,null);
                    sb.replace(0,sb.length(),queryString(cursor));
                    if(!sb.toString().isEmpty())
                        display1.setText("添加的是第"+newid+"条\n"+"数据内容为："+sb.toString());
                    else
                        display1.setText("查询不到数据或者未添加数据为空");
                }

            }

        });
        PARTSEARCHHERODATA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查询部分Searchherodatahero数据 id为1
                Uri uri=Uri.parse("content://com.example.uiwidgettest.provider/Searchherodata/1");
                Cursor cursor=getContentResolver().query(uri,null,null,null,null);
                StringBuffer sb=new StringBuffer("");
                sb.replace(0,sb.length(),queryString(cursor));
                if(!sb.toString().isEmpty())
                    display1.setText("数据内容为："+sb.toString());
                else
                    display1.setText("查询不到数据或者未添加数据为空");
            }
        });
        ALLSEARCHHERODATA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查询全部hero数据
                Uri uri=Uri.parse("content://com.example.uiwidgettest.provider/Searchherodata");
                Cursor cursor=getContentResolver().query(uri,null,null,null,null);
                StringBuffer sb=new StringBuffer("");
                sb.replace(0,sb.length(),queryString(cursor));
                if(!sb.toString().isEmpty())
                    display1.setText("数据内容为："+sb.toString());
                else
                    display1.setText("查询不到数据或者未添加数据为空");

            }
        });
        ALLHERO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse("content://com.example.uiwidgettest.provider/hero");
                Cursor cursor=getContentResolver().query(uri,null,null,null,null);
                StringBuffer sb=new StringBuffer("");
                sb.replace(0,sb.length(),  queryToastDisplay(cursor));
                if(!sb.toString().isEmpty())
                    display1.setText("数据内容为："+sb.toString());
                else
                    display1.setText("查询不到数据或者未添加数据为空");


            }
        });
        PARTHERO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查询部分hero数据 id为1
                Uri uri=Uri.parse("content://com.example.uiwidgettest.provider/hero/1");
                Cursor cursor=getContentResolver().query(uri,null,null,null,null);
                StringBuffer sb=new StringBuffer("");
                sb.replace(0,sb.length(),  queryToastDisplay(cursor));
                if(!sb.toString().isEmpty())
                    display1.setText("数据内容为："+sb.toString());
                else
                    display1.setText("查询不到数据或者未添加数据为空");

            }
        });
        getContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getcontact
                Intent intent=new Intent(ContentProviderIntegration.this,GetContactMessage.class);
                startActivity(intent);
            }
        });
        callphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ContentProviderIntegration.this, Manifest.permission.CALL_PHONE) ==
                        PackageManager.PERMISSION_GRANTED) {
                      call();
                } else
                {
                    ActivityCompat.requestPermissions(ContentProviderIntegration.this,
                            new String[] {Manifest.permission.CALL_PHONE},1);
                }

            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case 1:if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                call();
            }
                else
                Toast.makeText(getBaseContext(), "获取权限失败！", Toast.LENGTH_SHORT).show();
                break;
                default:
        }

    }

    private void call()
    {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:10010"));
            startActivity(intent);
        }catch (SecurityException e)
        {
            Toast.makeText(getBaseContext(), "程序出错了！！！", Toast.LENGTH_SHORT).show();
        }
    }
    private String queryString(Cursor cursor)
    {
        //作用返回显示数据库只能用于searchherodata的内容
        StringBuffer sb=new StringBuffer("");
        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {
                int Id=cursor.getInt(cursor.getColumnIndex("id"));
                int loyal=cursor.getInt(cursor.getColumnIndex("loyal"));
                String exp=cursor.getString(cursor.getColumnIndex("exp"));
                sb.append(" id:"+Id+" 忠诚:"+loyal+" 经验:"+exp+"\n");
            }
            cursor.close();
        }
        return sb.toString();
    }

    private String queryToastDisplay(Cursor cursor)
    {
        StringBuffer sb=new StringBuffer("");
        if(cursor!=null)
        {
            while (cursor.moveToNext())
            {
                int Id=cursor.getInt(cursor.getColumnIndex("id"));
                int force=cursor.getInt(cursor.getColumnIndex("force"));
                String name=cursor.getString(cursor.getColumnIndex("name"));
                sb.append(" id:"+Id+" 姓名:"+name+" 武力:"+force+"\n");
            }
            cursor.close();

        }
      return sb.toString();
    }
private boolean IsInvaild(String sb)
{
    boolean just=false;
    try
    {
        Integer.valueOf(sb);
        just=true;
    }catch (Exception e)
    {
        Toast.makeText(getBaseContext(),"你输入的"+sb+"数据不合法",Toast.LENGTH_SHORT).show();
    }
    return just;
}
    }

