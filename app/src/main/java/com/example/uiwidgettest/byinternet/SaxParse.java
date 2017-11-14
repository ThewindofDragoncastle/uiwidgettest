package com.example.uiwidgettest.byinternet;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.uiwidgettest.R;


import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SaxParse extends AppCompatActivity implements Runnable {
//    很多不足但不想修改了
    private final String IP="http://39.108.123.220/陈宏林学习数据/appinfo.xml";
    private  TextView display;
    private static boolean PHONE=false;
    private CheckBox checkBox;
    Request request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sax_parse);
        Button SAXparseXML=(Button)findViewById(R.id.SAXparseXML);
        checkBox=(CheckBox)findViewById(R.id.Saxcheckbox);
        display=(TextView)findViewById(R.id.SAXxmlData);
        display.setText("无数据返回。");
        final SaxParse saxParse=this;
        SAXparseXML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked())
                    PHONE=false;
                else
                    PHONE=true;
                Thread s=new Thread(saxParse);
                s.start();
            }
        });

    }

    @Override
    public void run() {
        OkHttpClient client=new OkHttpClient();
        if(PHONE)
            request =new Request.Builder().url(IP).build();
        else
            request =new Request.Builder().url(IP).build();
        try {
            Response response = client.newCall(request).execute();
            String data=response.body().string();
            Log.d("SaxParse","传递的数据："+data);
            getXMLdata(data);
        }catch (IOException e) {
            Log.d("Saxparse", "呼叫异常");
        }
        catch (NullPointerException e) {
            Log.d("Saxparse", "空指针异常");
        }
    }

    public void getXMLdata(String data)
    {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader xmlReader = factory.newSAXParser().getXMLReader();
            ContentHelper1 contentHelper1=new ContentHelper1(display,this);
            xmlReader.setContentHandler(contentHelper1);
            xmlReader.parse(new InputSource(new StringReader(data)));

        }catch (ParserConfigurationException e)
        {
            Log.d(getLocalClassName(), "分析配置异常。");
        }
        catch (SAXException e)
        {
            Log.d(getLocalClassName(), "SAX异常。");
        }
        catch (IOException e)
        {
            Log.d(getLocalClassName(), "IO异常。");
        }
    }
}
class ContentHelper1 extends DefaultHandler
{
    public String nodename;
    public StringBuilder id;
    public StringBuilder name;
    public StringBuilder version;
    public SaxParse helper;
    private TextView display;
    public static StringBuilder fileinfo=new StringBuilder("文件信息：");
    public ContentHelper1(TextView textView,SaxParse helper1)
    {
        helper=helper1;
        fileinfo.replace(0,fileinfo.length(),"文件信息：");
        display=textView;
    }

    @Override
    public void startDocument() throws SAXException {
        id=new StringBuilder("");
        name=new StringBuilder("");
        version=new StringBuilder("");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
     nodename=localName;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
//        Log.d("SaxParse","本地名："+ch);
       if("id".equals(nodename))
            id.append(ch,start,length);
       else if("name".equals(nodename))
            name.append(ch,start,length);
       else if("version".equals(nodename))
        version.append(ch,start,length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if("app".equals(localName))
        {
            fileinfo.append("\nid="+id.toString().trim()+" name="+name.toString().trim()+" version="+version.toString().trim()+"\n");
            Log.d("SaxParse","本地名："+localName+fileinfo.toString());
            id.setLength(0);
            name.setLength(0);
            version.setLength(0);
        }
        if("apps".equals(localName))
        {
          show();
        }

    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }
    public void  show()
    {
        helper.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                display.setText(ContentHelper1.fileinfo.toString());
            }
        });

    }
}
