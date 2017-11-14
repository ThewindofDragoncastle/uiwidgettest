package com.example.uiwidgettest.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.uiwidgettest.R;

import butterknife.BindView;

public class ViewActivity extends AppCompatActivity {
    @BindView(R.id.fragmentplace)
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
    }
}
