package com.example.uiwidgettest.mvpupdatechat.json;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.mvpupdatechat.bufferdata.Bufferdata;
import com.example.uiwidgettest.mvpupdatechat.view.fragment.Intact;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 40774 on 2017/10/16.
 */
//对数据包装 或解调
public class HandleJson implements JSONData.HandleJson,JSONData.WrapJson {
    @Override
    public String GetHeadData(String data) {
        JSONArray jsonArray= null;
        try {
            jsonArray = new JSONArray(data);
            JSONObject object2=jsonArray.getJSONObject(0);
            data=object2.getString("head");
            return data;
        } catch (JSONException e) {
            return null;
        }
    }
    @Override
    public Boolean HandleLoginInfo(String data) {
        //解析服务器返回的数据 登录成功还是失败
        try {
            JSONArray jsonArray=new JSONArray(data);
            JSONObject object2=jsonArray.getJSONObject(0);
            if(object2.getString("message").equals("defeat"))
                return false;
            else
                return true;
        } catch (JSONException e) {
            return false;
        }
    }

    @Override
    public List<com.example.uiwidgettest.ui.sendorrecieve.Intact> HandleIntactInfo(String data) {
        JSONArray jsonArray= null;
        try {
            jsonArray = new JSONArray(data);
            JSONObject object2=jsonArray.getJSONObject(0);
            data=object2.getString("content");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyLog.d("HandleJson:","content:"+data);
        //由于这里返回的是字符串 无[]标志 所以下面会报错
        data="["+data+"]";
        Gson gson=new Gson();
        List<com.example.uiwidgettest.ui.sendorrecieve.Intact> intacts=
                gson.fromJson(data
                        , new TypeToken<List<com.example.uiwidgettest.ui.sendorrecieve.Intact>>(){}.getType());
        return intacts;
    }

    @Override
    public String HandleChatMessageInfo(String data) {
        //包装处理聊天数据
        try {
            JSONArray jsonArray=new JSONArray(data);
            JSONObject object2=jsonArray.getJSONObject(0);
            return object2.getString("message");
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public String HandleChatMessageFromAccount(String data) {
        //处理聊天目标数据
        try {
            JSONArray jsonArray=new JSONArray(data);
            JSONObject object2=jsonArray.getJSONObject(0);
            return object2.getString("target");
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public String WrapLoginInfo(String account, String password) {

        //包装登录请求数据
        JSONObject object2=new JSONObject();
        try {
            object2.put("account",account);
            object2.put("password",password);
            object2.put("head","login");
            JSONArray jsonArray=new JSONArray();
            jsonArray.put(object2);
            MyLog.d("HandleJson:","json:"+jsonArray.toString());
            return jsonArray.toString();
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public String WrapRequestIntactInfo() {
        //包装请求联系人数据
        try {
            JSONArray array=new JSONArray();
            JSONObject object=new JSONObject();
            object.put("head","requestintactinfo");
            array.put(object);
            return array.toString();
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public String WrapRequestunreadInfo() {
        //        对未读消息进行请求(下载未读消息)
        try {
            JSONArray array=new JSONArray();
            JSONObject object=new JSONObject();
            object.put("head","unread");
            object.put("target",Bufferdata.getTarget());
            array.put(object);
            return array.toString();
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public String WrapChatMessage(String message) {
        //包装聊天信息数据
        try {
            JSONArray array=new JSONArray();
            JSONObject object=new JSONObject();
            object.put("head","message");
            object.put("target", Bufferdata.getTarget());
            object.put("message",message);
            array.put(object);
            return array.toString();
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public String WrapInsertUnreadServerForResult(String data, String target) {
        //给云端传送当前消息不可直接读取 请求插入云数据库 并且要求服务器答应
        JSONArray jsonArray= null;
        try {
            JSONArray array=new JSONArray();
            JSONObject object=new JSONObject();
            object.put("head","insertunread");
            //谁发给服务器的服务器心理有数
            //但服务器不知道是谁发给该用户的未读消息
            object.put("target",target);
            object.put("message",data);
            array.put(object);
            return array.toString();
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public String WrapInsertUnreadServerNotResult(String data, String target) {
        //给云端传送当前消息不可直接读取 请求插入云数据库
        JSONArray jsonArray= null;
        try {
            JSONArray array=new JSONArray();
            JSONObject object=new JSONObject();
            object.put("head","insertunreadnoforresult");
            //谁发给服务器的服务器心理有数
            //但服务器不知道是谁发给该用户的未读消息
            object.put("target",target);
            object.put("message",data);
            array.put(object);
            return array.toString();
        } catch (JSONException e) {
            return null;
        }
    }
}
