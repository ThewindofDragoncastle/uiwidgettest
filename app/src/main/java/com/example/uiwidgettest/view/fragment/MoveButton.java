package com.example.uiwidgettest.view.fragment;

import android.animation.ObjectAnimator;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.view.view.AnimatorView;
import com.example.uiwidgettest.view.view.Customview;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 40774 on 2017/11/1.
 */

public class MoveButton extends Fragment {
    private boolean IsFirst=true;
//    动画往回滑动
    private boolean IsBack=false;
    private final String TAG=getClass().getName();
    private boolean IsMove=false;
    private final String FLOATBUTTON_TOP="floatbuttontop";
    private final String FLOATBUTTON_LEFT="floatbuttonleft";
    private final String FLOATBUTTON_BOTTOM="floatbuttonbuttom";
    private final String FLOATBUTTON_RIGHT="floatbuttonright";
    private int lastX=0;
    private int lastY=0;
    //缓存按钮位置。。。。
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
//    非属性动画
    @BindView(R.id.customview)
    Customview customview;
//    可滑动的动画
    @BindView(R.id.animatorview)
    AnimatorView animatorView;
    @BindView(R.id.floatactionbutton)
    FloatingActionButton actionButton;
    @OnClick(R.id.soomthmove)
    public void onclickSoomthmove()
    {
        animatorView.smoothScrollTo(-200,-300);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.floatactionbutton,container,false);
        //缓存器初始化 以及悬浮按钮位置初始化
        preferences=PreferenceManager.getDefaultSharedPreferences(getContext());
        ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MyLog.i(TAG,"LEFT:"+preferences.getInt(FLOATBUTTON_LEFT,0)
                +"\nRIGHT:"+preferences.getInt(FLOATBUTTON_RIGHT,0)+
                "\nTOP:"+preferences.getInt(FLOATBUTTON_TOP,0)
                +"\nBOTTOM:"+preferences.getInt(FLOATBUTTON_BOTTOM,0));
//        int x=preferences.getInt(FLOATBUTTON_TOP,0);
//        int y=preferences.getInt(FLOATBUTTON_LEFT,0);
//        ViewGroup.MarginLayoutParams layoutParams=(ViewGroup.MarginLayoutParams)actionButton.getLayoutParams();
//        layoutParams.leftMargin=x;
//        layoutParams.topMargin=y;
//        actionButton.setLayoutParams(layoutParams);
        editor=PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IsFirst) {
                    //        如果不存在数据就不设置
                    if (preferences.getInt(FLOATBUTTON_LEFT, 0) != 0)
                        actionButton.layout(
                                preferences.getInt(FLOATBUTTON_LEFT, 0)
                                , preferences.getInt(FLOATBUTTON_TOP, 0)
                                , preferences.getInt(FLOATBUTTON_RIGHT, 0)
                                , preferences.getInt(FLOATBUTTON_BOTTOM, 0));
                    IsFirst=false;
                }else
                Snackbar.make(actionButton,"九阳神功开始运功。",Snackbar.LENGTH_SHORT)
                        .setAction("激发潜力", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(!IsBack) {
                                    ObjectAnimator.ofFloat(animatorView
                                            , "translationX", 0, 300).setDuration(2000)
                                            .start();
                                    Toast.makeText(getContext(),"修炼速度提升至两倍",Toast.LENGTH_SHORT).show();
                                    IsBack=true;
                                }
                                else {
                                    ObjectAnimator.ofFloat(animatorView
                                            , "translationX", 300, 0).setDuration(1000)
                                            .start();
                                    IsBack=false;
                                    Toast.makeText(getContext(),"修炼速度快速倒退",Toast.LENGTH_SHORT).show();
                                }

                            }
                        }).show();
            }
        });

        actionButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                IsMove=true;
                return false;
            }
        });
        actionButton.setOnTouchListener(new View.OnTouchListener() {
            int moveX = 0;
            int moveY = 0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (IsMove) {
                    int X = (int) event.getX();
                    int Y = (int) event.getY();
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            lastX = X;
                            lastY = Y;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            moveX = X - lastX;
                            moveY = Y - lastY;
//                            MyLog.d(TAG,"原左坐标："+actionButton.getLeft() + " 移动距离："+moveX);
                            actionButton.layout(actionButton.getLeft() + moveX
                                , actionButton.getTop() + moveY
                                , actionButton.getRight() + moveX
                                , actionButton.getBottom() + moveY);

//                            MyLog.d(TAG,"左坐标："+actionButton.getLeft() + moveX);
//                            RelativeLayout.LayoutParams layoutParams=(  RelativeLayout.LayoutParams)actionButton.getLayoutParams();
//                            layoutParams.height=FrameLayout.LayoutParams.MATCH_PARENT;
//                            layoutParams.width=FrameLayout.LayoutParams.MATCH_PARENT;
//                            layoutParams.setMargins(actionButton.getLeft()+moveX,
//                                    actionButton.getTop()+moveY,
//                                    actionButton.getRight()-moveX,
//                                    actionButton.getBottom()-moveY);
//                            layoutParams.leftMargin=actionButton.getLeft()+moveX;
//                            layoutParams.topMargin=actionButton.getTop()+moveY;
//                            actionButton.setLayoutParams(layoutParams);
//                            MyLog.d(TAG,"MoveX:"+moveX+" MoveY:"+moveY);
                        break;
                        case MotionEvent.ACTION_UP:
//                            松开时保存数据
                            MyLog.d(TAG,"放开按钮\n");
//保存的位置应该是最后手指停留的位置 也就是getleft。。等等不能是移动距离。
                            editor.putInt(FLOATBUTTON_TOP,actionButton.getTop());
                            editor.putInt(FLOATBUTTON_LEFT,actionButton.getLeft());
                            editor.putInt(FLOATBUTTON_RIGHT,actionButton.getRight());
                            editor.putInt(FLOATBUTTON_BOTTOM, actionButton.getBottom());
                            editor.apply();
                            IsMove=false;
                            break;
                    }
                    return true;
                }
                else
                return false;
                }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               getActivity().runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       actionButton.performClick();
                   }
               });
            }
        }).start();
        customview.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.tranlatefu));
//        customview.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.tranlatezheng));
}

}
