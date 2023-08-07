package com.juegopp.object;

import java.awt.Color;
import java.awt.Graphics;

public class Circle {
    private int x;
    private int y;
    private int size;
    private Color color;

    public Circle(int x, int y, int size, Color color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x * size, y * size, size, size);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
