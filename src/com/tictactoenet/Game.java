package com.tictactoenet;

import java.util.Scanner;

public class Game {

    private static final char MARK_X = 'X';
    private static final char MARK_0 = '0';
    private int xCoordinate;
    private int yCoordinate;
    private char[][] gameField;
    private char lastMove;
    private char whoMove;
    private char myMark;
    private char opponentsMark;

    public Game(char[][] field, char whoMove, char myMark, char opponentsMark) {
        this.gameField = field;
        this.whoMove = whoMove;
        this.myMark = myMark;
        this.opponentsMark = opponentsMark;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public char[][] getGameField() {
        return gameField;
    }

    public void setGameField(char[][] gameField) {
        this.gameField = gameField;
    }

    public char getLastMove() {
        return lastMove;
    }

    public void setLastMove(char lastMove) {
        this.lastMove = lastMove;
    }

    public char getWhoMove() {
        return whoMove;
    }

    public void setWhoMove(char whoMove) {
        this.whoMove = whoMove;
    }

    public char getMyMark() {
        return myMark;
    }

    public void setMyMark(char myMark) {
        this.myMark = myMark;
    }

    public char getOpponentsMark() {
        return opponentsMark;
    }

    public void setOpponentsMark(char opponentsMark) {
        this.opponentsMark = opponentsMark;
    }

    public String[] makeMove() {
        do {
            checkRangeX();
            checkRangeY();
        }
        while (!checkCellAvailability(getxCoordinate(), getyCoordinate()));
        addNewValue(getxCoordinate(), getyCoordinate(), myMark);
        lastMove = myMark;
        if (whoMove == MARK_0) whoMove = MARK_X;
        else if (whoMove == MARK_X) whoMove = MARK_0;
        String[] result = {Integer.toString(getxCoordinate()), Integer.toString(getyCoordinate())};
        System.out.println("\r\nWaiting for the opponent's move...");
        return result;
    }

    public void waitForMove(String[] i) {
        int firstDigit = Integer.parseInt(i[0]);
        int secondDigit = Integer.parseInt(i[1]);
        addNewValue(firstDigit, secondDigit, opponentsMark);
        lastMove = opponentsMark;
        if (whoMove == MARK_0) whoMove = MARK_X;
        else if (whoMove == MARK_X) whoMove = MARK_0;
    }

    private void checkRangeX() {
        Scanner in = new Scanner(System.in);
        System.out.println("Your move! Input X coordinate");
        while (!in.hasNextInt()) {
            System.out.println("Incorrect input! Only integers [0-2]");
            System.out.println("Your move! Input X coordinate");
            in.next();
        }
        setxCoordinate(in.nextInt());
    }

    private void checkRangeY() {
        Scanner in = new Scanner(System.in);
        System.out.println("Your move! Input Y coordinate");
        while (!in.hasNextInt()) {
            System.out.println("Incorrect input! Only integers [0-2]");
            System.out.println("Your move! Input Y coordinate");
            in.next();
        }
        setyCoordinate(in.nextInt());

    }

    private void addNewValue(int x, int y, char z) {
        gameField[x][y] = z;
    }

    private boolean checkCellAvailability(int x, int y) {
        while (x < 0 || x > 2 || y < 0 || y > 2) {
            System.out.println("Incorrect input! Only integers [0-2]");
            return false;
        }
        if (gameField[x][y] == MARK_X || gameField[x][y] == MARK_0) {
            System.out.println("Cell is already occupied! Select another cell!");
            return false;
        } else return true;
    }

    public boolean checkIfWin() {
        for (int i = 0; i < 3; i++) {
            if ((gameField[0][i] == gameField[1][i] && gameField[1][i] == gameField[2][i] && gameField[2][i] != ' ') ||
                    (gameField[i][0] == gameField[i][1] && gameField[i][1] == gameField[i][2] && gameField[i][2] != ' ') ||
                    (gameField[0][0] == gameField[1][1] && gameField[1][1] == gameField[2][2] && gameField[2][2] != ' ') ||
                    (gameField[0][2] == gameField[1][1] && gameField[1][1] == gameField[2][0] && gameField[2][0] != ' ')
            )
                return false;
        }
        return true;
    }

    public void checkWhoWin() {
        if (lastMove == myMark) System.out.println("You Win!");
        else System.out.println("You lose!");
    }


}
