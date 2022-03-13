package com.projectis.paint;

import javafx.scene.canvas.GraphicsContext;

public interface Drawable {
    public void draw(GraphicsContext tool, double xStart, double yStart);
}
