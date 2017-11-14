package com.example.uiwidgettest.mvpupdatechat.presenter;

/**
 * Created by 40774 on 2017/10/14.
 */
//接口说明：例如 BaseModelService.LoginconnService.ChangeFragmentCallback
//    它的作用是在基础登录模型层对媒介层进行回调
//     BasePresenter.LoginPresenter.Down
//    它所在的基础登录媒介层中对媒介层进行调用
public interface PresenterContactLogin {
    interface UpStatusCallback extends BasePresenter.LoginPresenter {
        //媒介层的规范 本层调用
        //这里的正确还是错误是用于回调的
        //本层本可以不需要但直接显示进度条之类的操作逻辑不是太清楚
        void Start();
        void Correct();
        void Error();
        void NetError();
    }
    interface LoginServiceCallback extends BaseModelService.LoginconnService.Callback{
        //模型层向媒介层回调
        void OnNext(String data);
        void OnError();
    }
    interface setLoginServiceCallBack extends BasePresenter.LoginPresenter.Down
    {
        //媒介向模型层传递数据的
        void setCurrentAccount(String account);
        void setCommService();
        void sendtoService(String data);
    }
    interface DownPresent extends BaseView.LoginView.Down
    {
//        完成初始化 也是规范 但是是View调用的
//本页面用的不是继承 留给上层调用的
        void start();
        void sendtoServer(String account ,String password);
    }
    interface SetPresenterInterface extends BaseView.LoginView.Down {
// 接口初始化都在视图层(活动和碎片)完成
// 在初始化的时候这个地方的完成所有设置
//开启服务或关闭服务绑定服务解绑服务
//View层设置接口
        void setViewInterface(UpView view);
        void setUpStatusCallback(UpStatusCallback callback);
        //在服务中设置CommService接口
        void setCommService(setLoginServiceCallBack setcommServiceCallBack);
    }
    interface UpView extends BaseView.LoginView.Callback
    {
//        媒介对视图层的回调
//       只做视图的改变指令
//       返回数据，更新视图
//        如果密码正确替换碎片
        void IntoIntact();
        //记住密码
        void savePassword(String filename);
//        取出数据
        void getSave(String filename);
        void setCurrentOnServer();
        void showPasswordError();
    }
}
