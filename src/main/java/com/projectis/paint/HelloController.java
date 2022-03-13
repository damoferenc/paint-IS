package com.projectis.paint;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.lang.Math.min;

public class HelloController implements Initializable {

    boolean pencilOn = false;
    boolean pointOn = false;
    boolean lineOn = false;
    boolean polyLineOn = false;
    boolean circleOn = false;
    boolean ellipseOn = false;
    boolean squareOn = false;
    boolean rectangleOn = false;
    boolean lineEnd = false;
    boolean polyLineStart = false;
    boolean circleEnd = false;
    boolean ellipseEnd = false;
    boolean squareEnd = false;
    boolean rectangleEnd = false;
    GraphicsContext tool;
    double oldX;
    double oldY;
    double startX;
    double startY;
    ArrayList<Savable> objects;
    ArrayList<File> fileList;
    ArrayList<String> fileNames;
    boolean loadOn = false;


    void refresh(){
        pencilOn = false;
        pointOn = false;
        lineOn = false;
        polyLineOn = false;
        circleOn = false;
        ellipseOn = false;
        squareOn = false;
        rectangleOn = false;
        loadOn = false;
    }

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Canvas canvas;

    @FXML
    private TextField textField;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    public void pencilOn(ActionEvent e){
        refresh();
        pencilOn = true;
    }

    @FXML
    public void pointOn(ActionEvent e){
        refresh();
        pointOn = true;
    }

    @FXML
    public void lineOn(ActionEvent e){
        refresh();
        lineOn = true;
        lineEnd = false;
    }

    @FXML
    public void polyLineOn(ActionEvent e){
        refresh();
        polyLineOn = true;
        polyLineStart = false;
    }

    @FXML
    public void circleOn(ActionEvent e){
        refresh();
        circleOn = true;
        circleEnd = false;
    }

    @FXML
    public void ellipseOn(ActionEvent e){
        refresh();
        ellipseOn = true;
        ellipseEnd = false;
    }

    @FXML
    public void squareOn(ActionEvent e){
        refresh();
        squareOn = true;
        squareEnd = false;
    }

    @FXML
    public void rectangleOn(ActionEvent e){
        refresh();
        rectangleOn = true;
        rectangleEnd = false;
    }

    @FXML
    public void saveOn(ActionEvent e){
        try (FileOutputStream fos = new FileOutputStream(textField.getText() + ".paint");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            SavableObject savableObject = new SavableObject(objects);
            oos.writeObject(savableObject);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        File[] files = new File(".").listFiles();
        fileList = new ArrayList<>();
        fileNames = new ArrayList<>();
        for (File file : files) {
            if (file.isFile()) {
                if(file.getName().endsWith(".paint")){
                    fileList.add(file);
                    fileNames.add(file.getName().substring(0,file.getName().length() - 6));
                    comboBox.getItems().clear();
                    comboBox.getItems().setAll(fileNames);
                }
            }
        }
    }

    @FXML
    public void loadOn(ActionEvent e){
        String name = comboBox.getValue();
        int index = 0;
        for (String fileName : this.fileNames){
            if (name.equals(fileName)){
                break;
            }
            index ++;
        }
        File file = fileList.get(index);
        try
        {
            FileInputStream fileInputStream = new FileInputStream(file.getName());
            ObjectInputStream in = new ObjectInputStream(fileInputStream);

            SavableObject savableObject = (SavableObject) in.readObject() ;
            in.close();
            fileInputStream.close();
            objects.add(savableObject);
        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught");
            ex.printStackTrace();
        }

        catch(ClassNotFoundException ex)
        {
            System.out.println("ClassNotFoundException is caught");
        }
        refresh();
        loadOn = true;
    }

    @FXML
    public void clearOn(ActionEvent e){
        tool.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        objects = new ArrayList<>();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        objects = new ArrayList<>();
        fileList = new ArrayList<>();
        fileNames = new ArrayList<>();
        File[] files = new File(".").listFiles();

        for (File file : files) {
            if (file.isFile()) {
                if(file.getName().endsWith(".paint")){
                    fileList.add(file);
                    fileNames.add(file.getName().substring(0,file.getName().length() - 6));
                    comboBox.getItems().setAll(fileNames);
                }
            }
        }
        tool = canvas.getGraphicsContext2D();
        canvas.setOnMouseDragged( e -> {
            double x = e.getX();
            double y = e.getY();
            Color color = colorPicker.getValue();
            if(pencilOn){
                tool.setFill(colorPicker.getValue());
                tool.fillRoundRect(x, y, 5, 5, 5, 5);
                objects.add(new Point(x,y,color));
            }
        });
        canvas.setOnMouseClicked(e -> {
            double x = e.getX();
            double y = e.getY();
            Color color = colorPicker.getValue();
            tool.setFill(colorPicker.getValue());
            tool.setStroke(colorPicker.getValue());
            if(pointOn){
                tool.fillRoundRect(x, y, 5, 5, 5, 5);
                objects.add(new Point(x,y,color));
            }
            if(lineOn){
                if(lineEnd){
                    tool.strokeLine(oldX, oldY, x, y);
                    objects.add(new Line(oldX, oldY, x, y, color));
                    lineEnd = false;
                }
                else{
                    oldX = x;
                    oldY = y;
                    lineEnd = true;
                }
            }
            if(polyLineOn){
                if(polyLineStart){
                    if (x < startX + 5 && x > startX - 5 && y < startY + 5 && y > startY - 5){
                        tool.strokeLine(oldX, oldY, startX, startY);
                        objects.add(new Line(oldX, oldY, x, y, color));
                        polyLineStart = false;
                    }
                    else{
                        tool.strokeLine(oldX, oldY, x, y);
                        oldX = x;
                        oldY = y;
                    }
                }
                else{
                    startX = x;
                    startY = y;
                    oldX = x;
                    oldY = y;
                    polyLineStart = true;
                }
            }
            if(circleOn){
                if(circleEnd){
                    double width = Math.max(java.lang.Math.abs(x - oldX), java.lang.Math.abs(y - oldY));
                    tool.strokeOval(min(x, oldX), min(y, oldY), width, width);
                    objects.add(new Ellipse(min(x, oldX), min(y, oldY), width, width, color));
                    circleEnd = false;
                }
                else{
                    circleEnd = true;
                    oldX = x;
                    oldY = y;
                }
            }
            if(ellipseOn){
                if(ellipseEnd){
                    tool.strokeOval(min(x, oldX), min(y, oldY), java.lang.Math.abs(x - oldX), java.lang.Math.abs(y - oldY));
                    objects.add(new Ellipse(min(x, oldX), min(y, oldY), java.lang.Math.abs(x - oldX), java.lang.Math.abs(y - oldY), color));
                    ellipseEnd = false;
                }
                else{
                    ellipseEnd = true;
                    oldX = x;
                    oldY = y;
                }
            }
            if(squareOn){
                if(squareEnd){
                    double width = Math.max(java.lang.Math.abs(x - oldX), java.lang.Math.abs(y - oldY));
                    tool.strokeRect(min(x, oldX), min(y, oldY), width, width);
                    objects.add(new Rectangle(min(x, oldX), min(y, oldY), width, width, color));
                    squareEnd = false;
                }
                else{
                    squareEnd = true;
                    oldX = x;
                    oldY = y;
                }
            }
            if(rectangleOn){
                if(rectangleEnd){
                    tool.strokeRect(min(x, oldX), min(y, oldY), java.lang.Math.abs(x - oldX), java.lang.Math.abs(y - oldY));
                    objects.add(new Rectangle(min(x, oldX), min(y, oldY), java.lang.Math.abs(x - oldX), java.lang.Math.abs(y - oldY), color));
                    rectangleEnd = false;
                }
                else{
                    rectangleEnd = true;
                    oldX = x;
                    oldY = y;
                }
            }
            if(loadOn){
                loadOn = false;
                SavableObject object = (SavableObject)objects.get(objects.size() - 1);
                object.draw(tool, x, y);
            }

        });
    }
}