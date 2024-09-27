package org.example.entity;

public class Ship {
    private final int size;
    private final boolean[] hits;
    private final boolean vertical;

    public Ship(int size, boolean vertical) {
        this.size = size;
        this.hits = new boolean[size];
        this.vertical = vertical;
    }

    public boolean hit(int position) {
        if (position >= 0 && position < size) {
            hits[position] = true;
            return true;
        }
        return false;
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
}
