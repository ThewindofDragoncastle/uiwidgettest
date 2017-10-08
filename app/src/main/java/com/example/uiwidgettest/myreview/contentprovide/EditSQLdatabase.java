package com.example.uiwidgettest.myreview.contentprovide;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.myreview.DisplayData;
import com.example.uiwidgettest.myreview.OnClickListener;
import com.example.uiwidgettest.myreview.json.Hero;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 40774 on 2017/10/3.
 */
//对数据库编辑的碎片
public class EditSQLdatabase extends Fragment implements View.OnClickListener,Onclicksomething {
    private RecyclerView recyclerView;
    private CutFragment fragment;
    private Fragment insert;
    private SQLhelper sqLhelper;
    private  SQLiteDatabase databaseR;
    private SQLiteDatabase databaseW;
    private List<Hero> alist;
    HeroAdapter arrayAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragmenteditdatabse,container,false);
        MyLog.d("碎片","创建视图");
        try {
            ViewGroup parent=(ViewGroup) view.getParent();
            parent.removeAllViews();
        }catch (NullPointerException e)
        {
            MyLog.d("碎片","哈哈哈哈空指针异常");
        }
        sqLhelper=new SQLhelper();
        //展示数据库
        HeroDatabase heroDatabase=new HeroDatabase(getContext(),"reviewhero", null,1);
         databaseR=heroDatabase.getReadableDatabase();
         databaseW=heroDatabase.getWritableDatabase();
        TextView displaySQL=(TextView)view.findViewById(R.id.ReviewdisplaySQL);
        displaySQL.setText("两个碎片\n1.图形化管理数据库\n2.添加数据\n"+"数据库数据:\n"+sqLhelper.getSqlContent(databaseR));
        recyclerView=(RecyclerView) view.findViewById(R.id.fragmentlist);
        Button iwanttoinsert=(Button)view.findViewById(R.id.iwanttoinsert);
        iwanttoinsert.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DisplayData displayData=(DisplayData) getActivity();
        fragment=displayData;
        insert=displayData.getInsertDatatoSQL();
      //传入只读数据库信息从数据库帮组类中获取hero数组
         alist=sqLhelper.getListHero(databaseR);
         arrayAdapter=new HeroAdapter(getContext(), alist,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
         recyclerView.setAdapter(arrayAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iwanttoinsert:
                fragment.Cut(this,insert);break;
        }

    }
    boolean Isdelete=false;
    @Override
    public boolean delete(final int Id) {

        new AlertDialog.Builder(getContext()).setTitle("").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseW.delete("hero","id=?",new String[]{(Id+1)+""});
                alist.remove(Id);
                arrayAdapter.notifyItemRemoved(Id);
                Isdelete=true;
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setTitle("你确定删除第"+(Id+1)+"的数据吗？")
                .show();
        return Isdelete;
    }

    @Override
    public void confirm(Hero hero1,int Id) {
        sqLhelper.Update(databaseW,hero1,Id+1);
        Hero hero=new Hero();
        hero.setName(hero1.getName());
        hero.setCognomen(hero1.getCognomen());
        hero.setEnergy(hero1.getEnergy());
        hero.setWit(hero1.getWit());
        hero.setForce(hero1.getForce());
        hero.setGender(hero1.getGender());
        hero.setVirtue(hero1.getVirtue());
        hero.setLoyal(hero1.getLoyal());
        hero.setId(Id+1);
        alist.remove(Id);
        alist.add(Id,hero);
        arrayAdapter.notifyItemChanged(Id);
    }
}




