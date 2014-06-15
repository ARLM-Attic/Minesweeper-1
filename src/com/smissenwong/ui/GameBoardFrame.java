package com.smissenwong.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.smissenwong.GameBoardController;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;

public class GameBoardFrame extends JFrame {

    private final int SIZE = 10;
    private GameBoardController board;
    private JButton[][] cells;
    private JPanel gameBoard;
    private JPanel menu;
    private JTextField flags;

    protected void frameInit() {

        board = new GameBoardController();
        cells = new JButton[SIZE][SIZE];
        gameBoard = new JPanel();

        super.frameInit();
        getContentPane().setLayout(new FlowLayout());

        gameBoard.setLayout(new GridLayout(SIZE, SIZE));

        MyEventListener listener = new MyEventListener();
        MyMouseListener mouseListener = new MyMouseListener();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                cells[i][j] = new JButton();
                cells[i][j].setPreferredSize(new Dimension(50, 50));
                cells[i][j].setName(i + "_" + j);
                cells[i][j].setBackground(Color.LIGHT_GRAY);
                cells[i][j].setText(" ");
                gameBoard.add(cells[i][j]);
                //getContentPane().add(cells[i][j]);
                cells[i][j].addActionListener(listener);
                cells[i][j].addMouseListener(mouseListener);
            }
        }

        menu = new JPanel();
        menu.setLayout(new FlowLayout());
        flags = new JTextField();
        flags.setText("Mines Remaining on Board: " + SIZE);
        menu.add(flags);
        menu.setVisible(true);
        setResizable(false);

        getContentPane().add(menu, BorderLayout.PAGE_START);
        getContentPane().add(gameBoard, BorderLayout.CENTER);

        board.resetGame();
    }

    public void redrawBoard() {
        int row = 0;
        int column = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                String locale = cells[i][j].getName();
                String[] cell = locale.split("_");
                row = Integer.parseInt(cell[0]);
                column = Integer.parseInt(cell[1]);
                if (board.isExposed(row, column)) {
                    cells[row][column].setBackground(Color.white);
                    if (board.getNumberOfAdjacentMines(row, column) == 0) {
                        cells[row][column].setText(" ");
                    } else if (board.isMine(i, j) == true) {
                        cells[row][column].setText("M");
                        cells[row][column].setBackground(Color.RED);
                        cells[row][column].setForeground(Color.WHITE);
                    } else {
                        cells[row][column].setText(String.valueOf(board.getNumberOfAdjacentMines(row, column)));
                        cells[row][column].setForeground(Color.BLACK);
                    }
                } else if (board.isFlagged(row, column)) {
                    cells[row][column].setBackground(Color.YELLOW);
                    cells[row][column].setForeground(Color.BLACK);
                    cells[row][column].setText("F");
                } else {
                    cells[row][column].setBackground(Color.LIGHT_GRAY);
                    cells[row][column].setText(" ");
                }
            }
        }
        int numberOfFlags = SIZE;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board.isFlagged(i, j)) {
                    numberOfFlags--;
                }
            }
        }
        flags.setText("Mines Remaining on Board: " + String.valueOf(numberOfFlags));
    }

    private void exposeAllMinesOnLoseGame() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board.isMine(i, j)) {
                    board.exposeCellAt(i, j);
                }
            }
        }
    }

    class MyEventListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (!board.isGameOver()) {
                JButton theButton = (JButton) e.getSource();
                String buttonName = theButton.getName();
                String[] clickedCell = buttonName.split("_");
                int row = Integer.parseInt(clickedCell[0]);
                int column = Integer.parseInt(clickedCell[1]);
                if (!board.isFlagged(row, column)) {
                    board.exposeCellAt(row, column);
                    redrawBoard();
                }

                if (board.isGameWon()) {
                    int option = displayDialogIsForWin(true);
                    if (option == JOptionPane.YES_OPTION) {
                        board.resetGame();
                        redrawBoard();
                    } else {
                        dispose();
                    }
                }
                if (board.isGameOver()) {
                    exposeAllMinesOnLoseGame();
                    redrawBoard();
                    int option = displayDialogIsForWin(false);
                    if (option == JOptionPane.YES_OPTION) {
                        board.resetGame();
                        redrawBoard();
                    } else {
                        dispose();
                    }
                }
            } else {
                exposeAllMinesOnLoseGame();
                redrawBoard();
                int option = displayDialogIsForWin(false);
                if (option == JOptionPane.YES_OPTION) {
                    board.resetGame();
                    redrawBoard();
                } else {
                    dispose();
                }
            }
        }

        private int displayDialogIsForWin(boolean isWin) {
            int playAgain = 0;
            if (isWin) {
                playAgain = JOptionPane.showConfirmDialog(null, "You Won! Would you like to play again ?",
                        "You Won!",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
            } else {
                playAgain = JOptionPane.showConfirmDialog(null, "You Lost! Would you like to play again ?",
                        "You Lost!",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
            }
            return playAgain;
        }
    }

    class MyMouseListener extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.isPopupTrigger()) {
                JButton button = (JButton) e.getSource();
                String buttonName = button.getName();
                String[] clickedCell = buttonName.split("_");
                int row = Integer.parseInt(clickedCell[0]);
                int column = Integer.parseInt(clickedCell[1]);
                board.toggleFlagAt(row, column);
                redrawBoard();
            }
        }
    }

    public static void main(String[] args) {
        GameBoardFrame frame = new GameBoardFrame();
        frame.setMinimumSize(new Dimension(600, 600));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
