package com.example.uiwidgettest.myreview.media;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.myreview.DisplayData;
import com.example.uiwidgettest.myreview.contentprovide.CutFragment;

import org.litepal.tablemanager.Generator;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 40774 on 2017/10/6.
 */

public class TakeSelectPhoto extends Fragment implements CutFragment,View.OnClickListener
{
    //碎片拍照选择图片
    private final int TAKE_PHOTO=1;
    private final int SELECT_PHOTO=2;
    private TextView display;
    private ImageView photo;
    private final String INTRODUCE="相片存储地址：SD卡//开发专用文件夹//我的照片\n" +
            "照片格式:照片当前时间的毫秒数.jpg\n" +
            "执行过程：预先请求权限，如有权限进行判断SDK版本，大于或等于7.0，使用文件提供器获取FILEURI" +
            "低于7.0直接获取URI，启动相机。等待返回结果，如果结果是RESULT__OK。使用GLIDE加载图片。\n" +
            "选择图片隐式启动传入type（image/*）相册，选择图片，传回URI。";
    File file1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.takephoto,container,false);
        display=(TextView)view.findViewById(R.id.display);
        display.setText(INTRODUCE);
        Button takephoto=(Button)view.findViewById(R.id.takephoto);
        takephoto.setOnClickListener(this);
        Button selectphoto=(Button)view.findViewById(R.id.selectphoto);
        photo=(ImageView)view.findViewById(R.id.myphoto);
        selectphoto.setOnClickListener(this);
        Button hide=(Button)view.findViewById(R.id.hide);
        hide.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DisplayData displayData=(DisplayData) getActivity();
    }

    @Override
    public void Cut(Fragment Remove, Fragment fragment) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.takephoto:takephoto();break;
            case R.id.selectphoto:
                selectPhoto();
                break;
            case R.id.hide:
                //textview 有两个父布局
                ViewGroup parent=(ViewGroup) display.getParent();
                ViewGroup parent1=(ViewGroup) parent.getParent();
                parent1.removeAllViews();
                break;
        }
    }
    private void selectPhoto()
    {
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,SELECT_PHOTO);
    }
    private void takephoto()
    {
            File file=new File(Environment.getExternalStorageDirectory()+File.separator
                    +"开发专用文件夹"+File.separator+"我的照片");
            if(!file.exists())
                file.mkdirs();
           file1=new File(Environment.getExternalStorageDirectory()+File.separator
                    +"开发专用文件夹"+File.separator+"我的照片"+File.separator+
            "照片"+System.currentTimeMillis()+".jpg");
            try {
                if(!file1.exists())
                file1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
           Uri ImageUri=null;
            if(Build.VERSION.SDK_INT<24) {
                MyLog.d("TakeSelectPhoto:","当前版本低于7.0");
                ImageUri = Uri.fromFile(file1);
            }
            else {
                MyLog.d("TakeSelectPhoto:", "当前版本高于7.0");
                ImageUri = FileProvider.getUriForFile(getContext(), "com.example.uiwidgettest.fileprovider", file1);
            }
            Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT,ImageUri);//输出路径
            startActivityForResult(intent,TAKE_PHOTO);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case TAKE_PHOTO:
                if(resultCode==RESULT_OK)
                    Glide.with(this).load(file1).into(photo);
                break;
            case SELECT_PHOTO:

                if(resultCode==RESULT_OK)
                {
                    Uri uri=data.getData();
                    Glide.with(this).load(uri).into(photo);
                }

                break;
        }
    }
}
