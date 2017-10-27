package com.example.uiwidgettest.framework.mvp.useclass;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uiwidgettest.R;
import com.example.uiwidgettest.framework.mvp.useinterface.DatainfoContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by 40774 on 2017/10/13.
 */

public class DataInfoFragment extends Fragment implements DatainfoContract.View,View.OnClickListener{
   @BindView(R.id.tv_name)
    TextView name;
    @BindView(R.id.tv_conogment)
    TextView conogment;
    @BindView(R.id.tv_age)
    TextView age;
    @BindView(R.id.ban)
    Button ban;
    @BindView(R.id.admit)
    Button admit;
    private Dialog mDialog;
    private DatainfoContract.Presenter mPresenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.mvpdatainfo,container,false);
        //直接用this
        ButterKnife.bind(this,view);
        admit.setOnClickListener(this);
        ban.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
          mDialog=new ProgressDialog(getActivity());
          mDialog.setTitle("正在获取数据...");
    }
    @OnClick(R.id.bt_data)
    public void start()
    {
        mPresenter.getDataInfo("123456789");
        name.setText("");
//        Toast.makeText(getContext(),"别说话。",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(DatainfoContract.Presenter presenter) {
        mPresenter=presenter;
    }


    public void subscribe() {

            Toast.makeText(getContext(),"你还没有与网络建立连接。",Toast.LENGTH_SHORT).show();
    }


    public void unSubscribe() {
            Toast.makeText(getContext(),"你还没有与网络建立连接。",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setDataInfo(final Hero hero) {
           if(hero!=null&&hero.getName()!=null)
           {
               getActivity().runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       name.setText(hero.getName());
                   }
               });

           }
    }

    @Override
    public void showLoading() {
        //已经将这几个子线程通过Rxjava转移到主线程了
                mDialog.show();
    }

    @Override
    public void hideLoading() {
                    if(mDialog.isShowing())
                    mDialog.dismiss();
    }

    @Override
    public void showError() {
                Toast.makeText(getContext(),"出现错误。",Toast.LENGTH_SHORT).show();
    }
public  static DataInfoFragment newInstance()
{
    return new DataInfoFragment();
}
    @Override
    public boolean IsActive() {
        return isAdded();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ban:
                unSubscribe();
                break;
            case R.id.admit:
                subscribe();
                break;
        }
    }
}
