package org.example.entity;

public class Ship {
    private final int size;
    private final boolean[] hits;
    private final boolean vertical;
    private final int row;
    private final int col;

    public Ship(int size, boolean vertical, int row, int col) {
        this.size = size;
        this.hits = new boolean[size];
        this.vertical = vertical;
        this.row = row;
        this.col = col;
    }

    public void hit(int position) {
        if (position >= 0 && position < size) {
            hits[position] = true;
        }
    }

    public boolean isSunk() {
        for (boolean hit : hits) {
            if (!hit) return false;
        }
        return true;
    }

    public int getSize() {
        return size;
    }

    public boolean isVertical() {
        return vertical;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
