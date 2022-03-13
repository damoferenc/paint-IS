package com.projectis.paint;

import javafx.scene.paint.Color;

import java.io.Serializable;

public class Line implements Serializable, Savable{
    private double x;
    private double y;
    private double x2;
    private double y2;
    private double v;
    private double v1;
    private double v2;
    private double v3;

    public Line(double x, double y, double x2, double y2, Color color){
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
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

    public double getX2() {
        return x2;
    }

    public double getY2() {
        return y2;
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
