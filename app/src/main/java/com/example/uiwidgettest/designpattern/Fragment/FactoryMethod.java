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
import com.example.uiwidgettest.designpattern.support.factorymethod.ComputerFactory;
import com.example.uiwidgettest.designpattern.support.simfactory.AsusComputer;
import com.example.uiwidgettest.designpattern.support.simfactory.Computer;
import com.example.uiwidgettest.designpattern.support.simfactory.Factory;
import com.example.uiwidgettest.designpattern.support.simfactory.HpComputer;
import com.example.uiwidgettest.designpattern.support.simfactory.LenovoComputer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 40774 on 2017/11/5.
 */

public class FactoryMethod extends Fragment{
//    工厂方法模式
//    简单工厂模式
private final String INTRODUCE="Class分为：" +
        "Factory工厂父类,Factory类，产品类（3个子类），" +
        "调用碎片。" +
        "特征：增加新电脑需要不需要修改父类，新建一个类，再传入Class就可以";
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
        hp.setText("HpComputer.class");
        lenovo.setText("LenovoComputer.class");
        asus.setText("AsusComputer.class");
        mytext.setText(INTRODUCE);
    }
    @OnClick(R.id.confirm)
    public void onClickConfirm()
    {
        com.example.uiwidgettest.designpattern.support.factorymethod.Factory factory=
                new com.example.uiwidgettest.designpattern.support.factorymethod.Factory();
        Computer computer=null;
        if(lenovo.isChecked())
          computer=factory.getComputer(LenovoComputer.class);
        else if(hp.isChecked())
            computer=factory.getComputer(HpComputer.class);
        else if(asus.isChecked())
            computer=factory.getComputer(AsusComputer.class);
        else
            Toast.makeText(getContext(),"请先勾选电脑型号",Toast.LENGTH_SHORT).show();
        if (computer!=null) {
            mytext.setText(computer.start());
        }
    }
}
