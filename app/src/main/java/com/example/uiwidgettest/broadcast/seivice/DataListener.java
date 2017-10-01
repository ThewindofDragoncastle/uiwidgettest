package com.example.uiwidgettest.broadcast.seivice;

import java.io.Serializable;

/**
 * Created by 40774 on 2017/9/16.
 */

public interface DataListener extends Serializable {
    void ReturnData(String data);
    void Already();
}
