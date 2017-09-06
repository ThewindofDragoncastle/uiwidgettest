package com.example.uiwidgettest.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uiwidgettest.R;

import java.util.ArrayList;

public class Main4Activity extends AppCompatActivity {
    ArrayList<Hero> al=new ArrayList<Hero>();
    ImageView iv;
    TextView tv;
    ListView lv;
    static int index=-2;
    static int index1=-2;
    static boolean index2=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        setzerohero();
        final heroAdapter ad=new heroAdapter(Main4Activity.this,R.layout.hero,al);
        lv=(ListView)findViewById(R.id.Lv1);
        lv.setAdapter(ad);
        lv.setOnItemClickListener(
        new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Hero hero=al.get(position);
//             Log.d("index ",index+" index1 "+index1+"position "+position);
                    if(index!=index1&&index!=position) {
                        Hero hero1 = al.get(index);
                        al.remove(index);
                        switch (hero1.getImage()) {
                            case R.drawable.kynls:
                                hero1.setImage(R.drawable.kynls1);
                                break;
                            case R.drawable.wjjs:
                                hero1.setImage(R.drawable.wjjs1);
                                break;
                            case R.drawable.lbfx:
                                hero1.setImage(R.drawable.lbfx1);
                                break;
                            case R.drawable.ltpx:
                                hero1.setImage(R.drawable.ltpx1);
                                break;
                            case R.drawable.nr:
                                hero1.setImage(R.drawable.nr1);
                                break;
                            case R.drawable.ntqz:
                                hero1.setImage(R.drawable.ntqz1);
                                break;
                            case R.drawable.sjck:
                                hero1.setImage(R.drawable.sjck1);
                                break;
                            case R.drawable.sjlr:
                                hero1.setImage(R.drawable.sjlr1);
                                break;
                            case R.drawable.smtf:
                                hero1.setImage(R.drawable.smtf1);
                                break;
                            case R.drawable.tkmh:
                                hero1.setImage(R.drawable.tkmh1);
                                break;
                            case R.drawable.zyzl:
                                hero1.setImage(R.drawable.zyzl1);
                                break;
                            case R.drawable.zznh:
                                hero1.setImage(R.drawable.zznh1);
                                break;
                            case R.drawable.xkzy:
                                hero1.setImage(R.drawable.xkzy1);
                                break;
                        }

                         al.add(index,hero1);
                    }
                 index1 = -3 ;
                 index = position;
//                iv.setImageResource(R.drawable.kynls1);


                  al.remove(position);
                    switch (hero.getImage()) {
                        case R.drawable.kynls:
                            hero.setImage(R.drawable.kynls1);
                            hero.setName("狂野的美洲狮，无惧一切敌人");
                            break;
                        case R.drawable.lbfx:
                            hero.setImage(R.drawable.lbfx1);
                            break;
                        case R.drawable.ltpx:
                            hero.setImage(R.drawable.ltpx1);
                            break;
                        case R.drawable.nr:
                            hero.setImage(R.drawable.nr1);
                            break;
                        case R.drawable.ntqz:
                            hero.setImage(R.drawable.ntqz1);
                            break;
                        case R.drawable.sjck:
                            hero.setImage(R.drawable.sjck1);
                            break;
                        case R.drawable.sjlr:
                            hero.setImage(R.drawable.sjlr1);
                            break;
                        case R.drawable.smtf:
                            hero.setImage(R.drawable.smtf1);
                            break;
                        case R.drawable.tkmh:
                            hero.setImage(R.drawable.tkmh1);
                            break;
                        case R.drawable.zyzl:
                            hero.setImage(R.drawable.zyzl1);
                            break;
                        case R.drawable.zznh:
                            hero.setImage(R.drawable.zznh1);
                            break;
                        case R.drawable.xkzy:
                            hero.setImage(R.drawable.xkzy1);
                            break;
                        case R.drawable.wjjs:
                            hero.setImage(R.drawable.wjjs1);
                            break;
                            case R.drawable.kynls1:
                                hero.setImage(R.drawable.kynls);
                                hero.setName("狂野女猎手");
                                break;
                            case R.drawable.wjjs1:
                                hero.setImage(R.drawable.wjjs);
                                break;
                            case R.drawable.lbfx1:
                                hero.setImage(R.drawable.lbfx);
                                break;
                            case R.drawable.ltpx1:
                                hero.setImage(R.drawable.ltpx);
                                break;
                            case R.drawable.nr1:
                                hero.setImage(R.drawable.nr);
                                break;
                            case R.drawable.ntqz1:
                                hero.setImage(R.drawable.ntqz);
                                break;
                            case R.drawable.sjck1:
                                hero.setImage(R.drawable.sjck);
                                break;
                            case R.drawable.sjlr1:
                                hero.setImage(R.drawable.sjlr);
                                break;
                            case R.drawable.smtf1:
                                hero.setImage(R.drawable.smtf);
                                break;
                            case R.drawable.tkmh1:
                                hero.setImage(R.drawable.tkmh);
                                break;
                            case R.drawable.zyzl1:
                                hero.setImage(R.drawable.zyzl);
                                break;
                            case R.drawable.zznh1:
                                hero.setImage(R.drawable.zznh);
                                break;
                            case R.drawable.xkzy1:
                                hero.setImage(R.drawable.xkzy);
                                break;
                    }

              al.add(position,hero);

//                lv.setAdapter(ad);
                ad.notifyDataSetChanged();
//                view.postInvalidate();
//                lv.setSelection(position-1);
//                lv.requestLayout();
                Toast.makeText(Main4Activity.this,hero.getName(),Toast.LENGTH_SHORT).show();
              if (position==5||hero.getName().equals("牛头酋长")) {

                 AlertDialog.Builder ad=new  AlertDialog.Builder(Main4Activity.this);
                  ad.setCancelable(false);
                  ad.setTitle("英雄选择");
                  ad.setMessage("你确定要选择"+hero.getName()+"?");
                  ad.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          Intent it = new Intent(Main4Activity.this, Main5Activity.class);
                          ProgressDialog pd = new ProgressDialog(Main4Activity.this);
                          pd.setTitle("加载中");
                          pd.setMessage("请等待..");
                          pd.show();
                          pd.dismiss();
                          startActivity(it);
                      }
                  });
                  ad.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {

                      }
                  });
                  ad.show();
            }
            }
        }
);
    }
    public void setzerohero()
    {

        al.add(new Hero(R.drawable.wjjs1,"无极剑圣"));
        al.add(new Hero(R.drawable.ltpx1,"雷霆咆哮"));
        al.add(new Hero(R.drawable.zyzl1,"赵云子龙"));
        al.add(new Hero(R.drawable.lbfx1,"吕布奉先"));
        al.add(new Hero(R.drawable.kynls1,"狂野女猎手"));
        al.add(new Hero(R.drawable.smtf1,"沙漠屠夫"));
        al.add(new Hero(R.drawable.sjck1,"时间刺客"));
        al.add(new Hero(R.drawable.tkmh1,"铁凯冥魂"));
        al.add(new Hero(R.drawable.nr1,"纳尔"));
        al.add(new Hero(R.drawable.sjlr1,"赏金猎人"));
        al.add(new Hero(R.drawable.xkzy1,"虚空恐惧"));
        al.add(new Hero(R.drawable.ntqz1,"牛头酋长"));
        al.add(new Hero(R.drawable.zznh1,"蜘蛛女皇"));


    }
}
