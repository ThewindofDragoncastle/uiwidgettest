package com.example.uiwidgettest.designpattern.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uiwidgettest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.example.uiwidgettest.designpattern.support.simfactory.*;
/**
 * Created by 40774 on 2017/11/3.
 */

public class FactoryMode extends Fragment {
//    简单工厂模式
    private final String INTRODUCE="Class分为：" +
        "Factory类，产品类（1个抽象父类，3个抽象子类），" +
        "调用碎片。" +
        "特征：增加新电脑需要修改父类";
    @BindView(R.id.displaytext)
    TextView mytext;
    @BindView(R.id.lenovo)
    RadioButton lenovo;
    @BindView(R.id.Asus)
    RadioButton asus;
    @BindView(R.id.Hp)
    RadioButton hp;
    @BindView(R.id.confirm)
    Button confirm;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.factorymode,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mytext.setText(INTRODUCE);
    }
    @OnClick(R.id.confirm)
    public void onClickConfirm()
    {
        String computername=null;
        if(lenovo.isChecked())
            computername=lenovo.getText().toString();
        else if(hp.isChecked())
            computername=hp.getText().toString();
        else if(asus.isChecked())
            computername=asus.getText().toString();
        else
            Toast.makeText(getContext(),"请先勾选电脑型号",Toast.LENGTH_SHORT).show();
        if (computername!=null) {
            Computer computer = Factory.getComputer(computername);
            mytext.setText(computer.start());
        }
    }
}
