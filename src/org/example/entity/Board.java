package org.example.entity;

import java.util.Random;

public class Board {

    private static final int[] shipSizes = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
    private final Ship[][] ships;
    private final boolean[][] shots;
    private final Random random;

    public Board(int boardSize) {
        ships = new Ship[boardSize][boardSize];
        shots = new boolean[boardSize][boardSize];
        random = new Random();
    }

    public ShootResult shoot(int row, int col) {
        shots[row][col] = true;
        var ship = ships[row][col];

        if (ship != null) {
            ship.hit(ship.isVertical() ? row - ship.getRow() : col - ship.getCol());
            if (ship.isSunk()) {
                return ShootResult.DESTROYED;
            }
            return ShootResult.HIT;
        }
        return ShootResult.MISS;
    }

    public boolean allShipsSunk() {
        for (Ship[] ship : ships) {
            for (int j = 0; j < ships.length; j++) {
                if (ship[j] != null && !ship[j].isSunk()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void autoPlaceShips() {
        for (int size : shipSizes) {
            placeShipRandomly(size);
        }
    }

    private void placeShipRandomly(int size) {
        boolean placed = false;
        while (!placed) {
            int row = random.nextInt(ships.length);
            int col = random.nextInt(ships.length);
            boolean vertical = random.nextBoolean();

            if (canPlaceShip(size, row, col, vertical)) {
                Ship ship = new Ship(size, vertical, row, col);
                placeShip(ship, row, col);
                placed = true;
            }
        }
    }

    private boolean canPlaceShip(int size, int row, int col, boolean vertical) {
        // Проверка границ
        if (vertical && row + size > ships.length || !vertical && col + size > ships.length) {
            return false;
        }

        // Проверка окружения
        int startRow = Math.max(0, row - 1);
        int endRow = Math.min(ships.length - 1, row + size + (vertical ? 0 : 1));
        int startCol = Math.max(0, col - 1);
        int endCol = Math.min(ships.length - 1, col + size + (vertical ? 1 : 0));

        return isAreaClear(startRow, endRow, startCol, endCol);
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

    private boolean isAreaClear(int startRow, int endRow, int startCol, int endCol) {
        for (int i = startRow; i <= endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
                if (ships[i][j] != null) {
                    return false; // Найден другой корабль
                }
            }
        }
        return true; // Область свободна
    }

    public boolean hasShip(int row, int col) {
        return ships[row][col] != null;
    }

    public boolean isShipSunk(int row, int col) {
        var ship = ships[row][col];
        if (ship != null) {
            return ship.isSunk();
        }

        return false;
    }

    public boolean hasShot(int row, int col) {
        return shots[row][col];
    }
}