package com.bkset.vutuan.wisun.model;

/**
 * Created by vutuan on 02/05/2018.
 */

public class Datapackage {
    private int Id;
    private String CreatedDate;
    private String Temp;
    private String Humi;
    private String Gas;
    private String Dust;

    public Datapackage(int id, String createdDate, String temp, String humi, String gas, String dust) {
        Id = id;
        CreatedDate = createdDate;
        Temp = temp;
        Humi = humi;
        Gas = gas;
        Dust = dust;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getTemp() {
        return Temp;
    }

    public void setTemp(String temp) {
        Temp = temp;
    }

    public String getHumi() {
        return Humi;
    }

    public void setHumi(String humi) {
        Humi = humi;
    }

    public String getGas() {
        return Gas;
    }

    public void setGas(String gas) {
        Gas = gas;
    }

    public String getDust() {
        return Dust;
    }

    public void setDust(String dust) {
        Dust = dust;
    }
}
