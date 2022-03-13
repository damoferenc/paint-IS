package com.projectis.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.Math.min;

public class SavableObject implements Serializable, Savable, Drawable {

    private ArrayList<Savable> savables;

    public SavableObject(){
        savables = new ArrayList<Savable>();
    }

    public SavableObject(ArrayList<Savable> list){
        savables = list;
    }

    public void addObject(Savable savable){
        savables.add(savable);
    }

    @Override
    public void draw(GraphicsContext tool, double xStart, double yStart) {
        for(Savable item : savables){
            if (item instanceof Point){
                Point point = (Point) item;
                tool.setFill(new Color(point.getV(), point.getV1(), point.getV2(), point.getV3()));
                tool.fillRoundRect(point.getX() + xStart, point.getY() + yStart, 5, 5, 5, 5);
            }
            if (item instanceof Line){
                Line line = (Line) item;
                tool.setStroke(new Color(line.getV(), line.getV1(), line.getV2(), line.getV3()));
                tool.strokeLine(line.getX() + xStart, line.getY() + yStart, line.getX2() + xStart, line.getY2() + yStart);
            }
            if (item instanceof Ellipse){
                Ellipse ellipse = (Ellipse) item;
                tool.setStroke(new Color(ellipse.getV(), ellipse.getV1(), ellipse.getV2(), ellipse.getV3()));
                tool.strokeOval(ellipse.getX() + xStart, ellipse.getY() + yStart, ellipse.getWidthX(),ellipse.getWidthY());
            }
            if (item instanceof Rectangle){
                Rectangle rectangle = (Rectangle) item;
                tool.setStroke(new Color(rectangle.getV(), rectangle.getV1(), rectangle.getV2(), rectangle.getV3()));
                tool.strokeRect(rectangle.getX() + xStart,rectangle.getY() + yStart, rectangle.getWidthX(),rectangle.getWidthY());
            }
            if(item instanceof SavableObject){
                SavableObject savableObject = (SavableObject) item;
                savableObject.draw(tool, xStart, yStart);
            }
        }
    }
}
