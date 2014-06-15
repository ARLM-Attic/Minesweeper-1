package com.smissenwong;

import junit.framework.TestCase;

public class GameBoardControllerTest extends TestCase {

    private GameBoardController _board;
    private final int SIZE = 10;

    public void setUp() {
        _board = new GameBoardController();
    }

    public void testCanary() {
        assertTrue(true);
    }

    public void testIsGameOverOnCreate() {
        assertFalse(_board.isGameOver());
    }

    public void testExposeSealedCell() {
        assertTrue(_board.exposeCellAt(0, 0));
    }

    public void testExposeExposedCell() {
        _board.exposeCellAt(0, 0);
        assertTrue(_board.exposeCellAt(0, 0));
    }

    public void testExposeFlaggedCell() {
        _board.toggleFlagAt(0, 0);
        assertTrue(_board.exposeCellAt(0, 0));
    }

    public void testFlagSealedCell() {
        assertTrue(_board.toggleFlagAt(0, 0));
    }

    public void testFlagExposedCell() {
        _board.exposeCellAt(0, 0);
        assertFalse(_board.toggleFlagAt(0, 0));
    }

    public void testLoseGameByExposingAMineCell() {
        _board.setMine(3, 2);
        _board.exposeCellAt(3, 2);
        assertTrue(_board.isGameOver());
    }

    public void testWinGameByFlaggingAllMinesAndExposingAllEmptyCells() {
        _board.setMine(0, 0);
        _board.toggleFlagAt(0, 0);
        for (int i = 1; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                _board.exposeCellAt(i, j);
            }
            _board.exposeCellAt(0, i);
        }
        assertTrue(_board.isGameWon());
    }

    public void testExposeOutOfBoundCell() {
        assertFalse(_board.exposeCellAt(200, 100));
    }

    public void testFlagOutOfBoundCell() {
        assertFalse(_board.toggleFlagAt(200, 100));
    }

    public void testUnflagOutOfBoundCell() {
        assertFalse(_board.toggleFlagAt(200, 100));
    }

    public void testGetCellValueByZeroMines() {
        assertEquals(0, _board.getNumberOfAdjacentMines(4, 6));
    }

    public void testGetCellValueByOneMine() {
        _board.setMine(4, 7);
        assertEquals(1, _board.getNumberOfAdjacentMines(4, 6));
    }

    public void testGetCellValueByTwoMines() {
        _board.setMine(4, 7);
        _board.setMine(4, 5);
        assertEquals(2, _board.getNumberOfAdjacentMines(4, 6));
    }

    public void testGetCellValueByThreeMines() {
        _board.setMine(7, 4);
        _board.setMine(7, 5);
        _board.setMine(8, 5);
        assertEquals(3, _board.getNumberOfAdjacentMines(8, 4));
    }

    public void testGetCellValueByFourMines() {
        _board.setMine(4, 7);
        _board.setMine(4, 9);
        _board.setMine(5, 9);
        _board.setMine(6, 8);
        assertEquals(4, _board.getNumberOfAdjacentMines(5, 8));
    }

    public void testGetCellValueByFiveMines() {
        _board.setMine(1, 7);
        _board.setMine(1, 9);
        _board.setMine(2, 7);
        _board.setMine(3, 7);
        _board.setMine(3, 8);
        assertEquals(5, _board.getNumberOfAdjacentMines(2, 8));
    }

    public void testGetCellValueBySixMines() {
        _board.setMine(6, 7);
        _board.setMine(6, 8);
        _board.setMine(7, 6);
        _board.setMine(7, 8);
        _board.setMine(8, 7);
        _board.setMine(8, 8);
        assertEquals(6, _board.getNumberOfAdjacentMines(7, 7));
    }

    public void testGetCellValueBySevenMines() {
        _board.setMine(6, 3);
        _board.setMine(6, 4);
        _board.setMine(7, 2);
        _board.setMine(7, 4);
        _board.setMine(8, 2);
        _board.setMine(8, 3);
        _board.setMine(8, 4);
        assertEquals(7, _board.getNumberOfAdjacentMines(7, 3));
    }

    public void testGetCellValueByEightMines() {
        _board.setMine(2, 1);
        _board.setMine(2, 2);
        _board.setMine(2, 3);
        _board.setMine(3, 1);
        _board.setMine(3, 3);
        _board.setMine(4, 1);
        _board.setMine(4, 2);
        _board.setMine(4, 3);
        assertEquals(8, _board.getNumberOfAdjacentMines(3, 2));
    }

    public void testGetCellValueByTopLeftCornerWithZeroMines() {
        assertEquals(0, _board.getNumberOfAdjacentMines(0, 0));
    }

    public void testGetCellValueByTopLeftCornerWithOneMine() {
        _board.setMine(0, 1);
        assertEquals(1, _board.getNumberOfAdjacentMines(0, 0));
    }

    public void testGetCellValueByTopLeftCornerWithTwoMines() {
        _board.setMine(0, 1);
        _board.setMine(1, 0);
        assertEquals(2, _board.getNumberOfAdjacentMines(0, 0));
    }

    public void testGetCellValueByTopLeftCornerThreeMines() {
        _board.setMine(0, 1);
        _board.setMine(1, 0);
        _board.setMine(1, 1);
        assertEquals(3, _board.getNumberOfAdjacentMines(0, 0));
    }

    public void testGetCellValueByTopRightCornerWithNoMines() {
        assertEquals(0, _board.getNumberOfAdjacentMines(0, SIZE - 1));
    }

    public void testGetCellValueByTopRightCornerWithOneMine() {
        _board.setMine(0, SIZE - 2);
        assertEquals(1, _board.getNumberOfAdjacentMines(0, SIZE - 1));
    }

    public void testGetCellValueByTopRightCornerWithTwoMines() {
        _board.setMine(0, SIZE - 2);
        _board.setMine(1, SIZE - 1);
        assertEquals(2, _board.getNumberOfAdjacentMines(0, SIZE - 1));
    }

    public void testGetCellValueByTopRightCornerWithThreeMines() {
        _board.setMine(0, SIZE - 2);
        _board.setMine(1, SIZE - 1);
        _board.setMine(1, SIZE - 2);
        assertEquals(3, _board.getNumberOfAdjacentMines(0, SIZE - 1));
    }

    public void testGetCellValueByBottomRightCornerWithNoMines() {
        assertEquals(0, _board.getNumberOfAdjacentMines(SIZE - 1, SIZE - 1));
    }

    public void testGetCellValueByBottomRightCornerWithOneMine() {
        _board.setMine(SIZE - 2, SIZE - 1);
        assertEquals(1, _board.getNumberOfAdjacentMines(SIZE - 1, SIZE - 1));
    }

    public void testGetCellValueByBottomRightCornerWithTwoMines() {
        _board.setMine(SIZE - 2, SIZE - 1);
        _board.setMine(SIZE - 1, SIZE - 2);
        assertEquals(2, _board.getNumberOfAdjacentMines(SIZE - 1, SIZE - 1));
    }

    public void testGetCellValueByBottomRightCornerWithThreeMines() {
        _board.setMine(SIZE - 2, SIZE - 2);
        _board.setMine(SIZE - 2, SIZE - 1);
        _board.setMine(SIZE - 1, SIZE - 2);
        assertEquals(3, _board.getNumberOfAdjacentMines(SIZE - 1, SIZE - 1));
    }

    public void testGetCellValueByBottomLeftCornerWithNoMines() {
        assertEquals(0, _board.getNumberOfAdjacentMines(SIZE - 1, 0));
    }

    public void testGetCellValueByBottomLeftCornerWithOneMine() {
        _board.setMine(SIZE - 2, 0);
        assertEquals(1, _board.getNumberOfAdjacentMines(SIZE - 1, 0));
    }

    public void testGetCellValueByBottomLeftCornerWithTwoMines() {
        _board.setMine(SIZE - 1, 1);
        _board.setMine(SIZE - 2, 0);
        assertEquals(2, _board.getNumberOfAdjacentMines(SIZE - 1, 0));
    }

    public void testGetCellValueByBottomLeftCornerWithThreeMines() {
        _board.setMine(SIZE - 2, 0);
        _board.setMine(SIZE - 2, 1);
        _board.setMine(SIZE - 1, 1);
        assertEquals(3, _board.getNumberOfAdjacentMines(SIZE - 1, 0));
    }

    public void testGetCellValueByTopSide() {
        _board.setMine(0, 5);
        _board.setMine(1, 6);
        assertEquals(2, _board.getNumberOfAdjacentMines(0, 6));
    }

    public void testGetCellValueByRightSide() {
        _board.setMine(3, SIZE - 1);
        _board.setMine(4, SIZE - 2);
        _board.setMine(5, SIZE - 2);
        assertEquals(3, _board.getNumberOfAdjacentMines(4, SIZE - 1));
    }

    public void testGetCellValueByBottomSide() {
        _board.setMine(SIZE - 1, 5);
        _board.setMine(SIZE - 2, 4);
        _board.setMine(SIZE - 2, 5);
        assertEquals(3, _board.getNumberOfAdjacentMines(SIZE - 1, 4));
    }

    public void testGetCellValueByLeftSide() {
        _board.setMine(0, 6);
        _board.setMine(0, 8);
        assertEquals(2, _board.getNumberOfAdjacentMines(0, 7));
    }

    public void testExposeNeighbors() {
        _board.setMine(8, 6);
        _board.exposeCellAt(6, 6);
        _board.exposeNeighbors(6, 6);
        assertTrue(_board.isExposed(7, 6));
    }

    public void testWinGameByExposingAllNonMineCells() {
        _board.setMine(0, 1);
        _board.setMine(0, 7);
        _board.setMine(1, 1);
        _board.setMine(1, 7);
        _board.setMine(2, 0);
        _board.setMine(2, 1);
        _board.setMine(3, 7);
        _board.setMine(4, 6);
        _board.setMine(6, 4);
        _board.setMine(8, 0);
        _board.exposeCellAt(1, 4);
        _board.exposeNeighbors(1, 4);
        _board.exposeCellAt(1, 0);
        _board.exposeNeighbors(1, 0);
        _board.exposeCellAt(0, 0);
        _board.exposeNeighbors(0, 0);
        _board.exposeCellAt(9, 0);
        _board.exposeNeighbors(9, 0);
        _board.exposeCellAt(2, 7);
        _board.exposeNeighbors(2, 7);
        assertTrue(_board.isGameWon());
    }
    
    public void testSetMines(){
        _board.setMines();
        int numberOfMines = 0;
        for(int i=0;i<SIZE;i++){
            for(int j=0;j<SIZE;j++){
                if(_board.isMine(i,j)){
                    numberOfMines++;
                }
            }
        }
        assertEquals(10,numberOfMines);
    }
    
    public void testResetGame(){
        _board.setMine(5, 5);
        _board.exposeCellAt(5, 5);
        _board.resetGame();
        assertFalse(_board.isGameOver());
    }
    
    public void testIsFlagged(){
        _board.toggleFlagAt(5, 5);
        assertTrue(_board.isFlagged(5,5)); 
    }
    
}
