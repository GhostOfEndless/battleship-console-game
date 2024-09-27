package org.example.view;

import org.example.entity.Board;

import java.util.Scanner;

public class ConsoleView {
    private static final int BOARD_SIZE = 10;
    private static final String EMPTY = "·";
    private static final String SHIP = "■";
    private static final String HIT = "X";
    private static final String MISS = "*";

    private final Scanner scanner;

    public ConsoleView() {
        scanner = new Scanner(System.in);
    }

    public void displayBoards(Board playerBoard, Board computerBoard) {
        System.out.println("\nВаше поле:                    Поле компьютера:");
        System.out.println("  0 1 2 3 4 5 6 7 8 9           0 1 2 3 4 5 6 7 8 9");

        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print((char)('A' + i) + " ");
            printBoardRow(playerBoard, i, false);
            System.out.print("        " + (char)('A' + i) + " ");
            printBoardRow(computerBoard, i, true);
            System.out.println();
        }
        System.out.println();
    }

    private void printBoardRow(Board board, int row, boolean hideShips) {
        for (int col = 0; col < BOARD_SIZE; col++) {
            if (board.hasShot(row, col)) {
                System.out.print(board.hasShip(row, col) ? HIT : MISS);
            } else if (!hideShips && board.hasShip(row, col)) {
                System.out.print(SHIP);
            } else {
                System.out.print(EMPTY);
            }
            System.out.print(" "); // Добавляем пробел между символами
        }
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public int[] getPlayerShot() {
        while (true) {
            System.out.print("Введите координаты выстрела (например, A5): ");
            String input = scanner.nextLine().toUpperCase();
            if (input.length() == 2 || input.length() == 3) {
                char rowChar = input.charAt(0);
                int row = rowChar - 'A';
                int col;
                try {
                    col = Integer.parseInt(input.substring(1));
                } catch (NumberFormatException e) {
                    System.out.println("Неверный формат. Попробуйте еще раз.");
                    continue;
                }
                if (row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE) {
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
