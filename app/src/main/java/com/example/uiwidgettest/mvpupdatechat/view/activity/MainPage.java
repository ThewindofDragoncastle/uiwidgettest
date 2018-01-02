package com.example.uiwidgettest.mvpupdatechat.view.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.uiwidgettest.BaseActivity;
import com.example.uiwidgettest.MainPage.FunctionIntegration;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.mvpupdatechat.model.service.ConnwithService;
import com.example.uiwidgettest.mvpupdatechat.model.service.RecieveData;

import com.example.uiwidgettest.mvpupdatechat.presenter.login.MedLoginPresenter;
import com.example.uiwidgettest.mvpupdatechat.view.fragment.Chat;
import com.example.uiwidgettest.mvpupdatechat.view.fragment.FragmentCollector;
import com.example.uiwidgettest.mvpupdatechat.view.fragment.Intact;
import com.example.uiwidgettest.mvpupdatechat.view.fragment.Login;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MainPage extends BaseActivity implements  com.example.uiwidgettest.mvpupdatechat.presenter.BaseActivity.ActivityView {
@BindView(R.id.fragmentplace)
FrameLayout frameLayout;
    //记下按下退出键的次数
    private int backcount=0;
    //服务
    private ProgressDialog mPrePareDialog;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    Intent intent;
    private ProgressDialog mdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        ButterKnife.bind(this);
        frameLayout.removeAllViews();
        Login loginfragment=(Login)getSupportFragmentManager().findFragmentById(R.id.fragmentplace);
        if(loginfragment==null) {
            loginfragment = FragmentCollector.GetLogin();
            Utils.addFragment(getSupportFragmentManager(),loginfragment,R.id.fragmentplace);
        }
        MedLoginPresenter.getInstance().setServiceInterface(this);
        intent=new Intent(this, RecieveData.class);
        mdialog=new ProgressDialog(this);
        mdialog.setTitle("登录中");
        mPrePareDialog=new ProgressDialog(this);
        mPrePareDialog.setCancelable(false);
        mPrePareDialog.setMessage("初始化未完成");
        builder=new AlertDialog.Builder(this)
                .setTitle("网络已经断开")
                .setPositiveButton("重新连接", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        unbindService();
                        stopService();
                        bindService();
                        startService();
                    }
                })
                .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
    }

    @Override
    public void bindService() {
           bindService(intent, ConnwithService.getConnection(),BIND_AUTO_CREATE);
    }

    @Override
    public void unbindService() {
           unbindService(ConnwithService.getConnection());
    }

    @Override
    public void startService() {
           startService(intent);
    }

    @Override
    public void stopService() {
            stopService(intent);
    }

    @Override
    public void onBackPressed() {
        Chat chat=FragmentCollector.GetChat();
        Login login= FragmentCollector.GetLogin();
        Intact intact=FragmentCollector.GetIntact();
        //在这一个地方找碎片
        Fragment fragment=getSupportFragmentManager().findFragmentById(R.id.fragmentplace);
           if(fragment.equals(login))
            {
                finish();
            }
            else if(fragment.equals(chat))
           {
               Utils.replament(getSupportFragmentManager(),intact,chat);
           }
            else if(fragment.equals( intact))
           {
               if(backcount==0) {
                   Toast.makeText(this, "再次按下退出键退出", Toast.LENGTH_SHORT).show();
                   backcount++;
               }
               else {
                   //摧毁活动没有意义，因为退出之后还必须服务接受消息
                   Intent intent=new Intent(MainPage.this,FunctionIntegration.class);
                   startActivity(intent);
                   backcount=0;
               }
           }
    }
    @Override
    public void ShowPrepareDialog(String title) {
        if(!mPrePareDialog.isShowing()) {
            mPrePareDialog.setTitle(title);
            mPrePareDialog.show();
        }
    }

    @Override
    public void HidePrepareDialog() {
        if(mPrePareDialog.isShowing())
            mPrePareDialog.dismiss();
    }
    @Override
    public void ShowNetErrorWarning(String title) {
        dialog=builder.show();
        if(!dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void HideNetWarning() {
        if(dialog.isShowing())
            dialog.dismiss();
    }
    @Override
    public void showErrorToast() {
        Toast.makeText(this,"网络故障",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void ShowProBar() {
        if(!mdialog.isShowing())
            mdialog.show();
    }

    @Override
    public void HideProBar() {
        if(mdialog.isShowing())
            mdialog.dismiss();
    }

}
