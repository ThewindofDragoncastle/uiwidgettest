package com.example.uiwidgettest.mvpupdatechat.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.mvpupdatechat.presenter.login.MedLoginPresenter;
import com.example.uiwidgettest.mvpupdatechat.presenter.PresenterContactLogin;
import com.example.uiwidgettest.mvpupdatechat.view.activity.CurrentData;
import com.example.uiwidgettest.mvpupdatechat.view.activity.MainPage;
import com.example.uiwidgettest.mvpupdatechat.view.activity.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 40774 on 2017/10/14.
 */

public class Login extends Fragment implements PresenterContactLogin.UpView{
    @BindView(R.id.eyes)
   public Button eye;
    @BindView(R.id.passwordinput)
    public EditText passwordinput;
    //账户编辑文本
    @BindView(R.id.accountinput)
    public EditText accountinput;
    //图片按钮Signin
    @BindView(R.id. Signin)
    public ImageView Signin;
    //“管理者”单选框
    @BindView(R.id.manger)
    public RadioButton Manger;
    //“学生”单选框
    @BindView(R.id.student)
    public RadioButton Student;
    //选择框记住密码
    @BindView(R.id.isrememberpassword)
    public CheckBox Rememberpassword;
    //登录按钮
    @BindView(R.id.login)
    public Button Login;
    //忘记密码按钮
    @BindView(R.id.isunremember)
    public Button IsunRemember;
    //是否可视密码
    private boolean Cansee=false;
    //引入碎片
    //将返回键隐藏
    @BindView(R.id.toolbarback)
    public Button back;

    private  View v;
    private MedLoginPresenter basePresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.login,container,false);
        ButterKnife.bind(this,v);
        back=(Button) v.findViewById(R.id.toolbarback);
        back.setVisibility(View.GONE);
        basePresenter= MedLoginPresenter.getInstance();
        basePresenter.setViewInterface(this);
        basePresenter.setUpStatusCallback(basePresenter);
        basePresenter.setCommService(basePresenter);
        basePresenter.start();
        MyLog.d("Login:","视图被创建");
        return v;
    }


    //固定操作 显示密码 点击登录前查看勾选信息
    @OnClick(R.id.eyes)
    public void DispalyPassword()
    {
        //点击眼睛是否显示密码
        if(Cansee) {
            //如果可见设为密码不可见
            passwordinput.setTransformationMethod(PasswordTransformationMethod.getInstance());
            passwordinput.setSelection(passwordinput.getText().length());
            Cansee=false;
            eye.setBackgroundResource(R.drawable.eye);
        }
        else
        {
            passwordinput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            passwordinput.setSelection(passwordinput.getText().length());
            Cansee=true;
            eye.setBackgroundResource(R.drawable.closeeye);
        }
    }
    @OnClick(R.id.login)
    public void RiadioChecked()
    {
        //如果单选框，未选择，弹出，警示
        if(Manger.isChecked()||Student.isChecked())
        {
            //进入下一目标
            basePresenter.sendtoServer(accountinput.getText().toString()
                    ,passwordinput.getText().toString());
        }
        else
        {
            new AlertDialog.Builder(getActivity())
                    .setMessage(" 请先选择身份。")
                    .setTitle("选择身份")
                    .setIcon(R.drawable.completexiaohui)
                    .setPositiveButton("确认",null)
                    .show();
        }
    }

    @Override
    public void IntoIntact() {
        MainPage page=(MainPage)getActivity();
        Utils.replament(page.getSupportFragmentManager(),FragmentCollector.GetIntact(),this);
    }

    @Override
    public void savePassword(String filename) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(filename, Context.MODE_PRIVATE).edit();
        //登录成功保存密码
        if(Rememberpassword.isChecked()) {
            editor.putString("account",accountinput.getText().toString() );
            editor.putString("password", passwordinput.getText().toString());
            editor.putBoolean("isremeber",true);
        }
        else
        {
            //如果没有选就把密码清空
            editor.putString("account", "");
            editor.putString("password","");
            editor.putBoolean("isremeber",false);
        }
        editor.apply();

    }

    @Override
    public void setCurrentOnServer() {
        //登录成功在服务器设置当前帐户 本来可以直接设 但在碎片处理逻辑更清楚
        basePresenter.setCurrentAccount(accountinput.getText().toString());
    }

    @Override
    public void showPasswordError() {
        accountinput.setError("密码错误");
        passwordinput.setText("");
    }

    @Override
    public void getSave(String filename) {
        SharedPreferences preferences=
                getActivity().getSharedPreferences(filename,Context.MODE_PRIVATE);
        Rememberpassword.setChecked(preferences.getBoolean("isremeber",false));
        accountinput.setText(preferences.getString("account",""));
        passwordinput.setText(preferences.getString("password",""));
    }
    @Override
    public void onResume() {
        //活动创建时，把碎片加入CurrentData 以方便其他类判断当前处于那个界面
        CurrentData.setCurrentfragment(this);
        super.onResume();
    }
}
