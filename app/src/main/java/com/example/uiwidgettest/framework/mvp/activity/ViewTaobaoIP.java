package com.example.uiwidgettest.framework.mvp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uiwidgettest.R;
import com.example.uiwidgettest.framework.mvp.useclass.ActivityUtils;
import com.example.uiwidgettest.framework.mvp.useclass.DataInfoFragment;
import com.example.uiwidgettest.framework.mvp.useclass.DataInfoPresenter;
import com.example.uiwidgettest.framework.mvp.useclass.DataInfoTask;
import com.example.uiwidgettest.framework.rxjava.adapter.ButtonandTextRecycle;
import com.example.uiwidgettest.framework.rxjava.useinterface.CallBackButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class ViewTaobaoIP extends AppCompatActivity implements CallBackButton {
    private DataInfoPresenter presenter;
    @BindView(R.id.fragment)
    LinearLayout frameLayout;
    @BindString(R.string.mvpdata)
    String mvpdata;
    @BindView(R.id.display)
    TextView display;
    @BindView(R.id.reclyer)
    RecyclerView recycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        ButterKnife.bind(this);
        List<String> name=new ArrayList<>();
        name.add("简单的MVP模式");
        recycler.setAdapter(new ButtonandTextRecycle(this,this,name));
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View view, int position) {
        switch (view.getId())
        {
            case R.id.implement:
                switch (position)
                {
                    case 0:
                        addFragment();
                        break;
                }
                break;
        }
    }
    private void addFragment()
    {
        frameLayout.removeAllViews();
        DataInfoFragment infoFragment=(DataInfoFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragmentplace);
        if (infoFragment==null)
        {
            infoFragment=DataInfoFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),infoFragment,R.id.fragmentplace);
        }
        DataInfoTask dataInfoTask=DataInfoTask.getInstance();
        presenter=new DataInfoPresenter(infoFragment,dataInfoTask);
        infoFragment.setPresenter(presenter);
    }
}
