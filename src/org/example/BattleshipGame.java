package org.example;

import org.example.controller.GameController;

public class BattleshipGame {

    public static void main(String[] args) {
        GameController game = new GameController(10);
        game.startGame();
    }
}
