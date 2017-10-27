package com.example.uiwidgettest.framework.annotation.useclass;

import java.lang.reflect.Method;

/**
 * Created by 40774 on 2017/10/12.
 */

public class AnnotnationProcessor {
    public static void main(String[] args)
    {
        Method[] methods=AnnotnationnTest.class.getDeclaredMethods();
        for (Method m:methods)
        {
            Get get=m.getAnnotation(Get.class);
            System.out.println(get.value());
        }
    }
}
class AnnotnationnTest
{
@Get(value = ""+12345)
public String value()
{
    return "3456";
}
}
