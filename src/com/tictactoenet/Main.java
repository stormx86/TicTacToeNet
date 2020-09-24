package com.tictactoenet;

public class Main {

    private static final char MARK_X = 'X';
    private static final char MARK_0 = '0';


    public static void main(String[] args) throws Exception {
        Field gameField = new Field();
        Connector connector = new Connector();
        connector.selectNetRole();
        if (connector.getRoleXor0() == MARK_X) {                     // if player has X
            PlayWithMarkX(gameField, connector);
        } else if (connector.getRoleXor0() == MARK_0) {              // if player has 0
            PlayWithMarkZero(gameField, connector);
        }
    }

    private static void PlayWithMarkX(Field gameField, Connector connector) {
        Game game = new Game(gameField.getGameField(), MARK_X, MARK_X, MARK_0);
        gameField.showField();
        connector.sender(game.makeMove());
        gameField.showField();
        while (game.checkIfWin()) {
            if (game.getWhoMove() == MARK_X) {
                connector.sender(game.makeMove());
                gameField.showField();
            } else if (game.getWhoMove() == MARK_0) {
                game.waitForMove(connector.receiver());
                gameField.showField();
            }
        }
        game.checkWhoWin();
    }

    private static void PlayWithMarkZero(Field gameField, Connector connector) {
        Game game = new Game(gameField.getGameField(), MARK_0, MARK_0, MARK_X);
        gameField.showField();
        game.waitForMove(connector.receiver());
        while (game.checkIfWin()) {
            if (game.getWhoMove() == MARK_X) {
                gameField.showField();
                connector.sender(game.makeMove());
                gameField.showField();
            } else if (game.getWhoMove() == MARK_0) {
                game.waitForMove(connector.receiver());
                gameField.showField();
            }
        }
        game.checkWhoWin();
    }
}
