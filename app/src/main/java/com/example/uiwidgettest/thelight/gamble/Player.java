package com.example.uiwidgettest.thelight.gamble;

/**
 * Created by 40774 on 2017/10/2.
 */

public class Player {
    private String name;
    private int Gold=1000;
    private int Silver=1000;

    public Player(String name)
    {
        this.name=name;
    }
    public int getGold() {
        return Gold;
    }

    public void setGold(int gold) {
        Gold = gold;
    }

    public void setSilver(int silver) {
        Silver = silver;
    }

    public int getSilver() {
        return Silver;
    }

    public String getName() {
        return name;
    }
}
