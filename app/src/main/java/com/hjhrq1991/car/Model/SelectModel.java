package com.hjhrq1991.car.Model;

/**
 * @author hjhrq1991 created at 1/10/16 16 36.
 * @Package com.hjhrq1991.car.Model
 * @Description:
 */
public class SelectModel {

    private String name;

    private int index;

    public SelectModel(){}

    public SelectModel(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
