package com.example.uiwidgettest.ui;

import java.io.Serializable;
import java.net.Socket;

/**
 * Created by 40774 on 2017/9/2.
 */

public class Warehousesocket implements Serializable
{
    Socket s;
    public Warehousesocket(Socket socket)
    {
        s=socket;
    }
}
