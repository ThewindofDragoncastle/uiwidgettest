package com.example.uiwidgettest.designpattern.support.simfactory;

public class Factory {
//    简单工厂类
    Computer computer=null;
    public static Computer getComputer(String name) {
        switch (name)
        {
            case "联想":
                return new LenovoComputer();
            case "惠普":
                return new HpComputer();
            case "华硕":
                return new AsusComputer();
                default:
                    return null;
        }
    }

}
