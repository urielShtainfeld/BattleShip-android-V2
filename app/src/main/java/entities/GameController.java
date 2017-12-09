package entities;


import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.ushtinfeld.battleship_uriel.Game;
import com.example.ushtinfeld.battleship_uriel.R;

import components.Cell;

public class GameController {
    private static final int HARD_BOARD_SIZE = 10;
    private static final int MEDIUM_BOARD_SIZE = 9;
    private static final int EASY_BOARD_SIZE = 8;
    public static final int[] EASY_SHIPS_SIZES = {3,4,5};
    public static final int[] MEDIUM_SHIPS_SIZES = {2,3,4,5};
    public static final int[] HARD_SHIPS_SIZES = {2,3,4,5,6};
    int boardSize;
    private LinearLayout setRowsLayout;
    private LinearLayout setColsLayout;
    private Cell [][] setBoard;
    private Cell [][] userBoard;
    private RelativeLayout setTable;
    private RelativeLayout userTable;
    private boolean setMode ;
    boolean randomMode;
    boolean compBoard;
    private int placedShips = 0;
    public int[] startPos;
    public int[] shipsSizes;
    String level;



    public GameController(String controllerLevel) {
        setControllerLevel(controllerLevel);
        this.setMode = true;
    }

    public void createUserBoard(Game game, GameController controller) {
        userBoard = new Cell[controller.getBoardSize()][controller.getBoardSize()];
        setRowsLayout = new LinearLayout(game);
        setRowsLayout.setBackgroundColor(Color.TRANSPARENT);
        setRowsLayout.setOrientation(LinearLayout.VERTICAL);
        //setRowsLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        for (int col = 0; col < controller.getBoardSize(); col++) {
            setColsLayout = new LinearLayout(game);
            setColsLayout.setBackgroundColor(Color.TRANSPARENT);
            setColsLayout.setOrientation(LinearLayout.HORIZONTAL);
            setColsLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            for (int row = 0; row < controller.getBoardSize(); row++) {
                userBoard[col][row] = new Cell(game);
                userBoard[col][row].setPosition(col,row);
                userBoard[col][row].setListener(game);
               // userBoard[col][row].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
                setColsLayout.addView(userBoard[col][row]);
                if (getSetBoard()[col][row].isGotShip()){
                    userBoard[col][row].setGotShip(true);
                    userBoard[col][row].setBackgroundResource(R.drawable.blue15x15);
                }else{
                    userBoard[col][row].setBackgroundResource(R.drawable.grey15x15);
                }
            }
            setRowsLayout.addView(setColsLayout);
        }
        userTable = (RelativeLayout)game.findViewById(R.id.userBoard);
        userTable.addView(setRowsLayout);
        userTable.setGravity(Gravity.CENTER);

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

    public void setControllerLevel(String level) {
        int i = 0;
        setLevel(level);
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
    public boolean setShips(int col, int row,Game setShipsScreen,GameController controller) {
        if (startPos[0] > -1) {
            if (col != startPos[0] && row != startPos[1]) {
                if (!isRandomMode()) {
                    AlertDialog.Builder notSameColRowAlert = new AlertDialog.Builder(setShipsScreen);
                    notSameColRowAlert.setMessage("The second click must be in the same row or collum like the first click");
                    notSameColRowAlert.setTitle("Error Message...");
                    notSameColRowAlert.setPositiveButton("OK", null);
                    notSameColRowAlert.setCancelable(true);
                    notSameColRowAlert.create().show();
                    notSameColRowAlert.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                } else {
                    return false;
                }
            } else {
                if (col == startPos[0]) {
                    if (checkSizeLeft(Math.abs(row - startPos[1]))) {
                        if (startPos[1] > row) {
                            if (checkIfValid(row, startPos[1], col, startPos[0],controller)) {
                                paintCells(row, startPos[1], col, startPos[0],controller);
                                startPos[0] = -1;
                            } else {
                                if (!isRandomMode()) {
                                    AlertDialog.Builder notValidAlert = new AlertDialog.Builder(setShipsScreen);
                                    notValidAlert.setMessage("You can't placed 2 ships on the same cell");
                                    notValidAlert.setTitle("Error Message...");
                                    notValidAlert.setPositiveButton("OK", null);
                                    notValidAlert.setCancelable(true);
                                    notValidAlert.create().show();
                                    notValidAlert.setPositiveButton("Ok",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            });
                                } else {
                                    return false;
                                }
                            }
                        } else {
                            if (checkIfValid(startPos[1], row, col, startPos[0],controller)) {
                                paintCells(startPos[1], row, col, startPos[0],controller);
                                startPos[0] = -1;
                            } else {
                                if (!isRandomMode()) {
                                    AlertDialog.Builder notValidAlert = new AlertDialog.Builder(setShipsScreen);
                                    notValidAlert.setMessage("You can't placed 2 ships on the same cell");
                                    notValidAlert.setTitle("Error Message...");
                                    notValidAlert.setPositiveButton("OK", null);
                                    notValidAlert.setCancelable(true);
                                    notValidAlert.create().show();
                                    notValidAlert.setPositiveButton("Ok",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            });
                                } else {
                                    return false;
                                }
                            }

                        }
                    } else {
                        if (placedShips != shipsSizes.length) {
                            if (!isRandomMode()) {
                                AlertDialog.Builder notThisSizeAlert = new AlertDialog.Builder(setShipsScreen);
                                notThisSizeAlert.setMessage("you don't have this size anymore");
                                notThisSizeAlert.setTitle("Error Message...");
                                notThisSizeAlert.setPositiveButton("OK", null);
                                notThisSizeAlert.setCancelable(true);
                                notThisSizeAlert.create().show();
                                notThisSizeAlert.setPositiveButton("Ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });
                            } else {
                                return false;
                            }
                        }
                    }
                } else {
                    if (checkSizeLeft(Math.abs(col - startPos[0]))) {
                        if (startPos[0] > col) {
                            if (checkIfValid(row, startPos[1], col, startPos[0],controller)) {
                                paintCells(row, startPos[1], col, startPos[0],controller);
                                startPos[0] = -1;
                            } else {
                                if (!isRandomMode()) {
                                    AlertDialog.Builder notValidAlert = new AlertDialog.Builder(setShipsScreen);
                                    notValidAlert.setMessage("You can't placed 2 ships on the same cell");
                                    notValidAlert.setTitle("Error Message...");
                                    notValidAlert.setPositiveButton("OK", null);
                                    notValidAlert.setCancelable(true);
                                    notValidAlert.create().show();
                                    notValidAlert.setPositiveButton("Ok",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            });
                                } else {
                                    return false;
                                }
                            }
                        } else {
                            if (checkIfValid(startPos[1], row, startPos[0], col,controller)) {
                                paintCells(startPos[1], row, startPos[0], col,controller);
                                startPos[0] = -1;
                            } else {
                                if (!isRandomMode()) {
                                    AlertDialog.Builder notValidAlert = new AlertDialog.Builder(setShipsScreen);
                                    notValidAlert.setMessage("You can't placed 2 ships on the same cell");
                                    notValidAlert.setTitle("Error Message...");
                                    notValidAlert.setPositiveButton("OK", null);
                                    notValidAlert.setCancelable(true);
                                    notValidAlert.create().show();
                                    notValidAlert.setPositiveButton("Ok",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            });

                                } else {
                                    return false;
                                }
                            }

                        }
                    } else {
                        if (placedShips != shipsSizes.length) {
                            if (!isRandomMode()) {
                                AlertDialog.Builder notThisSizeAlert = new AlertDialog.Builder(setShipsScreen);
                                notThisSizeAlert.setMessage("you don't have this size anymore");
                                notThisSizeAlert.setTitle("Error Message...");
                                notThisSizeAlert.setPositiveButton("OK", null);
                                notThisSizeAlert.setCancelable(true);
                                notThisSizeAlert.create().show();
                                notThisSizeAlert.setPositiveButton("Ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });
                            } else {
                                return false;
                            }
                        }

                    }

                }
            }
        } else {
            if (placedShips != shipsSizes.length) {
                if (checkIfValid(row, row, col, col,controller)) {
                    startPos[0] = col;
                    startPos[1] = row;
                    paintCells(startPos[1], startPos[1], startPos[0], startPos[0],controller);
                } else {
                    if (!isRandomMode()) {
                        AlertDialog.Builder notValidAlert = new AlertDialog.Builder(setShipsScreen);
                        notValidAlert.setMessage("You can't placed 2 ships on the same cell");
                        notValidAlert.setTitle("Error Message...");
                        notValidAlert.setPositiveButton("OK", null);
                        notValidAlert.setCancelable(true);
                        notValidAlert.create().show();
                        notValidAlert.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                    } else {
                        return false;
                    }
                }
            } else {
                if (!isRandomMode()) {
                    AlertDialog.Builder noMoreShipsAlert = new AlertDialog.Builder(setShipsScreen);
                    noMoreShipsAlert.setMessage("you placed all the ships please start the game");
                    noMoreShipsAlert.setTitle("Message");
                    noMoreShipsAlert.setPositiveButton("OK", null);
                    noMoreShipsAlert.setCancelable(true);
                    noMoreShipsAlert.create().show();
                    noMoreShipsAlert.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                } else {
                    return true;
                }
            }
        }
        return true;
    }

    public boolean checkSizeLeft(int size) {

        for (int i = 0; i < shipsSizes.length; i++) {
            if (size + 1 == shipsSizes[i]) {
                if (!randomMode) {
                    shipsSizes[i] = -1;
                    placedShips += 1;
                }
                return true;
            }

        }
        return false;
    }

    public boolean checkIfValid(int startX, int endX, int startY, int endY,GameController controller) {
        if (controller.getSetBoard()[startY][startX].isGotShip() || controller.getSetBoard()[startY][startX].isBesideShip())
            return false;
        if (startY == endY && startX != endX) {
            for (int i = 0; i < (endX - startX); i++) {
                if (controller.getSetBoard()[startY][startX + i + 1].isGotShip() || controller.getSetBoard()[startY][startX + i + 1].isBesideShip())
                    return false;
            }
        } else {
            if (startY != endY && startX == endX) {

                for (int i = 0; i < (endY - startY); i++) {
                    if (controller.getSetBoard()[startY + i + 1][startX].isGotShip() || controller.getSetBoard()[startY + i + 1][startX].isBesideShip())
                        return false;
                }
            }
        }
        return true;
    }

    private void paintCells(int startX, int endX, int startY, int endY,GameController controller) {
        if (startY == endY && startX != endX) {
            controller.getSetBoard()[startY][startX].setGotShip(true);
            if (!isCompBoard())
                controller.getSetBoard()[startY][startX].setBackgroundResource(R.drawable.blue_cell);
            if (startY != 0) {
                if (!isCompBoard())
                    controller.getSetBoard()[startY - 1][startX].setBackgroundResource(R.drawable.box_grey_beside_ship);
                controller.getSetBoard()[startY - 1][startX].setBesideShip(true);
            }
            if (startY != (controller.getBoardSize() - 1)) {
                if (!isCompBoard())
                    controller.getSetBoard()[startY + 1][startX].setBackgroundResource(R.drawable.box_grey_beside_ship);
                controller.getSetBoard()[startY + 1][startX].setBesideShip(true);
            }
            if (startX != 0) {
                if (!isCompBoard())
                    controller.getSetBoard()[startY][startX - 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                controller.getSetBoard()[startY][startX - 1].setBesideShip(true);
                if (startY != 0) {
                    if (!isCompBoard())
                        controller.getSetBoard()[startY - 1][startX - 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                    controller.getSetBoard()[startY - 1][startX - 1].setBesideShip(true);

                }
                if (startY != (controller.getBoardSize() - 1)) {
                    if (!isCompBoard())
                        controller.getSetBoard()[startY + 1][startX - 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                    controller.getSetBoard()[startY + 1][startX - 1].setBesideShip(true);
                }
            }
            for (int i = 0; i < (endX - startX); i++) {
                if (!isCompBoard())
                    controller.getSetBoard()[startY][startX + i + 1].setBackgroundResource(R.drawable.blue_cell);
                controller.getSetBoard()[startY][startX + i + 1].setGotShip(true);
                if (startY != 0) {
                    if (!isCompBoard())
                        controller.getSetBoard()[startY - 1][startX + i + 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                    controller.getSetBoard()[startY - 1][startX + i + 1].setBesideShip(true);
                }
                if (startY != (controller.getBoardSize() - 1)) {
                    if (!isCompBoard())
                        controller.getSetBoard()[startY + 1][startX + i + 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                    controller.getSetBoard()[startY + 1][startX + i + 1].setBesideShip(true);
                }
            }
            if (endX != (controller.getBoardSize() - 1)) {
                if (!isCompBoard())
                    controller.getSetBoard()[startY][endX + 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                controller.getSetBoard()[startY][endX + 1].setBesideShip(true);
                if (startY != 0) {
                    if (!isCompBoard())
                        controller.getSetBoard()[startY - 1][endX + 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                    controller.getSetBoard()[startY - 1][endX + 1].setBesideShip(true);

                }
                if (startY != (controller.getBoardSize() - 1)) {
                    if (!isCompBoard())
                        controller.getSetBoard()[startY + 1][endX + 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                    controller.getSetBoard()[startY + 1][endX + 1].setBesideShip(true);
                }
            }
        } else {
            if (startY != endY && startX == endX) {

                controller.getSetBoard()[startY][startX].setGotShip(true);
                if (!isCompBoard())
                    controller.getSetBoard()[startY][startX].setBackgroundResource(R.drawable.blue_cell);
                if (startX != 0) {
                    if (!isCompBoard())
                        controller.getSetBoard()[startY][startX - 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                    controller.getSetBoard()[startY][startX - 1].setBesideShip(true);

                }
                if (startX != (controller.getBoardSize() - 1)) {
                    if (!isCompBoard())
                        controller.getSetBoard()[startY][startX + 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                    controller.getSetBoard()[startY][startX + 1].setBesideShip(true);
                }

                if (startY != 0) {
                    if (!isCompBoard())
                        controller.getSetBoard()[startY - 1][startX].setBackgroundResource(R.drawable.box_grey_beside_ship);
                    controller.getSetBoard()[startY - 1][startX].setBesideShip(true);
                    if (startX != 0) {
                        if (!isCompBoard())
                            controller.getSetBoard()[startY - 1][startX - 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                        controller.getSetBoard()[startY - 1][startX - 1].setBesideShip(true);

                    }
                    if (startX != (controller.getBoardSize() - 1)) {
                        if (!isCompBoard())
                            controller.getSetBoard()[startY - 1][startX + 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                        controller.getSetBoard()[startY - 1][startX + 1].setBesideShip(true);
                    }
                }
                for (int i = 0; i < (endY - startY); i++) {
                    if (!isCompBoard())
                        controller.getSetBoard()[startY + i + 1][startX].setBackgroundResource(R.drawable.blue_cell);
                    controller.getSetBoard()[startY + i + 1][startX].setGotShip(true);
                    if (startX != 0) {
                        if (!isCompBoard())
                            controller.getSetBoard()[startY + i + 1][startX - 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                        controller.getSetBoard()[startY + i + 1][startX - 1].setBesideShip(true);
                    }
                    if (startX != (controller.getBoardSize() - 1)) {
                        if (!isCompBoard())
                            controller.getSetBoard()[startY + i + 1][startX + 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                        controller.getSetBoard()[startY + i + 1][startX + 1].setBesideShip(true);
                    }
                }
                if (endY != (controller.getBoardSize() - 1)) {
                    if (!isCompBoard())
                        controller.getSetBoard()[endY + 1][startX].setBackgroundResource(R.drawable.box_grey_beside_ship);
                    controller.getSetBoard()[endY + 1][startX].setBesideShip(true);
                    if (startX != 0) {
                        if (!isCompBoard())
                            controller.getSetBoard()[endY + 1][startX - 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                        controller.getSetBoard()[endY + 1][startX - 1].setBesideShip(true);

                    }
                    if (startX != (controller.getBoardSize() - 1)) {
                        if (!isCompBoard())
                            controller.getSetBoard()[endY + 1][startX + 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                        controller.getSetBoard()[endY + 1][startX + 1].setBesideShip(true);
                    }
                }
            } else {
                if (!isCompBoard())
                    controller.getSetBoard()[startY][startX].setBackgroundResource(R.drawable.blue_cell);
            }
        }
    }

    public void setShipsRandom(Game setShipsScreen, GameController controller) {
        boolean OK;
        int count = 0;
        int randFirstCol;
        int randFirstRow;
        for (int i = 0; i < shipsSizes.length; i++) {
            do {
                randFirstCol = (int) (Math.random() * (controller.getBoardSize()-1));
                randFirstRow = (int) (Math.random() * (controller.getBoardSize()-1));
            } while (!setShips(randFirstCol, randFirstRow,setShipsScreen,controller));
            int randSecondCol = 0;
            int randSecondRow = 0;
            do {

                int verOrHor = (int) (Math.random() * 2);
                int plusOrMinus = (int) (Math.random() * 2);
                switch (verOrHor) {
                    case 0:

                        randSecondRow = randFirstRow;
                        switch (plusOrMinus) {
                            case 0:
                                randSecondCol = randFirstCol - shipsSizes[i] + 1;

                                break;
                            case 1:
                                randSecondCol = randFirstCol + shipsSizes[i] - 1;
                                break;
                        }
                        break;
                    case 1:
                        randSecondCol = randFirstCol;
                        switch (plusOrMinus) {
                            case 0:
                                randSecondRow = randFirstRow - shipsSizes[i] + 1;
                                break;
                            case 1:
                                randSecondRow = randFirstRow + shipsSizes[i] - 1;
                                break;
                        }
                        break;
                }
                if ((randSecondCol < 0 || randSecondCol > controller.getBoardSize()-1) || (randSecondRow < 0 || randSecondRow > controller.getBoardSize()-1) || (count == 10)) {
                    --i;
                    count = 0;
                    OK = false;
                    controller.getSetBoard()[startPos[0]][startPos[1]].setBackgroundResource(R.drawable.box_grey);
                    startPos[0] = -1;
                    break;
                } else {
                    count++;
                    OK = true;
                }
            } while (!setShips(randSecondCol, randSecondRow,setShipsScreen,controller));
            if (OK) {
                shipsSizes[i] = -1;
                placedShips += 1;
            }
        }
    }
    public void clearBoard(Game setShipsScreen,GameController controller) {
        controller.getSetTable().removeAllViews();
        controller.createSetShipsBoard(setShipsScreen, controller);
        String level = setShipsScreen.getIntent().getStringExtra("Level");
        switch (level) {
            case "EASY":
                shipsSizes = GameController.EASY_SHIPS_SIZES.clone();
                break;
            case "MEDIUM":
                shipsSizes = GameController.MEDIUM_SHIPS_SIZES.clone();
                break;
            case "HARD":
                shipsSizes = GameController.HARD_SHIPS_SIZES.clone();
                break;
        }
        placedShips = 0;
        startPos[0] = -1;
    }



    public boolean isRandomMode() {
        return randomMode;
    }

    public void setRandomMode(boolean randomMode) {
        this.randomMode = randomMode;
    }

    public boolean isCompBoard() {
        return compBoard;
    }

    public void setCompBoard(boolean compBoard) {
        this.compBoard = compBoard;
    }


    public void initVars(Game setShipsScreen, GameController controller) {
        startPos = new int[2];
        startPos[0] = -1;
        switch (controller.getLevel()) {
            case "EASY":
                shipsSizes = GameController.EASY_SHIPS_SIZES.clone();
                break;
            case "MEDIUM":
                shipsSizes = GameController.MEDIUM_SHIPS_SIZES.clone();
                break;
            case "HARD":
                shipsSizes = GameController.HARD_SHIPS_SIZES.clone();
                break;
        }
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Cell[][] getUserBoard() {
        return userBoard;
    }

    public void setUserBoard(Cell[][] userBoard) {
        this.userBoard = userBoard;
    }


}
