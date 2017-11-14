package com.example.uiwidgettest.winddragonnews.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.winddragonnews.useinterface.SettingFragmentinterface;

import java.io.File;

/**
 * Created by 40774 on 2017/11/13.
 */

public class SettingFragment extends PreferenceFragment implements SettingFragmentinterface{

    private final String TAG=getClass().getName();
    private  File filemedir;
    SharedPreferences.OnSharedPreferenceChangeListener mChangeListener;
    Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyLog.d(TAG,"创建设置碎片");
        addPreferencesFromResource(R.xml.mysetting);
        mActivity = getActivity();
        init();
        final EditTextPreference editTextPreference=(EditTextPreference)findPreference("setting_edit");
        editTextPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Toast.makeText(mActivity,"你输入的内容为："+editTextPreference.getText(),Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        mChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                MyLog.d(TAG,"key:"+key);
                if ("setting_wifi".equals(key) || "setting_bluetouh".equals(key) || "charge_lock_screen".equals(key) || "never_sleep".equals(key)) {
                    Toast.makeText(mActivity, "点击表示响应", Toast.LENGTH_SHORT).show();
                }else if("setting_timezone".equals(key))
                {
                    findPreference("setting_timezone").setSummary(sharedPreferences.getString(key,"GMY - 02:00"));
                }
                if(key.equals("setting_sound"))
                {
                    deletebuffer();
                }
            }
        };
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(getActivity());
        StringBuffer buffer=new StringBuffer();
        buffer.append("暴走选择开关："+preferences.getBoolean("setting_wifi",true));
        MyLog.i(TAG,"设置数据:"+buffer.toString());

        findPreference("setting_sound").setSummary("当前缓存："+querybuffer()/1024/1024.0+"M"
                +" \n缓存目录："+filemedir.getPath());
        findPreference("setting_sound").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                deletebuffer();
                findPreference("setting_sound").setSummary("当前缓存："+0+"M"
                        +" \n缓存目录："+filemedir.getPath());
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(mChangeListener);
    }


    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(mChangeListener);
    }

    @Override
    public void init() {
        filemedir=new File(Environment.getExternalStorageDirectory()+File.separator
                +"开发专用文件夹"+File.separator+"新闻图片");
    }

    @Override
    public long querybuffer() {
        long length=0;
        if(filemedir.isDirectory()) {
            for(File file:filemedir.listFiles())
            {
              length=file.length()+length;
            }
        }
        return length;
    }

    @Override
    public void deletebuffer() {
        if(filemedir.isDirectory()) {
            for(File file:filemedir.listFiles())
            {
               file.delete();
            }
        }
    }
}

