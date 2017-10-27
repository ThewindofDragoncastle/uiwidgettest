package com.example.uiwidgettest.mvpupdatechat.presenter.Intact;


import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.mvpupdatechat.bufferdata.Bufferdata;
import com.example.uiwidgettest.mvpupdatechat.json.HandleJson;
import com.example.uiwidgettest.mvpupdatechat.presenter.MedBasePresenter;
import com.example.uiwidgettest.mvpupdatechat.presenter.PresenterContractIntract.IntactServiceCallBack;
import com.example.uiwidgettest.ui.sendorrecieve.Intact;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.uiwidgettest.mvpupdatechat.presenter.PresenterContractIntract.*;

/**
 * Created by 40774 on 2017/10/16.
 */

public class MedIntactPresenter extends MedBasePresenter
        implements viewstart, IntactServiceCallBack
        ,SetIntactServiceCallDown,setIntactInterface {
    private Observer obs=new Observer<String>() {
        @Override
        public void onSubscribe(@NonNull Disposable disposable) {

        }

        @Override
        public void onNext(@NonNull String s) {
            MyLog.d("MedIntactPresenter:","data:"+s);
            //解析Json数据
            List<Intact> intacts=
            handleJson.HandleIntactInfo(s);
            view.EndRefresh();
            view.updateAdapter(intacts);
        }

        @Override
        public void onError(@NonNull Throwable throwable) {
            view.EndRefresh();
            activityView.showErrorToast();
        }

        @Override
        public void onComplete() {

        }
    };
    private HandleJson handleJson=new HandleJson();
    public Bufferdata bufferdata=Bufferdata.getIntance();
    private view view;
    private static  MedIntactPresenter medIntactPresenter;
    private MedIntactPresenter(){}
    public static MedIntactPresenter getInstance()
    {
        if(medIntactPresenter==null)
            medIntactPresenter=new MedIntactPresenter();
        return medIntactPresenter;
    }
    @Override
    public void Error() {
        obs.onError(new Exception());
    }

    @Override
    public void sendUnreadMessageNoforResult(String unread) {
        bufferdata.sendData(handleJson.WrapInsertUnreadServerNotResult
                (handleJson.HandleChatMessageInfo(unread)
                ,handleJson.HandleChatMessageFromAccount(unread)));
    }

    @Override
    public void sendUnreadMessageForResult(String unread) {
        //首先将此消息写入服务器数据库 表示未读
        //服务器返回新数据刷新联系人
       //从获取的数据中取出是谁发给该用户的然后转发服务器
        sendtoServerForinsert(handleJson.HandleChatMessageInfo(unread)
                ,handleJson.HandleChatMessageFromAccount(unread));
    }

    @Override
    public void Complete(final String data) {
//接收数据完成对列表进行更新
        Observable.just(data).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(obs);
    }

    @Override
    public void Start() {
            //初始化操作
            view.StartRefresh();
            setConnwithService();
            sendtoServerForIntactData();
    }

    @Override
    public void setConnwithService() {
        bufferdata.setIntactConn(this);
    }

    @Override
    public void setViewInterface(view viewInterface) {
        view=viewInterface;
    }

    @Override
    public void sendtoServerForIntactData() {
//        //从服务器初始化联系人信息
        bufferdata.sendData(handleJson.WrapRequestIntactInfo());
    }

    @Override
    public void sendtoServerForinsert(String unread,String target) {
        bufferdata.sendData(handleJson.WrapInsertUnreadServerForResult(unread,target));
    }

}
