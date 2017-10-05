package com.example.uiwidgettest.myreview;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.myreview.contentprovide.CutFragment;
import com.example.uiwidgettest.myreview.contentprovide.EditSQLdatabase;
import com.example.uiwidgettest.myreview.contentprovide.HeroDatabase;
import com.example.uiwidgettest.myreview.contentprovide.InsertDatatoSQL;
import com.example.uiwidgettest.myreview.contentprovide.SQLhelper;
import com.example.uiwidgettest.myreview.json.Hero;

public class DisplayData extends AppCompatActivity implements CutFragment {
    private final String  TAG="DisplayData:";
    private TextView displaydata;
    private FrameLayout linearLayout;
    private TextView displaySQL;
    private InsertDatatoSQL insertDatatoSQL;
    private EditSQLdatabase editSQLdatabase;
    //是否切换碎片
    private Button iwanttoinsert;
    private FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        MyLog.d(TAG,"继续执行。");

    }
private void initView()
{
    setContentView(R.layout.activity_display_data);
    linearLayout=(FrameLayout)findViewById(R.id.reviewfragmentLinearLayout);
    displaydata=(TextView)findViewById(R.id.MyreviewdisplayData);

    frameLayout=(FrameLayout)findViewById(R.id.ReviewFragment);
//    iwanttoinsert=(Button)findViewById(R.id.iwanttoinsert);
//    iwanttoinsert.setOnClickListener(this);
    //初始化两个碎片
    insertDatatoSQL=new InsertDatatoSQL();
    editSQLdatabase = (EditSQLdatabase)
            getSupportFragmentManager().findFragmentById(R.id.reviewpreviewfragment);
    //确定显示的内容是字符串还是碎片
    if(!getIntent().getBooleanExtra("display",false)) {
        displaydata.setVisibility(View.GONE);
    }
    else {
        displaydata.setText(getIntent().getStringExtra("stringtojson"));
        linearLayout.removeView(editSQLdatabase.getView());
    }
}
    private void replacefragment(Fragment Remove,Fragment fragment)
    {

        linearLayout.removeView(editSQLdatabase.getView());
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.remove(Remove);
        transaction.replace(R.id.ReviewFragment,fragment);
        transaction.commit();
    }

    public EditSQLdatabase getEditSQLdatabase() {
        return editSQLdatabase;
    }

    public InsertDatatoSQL getInsertDatatoSQL() {
        return insertDatatoSQL;
    }

    @Override
    public void Cut(Fragment Remove, Fragment fragment) {
        replacefragment(Remove,fragment);
    }
}
