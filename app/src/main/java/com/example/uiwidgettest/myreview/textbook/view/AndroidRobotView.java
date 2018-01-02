package com.example.uiwidgettest.myreview.textbook.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 40774 on 2017/12/29.
 */

public class AndroidRobotView extends View {
    private float width=0;
    private float height=0;
    private Paint robotPaint;
    private int Size=1;
    public AndroidRobotView(Context context) {
        super(context);
        initPath();
    }
    public AndroidRobotView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPath();
    }
    public AndroidRobotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPath();
    }

    private void initPath()
    {
        robotPaint=new Paint();
        robotPaint.setAntiAlias(true);
        robotPaint.setColor(0xFFA4C739);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(width/2-55*Size,height/2-150*Size);
//        边界左右上下离上和左边界距离

        RectF rectF_head=new RectF(10*Size,10*Size,100*Size,100*Size);
//        偏移
//        经过平移
        rectF_head.offset(0,20);
        canvas.drawArc(rectF_head,-10,-160,false,robotPaint);
//        eyes
        robotPaint.setColor(Color.WHITE);
        float length_x=rectF_head.right-rectF_head.left;
        float length_y=rectF_head.bottom-rectF_head.top;
        float a=rectF_head.top+43/90.0f*length_y;
//        以固定比例安放眼睛
//        这里算法是错误的 是强行弄到一起的 留待以后解决
        canvas.drawCircle(rectF_head.left+25/90.0f*length_x,rectF_head.bottom-20*Size-47/90.0f*length_y,4*Size,robotPaint);
        canvas.drawCircle(rectF_head.left+65/90.0f*length_x,rectF_head.bottom-20*Size-47/90.0f*length_y,4*Size,robotPaint);
//        天线
        robotPaint.setColor(0xFFA4C739);
        robotPaint.setStrokeWidth(2);
        canvas.drawLine(20*Size,15*Size,35*Size,35*Size,robotPaint);
        canvas.drawLine(90*Size,15*Size,75*Size,35*Size,robotPaint);
//        body
        canvas.drawRect(10*Size,75*Size,100*Size,150*Size,robotPaint);
        RectF rectF=new RectF(10*Size,140*Size,100*Size,160*Size);
//        rx：x方向上的圆角半径。
//        ry：y方向上的圆角半径。
        canvas.drawRoundRect(rectF,10,10,robotPaint);
//        手臂
        RectF rectF_hand=new RectF(-15*Size,75*Size,5*Size,140*Size);
        canvas.drawRoundRect(rectF_hand,10,10,robotPaint);
        rectF_hand.offset(120*Size,0);
        canvas.drawRoundRect(rectF_hand,10,10,robotPaint);
//        腿
        RectF rectF_leg=new RectF(25*Size,150*Size,45*Size,200*Size);
        canvas.drawRoundRect(rectF_leg,10,10,robotPaint);
        rectF_leg.offset(40*Size,0);
        canvas.drawRoundRect(rectF_leg,10,10,robotPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width=w;
        height=h;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
    public void setSize(int size)
    {
        this.Size=size;
        invalidate();
    }

}
