package com.example.uiwidgettest.myreview.json;

/**
 * Created by 40774 on 2017/10/2.
 */

public class Hero {
    private String name="涛哥";
    private String cognomen="江涛哥";
    private boolean gender=true;
    private int energy=0;
    private int force=0;
    private int wit=0;
    private int loyal=0;
    private int virtue=0;
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getEnergy() {
        return energy;
    }

    public int getForce() {
        return force;
    }

    public boolean getGender() {
        return gender;
    }

    public int getLoyal() {
        return loyal;
    }

    public int getVirtue() {
        return virtue;
    }

    public int getWit() {
        return wit;
    }

    public String getCognomen() {
        return cognomen;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCognomen(String cognomen) {
        this.cognomen = cognomen;
    }

    public void setEnergy(int energe) {
        this.energy = energe;
    }

    public void setForce(int force) {
        this.force = force;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public void setLoyal(int loyal) {
        this.loyal = loyal;
    }

    public void setVirtue(int virtue) {
        this.virtue = virtue;
    }

    public void setWit(int wit) {
        this.wit = wit;
    }
}
