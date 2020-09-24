package com.tictactoenet;

public class Field {

    private static final int MAX_FIELD = 3;
    private char[][] gameField = new char[MAX_FIELD][MAX_FIELD];

    public static int getMaxField() {
        return MAX_FIELD;
    }

    public char[][] getGameField() {
        return gameField;
    }

    public void setGameField(char[][] gameField) {
        this.gameField = gameField;
    }

    public Field() {
        for (int i = 0; i < MAX_FIELD; i++) {
            for (int k = 0; k < MAX_FIELD; k++) {
                gameField[i][k] = ' ';
            }
        }
    }

    private void showCell(int x, int y) {
        System.out.print("[" + gameField[x][y] + "]");
    }

    private void showLine(int lineNumber) {
        for (int i = 0; i < MAX_FIELD; i++) {
            showCell(i, lineNumber);
        }
    }

    public void showField() {
        System.out.println();
        for (int i = 0; i < MAX_FIELD; i++) {
            showLine(i);
            System.out.println();
        }
    }


}
