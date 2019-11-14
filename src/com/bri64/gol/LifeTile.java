package com.bri64.gol;

import java.awt.*;
import java.util.List;

public class LifeTile {
    private boolean checked;
    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    private int x;
    private int y;
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    private NextStep nextStep;

    public LifeTile(boolean checked, int x, int y) {
        this.checked = checked;
        this.x = x;
        this.y = y;
        this.nextStep = NextStep.NONE;
    }

    public void draw(Graphics g, int w_size, int h_size) {
        if (checked) {
            g.setColor(Color.BLUE);
            g.fillRect(x * w_size, y * h_size, w_size, h_size);
        }
    }

    public void calculate(List<List<LifeTile>> tiles) {
        if (shouldDie(tiles)) {
            this.nextStep = NextStep.DIE;
        } else if (shouldSpawn(tiles)) {
            this.nextStep = NextStep.SPAWN;
        } else {
            this.nextStep = NextStep.NONE;
        }
    }

    public void applyStep() {
        switch (this.nextStep) {
            case DIE: {
                setChecked(false);
                break;
            }
            case SPAWN: {
                setChecked(true);
                break;
            }
            default: {
                break;
            }
        }
        this.nextStep = NextStep.NONE;
    }

    private boolean shouldDie(List<List<LifeTile>> tiles) {
        int neighbors = getNeighbors(tiles);
        return isChecked() && (neighbors <= 1 || neighbors >= 4);
    }

    private boolean shouldSpawn(List<List<LifeTile>> tiles) {
        return !isChecked() && getNeighbors(tiles) == 3;
    }

    private int getNeighbors(List<List<LifeTile>> tiles) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                try {
                    if (tiles.get(x + i).get(y + j).isChecked()) {
                        count++;
                    }
                } catch (Exception ignored) {}
            }
        }
        return count;
    }

    private enum NextStep {
        NONE, DIE, SPAWN
    }
}
