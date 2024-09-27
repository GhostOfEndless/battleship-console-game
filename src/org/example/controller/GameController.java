package org.example.controller;

import org.example.entity.Board;
import org.example.entity.Point;
import org.example.entity.ShootResult;
import org.example.view.ConsoleView;

import java.util.*;

public class GameController {

    private final Board playerBoard;
    private final Board computerBoard;
    private final ConsoleView view;
    private final Random random;
    private final List<Point> availablePoints;

    public GameController(int boardSize) {
        playerBoard = new Board(boardSize);
        computerBoard = new Board(boardSize);
        view = new ConsoleView(boardSize);
        availablePoints = generateAvailablePoints();
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

    private List<Point> generateAvailablePoints() {
        List<Point> points = new LinkedList<>();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                points.add(new Point(i, j));
            }
        }

        return points;
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
                view.showMessage("К сожалению, вы проиграли :( Компьютер победил.");
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

            var result = computerBoard.shoot(row, col);
            var hasNextMove = processShootResult(result);

            if (!hasNextMove) {
                return;
            }
        }
    }

    private void computerTurn() {
        view.showMessage("Ход компьютера.");

        while (true) {
            Point point = availablePoints.get(random.nextInt(availablePoints.size()));

            if (playerBoard.hasShot(point.row(), point.col())) {
                continue;
            }

            view.showMessage("Компьютер ходит %c%d".formatted((char) ('A' + point.row()), point.col()));
            availablePoints.remove(point);

            var result = playerBoard.shoot(point.row(), point.col());
            var hasNextMove = processShootResult(result, false,"Компьютер промахнулся.",
                    "Компьютер попал в ваш корабль!", "Компьютер уничтожил ваш корабль!");

            if (!hasNextMove) {
                return;
            }
        }
    }

    private boolean processShootResult(ShootResult result, boolean displayBoard, String missMessage,
                                       String hitMessage, String destroyMessage) {
        return switch (result) {
            case MISS -> {
                view.showMessage(missMessage == null? "Промах!": missMessage);
                yield false;
            }
            case HIT -> {
                view.showMessage(hitMessage == null? "Попадание!": hitMessage);
                if (displayBoard) {
                    view.displayBoards(playerBoard, computerBoard);
                }
                yield !playerBoard.allShipsSunk();
            }
            case DESTROYED -> {
                view.showMessage(destroyMessage == null? "Уничтожен!" : destroyMessage);
                if (displayBoard) {
                    view.displayBoards(playerBoard, computerBoard);
                }
                yield !playerBoard.allShipsSunk();
            }
        };
    }

    private boolean processShootResult(ShootResult result) {
        return processShootResult(result, true, null, null, null);
    }


    private void endGame() {
        view.showMessage("Игра окончена.");
        view.showMessage("Финальное состояние досок:");
        view.displayBoards(playerBoard, computerBoard);
        view.close();
    }
}
