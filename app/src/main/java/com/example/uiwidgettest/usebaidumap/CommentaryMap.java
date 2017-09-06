package com.example.uiwidgettest.usebaidumap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.uiwidgettest.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class CommentaryMap extends AppCompatActivity {
    private MapView mapView;
    public LocationClient client;
    private BaiduMap map;
    private Boolean IsFirst=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_commentary_map);
        client=new LocationClient(getApplicationContext());
        client.registerLocationListener(new LocationFound1());
        mapView=(MapView)findViewById(R.id.BaiduMapView);
        IsFirst=true;
        map=mapView.getMap();
        map.setMyLocationEnabled(true);
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
            RequestData();
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
                    RequestData();
                }else
                {
                    Toast.makeText(getBaseContext(),"程序出现意外！",Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }
private void RequestData()
{
    LocationClientOption option=new LocationClientOption();
    option.setIsNeedAddress(true);
    option.setScanSpan(5000);
    client.setLocOption(option);
    client.start();
}

private void navigateTo(BDLocation bdLocation)
{
    LatLng latLng;
    MapStatusUpdate update=null;
    while (IsFirst)
    {
        update=MapStatusUpdateFactory.zoomTo(16f);
        map.animateMapStatus(update);
        latLng=new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
        Log.d("CommentaryMap:","纬度："+bdLocation.getLatitude()+"\n经度："+bdLocation.getLongitude());
        update = MapStatusUpdateFactory.newLatLng(latLng);
        map.animateMapStatus(update);
        IsFirst=false;
    }
     latLng=new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
     update= MapStatusUpdateFactory.newLatLng(latLng);
    map.animateMapStatus(update);
    MyLocationData.Builder builder=new MyLocationData.Builder();
    builder.latitude(bdLocation.getLatitude());
    builder.longitude(bdLocation.getLongitude());
    MyLocationData data=builder.build();
    map.setMyLocationData(data);
}
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        client.stop();
        IsFirst=true;
        map.setMyLocationEnabled(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Commentary:","重新开始");
        mapView.onResume();
        IsFirst=true;
    }
    private class LocationFound1 implements BDLocationListener
    {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if(bdLocation.getLocType()==BDLocation.TypeNetWorkLocation||
                    bdLocation.getLocType()==BDLocation.TypeGpsLocation) {
                navigateTo(bdLocation);
                Log.d("Commentary:","移动位置！");
            }
        }
    }
}

