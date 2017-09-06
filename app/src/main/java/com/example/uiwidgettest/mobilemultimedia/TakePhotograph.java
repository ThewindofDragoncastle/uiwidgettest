package com.example.uiwidgettest.mobilemultimedia;
//拍照并显示出来  布局为按钮在图片
import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.uiwidgettest.R;
import com.example.uiwidgettest.news.Content;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public class TakePhotograph extends AppCompatActivity {
    ImageView Photo;
  static Uri imageuri=null;
    private void takep()
    {
        File PhotoView;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.d("TakePhoto:", "内存卡存在");
            PhotoView = new File(getExternalFilesDir("Candoeverything"), "开发软件摄影.png");
        } else {
            Log.d("TakePhoto:", "内存卡不存在");
            PhotoView = new File(getCacheDir(), "开发软件摄影.png");
        }
        Boolean IsSuccess = false;
        try {
            if (PhotoView.exists()) {
                IsSuccess = PhotoView.delete();
                Log.d("TakePhoto:", "删除是否成功:" + IsSuccess);
            }
            IsSuccess = false;
            IsSuccess = PhotoView.createNewFile();
            Log.d("TakePhoto:", "文件创建是否成功:" + IsSuccess);
        } catch (IOException e) {
            Log.d("TakePhoto", "文件创建发生错误");
        }
        if (Build.VERSION.SDK_INT >= 24)//安卓7.0 必须封装Uri
        {//必须使用内容提供器
            imageuri = FileProvider.getUriForFile(TakePhotograph.this,
                    "com.example.uiwidgettest.fileprovider", PhotoView);
        } else {
            imageuri = Uri.fromFile(PhotoView);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageuri);
        Log.d("TakePhoto:", "文件uri：" + imageuri);
        startActivityForResult(intent, 1);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photograph);
        Button button=(Button)findViewById(R.id.TakePhoto);
        Photo=(ImageView)findViewById(R.id.Photograph);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getBaseContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED) {
             takep();
                }
                else ActivityCompat.requestPermissions(TakePhotograph.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},3);
            }
            });
        Button ChooseFromAlbum=(Button)findViewById(R.id.ChooseFromAlbum);
        ChooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                    if (ContextCompat.checkSelfPermission(TakePhotograph.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        OpenAlbum();
                    } else
                        ActivityCompat.requestPermissions(TakePhotograph.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case 2:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                OpenAlbum();
                else
                    Toast.makeText(getBaseContext(),"你拒绝了授权。",Toast.LENGTH_SHORT).show();
                break;
            case 3:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    takep();
                else
                    Toast.makeText(getBaseContext(),"你拒绝了授权。",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void  OpenAlbum()
{
    Intent intent=new Intent("android.intent.action.GET_CONTENT");
    intent.setType("image/*");
    startActivityForResult(intent,2);
}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode)
        {
            case 1:
                if(resultCode==RESULT_OK) {
                    try {
                        Log.d("TakePhoto:", imageuri+" ");
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageuri));
                        Photo.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.d("TakePhoto:", "文件未发现");
                    }
                    catch (NullPointerException e1) {
                        Log.d("TakePhoto:", "空指针异常。");
                        Toast.makeText(getBaseContext(),"图片路径出错！",Toast.LENGTH_SHORT).show();
                    }
                }
            break;
            case 2:
                if(resultCode==RESULT_OK)
                {
                    if(Build.VERSION.SDK_INT<=19)
                    {
                        imagesmall19(data);
                    }
                    else
                    {
                        imagelarge19(data);
                    }
    }
        }
    }
    private void imagelarge19(Intent data)
    {
        Uri uri=data.getData();
        String ImagePath=null;
    if(uri.getScheme().equalsIgnoreCase("content"))
   {
       ImagePath=getImagePath(uri,null);
    Log.d("TakePhoto:","URI类型为：content");
  } else if (uri.getScheme().equalsIgnoreCase("file"))
  {
    ImagePath=uri.getPath();
      Log.d("TakePhoto:","URI类型为：content");
  }
  else if(DocumentsContract.isDocumentUri(getBaseContext(),uri))
  //系统认为api不能小于19，前面代码大于才能执行
    {
        String docid=DocumentsContract.getDocumentId(uri);
        if(uri.getAuthority().equals("com.android.providers.media.documents"))
        {
            String id=docid.split(":")[1];
            String selection=MediaStore.Images.Media._ID+"="+id;
            ImagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            Log.d("TakePhoto:","URI类型为：Document.Media");
        }
            else if (uri.getAuthority().equals("com.android.providers.downloads.documents"))
    {
        Uri contentUri= ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docid));
        ImagePath=getImagePath(contentUri,null);
        Log.d("TakePhoto:","URI类型为：Document.Download");
    }

    }
        displayimage(ImagePath);

}

    private void imagesmall19(Intent intent)
    {
        Uri uri=intent.getData();
        String imagepath=getImagePath(uri,null);
        displayimage(imagepath);
    }
    private String getImagePath(Uri uri,String sele)
    {
        String path=null;
        Cursor cursor=getContentResolver().query(uri,null,sele,null,null);
        if(cursor!=null)
        {
            if(cursor.moveToFirst())
            {
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    private void displayimage(String imagepath)
    {
        if(imagepath!=null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
            Photo.setImageBitmap(bitmap);
        }
        else
            Toast.makeText(getBaseContext(),"找不到文件！",Toast.LENGTH_SHORT).show();
    }
}
