package com.example.uiwidgettest.designpattern.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.uiwidgettest.R;
import com.example.uiwidgettest.designpattern.Fragment.BuilderMode;
import com.example.uiwidgettest.designpattern.Fragment.FactoryMethod;
import com.example.uiwidgettest.designpattern.Fragment.FactoryMode;
import com.example.uiwidgettest.designpattern.support.DesignDataSupport;
import com.example.uiwidgettest.designpattern.supportinterface.Designpattern;

import org.litepal.crud.DataSupport;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DesignDisplayData extends AppCompatActivity
        implements Designpattern {
// 碎片参数
    private final int SINGLETON=0;
    private final int SIMPLEFACTORY=1;
    private final int FACTORYMETHOD=2;
    private final int BUIILDER_MODE=3;
//判断单击悬浮键后应该执行显示文本 还是实体操作
    private boolean IsText=true;
//    判断所处的模式
int FragmentParams=-1;
//所需要的碎片
    FactoryMode factoryMode;
    FactoryMethod factoryMethod;
    BuilderMode builderMode;

    @BindView(R.id.mytext)
    TextView mytext;
    @BindView(R.id.FrameViewGroup)
    FrameLayout frameLayout;
    @BindView(R.id.textfragment)
    FrameLayout textfragment;
    @BindView(R.id.floatactionbutton)
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_display_data);
        ButterKnife.bind(this);
        FragmentParams=getIntent().getIntExtra("FragmentParams",-1);
//        执行显示设置
        showFragment(FragmentParams);
//        所需碎片初始化
        factoryMode=new FactoryMode();
        factoryMethod=new FactoryMethod();
        builderMode=new BuilderMode();
    }
    private void showFragment(int FragmentParams)
    {
        switch (FragmentParams)
        {
            case -1:
                defaultMode();
                break;
            case SINGLETON:
                mytext.setText(DesignDataSupport.SINGLETON_INTRODUCE);
                break;
            case SIMPLEFACTORY:
                mytext.setText(DesignDataSupport.SIMPLEFACTORY_INTRODUCE);
                break;
            case FACTORYMETHOD:
                mytext.setText(DesignDataSupport.FACTORY_INTRODUCE);
                break;
            case BUIILDER_MODE:
                mytext.setText(DesignDataSupport.BUILDERMODE_INTRODUCE);
                break;
        }
    }
    @OnClick(R.id.floatactionbutton)
    public void onClickfab()
    {
        switch (FragmentParams)
        {
            case -1:
             defaultMode();
                break;
            case SINGLETON:
                singleTon();
                break;
            case SIMPLEFACTORY:
               simpleFactory();
                break;
            case FACTORYMETHOD:
                FactoryMethod();
                break;
            case BUIILDER_MODE:
                BuilderMode();
                break;
        }

    }
    private void clearAll()
    {
        frameLayout.removeView(textfragment);
//        frameLayout.removeView(mytext);
    }

    @Override
    public void defaultMode() {
        mytext.setText("传入数据失败");
    }

    @Override
    public void singleTon() {
        if(IsText)
        {
            mytext.setText("暂不可操作这种设计模式，原因：不适宜图形化展示。");
            IsText=false;
        }
        else
        {
            mytext.setText(DesignDataSupport.SINGLETON_INTRODUCE);
            IsText=true;
        }
    }

    @Override
    public void simpleFactory() {
       changeToFragment(factoryMode, DesignDataSupport.SIMPLEFACTORY_INTRODUCE);
    }

    @Override
    public void FactoryMethod() {
        changeToFragment(factoryMethod, DesignDataSupport.FACTORY_INTRODUCE);
    }

    @Override
    public void BuilderMode() {
        changeToFragment(builderMode,  DesignDataSupport.BUILDERMODE_INTRODUCE);
    }

    @Override
    public void changeToFragment(Fragment fragment,String text) {
        if(IsText)
        {
            if(!fragment.isAdded())
                addFragment(fragment);
            else
            {
                frameLayout.removeView(textfragment);
                frameLayout.addView(fragment.getView());
            }
            IsText=false;
        }
        else
        {
//            只保留悬浮键
            frameLayout.removeView(fragment.getView());
//            不能添加textview应该添加它的父类
            frameLayout.addView(textfragment);
            mytext.setText(text);
            IsText=true;
        }
    }

    private void replaceFragment(Fragment add,Fragment remove)
    {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.remove(remove);
        fragmentTransaction.replace(R.id.FrameViewGroup,add);
        fragmentTransaction.commit();
    }
    private void addFragment(Fragment add)
    {
        frameLayout.removeView(textfragment);
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.FrameViewGroup,add);
        fragmentTransaction.commit();
    }
}
