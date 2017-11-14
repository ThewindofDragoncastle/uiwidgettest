package com.example.uiwidgettest.view.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.uiwidgettest.MyLog;

/**
 * Created by 40774 on 2017/11/2.
 */

public class Customview extends View {
    private int lastX=0;
    private int lastY=0;
    private final String TAG=getClass().getName();
    public Customview(Context context) {
        super(context);
    }
    public Customview(Context context, AttributeSet set, int def) {
        super(context,set,def);
    }
    public Customview(Context context, AttributeSet set) {
        super(context,set);
    }
}
