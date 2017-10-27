package com.example.uiwidgettest.mvpupdatechat.presenter;

import com.example.uiwidgettest.ui.sendorrecieve.Intact;

import java.util.List;

/**
 * Created by 40774 on 2017/10/18.
 */

public interface PresenterContractIntract {
    interface FragmentWithAdapter extends BaseView.IntactView {
        //适配器回调
        void IntoChat(Intact intact);
    }
        interface view extends BaseView.IntactView.Callback
        {//对视图的回调
            void updateAdapter(List<Intact> intacts);
            void StartRefresh();
            void EndRefresh();
        }
       interface viewstart extends BaseView.IntactView.Down
       {//启动类
            void Start();
        }
        interface SetIntactServiceCallDown extends BasePresenter.IntactPrenter.Down
        {
            //初始化操作 可以被上层或本层调用（此处被本层调用）
            void setConnwithService();
            void sendtoServerForIntactData();
            void sendtoServerForinsert(String unread,String target);
        }
        interface IntactServiceCallBack extends BaseModelService.IntactconnService.Callback
        {
            void Error();
            //发送不处于聊天界面的消息插入服务器数据库
            void sendUnreadMessageNoforResult(String unread);
            void sendUnreadMessageForResult(String unread);
            void Complete(String data);
        }
       interface setIntactInterface {
           //对视图操作的接口
          void setViewInterface(view viewInterface);
       }
}
