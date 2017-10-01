package com.example.uiwidgettest.myreview;

/**
 * Created by 40774 on 2017/9/30.
 */

public class ButtonAndText {
    private String ButtonName;
    private String Text;
    //每个对象的唯一编号
    private final int CODE;

    public int getCODE() {
        return CODE;
    }

    public String getButtonName() {
        return ButtonName;
    }

    public String getText() {
        return Text;
    }
   public ButtonAndText(int code)
  {
    CODE=code;
  }
    public void setButtonName(String buttonName) {
        ButtonName = buttonName;
    }

    public void setText(String text) {
        Text = text;
    }
}
