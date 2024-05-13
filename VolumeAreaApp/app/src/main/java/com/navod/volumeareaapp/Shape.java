package com.navod.volumeareaapp;

public class Shape {
    private int shapeImg;
    private String shapeName;

    public Shape() {
    }

    public Shape(int shapeImg, String shapeName) {
        this.shapeImg = shapeImg;
        this.shapeName = shapeName;
    }

    public int getShapeImg() {
        return shapeImg;
    }

    public void setShapeImg(int shapeImg) {
        this.shapeImg = shapeImg;
    }

    public String getShapeName() {
        return shapeName;
    }

    public void setShapeName(String shapeName) {
        this.shapeName = shapeName;
    }

    @Override
    public String toString() {
        return "Shape{" +
                "shapeImg=" + shapeImg +
                ", shapeName='" + shapeName + '\'' +
                '}';
    }
}
