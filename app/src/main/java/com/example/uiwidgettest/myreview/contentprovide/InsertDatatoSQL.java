package com.example.uiwidgettest.myreview.contentprovide;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.myreview.DisplayData;
import com.example.uiwidgettest.myreview.OnClickListener;
import com.example.uiwidgettest.myreview.json.Hero;

/**
 * Created by 40774 on 2017/10/4.
 */
//增加数据库内容的碎片
public class InsertDatatoSQL extends Fragment implements View.OnClickListener {
    private final String TAG="InsertDatatoSQL:";
    private CutFragment fragment;
    private Button cancel;
    private Button sure;
    private Fragment editSQLdatabase;
    private SQLiteDatabase databaseW;
    private SQLhelper sqLhelper;
    private EditText name;
    private EditText cognoment;
    private RadioButton genderman;
    private RadioButton genderfamale;
    private EditText energy;
    private EditText force;
    private EditText wit;
    private EditText loyal;
    private EditText virtue;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MyLog.d("碎片：","创建视图");

        View view=inflater.inflate(R.layout.insertdatatosql,container,false);
        initView(view);
        try
        {
            ViewGroup group=(ViewGroup)view.getParent();
            group.removeAllViews();
        }catch (NullPointerException e)
        {
            MyLog.d("碎片：","清除父布局失败");
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DisplayData displayData=(DisplayData)getActivity();
        fragment=displayData;
        editSQLdatabase=displayData.getEditSQLdatabase();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
            fragment.Cut(this, editSQLdatabase);
                break;
            case R.id.sure:
              insertData();
                break;
        }
    }
    private void insertData()
    {
        if(NotEmpty(name)&&NotEmpty(cognoment)&&NotEmpty(energy)
                &&NotEmpty(force)&&NotEmpty(wit)&&NotEmpty(loyal)&&NotEmpty(virtue)) {
            sqLhelper.insert(databaseW,getHerofrom());
            Toast.makeText(getContext(), "插入数据成功！", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(getContext(),"请先输入数据",Toast.LENGTH_SHORT).show();
    }
    private boolean NotEmpty(EditText editText)
    {
            return !editText.getText().toString().isEmpty();
    }
    private Hero getHerofrom()
    {
        Hero hero=new Hero();
        hero.setName(name.getText().toString());
        hero.setCognomen(cognoment.getText().toString());
        if(genderman.isChecked())
        hero.setGender(true);
        else
            hero.setGender(false);
        hero.setEnergy(Integer.valueOf(energy.getText().toString()));
        hero.setForce(Integer.valueOf(force.getText().toString()));
        hero.setLoyal(Integer.valueOf(loyal.getText().toString()));
        hero.setWit(Integer.valueOf(wit.getText().toString()));
        hero.setVirtue(Integer.valueOf(virtue.getText().toString()));
        return hero;
    }
    private void initView(View view)
    {
        cancel=(Button) view.findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        sure=(Button) view.findViewById(R.id.sure);
        sure.setOnClickListener(this);
        name=(EditText)view.findViewById(R.id.name);
        cognoment=(EditText)view.findViewById(R.id.congoment);
        energy=(EditText)view.findViewById(R.id.energy);
        force=(EditText)view.findViewById(R.id.force);
        wit=(EditText)view.findViewById(R.id.wit);
        loyal=(EditText)view.findViewById(R.id.loyal);
        virtue=(EditText)view.findViewById(R.id.virtue);
        genderfamale=(RadioButton)view.findViewById(R.id.genderfamale);
        genderman=(RadioButton)view.findViewById(R.id.genderman);

        sqLhelper=new SQLhelper();
        HeroDatabase heroDatabase=new HeroDatabase(getContext(),"reviewhero", null,1);
        databaseW=heroDatabase.getWritableDatabase();

        name.setText("吕布");
        cognoment.setText("奉先");
        energy.setText("99");
        force.setText("99");
        wit.setText("17");
        loyal.setText("30");
        genderman.setChecked(true);
        virtue.setText("35");
    }
}
