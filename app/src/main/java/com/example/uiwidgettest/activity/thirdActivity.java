package com.example.uiwidgettest.activity;

import android.os.Bundle;
import android.util.Log;
import com.example.uiwidgettest.R;
public class thirdActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Log.d("第三个活动","得到的id是："+getTaskId());

    }
}
