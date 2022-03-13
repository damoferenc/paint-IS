package com.projectis.paint;

import javafx.scene.paint.Color;

import java.io.Serializable;

public class Point implements Serializable, Savable{
    private double x;
    private double y;
    private double v;
    private double v1;
    private double v2;
    private double v3;

    public Point(double x, double y, Color color){
        this.x = x;
        this.y = y;
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
