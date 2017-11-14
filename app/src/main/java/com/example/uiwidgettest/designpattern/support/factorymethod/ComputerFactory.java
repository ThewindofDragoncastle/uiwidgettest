package com.example.uiwidgettest.designpattern.support.factorymethod;

import com.example.uiwidgettest.designpattern.support.simfactory.Computer;

/**
 * Created by 40774 on 2017/11/5.
 */

public abstract class ComputerFactory {
    public abstract <T extends Computer>T getComputer(Class<T> clz);
}
