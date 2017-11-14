package com.example.uiwidgettest.view.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import com.example.uiwidgettest.MyLog;

/**
 * Created by 40774 on 2017/11/2.
 */

public class AnimatorView extends View{
        private int lastX=0;
        private int lastY=0;
//        平滑效果
    Scroller mscoller;
        private final String TAG=getClass().getName();
        public AnimatorView(Context context) {
            super(context);
        }
        public AnimatorView(Context context, AttributeSet set, int def) {
            super(context,set,def);
        }
        public AnimatorView(Context context, AttributeSet set) {
//            增加平滑移动
            super(context,set);
            mscoller=new Scroller(context);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            int X=(int) event.getX();
            int Y=(int) event.getY();
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    lastY=X;
                    lastX=Y;
                    break;
                case MotionEvent.ACTION_MOVE:
                    int MoveX=X-lastX;
                    int MoveY=Y-lastY;
                    ((View)getParent()).scrollBy(-MoveX,-MoveY);
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
            return true;
        }

    @Override
    public void computeScroll() {
        super.computeScroll();
//        MyLog.d(TAG,"重绘开始");
        if(mscoller.computeScrollOffset())
        {
            ((View)getParent()).scrollTo(mscoller.getCurrX(),mscoller.getCurrY());
            invalidate();
//            MyLog.d(TAG,"平滑移动");
        }
    }
    public void smoothScrollTo(int destX,int destY)
    {
        int scrollX=getScrollX();
        int scrollY=getScrollY();
        int delta=destX-scrollX;
        mscoller.startScroll(scrollX,scrollY,delta,destY,2000);
        invalidate();
    }
}
