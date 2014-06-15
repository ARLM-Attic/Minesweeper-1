package com.smissenwong;

import java.util.Random;

public class GameBoardController {

    private final int SIZE = 10;
    private CellStatus[][] _cellStatus;
    private boolean[][] _cellContainsMine;

    private enum CellStatus {

        SEALED, FLAGGED, EXPOSED
    }

    public GameBoardController() {
        _cellStatus = new CellStatus[SIZE][SIZE];
        _cellContainsMine = new boolean[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                _cellStatus[i][j] = CellStatus.SEALED;
                _cellContainsMine[i][j] = false;
            }
        }
    }

    public void resetGame() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                _cellStatus[i][j] = CellStatus.SEALED;
                _cellContainsMine[i][j] = false;
            }
        }
        setMines();
    }

    void setMine(int row, int column) {
        _cellContainsMine[row][column] = true;
    }

    public void setMines() {
        int row, column;
        Random generator = new Random(System.currentTimeMillis());
        for (int i = 0; i < SIZE; i++) {
            row = generator.nextInt(SIZE);
            column = generator.nextInt(SIZE);
            if (!isMine(row, column)) {
                setMine(row, column);
            } else {
                while (isMine(row, column)) {
                    row = generator.nextInt(SIZE);
                    column = generator.nextInt(SIZE);
                }
                setMine(row, column);
            }
        }
    }

    public boolean isMine(int row, int column) {
        return _cellContainsMine[row][column];
    }

    public boolean isFlagged(int row, int column) {
        return (_cellStatus[row][column] == CellStatus.FLAGGED);
    }

    public boolean isExposed(int row, int column) {
        return _cellStatus[row][column] == CellStatus.EXPOSED;
    }

    public boolean isGameOver() {
        boolean mineExposed = false;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (_cellStatus[i][j] == CellStatus.EXPOSED && _cellContainsMine[i][j]) {
                    mineExposed = true;
                }
            }
        }
        return mineExposed;
    }

    public boolean isGameWon() {
        boolean wonTheGame = true;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (_cellContainsMine[i][j] && _cellStatus[i][j] == CellStatus.EXPOSED) {
                    wonTheGame = false;
                }
                if (!_cellContainsMine[i][j] && _cellStatus[i][j] == CellStatus.SEALED) {
                    wonTheGame = false;
                }
                if (!_cellContainsMine[i][j] && _cellStatus[i][j] == CellStatus.FLAGGED) {
                    wonTheGame = false;
                }
            }
        }
        return wonTheGame;
    }

    public boolean exposeCellAt(int row, int column) {
        boolean isExposed = false;
        if (row < SIZE && column < 10) {
            _cellStatus[row][column] = CellStatus.EXPOSED;
            if (getNumberOfAdjacentMines(row, column) == 0) {
                exposeNeighbors(row, column);
            }
            isExposed = true;
        }
        return isExposed;
    }

    void exposeNeighbors(int row, int column) {
        for (int i = (row - 1); i < (row + 2); i++) {
            for (int j = (column - 1); j < (column + 2); j++) {
                if (i >= 0 && j >= 0 && i < SIZE && j < SIZE) {
                    if (!_cellContainsMine[i][j] && _cellStatus[i][j] == CellStatus.SEALED
                            && getNumberOfAdjacentMines(row, column) == 0) {
                        exposeCellAt(i, j);
                        exposeNeighbors(i, j);
                    }
                }
            }
        }
    }

    public boolean toggleFlagAt(int row, int column) {
        boolean isFlagged = false;
        if ((row < SIZE) && (column < SIZE)) {
            if (_cellStatus[row][column] != CellStatus.EXPOSED) {
                if (_cellStatus[row][column] == CellStatus.FLAGGED) {
                    _cellStatus[row][column] = CellStatus.SEALED;
                } else {
                    _cellStatus[row][column] = CellStatus.FLAGGED;
                    isFlagged = true;
                }
            }
        }
        return isFlagged;

    }

    public int getNumberOfAdjacentMines(int row, int column) {
        int numberOfMinesAdjacentToCell = 0;
        for (int i = (row - 1); i <= (row + 1); i++) {
            for (int j = (column - 1); j <= (column + 1); j++) {
                if (i >= 0 && j >= 0 && i < SIZE && j < SIZE) {
                    if (_cellContainsMine[i][j]) {
                        numberOfMinesAdjacentToCell++;
                    }
                }
            }
        }
        return numberOfMinesAdjacentToCell;
    }
}
