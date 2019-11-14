package com.bri64.gol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class GridPanel extends JPanel implements KeyListener {
    protected int width;
    protected int height;
    protected int cols;
    protected int rows;

    public int getWidthSize() {
        return width / cols;
    }

    public int getHeightSize() {
        return height / rows;
    }

    public GridPanel(int width, int height, int cols, int rows) {
        this.width = width;
        this.height = height;
        this.cols = cols;
        this.rows = rows;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                clickTile(e.getX() / getWidthSize(), e.getY() / getHeightSize());
            }
        });
    }

    public abstract void clickTile(int x, int y);

    @Override
    public void paintComponent(Graphics g) { }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) { }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
}