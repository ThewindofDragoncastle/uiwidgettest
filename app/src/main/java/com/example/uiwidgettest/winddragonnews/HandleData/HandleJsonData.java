package com.example.uiwidgettest.winddragonnews.HandleData;

import com.example.uiwidgettest.MyLog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 40774 on 2017/11/8.
 */

public class HandleJsonData {
private final String TAG=getClass().getName();
    public News handleNews(String json)
    {
//        解析出News
            News news=null;
//            json=VertifyData(json);
            MyLog.d(TAG,""+json);
            Gson gson=new Gson();
            news=gson.fromJson(json,News.class);
            return news;
    }
    /*解析出问题代码*/
//    public String VertifyData(String jsondata)
//    {
////        将不合符规范为空的图片地址给赋予一个值
//        try {
//            MyLog.d(TAG,""+jsondata);
//            JSONObject object=new JSONObject(jsondata);
//            String data=object.getString("data");
//            JSONArray myarray=new JSONArray();
//            JSONArray array=new JSONArray(data);
////            解析图片
//            JSONObject orjsonObject;
//            for (int i=0;i<array.length();i++) {
//                orjsonObject = array.getJSONObject(i);
//                String urls = orjsonObject.getString("imageUrls");
////                 解析的空不为空为null字符串
//
//                  orjsonObject.put("imageUrls",urls);
//              myarray.put(orjsonObject);
//            }
////            {"hasNext":true,"retcode":"000000","appCode":"qihoo","dataType":"news","pageToken":"2","data":"[{\"posterId\":null,\"tags\":null,\"publishDate\":1510309680,\"commentCount\":null,\"imageUrls\":[\"http:\\\/\\\/p5.qhmsg.com\\\/dmfd\\\/144_96_\\\/t0154ba7df0496ac0d9.png?size=519x389\"],\"id\":\"bb1bbc32989a40e70f2f34448ef68670\",\"posterScreenName\":\"腾讯大苏网\",\"title\":\"南京经五立交桥一男子坠落身亡 目前原因不明\",\"url\":\"http:\\\/\\\/js.qq.com\\\/a\\\/20171110\\\/034206.htm\",\"publishDateStr\":\"2017-11-10T10:28:00\",\"content\":\"中午,南京玄武区经五立交桥上,一名男子突然从高架桥坠落到水泥地面,头部流血动弹不得。目击者报警,经120检...\"},{\"posterId\":null,\"tags\":null,\"publishDate\":1510308120,\"commentCount\":null,\"imageUrls\":[\"http:\\\/\\\/p3.qhmsg.com\\\/dmfd\\\/144_96_\\\/t018deaf5e93fb5425c.jpg?size=641x427\"],\"id\":\"21e6009722ba478f7586739224075cab\",\"posterScreenName\":\"腾讯\",\"title\":\"女子围棋世锦赛-王晨星遭逆转 韩国崔精第2次夺冠\",\"url\":\"http:\\\/\\\/sports.qq.com\\\/a\\\/20171110\\\/033250.htm\",\"publishDateStr\":\"2017-11-10T10:02:00\",\"content\":\"三个多月前,王晨星刚生下她与刘星七段的孩子,这次比赛由母亲陪同、带着孩子,白天比赛,晚上带孩子,自称白天...\"},{\"posterId\":null,\"tags\":null,\"publishDate\":1510308060,\"commentCount\":null,\"imageUrls\":[\"http:\\\/\\\/p6.qhmsg.com\\\/dmfd\\\/144_96_\\\/t012301c5a9bdbcf807.png?size=640x425\"],\"id\":\"a0aaf0ac31f2da97735ff0f8d4ea9a6f\",\"posterScreenName\":\"腾讯房产网\",\"title\":\"双11脱光攻略 来恒大海泉湾谈场恋爱吧\",\"url\":\"http:\\\/\\\/zh.house.qq.com\\\/a\\\/20171110\\\/033231.htm\",\"publishDateStr\":\"2017-11-10T10:01:00\",\"content\":\"赤脚踩在银白色的沙滩上,让海风拂过发梢、拂过脸颊、拂过长裙短裤,暂时放下所有的生活琐碎和烦扰,尽情和大...\"},{\"posterId\":null,\"tags\":null,\"publishDate\":1510307820,\"commentCount\":null,\"imageUrls\":\"[\\\"\\\"]\",\"id\":\"3edc779d10161f80e58f9e10174aadec\",\"posterScreenName\":\"腾讯\",\"title\":\"七机构发布 智慧选择食物 联合提示,教你健康 挑食儿\",\"url\":\"http:\\\/\\\/health.qq.com\\\/a\\\/20171110\\\/033108.htm\",\"publishDateStr\":\"2017-11-10T09:57:00\",\"content\":\"营养成分表 是营养标签的核心内容。其中能量和蛋白质、脂肪、碳水化合物、钠的含量为强制标示的内容,又称 1+4 。其他成分如钙、铁、维生素等信息,由生产企...\"},{\"posterId\":null,\"tags\":null,\"publishDate\":1510306680,\"commentCount\":null,\"imageUrls\":[\"http:\\\/\\\/p3.qhmsg.com\\\/dmfd\\\/144_96_\\\/t01a7c9f0a61dee2fc8.jpg?size=640x959\",\"http:\\\/\\\/p6.qhmsg.com\\\/dmfd\\\/144_96_\\\/t01966ca3568566d834.jpg?size=640x476\",\"http:\\\/\\\/p6.qhmsg.com\\\/dmfd\\\/144_96_\\\/t0129e2821c9510a209.jpg?size=640x427\"],\"id\":\"727523915cba16a1acf329de8d6efbeb\",\"posterScreenName\":\"腾讯大湘网\",\"title\":\"《中餐厅》完美收官 泰国象岛走红成游客新宠\",\"url\":\"http:\\\/\\\/hn.qq.com\\\/a\\\/20171110\\\/032459.htm\",\"publishDateStr\":\"2017-11-10T09:38:00\",\"content\":\"8图\"},{\"posterId\":null,\"tags\":null,\"publishDate\":1510306500,\"commentCount\":null,\"imageUrls\":[\"http:\\\/\\\/p2.qhmsg.com\\\/dmfd\\\/144_96_\\\/t01cf5446284071794b.png?size=501x498\",\"http:\\\/\\\/p9.qhmsg.com\\\/dmfd\\\/144_96_\\\/t01b1bf16d8f4047107.png?size=499x496\",\"http:\\\/\\\/p1.qhmsg.com\\\/dmfd\\\/144_96_\\\/t0106b0f76a7208484a.png?size=502x497\"],\"id\":\"81c74af48f399dc1da8471d8289ec9f6\",\"posterScreenName\":\"腾讯大燕网\",\"title\":\"简约美甲款式 有时简约也很美\",\"url\":\"http:\\\/\\\/hb.jjj.qq.com\\\/a\\\/20171110\\\/032302.htm\",\"publishDateStr\":\"2017-11-10T09:35:00\",\"content\":\"4图\"},{\"posterId\":null,\"tags\":null,\"publishDate\":1510306200,\"commentCount\":null,\"imageUrls\":[\"http:\\\/\\\/p3.qhmsg.com\\\/d
////            {"hasNext":true,"retcode":"000000","appCode":"qihoo","dataType":"news","pageToken":"2","data":[{"posterId":null,"tags":null,"publishDate":1510309680,"commentCount":null,"imageUrls":["http:\/\/p5.qhmsg.com\/dmfd\/144_96_\/t0154ba7df0496ac0d9.png?size=519x389"],"id":"bb1bbc32989a40e70f2f34448ef68670","posterScreenName":"腾讯大苏网","title":"南京经五立交桥一男子坠落身亡 目前原因不明","url":"http:\/\/js.qq.com\/a\/20171110\/034206.htm","publishDateStr":"2017-11-10T10:28:00","content":"中午,南京玄武区经五立交桥上,一名男子突然从高架桥坠落到水泥地面,头部流血动弹不得。目击者报警,经120检..."},{"posterId":null,"tags":null,"publishDate":1510308120,"commentCount":null,"imageUrls":["http:\/\/p3.qhmsg.com\/dmfd\/144_96_\/t018deaf5e93fb5425c.jpg?size=641x427"],"id":"21e6009722ba478f7586739224075cab","posterScreenName":"腾讯","title":"女子围棋世锦赛-王晨星遭逆转 韩国崔精第2次夺冠","url":"http:\/\/sports.qq.com\/a\/20171110\/033250.htm","publishDateStr":"2017-11-10T10:02:00","content":"三个多月前,王晨星刚生下她与刘星七段的孩子,这次比赛由母亲陪同、带着孩子,白天比赛,晚上带孩子,自称白天..."},{"posterId":null,"tags":null,"publishDate":1510308060,"commentCount":null,"imageUrls":["http:\/\/p6.qhmsg.com\/dmfd\/144_96_\/t012301c5a9bdbcf807.png?size=640x425"],"id":"a0aaf0ac31f2da97735ff0f8d4ea9a6f","posterScreenName":"腾讯房产网","title":"双11脱光攻略 来恒大海泉湾谈场恋爱吧","url":"http:\/\/zh.house.qq.com\/a\/20171110\/033231.htm","publishDateStr":"2017-11-10T10:01:00","content":"赤脚踩在银白色的沙滩上,让海风拂过发梢、拂过脸颊、拂过长裙短裤,暂时放下所有的生活琐碎和烦扰,尽情和大..."},{"posterId":null,"tags":null,"publishDate":1510307820,"commentCount":null,"imageUrls":null,"id":"3edc779d10161f80e58f9e10174aadec","posterScreenName":"腾讯","title":"七机构发布 智慧选择食物 联合提示,教你健康 挑食儿","url":"http:\/\/health.qq.com\/a\/20171110\/033108.htm","publishDateStr":"2017-11-10T09:57:00","content":"营养成分表 是营养标签的核心内容。其中能量和蛋白质、脂肪、碳水化合物、钠的含量为强制标示的内容,又称 1+4 。其他成分如钙、铁、维生素等信息,由生产企..."},{"posterId":null,"tags":null,"publishDate":1510306680,"commentCount":null,"imageUrls":["http:\/\/p3.qhmsg.com\/dmfd\/144_96_\/t01a7c9f0a61dee2fc8.jpg?size=640x959","http:\/\/p6.qhmsg.com\/dmfd\/144_96_\/t01966ca3568566d834.jpg?size=640x476","http:\/\/p6.qhmsg.com\/dmfd\/144_96_\/t0129e2821c9510a209.jpg?size=640x427"],"id":"727523915cba16a1acf329de8d6efbeb","posterScreenName":"腾讯大湘网","title":"《中餐厅》完美收官 泰国象岛走红成游客新宠","url":"http:\/\/hn.qq.com\/a\/20171110\/032459.htm","publishDateStr":"2017-11-10T09:38:00","content":"8图"},{"posterId":null,"tags":null,"publishDate":1510306500,"commentCount":null,"imageUrls":["http:\/\/p2.qhmsg.com\/dmfd\/144_96_\/t01cf5446284071794b.png?size=501x498","http:\/\/p9.qhmsg.com\/dmfd\/144_96_\/t01b1bf16d8f4047107.png?size=499x496","http:\/\/p1.qhmsg.com\/dmfd\/144_96_\/t0106b0f76a7208484a.png?size=502x497"],"id":"81c74af48f399dc1da8471d8289ec9f6","posterScreenName":"腾讯大燕网","title":"简约美甲款式 有时简约也很美","url":"http:\/\/hb.jjj.qq.com\/a\/20171110\/032302.htm","publishDateStr":"2017-11-10T09:35:00","content":"4图"},{"posterId":null,"tags":null,"publishDate":1510306200,"commentCount":null,"imageUrls":["http:\/\/p3.qhmsg.com\/dmfd\/144_96_\/t019078e6df118dac78.jpg?size=603x462"],"id":"67846d8aa6639fef343d4797567e1220","posterScreenName":"腾讯大渝网","title":"驾驶员抢黄灯致两车相撞 大货车轮胎都撞爆了","url":"http:\/\/cq.qq.com\/a\/20171110\/032090.htm","publishDateStr":"2017-11-10T09:30:00","content":"据造成此次事故的主要责任人大货车司机介绍,当时他正赶往江津,一辆白�
////  for (int i=0;i<myarray.length();i++) {
////                orjsonObject = array.getJSONObject(i);
////                String url = orjsonObject.getString("imageUrls");
////                MyLog.d(TAG,"url:"+url);
////            }
////            这里放入字符串就好 放入字符串会出错
//              object.put("data",myarray);
//            return object.toString();
//        } catch (JSONException e) {
//            return null;
//        }
//
//    }
//   public String imageURLS(String url)
//   {
//       try {
//           if(!url.equals("null")) {
//               JSONArray array = new JSONArray(url);
//               MyLog.d(TAG,array.toString());
//               return null;
//           }
//           return null;
//       } catch (JSONException e) {
//           return null;
//       }
//
//   }
}
