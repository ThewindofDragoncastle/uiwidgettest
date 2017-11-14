package com.example.uiwidgettest.byinternet;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.uiwidgettest.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PullMethod extends AppCompatActivity {
    private final String IP="http://39.108.123.220/陈宏林学习数据/appinfo.xml";
   private String finaldisplay="解析的网址：http://39.108.123.220/陈宏林学习数据/appinfo.xml";
    StringBuffer display=new StringBuffer ("");
    TextView displayData;
    CheckBox checkBox;
    private static  Boolean PHONE=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_method);
        displayData=(TextView)findViewById(R.id.xmlData);
        Button parseXML=(Button)findViewById(R.id.parseXML);
//        displayData.setText(finaldisplay);
        parseXML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runit();
            }
        });
       checkBox=(CheckBox)findViewById(R.id.pullcheckbox);}
    private void runit()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                if(checkBox.isChecked())
                {
                    PHONE=false;
                }
                else
                    PHONE=true;
                Request request;
                if(PHONE) {
                    finaldisplay=IP;
                    request = new Request.Builder().url(IP).build();
                }
                else {
                    finaldisplay=IP;
                    request = new Request.Builder().url(IP).build();
                }
                try {
                    Response response=client.newCall(request).execute();
                    display.append(response.body().string());
                    parseXML(display.toString());
                    show();
                }catch (IOException e)
                {
                    Log.d(getLocalClassName(),"文件流出错");
                }
            }
        }).start();

    }
    private void show()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                displayData.setText(finaldisplay+"\n未解析的数据："+display.toString().trim());
            }
        });
    }
    private void parseXML(String data)
    {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser pullParser=factory.newPullParser();
            pullParser.setInput(new StringReader(data));
            int event=pullParser.getEventType();
            String id="";
            String name="";
            String version="";
            Log.d(getLocalClassName(),""+XmlPullParser.END_DOCUMENT);
//            display.setLength(0);
            while(event!=XmlPullParser.END_DOCUMENT)
            {
                String nodeName = pullParser.getName();
                Log.d(getLocalClassName(),""+nodeName);
                switch (event)
                {
                    case XmlPullParser.START_TAG:
                        if (nodeName.equals("id"))
                            id = pullParser.nextText();
                        else if (nodeName.equals("name"))
                            name = pullParser.nextText();
                        else if (nodeName.equals("version"))
                            version = pullParser.nextText();
                           break;
                    case XmlPullParser.END_TAG:
                        if(nodeName.equals("app"))
                        display.append("\nid为：" + id + "\n名称为：" + name + "\n版本为：" + version);
                        break;
                      default:
                        break;
                }
                event = pullParser.next();
            }

//            displayData.setText(display.toString());
        }catch (XmlPullParserException e)
        {
            Log.d(getLocalClassName(),"解析异常！");
        }
        catch (IOException e)
        {
            Log.d(getLocalClassName(),"文件流异常！");
        }
    }
}
