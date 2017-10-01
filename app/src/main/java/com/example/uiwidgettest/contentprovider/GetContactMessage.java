package com.example.uiwidgettest.contentprovider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.uiwidgettest.R;

import java.util.ArrayList;
import java.util.List;

public class GetContactMessage extends AppCompatActivity {
    ArrayAdapter<String> viewAdapter;
  List<String> message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_contact_message);
        message=new ArrayList<String>();
        ListView ContactView=(ListView)findViewById(R.id.contactlistveiw);
        viewAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,message);
        ContactView.setAdapter(viewAdapter);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)==
                PackageManager.PERMISSION_GRANTED)
        {
            readmessage();
        }
        else
        {
            ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.READ_CONTACTS},1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    readmessage();
                }
                else
                {
                    Toast.makeText(getBaseContext(),"你拒绝了授权！",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    private void readmessage()
    {
        Cursor cursor=null;
        try {
            cursor= getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null
                    , null, null, null);
            StringBuffer numbersb=new StringBuffer("");
            StringBuffer namesb=new StringBuffer("");
            if(cursor.moveToFirst())
            {
                do {
                    namesb.replace(0,namesb.length(),cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds
                            .Phone.DISPLAY_NAME)));
                    numbersb.replace(0,numbersb.length(),cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds
                            .Phone.NUMBER)));
                    message.add("姓名："+namesb.toString()+"\n电话号码："+numbersb.toString());
                    viewAdapter.notifyDataSetChanged();
                }while (cursor.moveToNext());
            }
//
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if(cursor!=null)
            cursor.close();
        }
    }
}
