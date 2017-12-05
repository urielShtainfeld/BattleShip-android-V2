package entities;


import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.ushtinfeld.battleship_uriel.Game;
import com.example.ushtinfeld.battleship_uriel.R;

import components.Cell;

public class GameController {
    private static final int EASY_NO_OF_SHIPS = 3;
    private static final int MEDIUM_NO_OF_SHIPS = 4;
    private static final int HARD_NO_OF_SHIPS = 5;
    private static final int HARD_BOARD_SIZE = 10;
    private static final int MEDIUM_BOARD_SIZE = 9;
    private static final int EASY_BOARD_SIZE = 8;
    public static final int[] EASY_SHIPS_SIZE = {3,4,5};
    public static final int[] MEDIUM_SHIPS_SIZE = {2,3,4,5};
    public static final int[] HARD_SHIPS_SIZE = {2,3,3,4,5};
    private Ship[] ships;
    int boardSize;
    private LinearLayout setRowsLayout;
    private LinearLayout setColsLayout;
    private Cell [][] setBoard;
    private RelativeLayout setTable;
    private boolean setMode ;



    public GameController(String level) {
        setLevel(level);
        this.setMode = true;
    }

    public void createUserBoard() {


    }

    public void createCompBoard() {


    }
    public void createSetShipsBoard(Game setShipsScreen, GameController controller) {
        setBoard = new Cell[controller.getBoardSize()][controller.getBoardSize()];
        setRowsLayout = new LinearLayout(setShipsScreen);
        setRowsLayout.setBackgroundColor(Color.TRANSPARENT);
        setRowsLayout.setOrientation(LinearLayout.VERTICAL);
        setRowsLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        for (int col = 0; col < controller.getBoardSize(); col++) {
            setColsLayout = new LinearLayout(setShipsScreen);
            setColsLayout.setBackgroundColor(Color.TRANSPARENT);
            setColsLayout.setOrientation(LinearLayout.HORIZONTAL);
            setColsLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            for (int row = 0; row < controller.getBoardSize(); row++) {
                setBoard[col][row] = new Cell(setShipsScreen);
                setBoard[col][row].setPosition(col,row);
                setBoard[col][row].setListener(setShipsScreen);
                setBoard[col][row].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
                setColsLayout.addView(setBoard[col][row]);
                setBoard[col][row].setBackgroundResource(R.drawable.box_grey);
            }
            setRowsLayout.addView(setColsLayout);
        }
        setTable = (RelativeLayout)setShipsScreen.findViewById(R.id.SetAndOpBoard);
        setTable.addView(setRowsLayout);
        setTable.setGravity(Gravity.CENTER);

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

    public void checkCell(int row, int col) {

    }
    public boolean isSetMode() {
        return setMode;
    }

    public void setSetMode(boolean setMode) {
        this.setMode = setMode;
    }
    public Cell[][] getSetBoard() {
        return setBoard;
    }

    public RelativeLayout getSetTable() {
        return setTable;
    }

    public void setLevel(String level) {
        int i = 0;
        switch (level) {
            case "EASY":
                setBoardSize(EASY_BOARD_SIZE);

                ships = new Ship[EASY_NO_OF_SHIPS];
                for (Ship ship:ships) {
                    ship = new Ship(EASY_SHIPS_SIZE[i]);
                    i++;
                }
                break;
            case "MEDIUM":
                setBoardSize(MEDIUM_BOARD_SIZE);
                ships = new Ship[MEDIUM_NO_OF_SHIPS];
                for (Ship ship:ships) {
                    ship = new Ship(MEDIUM_SHIPS_SIZE[i]);
                    i++;
                }
                break;
            case "HARD":
                setBoardSize(HARD_BOARD_SIZE);
                ships = new Ship[HARD_NO_OF_SHIPS];
                for (Ship ship:ships) {
                    ship = new Ship(HARD_SHIPS_SIZE[i]);
                    i++;
                }
                break;

        }
    }

}
