package com.navod.spartsapp.model;

public class Sport {
    private String sportName;
    private int sportImg;

    public Sport() {
    }

    public Sport(String sportName, int sportImg) {
        this.sportName = sportName;
        this.sportImg = sportImg;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public int getSportImg() {
        return sportImg;
    }

    public void setSportImg(int sportImg) {
        this.sportImg = sportImg;
    }

    @Override
    public String toString() {
        return "Sport{" +
                "sportName='" + sportName + '\'' +
                ", sportImg=" + sportImg +
                '}';
    }
}
