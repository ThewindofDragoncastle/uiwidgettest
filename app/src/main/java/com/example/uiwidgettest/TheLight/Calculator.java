package com.example.uiwidgettest.TheLight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.uiwidgettest.R;
import com.example.uiwidgettest.ui.Sendorrecieve.Intact;

public class Calculator extends AppCompatActivity implements View.OnClickListener {

    Button b1;
    Button b2;
    Button b3;
    Button b4;
    Button b5;
    Button b6;
    Button b7;
    Button b8;
    Button b9;
    Button b0;
    Button badd;
    Button bsub;
    Button badv;
    Button bmut;
    Button equal;
    Button clear;
    TextView display;
    int a=0;
    int b=0;
    int c=0;
    int account=0;
    int value=0;
    final int ADD=1;
    final int SUB=2;
    final int MUT=3;
    final int ADV=4;
    StringBuffer sb=new StringBuffer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        setOnClick();
    }

    public void setOnClick() {
        display=(TextView)findViewById(R.id.calculatordisplay);
        b1=(Button)findViewById(R.id.calculator1);
        b2=(Button)findViewById(R.id.calculator2);
        b3=(Button)findViewById(R.id.calculator3);
        b4=(Button)findViewById(R.id.calculator4);
        b5=(Button)findViewById(R.id.calculator5);
        b6=(Button)findViewById(R.id.calculator6);
        b7=(Button)findViewById(R.id.calculator7);
        b8=(Button)findViewById(R.id.calculator8);
        b9=(Button)findViewById(R.id.calculator9);
        b0=(Button)findViewById(R.id.calculator0);
        badd=(Button)findViewById(R.id.calculatorand);
        bsub=(Button)findViewById(R.id.calculatorsub);
        bmut=(Button)findViewById(R.id.calculatormulti);
        badv=(Button)findViewById(R.id.calculatoradv);
        equal=(Button)findViewById(R.id.calculatorequal);
        clear=(Button)findViewById(R.id.calculatorclear);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);
        b0.setOnClickListener(this);
        badv.setOnClickListener(this);
        badd.setOnClickListener(this);
        bsub.setOnClickListener(this);
        bmut.setOnClickListener(this);
        equal.setOnClickListener(this);
        clear.setOnClickListener(this);
    }
    public void seta(int c1)
    {

        if(value==0) {
            a = c1 + a* 10;
            sb.replace(0, sb.length(), a + "");
            display.setText(sb.toString());
        }
        else
        {
            b = c1 + b * 10;
            if(value==ADD) {
                sb.replace(0, sb.length(), a + "+" + b);
                display.setText(sb.toString());
            }
        if(value==ADV) {
            sb.replace(0,sb.length(),a+"÷"+b);
            display.setText(sb.toString());
        }
        if(value==MUT) {
            sb.replace(0,sb.length(),a+"×"+b);
            display.setText(sb.toString());
        }
        if(value==SUB) {
            sb.replace(0,sb.length(),a+"-"+b);
            display.setText(sb.toString());
        }
        }
    }
    public void setb(int value)
    {
        beforeSetb();
        this.value=value;
        account=0;

            if (value == ADD) {
                sb.replace(0, sb.length(), a + "+");
                display.setText(sb.toString());
            }
            if (value == ADV) {

                        sb.replace(0, sb.length(), a + "÷");
                        display.setText(sb.toString());



            }
            if (value == MUT) {

                sb.replace(0, sb.length(), a + "×");
                display.setText(sb.toString());
            }
            if (value == SUB) {

                sb.replace(0, sb.length(), a + "-");
                display.setText(sb.toString());
            }
      b=0;
    }
    public  void beforeSetb()
    {
        if (value == ADD) {
            if(b!=0)
            {
                a=b+a;
            }
        }
        if (value == ADV) {
            if(b!=0)
            {
                try {
                    a = a/b;
                }catch (ArithmeticException e)
                {
                    display.setText("你输入的数据不合法！");
                }
            }

        }
        if (value == MUT) {
            if(b!=0)
            {
                a=b*a;
            }
        }
        if (value == SUB) {
            if(b!=0)
            {
                a=a-b;
            }
        }
    }
    public void clearAll()
    {
        a=0;
        b=0;
        value=0;
        display.setText("");
    }
    public void clearAllbutnoText()
    {
        a=c;
        b=0;
        value=0;
        sb.setLength(0);
    }

    public void Equal()
    {
      if(value==ADD) {
          c=a+b;
          display.setText("计算值为：" + a +"+"+ b+"="+c);
      }
        if(value==ADV) {
            try {
                double c=a/(double)b;
                display.setText("计算值为：" + a +"÷"+ b+"="+c);
            }catch (ArithmeticException e)
            {
                display.setText("你输入的数据不合法！");
            }


        }
        if(value==MUT) {
            c=a*b;
            display.setText("计算值为：" + a +"×"+ b+"="+c);
        }
        if(value==SUB) {
            c=a-b;
            display.setText("计算值为：" + a +"-"+ b+"="+c);
        }
        clearAllbutnoText();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.calculator1:seta(1);break;
            case R.id.calculator2:seta(2);break;
            case R.id.calculator3:seta(3);break;
            case R.id.calculator4:seta(4);break;
            case R.id.calculator5:seta(5);break;
            case R.id.calculator6:seta(6);break;
            case R.id.calculator7:seta(7);break;
            case R.id.calculator8:seta(8);break;
            case R.id.calculator9:seta(9);break;
            case R.id.calculator0:seta(0);break;
            case R.id.calculatorand:setb(ADD);break;
            case R.id.calculatorsub:setb(SUB);break;
            case R.id.calculatormulti:setb(MUT);break;
            case R.id.calculatoradv:setb(ADV);break;
            case R.id.calculatorequal:Equal();break;
            case R.id.calculatorclear:clearAll();break;
        }
    }
}
