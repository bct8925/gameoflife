package com.bri64.gol;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.*;

public class LifeGrid extends GridPanel {
    private List<List<LifeTile>> backup;
    private List<List<LifeTile>> tiles;
    private Timer timer = new Timer();
    private boolean isRunning = false;
    private int speed = 250;

    public void toggleRunning() {
        this.isRunning = !this.isRunning;
    }

    public LifeGrid(int width, int height, int cols, int rows, Set<Point2D> prefilled) {
        super(width, height, cols, rows);

        loadTiles(prefilled);

        timer.schedule(new GameTimer(), 0);
    }

    private void loadTiles(Set<Point2D> prefilled) {
        this.tiles = new ArrayList<>();
        for (int i = 0; i < cols; i++) {
            List<LifeTile> thisRow = new ArrayList<>();
            for (int j = 0; j < rows; j++) {
                if (prefilled != null && prefilled.contains(new Point2D.Double(j, i))) {
                    thisRow.add(new LifeTile(true, i, j));
                } else {
                    thisRow.add(new LifeTile(false, i, j));
                }
            }
            this.tiles.add(thisRow);
        }
    }

    @Override
    public void clickTile(int x, int y) {
        LifeTile tile = tiles.get(x).get(y);
        tile.setChecked(!tile.isChecked());
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        int w_size = getWidthSize();
        int h_size = getHeightSize();
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                g.setColor(Color.BLACK);
                g.drawRect(i * w_size, j * h_size, w_size, h_size);

                tiles.get(i).get(j).draw(g, w_size, h_size);
            }
        }

        if (!this.isRunning) {
            g.setColor(new Color(0, 0, 0, 64));
            g.fillRect(0, 0, width, height);
        }
    }

    private synchronized void applyLogic() {
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                tiles.get(i).get(j).calculate(tiles);
            }
        }
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                tiles.get(i).get(j).applyStep();
            }
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            toggleRunning();
            repaint();
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            this.speed = Math.max(this.speed - 10, 10);
            repaint();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            this.speed = Math.min(this.speed + 10, 1000);
            repaint();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            this.backup = cloneGrid(tiles);
        } else if (e.getKeyCode() == KeyEvent.VK_L) {
            if (this.backup != null) {
                isRunning = false;
                tiles = cloneGrid(backup);
                repaint();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            loadTiles(new HashSet<>());
            repaint();
        }
    }

    private static List<List<LifeTile>> cloneGrid(List<List<LifeTile>> grid) {
        List<List<LifeTile>> copied = new ArrayList<>();
        for (List<LifeTile> col : grid) {
            List<LifeTile> row = new ArrayList<>();
            for (LifeTile tile : col) {
                row.add(new LifeTile(tile.isChecked(), tile.getX(), tile.getY()));
            }
            copied.add(row);
        }
        return copied;
    }

    private class GameTimer extends TimerTask {
        @Override
        public void run() {
            if (isRunning) {
                applyLogic();
            }

            timer.cancel();
            timer = new Timer();
            timer.schedule(new GameTimer(), speed);
        }
    }

}