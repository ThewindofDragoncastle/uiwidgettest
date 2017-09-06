package com.example.uiwidgettest.usebaidumap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.example.uiwidgettest.MessageLogin;
import com.example.uiwidgettest.R;

import java.util.ArrayList;
import java.util.List;

public class BaidumapTextView extends AppCompatActivity {

    public LocationClient client;
    static TextView Locationinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidumap_text_view);
        Locationinfo=(TextView) findViewById(R.id.Locationinfo);
        client=new LocationClient(getApplicationContext());
        client.registerLocationListener(new Locationfound());
        List<String> permissionlist=new ArrayList<String>();
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)!=
        PackageManager.PERMISSION_GRANTED)
        {
            permissionlist.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED)
        {
            permissionlist.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED)
        {
            permissionlist.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(!permissionlist.isEmpty())
        {
            String[] permissions=permissionlist.toArray(new String[permissionlist.size()]);
            ActivityCompat.requestPermissions(this,permissions,1);
        }
        else
        {
            requestLocation();

        }
    }
private void requestLocation()
    {
        LocationClientOption option=new LocationClientOption();
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        client.setLocOption(option);
        client.start();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case 1:
                if (grantResults.length>0) {
                    for(int result:grantResults)
                    {
                        if (result != PackageManager.PERMISSION_GRANTED)
                        {
                            Toast.makeText(getBaseContext(),"你必须同意所有的权限才能使用本程序！",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }else
                {
                    Toast.makeText(getBaseContext(),"程序出现意外！",Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        client.stop();
    }
}
class Locationfound implements BDLocationListener
{
    private StringBuilder First=new StringBuilder("");
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        StringBuilder currentLocation=new StringBuilder("");
        if (bdLocation.getStreet()!=null)
        currentLocation.append("国家："+bdLocation.getCountry()+"\n城市:"+bdLocation.getCity()
        +"\n区县："+bdLocation.getDistrict()+"\n街道："+bdLocation.getStreet()+"\n" +"街道号码："
                +bdLocation.getStreetNumber()+
                "\n纬度："+bdLocation.getLatitude()+"\n经度："+bdLocation.getLongitude()+"\n定位方式："
       );
        else
            currentLocation.append("纬度："+bdLocation.getLatitude()+"" +
                    "\n经度："+bdLocation.getLongitude()+"\n定位方式："
            );
        if ( bdLocation.getLocType()==BDLocation.TypeGpsLocation)
            currentLocation.append("GPS定位");
        else if (bdLocation.getLocType()==BDLocation.TypeNetWorkLocation)
            currentLocation.append("网络定位");
        BaidumapTextView.Locationinfo.setText(currentLocation.toString());
        if(!First.toString().equals(currentLocation.toString())) {
            MessageLogin login = new MessageLogin("百度位置信息.txt", "" + currentLocation.toString());
            login.CreateLogin();
            Log.d("BaidTextView：","百度位置信息.txt"+" 写入数据成功");
        }
        First.replace(0,First.length(),currentLocation.toString());
    }
}