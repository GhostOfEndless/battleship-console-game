package org.example.view;

import org.example.entity.Board;

import java.util.Scanner;

public class ConsoleView {
    private static final String EMPTY = "·";
    private static final String SHIP = "■";
    private static final String HIT = "□";
    private static final String DESTROYED = "⊞";
    private static final String MISS = "*";

    private final Scanner scanner;
    private final int boardSize;

    public ConsoleView(int boardSize) {
        scanner = new Scanner(System.in);
        this.boardSize = boardSize;
    }

    public void displayBoards(Board leftBoard, Board rightBoard) {
        System.out.println("\nВаше поле:                    Поле компьютера:");
        System.out.println("  0 1 2 3 4 5 6 7 8 9           0 1 2 3 4 5 6 7 8 9");

        for (int i = 0; i < boardSize; i++) {
            System.out.print((char)('A' + i) + " ");
            printBoardRow(leftBoard, i, false);
            System.out.print("        " + (char) ('A' + i) + " ");
            printBoardRow(rightBoard, i, true);
            System.out.println();
        }
        System.out.println();
    }

    private void printBoardRow(Board board, int row, boolean hideShips) {
        for (int col = 0; col < boardSize; col++) {
            if (board.hasShot(row, col)) {
                if (board.hasShip(row, col)) {
                    if ((board.isShipSunk(row, col))) {
                        System.out.print(DESTROYED);
                    } else {
                        System.out.print(HIT);
                    }
                } else {
                    System.out.print(MISS);
                }
            } else if (!hideShips && board.hasShip(row, col)) {
                System.out.print(SHIP);
            } else {
                System.out.print(EMPTY);
            }
            System.out.print(" ");
        }
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public int[] getPlayerShot() {
        while (true) {
            System.out.print("Введите координаты выстрела (например, A1): ");
            String input = scanner.nextLine().toUpperCase().trim();
            if (input.length() == 2) {
                char rowChar = input.charAt(0);
                int row = rowChar - 'A';
                int col;
                try {
                    col = Integer.parseInt(input.substring(1));
                } catch (NumberFormatException e) {
                    System.out.println("Неверный формат. Попробуйте еще раз.");
                    continue;
                }
                if (row >= 0 && row < boardSize && col >= 0 && col < boardSize) {
                    return new int[]{row, col};
                }
            }
            System.out.println("Неверные координаты. Попробуйте еще раз.");
        }
    }

    public void close() {
        scanner.close();
    }
}
