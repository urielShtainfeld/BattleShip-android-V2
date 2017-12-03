package entities;


import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.example.ushtinfeld.battleship_uriel.R;
import com.example.ushtinfeld.battleship_uriel.Set_Ships;

import components.Cell;

public class GameController {
    private static final int NO_OF_SHIPS = 5;
    private static final int HARD_BOARD_SIZE = 20;
    private static final int MEDIUM_BOARD_SIZE = 20;
    private static final int EASY_BOARD_SIZE = 20;
    private Ship[] ships;
    int boardSize;
    private LinearLayout setRowsLayout;
    private LinearLayout setColsLayout;
    private Cell [][] setBoard;
    private TableLayout setTable;

    public GameController() {
        ships = new Ship[NO_OF_SHIPS];
    }

    public void createUserBoard() {


    }

    public void createCompBoard() {


    }
    public void createSetShipsBoard(Set_Ships setShipsScreen) {
        setTable = (TableLayout)setShipsScreen.findViewById(R.id.board);
        setBoard = new Cell[this.getBoardSize()][this.getBoardSize()];
        setRowsLayout = new LinearLayout(setShipsScreen);
        setRowsLayout.setBackgroundColor(Color.TRANSPARENT);
        setRowsLayout.setOrientation(LinearLayout.VERTICAL);

        setRowsLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        for (int col = 0; col < this.getBoardSize(); col++) {
            setColsLayout = new LinearLayout(setShipsScreen);
            setColsLayout.setBackgroundColor(Color.TRANSPARENT);
            setColsLayout.setOrientation(LinearLayout.HORIZONTAL);

            setColsLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            for (int row = 0; row < this.getBoardSize(); row++) {
                setBoard[col][row] = new Cell(setShipsScreen);
                setBoard[col][row].setPosition(row, col);
                setBoard[col][row].setListener(setShipsScreen);
                setBoard[col][row].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                setColsLayout.addView(setBoard[col][row]);
            }
        }
        setRowsLayout.addView(setColsLayout);

    }
    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public Ship[] getShips() {
        return ships;
    }

    public void setShips() {

    }

    public void checkCell(int row, int col) {
    }

    public void setLevel(String level) {
        switch (level) {
            case "EASY":
                setBoardSize(EASY_BOARD_SIZE);
                break;
            case "MEDIUM":
                setBoardSize(MEDIUM_BOARD_SIZE);
                break;
            case "HARD":
                setBoardSize(HARD_BOARD_SIZE);
                break;

        }
    }

}
