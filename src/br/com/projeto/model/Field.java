package br.com.projeto.model;

import br.com.projeto.exception.ExplosionException;

import java.util.ArrayList;
import java.util.List;

public class Field {
    private int line;
    private int column;
    private boolean opened;
    private boolean mined;
    private boolean marked;
    private List<Field> neighbors = new ArrayList<>();

    public Field(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public List<Field> getNeighbors() {
        return neighbors;
    }

    public boolean addNeighbor(Field field) {
        boolean differentLine = this.line != field.line;
        boolean differentColumn = this.column != field.column;
        boolean diagonal = differentLine && differentColumn;

        var lineDelta = Math.abs(this.line - field.line);
        var columnDelta = Math.abs(this.column - field.column);
        var generalDelta = lineDelta + columnDelta;

        if (generalDelta == 1 && !diagonal) {
            neighbors.add(field);
            return true;
        } else if (generalDelta == 2 && diagonal) {
            neighbors.add(field);
            return true;
        } else {
            return false;
        }
    }

    public boolean open() {
        if (mined) {
            throw new ExplosionException();
        }
        if (!marked && !opened) {
            opened = true;
            if (safeNeighborhood()) {
                for (var neighbor : neighbors) {
                    neighbor.open();
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public void SwitchMarked() {
        if (!opened) {
            marked = !marked;
        }
    }

    public boolean safeNeighborhood() {
        return neighbors.stream().noneMatch(neighbor -> neighbor.mined);

    }

    public boolean goalAchieved() {
        boolean revealed = !mined && opened;
        boolean secured = mined && marked;

        return revealed || secured;
    }

    public Long neighborhoodMines() {
        return neighbors.stream().filter(neighbor -> neighbor.mined).count();

    }

    public void restart() {
        opened = false;
        mined = false;
        marked = false;
    }

    @Override
    public String toString() {
        if (marked) {
            return "x";
        } else if (opened && mined) {
            return "*";
        } else if (opened && neighborhoodMines() > 0) {
            return Long.toString(neighborhoodMines());
        } else if (opened) {
            return " ";
        }
        return "?";
    }

    //TODO: Criar o método -> SET_OPENED(BOOLEAN OPENED)|VOID|
    void setOpened(boolean opened) {
        this.opened = opened;
    }


    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public boolean getMined() {
        return mined;
    }

    public boolean isMined() {
        return mined;
    }

    public void setMined() {
        this.mined = true;
    }

    public boolean isOpened() {
        return opened;
    }
}