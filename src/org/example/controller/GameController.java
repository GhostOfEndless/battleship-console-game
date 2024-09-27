package org.example.controller;

import org.example.entity.Board;
import org.example.view.ConsoleView;

import java.util.Random;

public class GameController {
    private final Board playerBoard;
    private final Board computerBoard;
    private final ConsoleView view;
    private final Random random;

    public GameController() {
        playerBoard = new Board();
        computerBoard = new Board();
        view = new ConsoleView();
        random = new Random();
    }

    public void startGame() {
        view.showMessage("Добро пожаловать в игру Морской Бой!");

        view.showMessage("Расстановка кораблей...");
        playerBoard.autoPlaceShips();
        computerBoard.autoPlaceShips();

        view.showMessage("Корабли расставлены. Начинаем игру!");
        playGame();
    }

    private void playGame() {
        while (true) {
            view.displayBoards(playerBoard, computerBoard);

            playerTurn();
            if (computerBoard.allShipsSunk()) {
                view.showMessage("Поздравляем! Вы победили!");
                break;
            }

            computerTurn();
            if (playerBoard.allShipsSunk()) {
                view.showMessage("К сожалению, вы проиграли. Компьютер победил.");
                break;
            }
        }
        endGame();
    }

    private void playerTurn() {
        view.showMessage("Ваш ход.");
        while (true) {
            int[] shot = view.getPlayerShot();
            int row = shot[0];
            int col = shot[1];

            if (computerBoard.hasShot(row, col)) {
                view.showMessage("Вы уже стреляли в эту клетку. Попробуйте еще раз.");
                continue;
            }

            boolean hit = computerBoard.shoot(row, col);
            if (hit) {
                view.showMessage("Попадание!");
                if (computerBoard.allShipsSunk()) {
                    return;
                }
            } else {
                view.showMessage("Промах!");
                return;
            }
        }
    }

    private void computerTurn() {
        view.showMessage("Ход компьютера.");
        while (true) {
            int row = random.nextInt(10);
            int col = random.nextInt(10);

            if (playerBoard.hasShot(row, col)) {
                continue;
            }

            boolean hit = playerBoard.shoot(row, col);
            if (hit) {
                view.showMessage("Компьютер попал в ваш корабль!");
                if (playerBoard.allShipsSunk()) {
                    return;
                }
            } else {
                view.showMessage("Компьютер промахнулся.");
                return;
            }
        }
    }

    private void endGame() {
        view.showMessage("Игра окончена.");
        view.showMessage("Финальное состояние досок:");
        view.displayBoards(playerBoard, computerBoard);
        view.close();
    }
}
