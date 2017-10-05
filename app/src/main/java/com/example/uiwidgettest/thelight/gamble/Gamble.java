package com.example.uiwidgettest.thelight.gamble;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uiwidgettest.R;
import com.example.uiwidgettest.thelight.Calculator;
import com.example.uiwidgettest.thelight.cardview;
import com.example.uiwidgettest.thelight.tab.TabActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Gamble extends AppCompatActivity implements View.OnClickListener {

   private Intent intent;
    private EditText wei;
    private EditText shu;
    private EditText wu;
    private EditText convertmount;
    private int gold=0;
    private int silver=0;
    private int expgold=0;
    private int expsilver=0;
    private TextView display;
    private final String NAME="绝对痴杀" ;
    private String winner="无";
    private int i=0;
    private int countdown;
    private Random random=new Random();
    private int PlayerSupport=-1;
    private boolean Iswin=false;
    private int currentexpg=0;
    private int currentexps=0;

    private List<Player> players=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamble);
        Button gshu=(Button)findViewById(R.id.goldshu);
        gshu.setOnClickListener(this);
        Button gwei=(Button)findViewById(R.id.goldwei);
        gwei.setOnClickListener(this);
        Button gwu=(Button)findViewById(R.id.goldwu);
        gwu.setOnClickListener(this);
        Button sshu=(Button)findViewById(R.id.silvershu);
        sshu.setOnClickListener(this);
        Button swei=(Button)findViewById(R.id.silverwei);
        swei.setOnClickListener(this);
        Button swu=(Button)findViewById(R.id.silverwu);
        swu.setOnClickListener(this);
        Button convertG=(Button)findViewById(R.id.convertgold);
        convertG.setOnClickListener(this);
        Button convertS=(Button)findViewById(R.id.convertsilver);
        convertS.setOnClickListener(this);
        Button fraud=(Button)findViewById(R.id.fraud);
        fraud.setOnClickListener(this);
        wei=(EditText)findViewById(R.id.inputwei);
        shu=(EditText)findViewById(R.id.inputshu);
        wu=(EditText)findViewById(R.id.inputwu);
        convertmount=(EditText)findViewById(R.id.convertmount);
        display=(TextView)findViewById(R.id.gambledisplay);

        initPlayer();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    try {
                        i++;
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            countdown=20-i;
                            display.setText(NAME+"\n金："+gold+"\n银:"+silver+"\n\n上次的赢家为:"
                                            +winner+"\n"+
                                    "本次消耗金："+expgold+"  "+"银:"+expsilver+"\n"+
                                    "还有"+countdown+"秒决出胜负");
                            if(countdown==0)
                            {
                               switch (random.nextInt(3) )
                               {
                                   case 0:
                                       winner="魏";
                                       if (PlayerSupport==0)
                                           Iswin=true;
                                       break;
                                   case 1:
                                       winner="蜀";
                                       if (PlayerSupport==1)
                                           Iswin=true;
                                       break;
                                   case 2:
                                       winner="吴";
                                       if (PlayerSupport==2)
                                           Iswin=true;
                                       break;
                               }
                                i=0;
                                if(Iswin==true) {
                                    gold = gold + (int)(expgold * 2.9);
                                    silver=silver+(int)(expsilver * 2.9);
                                    PlayerSupport=-1;
                                    expgold=0;
                                    expsilver=0;
                                    Iswin=false;
                                    currentexpg=0;
                                    currentexps=0;
                                }
                                else
                                {
                                    PlayerSupport=-1;
                                    expgold=0;
                                    expsilver=0;
                                    currentexpg=0;
                                    currentexps=0;
                                }
                            }
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.goldwei:
                betG(wei,0);
                break;
            case R.id.goldshu:
                betG(shu,1);
                break;
            case R.id.goldwu:
                betG(wu,2);
                break;
            case R.id.silverwei:
                betS(wei,0);
                break;
            case R.id.silvershu:
                betS(shu,1);
                break;
            case R.id.silverwu:
                betS(wu,2);
                break;
            case R.id.fraud:
                gold=gold+1000;
                break;
            case R.id.convertgold:
                convertgold(Integer.valueOf(convertmount.getText().toString()));
                break;
            case R.id.convertsilver:
                convertsilver(Integer.valueOf(convertmount.getText().toString()));
                break;
        }
//        display.setText(NAME+"\n金："+gold+"\n银:"+silver);
    }
    private void initPlayer()
    {
        Player player=new Player("绝对痴杀");
        gold=player.getGold();
        silver=player.getSilver();
//        display.setText(NAME+"\n金："+gold+"\n银:"+silver);
    }
    public boolean IsGCan()
    {
        if(gold>=currentexpg)
        return true;
        else
        return false;
    }
    public boolean IsSCan()
    {
        if(silver>=currentexps)
            return true;
        else
            return false;
    }
    private void betG(EditText text,int code)
    {
        if (text.getText().toString().isEmpty()) {
            Toast.makeText(getBaseContext(), "请输入数据。", Toast.LENGTH_SHORT).show();
            return;
        }
        if(PlayerSupport==-1||PlayerSupport==code) {
        PlayerSupport=code;
        currentexpg=Integer.valueOf(text.getText().toString());
        expgold=currentexpg+expgold;
        if (IsGCan()) {
            gold = gold - currentexpg;
        }else {
            Toast.makeText(getBaseContext(),"你的金额不足。",Toast.LENGTH_SHORT).show();
            expgold = expgold - currentexpg;
        }
        }
        else
         Toast.makeText(getBaseContext(), "只能押一个国家", Toast.LENGTH_SHORT).show();
    }
    private void betS(EditText text,int code)
    {
        if (text.getText().toString().isEmpty()) {
            Toast.makeText(getBaseContext(), "请输入数据。", Toast.LENGTH_SHORT).show();
            return;
        }
        if(PlayerSupport==-1||PlayerSupport==code) {
            PlayerSupport = code;
            currentexps = Integer.valueOf(text.getText().toString());
            expsilver = currentexps + expsilver;
            if (IsSCan()) {
                silver = silver - currentexps ;
            } else {
                Toast.makeText(getBaseContext(), "你的金额不足。", Toast.LENGTH_SHORT).show();
                expsilver = expsilver - currentexps;
            }
        }
        else
            Toast.makeText(getBaseContext(), "只能押一个国家", Toast.LENGTH_SHORT).show();
    }
    private void convertgold(int mount)
    {
        if (convertmount.getText().toString().isEmpty()) {
            Toast.makeText(getBaseContext(), "请输入数据。", Toast.LENGTH_SHORT).show();
            return;
        }
        if(mount<=gold) {
            silver = mount * 500 + silver;
            gold = gold - mount;
        }
        else
            Toast.makeText(getBaseContext(), "金额不足", Toast.LENGTH_SHORT).show();
    }
    private void convertsilver(int mount)
    {
        if (convertmount.getText().toString().isEmpty()) {
            Toast.makeText(getBaseContext(), "请输入数据。", Toast.LENGTH_SHORT).show();
            return;
        }
        if(mount<=silver) {
            gold = (int)((double)mount / 500) + gold;
            silver = silver - mount;
        }
        else
            Toast.makeText(getBaseContext(), "金额不足", Toast.LENGTH_SHORT).show();
    }
}
