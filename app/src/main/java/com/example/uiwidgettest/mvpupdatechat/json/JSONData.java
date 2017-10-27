package com.example.uiwidgettest.mvpupdatechat.json;


import com.example.uiwidgettest.ui.sendorrecieve.Intact;

import java.util.List;

/**
 * Created by 40774 on 2017/10/22.
 */

public interface JSONData {
//    对json数据的包装或者处理
    interface HandleJson
    {
//        获取头文件
        String  GetHeadData(String data);
//        处理登录状态成功还是失败
        Boolean HandleLoginInfo(String data);
//        处理联系人信息
        List<Intact> HandleIntactInfo(String data);
//        处理聊天中的信息内容
        String  HandleChatMessageInfo(String data);
//        处理聊天中来源帐户
        String  HandleChatMessageFromAccount(String data);
    }
    interface WrapJson
    {
//        包装登录信息
        String WrapLoginInfo(String account, String password);
//        包装请求联系人信息
        String WrapRequestIntactInfo();
//        包装请求未读联系人信息
        String WrapRequestunreadInfo();
//        包装聊天信息
        String WrapChatMessage(String message);
//        包装向服务器插入不可读取（不处于聊天界面，处于联系人界面）的消息 要求服务器返回最新联系人信息
        String WrapInsertUnreadServerForResult(String data,String target);
//        包装向服务器插入不可读取（例如程序只是后台运行）的消息 不要求服务器返回最新联系人信息
        String WrapInsertUnreadServerNotResult(String data,String target);
    }
}
