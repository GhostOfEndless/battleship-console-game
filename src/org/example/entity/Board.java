package org.example.entity;

import java.util.Random;

public class Board {
    private static final int SIZE = 10;
    private final Ship[][] ships;
    private final boolean[][] shots;
    private final Random random;

    public Board() {
        ships = new Ship[SIZE][SIZE];
        shots = new boolean[SIZE][SIZE];
        random = new Random();
    }

    public boolean shoot(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE || shots[row][col]) {
            return false;
        }
        shots[row][col] = true;
        if (ships[row][col] != null) {
            ships[row][col].hit(0); // Упрощенно, без учета позиции на корабле
            return true;
        }
        return false;
    }

    public boolean allShipsSunk() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (ships[i][j] != null && !ships[i][j].isSunk()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void autoPlaceShips() {
        int[] shipSizes = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
        for (int size : shipSizes) {
            placeShipRandomly(size);
        }
    }

    private void placeShipRandomly(int size) {
        boolean placed = false;
        while (!placed) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);
            boolean vertical = random.nextBoolean();

            if (canPlaceShip(size, row, col, vertical)) {
                Ship ship = new Ship(size, vertical);
                placeShip(ship, row, col);
                placed = true;
            }
        }
    }

    private boolean canPlaceShip(int size, int row, int col, boolean vertical) {
        // Проверка границ
        if (vertical && row + size > SIZE) return false;
        if (!vertical && col + size > SIZE) return false;

        // Проверка окружения
        for (int i = Math.max(0, row - 1); i <= Math.min(SIZE - 1, row + size + (vertical ? 0 : 1)); i++) {
            for (int j = Math.max(0, col - 1); j <= Math.min(SIZE - 1, col + size + (vertical ? 1 : 0)); j++) {
                if (ships[i][j] != null) return false;
            }
        }

        return true;
    }

    private void placeShip(Ship ship, int row, int col) {
        for (int i = 0; i < ship.getSize(); i++) {
            if (ship.isVertical()) {
                ships[row + i][col] = ship;
            } else {
                ships[row][col + i] = ship;
            }
        }
    }



    public boolean hasShip(int row, int col) {
        return ships[row][col] != null;
    }

    public boolean hasShot(int row, int col) {
        return shots[row][col];
    }
}