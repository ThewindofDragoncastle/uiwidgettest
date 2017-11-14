package com.example.uiwidgettest.designpattern.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.designpattern.support.buildermode.Computer;
import com.example.uiwidgettest.designpattern.support.buildermode.Direcror;
import com.example.uiwidgettest.designpattern.support.buildermode.Merchant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 40774 on 2017/11/6.
 */

public class BuilderMode extends Fragment implements DialogInterface.OnClickListener{
    private final String TAG=getClass().getName();
    private String[] computers={"华硕","联想"
            ,"惠普","苹果"};
    private String[] cpu={"Intel 酷睿i5  参考报价：￥1399" ,
            "AMD Ryzen 5 参考报价：￥1299" ,
            "Intel 酷睿i7-8 参考报价：￥3399" ,
            "AMD Ryzen 7参考报价：￥2199"};
    private String[] mainboard={"技嘉 Z270 参考报价：￥2699", "华硕 B250 参考报价：￥729",
"微星 B350 参考报价：￥669", "七彩虹 Z270 参考报价：￥1499",
"映泰 970 参考报价：￥469"};
    private String[] Ram={"金邦 千禧条 参考报价：￥350",
            "金士顿 FURY 参考报价：￥949",
            "影驰 GAMER 参考报价：￥439",
            "英睿达16GB DDR3 1600 参考报价：￥1299",
            "瑞势 天狼 参考报价：￥399"};
//    默认数组下标是0
    private int index=0;
//    确认当前是在which选择界面
    private int choicepage=0;
    private final int CHOICE_COMPUTER=0;
    private final int CHOICE_CPU=1;
    private final int CHOICE_MAINBOARD=2;
    private final int CHOICE_RAM=3;
//    电脑信息
private String mycomputer=null;
    private String mycpu=null;
    private String mymainboard=null;
    private String myRam=null;

//    展示信息；
    private String info=null;
    @BindView(R.id.displaytext)
    TextView display;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.builder_pattern,container,false);
        ButterKnife.bind(this,view);
       return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        display.setText(info);
        super.onActivityCreated(savedInstanceState);
    }

    @OnClick(R.id.currentcomputer)
    public void OnclickCurrentcomputer()
    {
        info="电脑品牌："+mycomputer+"\n处理器  ："+mycpu+"\n主板    ："+mymainboard
                +"\n内存    ："+myRam;
        display.setText(info);
    }
    @OnClick(R.id.choicecomputer)
    public void Onclickchoicecomputer()
    {
        setAlert("选择电脑品牌",computers,null);
    }

    @Override
    public void onClick(final DialogInterface dialog, int which) {
        MyLog.d(TAG,"which:"+which);
       if(which==-1)
       {
           switch (choicepage) {
               case CHOICE_COMPUTER:
                   mycomputer=computers[index];
                   setAlert("请选择CPU", cpu,null);
                   break;
               case CHOICE_CPU:
                   mycpu=cpu[index];
                   setAlert("请选择主板",mainboard,null);
                   break;
               case CHOICE_MAINBOARD:
                   mymainboard=mainboard[index];
                   setAlert("请选择内存", Ram,null);
                   break;
               case CHOICE_RAM:
                   myRam=Ram[index];
                   info="电脑品牌："+mycomputer+"\n处理器  ："+mycpu+"\n主板    ："+mymainboard
                           +"\n内存    ："+myRam;
                   setAlert("请确认信息", null,info);
                   break;
               default:
                   break;
           }
           choicepage++;
       }
       else if (which==-2)
       {
//           当前页面减去一位就是前一个页面该显示的东西
           choicepage--;
           index=0;
           switch (choicepage) {
//               不按确定不选择品牌 但是指数得清零
               case CHOICE_COMPUTER:
                   setAlert("选择电脑品牌",computers,null);
                   break;
               case CHOICE_CPU:
                   setAlert("请选择CPU", cpu,null);
                   break;
               case CHOICE_MAINBOARD:
                   setAlert("请选择主板",mainboard,null);
                   break;
               case CHOICE_RAM:
                   setAlert("请选择内存", Ram,null);
                   break;
               default:
                   break;
           }
       }
       else if (which>=0)
       {
           index=which;
       }
    }
    private void setAlert(String title,String[] item,String Message)
    {
        AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
        builder.setTitle(title);
        if (item!=null) {
            builder.setSingleChoiceItems(item, 0, this);
            builder.setPositiveButton("下一步", this);
        }
        else {
            builder.setMessage(Message);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    达到最终页面清除数据
                    choicepage=CHOICE_COMPUTER;
//                    刷新显示
                    info="电脑品牌："+mycomputer+"\n处理器  ："+mycpu+"\n主板    ："+mymainboard
                            +"\n内存    ："+myRam;
//                    自己的组装商人
                    Merchant merchant=new Merchant();
//                    商人的组装规范
                    Direcror direcror=new Direcror(merchant);
                    Computer computer=direcror.createComputer(mycpu,mymainboard,myRam);
                    display.setText(info+"\n建造者模式数据："+
                            "\n处理器  ："+computer.getmCpu()+"\n主板    ："+computer.getmMainboard()
                            +"\n内存    ："+computer.getmRam());
                }
            });
        }
                builder.setNegativeButton("上一步",this);
        builder.show();
    }

}
