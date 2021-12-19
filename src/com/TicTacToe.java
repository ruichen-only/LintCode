package com;

public class TicTacToe {
    private int[][] checkerboard;
    private boolean xTurn = true;
    private boolean finished = false;

    /** Initialize your data structure here. */
    public TicTacToe() {
        checkerboard = new int[3][3];
    }

    public boolean move(int row, int col) throws AlreadyTakenException, GameEndException {
        if (finished) {
            throw new GameEndException();
        }
        if (checkerboard[row][col] != 0) {
            throw new AlreadyTakenException();
        }

        checkerboard[row][col] = xTurn ? 1 : 2;

        if(xTurn && isWinner(true)) {
            System.out.println("x player wins!");
            finished = true;
            return true;
        } else if(!xTurn && isWinner(false)) {
            System.out.println("o player wins!");
            finished = true;
            return true;
        }
        if (allDraw()) {
            System.out.println("it's a draw");
        }
        xTurn = !xTurn;
        return false;
    }

    private boolean allDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(checkerboard[i][j] == 0) {
                    return false;
                }
            }
        }
        finished = true;
        return true;
    }

    private boolean isWinner(boolean xTurn) {
        int verify = xTurn ? 1 : 2;

        boolean pos00 = checkerboard[0][0] == verify;
        boolean pos01 = checkerboard[0][1] == verify;
        boolean pos02 = checkerboard[0][2] == verify;
        boolean pos10 = checkerboard[1][0] == verify;
        boolean pos11 = checkerboard[1][1] == verify;
        boolean pos12 = checkerboard[1][2] == verify;
        boolean pos20 = checkerboard[2][0] == verify;
        boolean pos21 = checkerboard[2][1] == verify;
        boolean pos22 = checkerboard[2][2] == verify;

        return pos00 && pos01 && pos02
            || pos10 && pos11 && pos12
            || pos20 && pos21 && pos22
            || pos00 && pos10 && pos20
            || pos01 && pos11 && pos21
            || pos02 && pos12 && pos22
            || pos00 && pos11 && pos22
            || pos20 && pos11 && pos02;
    }
}

class AlreadyTakenException extends Exception {
    public AlreadyTakenException() {
        super("This place has been taken");
    }
}

class GameEndException extends Exception {
    public GameEndException() {
        super("Game has been ended, cannot make any more moves");
    }
}
