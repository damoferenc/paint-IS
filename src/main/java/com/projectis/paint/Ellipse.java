package com.projectis.paint;

import javafx.scene.paint.Color;

import java.io.Serializable;

public class Ellipse implements Serializable, Savable{
    private double x;
    private double y;
    private double widthX;
    private double widthY;
    private double v;
    private double v1;
    private double v2;
    private double v3;

    public Ellipse(double x, double y, double widthX, double widthY, Color color){
        this.x = x;
        this.y = y;
        this.widthX = widthX;
        this.widthY = widthY;
        this.v = color.getRed();
        this.v1 = color.getGreen();
        this.v2 = color.getBlue();
        this.v3 = color.getOpacity();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidthX() {
        return widthX;
    }

    public double getWidthY() {
        return widthY;
    }

    public double getV() {
        return v;
    }

    public double getV1() {
        return v1;
    }

    public double getV2() {
        return v2;
    }

    public double getV3() {
        return v3;
    }
}
