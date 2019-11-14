package com.bri64.gol;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GameOfLife extends JFrame {

    public static void main(String[] a) {
        GameOfLife m = new GameOfLife();
        m.setResizable(false);
        m.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Set<Point2D> prefilled = new HashSet<>(Arrays.asList(
                new Point2D.Double(5, 1),
                new Point2D.Double(5, 2),
                new Point2D.Double(6, 1),
                new Point2D.Double(6, 2),
                new Point2D.Double(5, 11),
                new Point2D.Double(6, 11),
                new Point2D.Double(7, 11),
                new Point2D.Double(4, 12),
                new Point2D.Double(3, 13),
                new Point2D.Double(3, 14),
                new Point2D.Double(8, 12),
                new Point2D.Double(9, 13),
                new Point2D.Double(9, 14),
                new Point2D.Double(6, 15),
                new Point2D.Double(4, 16),
                new Point2D.Double(5, 17),
                new Point2D.Double(6, 17),
                new Point2D.Double(7, 17),
                new Point2D.Double(6, 18),
                new Point2D.Double(8, 16),
                new Point2D.Double(3, 21),
                new Point2D.Double(4, 21),
                new Point2D.Double(5, 21),
                new Point2D.Double(3, 22),
                new Point2D.Double(4, 22),
                new Point2D.Double(5, 22),
                new Point2D.Double(2, 23),
                new Point2D.Double(6, 23),
                new Point2D.Double(1, 25),
                new Point2D.Double(2, 25),
                new Point2D.Double(6, 25),
                new Point2D.Double(7, 25),
                new Point2D.Double(3, 35),
                new Point2D.Double(4, 35),
                new Point2D.Double(3, 36),
                new Point2D.Double(4, 36)
        ));
        LifeGrid xyz = new LifeGrid(800, 800, 50, 50, prefilled);
        xyz.addKeyListener(xyz);
        xyz.setFocusable(true);
        m.add(xyz);
        m.pack();

        m.setVisible(true);
    }
}