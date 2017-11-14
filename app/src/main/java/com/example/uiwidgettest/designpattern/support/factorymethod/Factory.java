package com.example.uiwidgettest.designpattern.support.factorymethod;

import com.example.uiwidgettest.designpattern.support.simfactory.Computer;

/**
 * Created by 40774 on 2017/11/5.
 */

public class Factory extends ComputerFactory {
    @Override
    public <T extends Computer> T getComputer(Class<T> clz) {
        try {
            try {
                Computer computer=(Computer) Class.forName(clz.getName()).newInstance();
                return (T)computer;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
