package com.example.uiwidgettest.mvpupdatechat.view.fragment;

/**
 * Created by 40774 on 2017/10/16.
 */

public class FragmentCollector {
    private static Login login;
    public static Login GetLogin()
    {
        if (login==null)
            login=new Login();
        return login;
    }
    private static Intact intact;
    public static Intact GetIntact()
    {
        if (intact==null)
            intact=new Intact();
        return intact;
    }
    private static Chat chat=null;
    public static Chat GetChat()
    {
        if(chat==null)
            chat=new Chat();
        return chat;
    }
}
