package com.example.uiwidgettest.myreview.textbook.activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.uiwidgettest.R;
import com.example.uiwidgettest.myreview.textbook.fragment.AndroidRobot;
import com.example.uiwidgettest.myreview.textbook.fragment.DrawViewFragment;
import com.example.uiwidgettest.myreview.textbook.fragment.SpinnerAndTable;

import java.util.ArrayList;
import java.util.List;

public class Textbook_MainPage extends AppCompatActivity {
private Spinner spinner;
private FrameLayout frameLayout;
private SpinnerAndTable spinnerAndTable;
//记录上一个碎片
    private Fragment lastfragment;
    private List<Fragment> fragmentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textbook__main_page);
        spinner=(Spinner) findViewById(R.id.textbook_spinner);
        spinnerAndTable=(SpinnerAndTable) getSupportFragmentManager().findFragmentById(R.id.textbook_fragment);
        frameLayout=(FrameLayout) findViewById(R.id.textbook_fragment_place);
        final String[] names=getResources().getStringArray(R.array.spinner_3_16);
        lastfragment=spinnerAndTable;
        fragmentList=new ArrayList<>();
        fragmentList.add(spinnerAndTable);
        fragmentList.add(new AndroidRobot());
        fragmentList.add(new DrawViewFragment());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        addFragment(fragmentList.get(0));
                        break;
                    case 1:
                        addFragment(fragmentList.get(1));
                        break;
                    case 2:
                        addFragment(fragmentList.get(2));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getBaseContext(),"你什么都没有选",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void addFragment(Fragment add)
    {
//        ViewGroup group=(ViewGroup) add.getView().getParent();
//        if(group!=null)
//            group.removeAllViews();

        if(!lastfragment.equals(add)) {
//           移除上一个碎片
            FragmentManager manager=getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            frameLayout.removeView(lastfragment.getView());
            transaction.remove(lastfragment);
            transaction.replace(R.id.textbook_fragment_place, add);
            transaction.commit();
        }
        lastfragment=add;
    }
}
