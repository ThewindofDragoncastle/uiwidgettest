package com.example.uiwidgettest.mvpupdatechat.presenter;

import com.example.uiwidgettest.mvpupdatechat.view.adapter.Message;

/**
 * Created by 40774 on 2017/10/18.
 */

public interface PresenterContractChat {
    interface chatviewcallback extends BaseView.ChatView.Callback {
        void establishDatabase();
        void insertDatetoDB(String data,boolean isrepient);
        void displayData(Message message);

        //展示多少条数据
        void querydatabases(int i);
        //供自己使用
    }
    interface chatview extends BaseView.ChatView.Down
    {
    }
    interface setInterface extends BaseView.ChatView.Down
    {
        void setChatviewCallback(chatviewcallback chatviewCallback);
    }
    interface setCommService extends BaseModelService.ChatconnService.Down
    {
        void sendData(String data);
        void setCommService(CommService service);
    }
    interface init extends BasePresenter.ChatPresenter
    {
        //启动类
        void start();
    }
    interface CommService extends BaseModelService.ChatconnService.Callback
    {
        void getData(String data);
    }
}
