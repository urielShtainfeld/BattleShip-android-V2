package entities;


import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ushtinfeld.battleship_uriel.Game;
import com.example.ushtinfeld.battleship_uriel.R;

import components.Cell;

public class GameController {
    private static final int HARD_BOARD_SIZE = 10;
    private static final int MEDIUM_BOARD_SIZE = 9;
    private static final int EASY_BOARD_SIZE = 8;
    private static final int[] EASY_SHIPS_SIZES = {3, 4, 5};
    private static final int[] MEDIUM_SHIPS_SIZES = {2, 3, 4, 5};
    private static final int[] HARD_SHIPS_SIZES = {2, 3, 4, 5, 6};
    public static final int HARD_MIN_HITS = 20;
    public static final int MEDIUM_MIN_HITS = 14;
    public static final int EASY_MIN_HITS = 12;

    private int boardSize;
    private LinearLayout setRowsLayout;
    private LinearLayout setColsLayout;
    private Cell[][] setBoard;
    private Cell[][] userBoard;
    public RelativeLayout setTable;
    private RelativeLayout userTable;
    private boolean setMode;
    private boolean randomMode;
    private boolean compBoard;
    private int placedShips = 0;
    private int[] startPos;
    private int[] shipsSizes;
    private String level;
    private Ship[] comShips, userShips;
    private int currShipSize;


    public GameController(String thisLevel) {
        setThisLevel(thisLevel);
        this.setMode = true;
    }

    public void createUserBoard(Game game) {
        userBoard = new Cell[this.getBoardSize()][this.getBoardSize()];
        setRowsLayout = new LinearLayout(game);
        setRowsLayout.setBackgroundColor(Color.TRANSPARENT);
        setRowsLayout.setOrientation(LinearLayout.VERTICAL);
        for (int col = 0; col < this.getBoardSize(); col++) {
            setColsLayout = new LinearLayout(game);
            setColsLayout.setBackgroundColor(Color.TRANSPARENT);
            setColsLayout.setOrientation(LinearLayout.HORIZONTAL);
            setColsLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            for (int row = 0; row < this.getBoardSize(); row++) {
                userBoard[col][row] = new Cell(game);
                userBoard[col][row].setPosition(col, row);
                userBoard[col][row].setListener(game);
                setColsLayout.addView(userBoard[col][row]);
                if (getSetBoard()[col][row].isGotShip()) {
                    userBoard[col][row].setGotShip(true);
                    userBoard[col][row].setShipID(getSetBoard()[col][row].getShipID());
                    userBoard[col][row].setBackgroundResource(R.drawable.blue15x15);
                } else {
                    userBoard[col][row].setBackgroundResource(R.drawable.grey15x15);
                }

                userBoard[col][row].setClickable(false);
            }
            setRowsLayout.addView(setColsLayout);
        }
        userTable = (RelativeLayout) game.findViewById(R.id.userBoard);
        userTable.addView(setRowsLayout);
        userTable.setGravity(Gravity.CENTER);

    }

    public void createSetShipsBoard(Game setShipsScreen) {
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
                setBoard[col][row].setPosition(col, row);
                setBoard[col][row].setListener(setShipsScreen);
                setBoard[col][row].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                setColsLayout.addView(setBoard[col][row]);
                setBoard[col][row].setBackgroundResource(R.drawable.box_grey);
            }
            setRowsLayout.addView(setColsLayout);
        }
        setTable = (RelativeLayout) setShipsScreen.findViewById(R.id.SetAndOpBoard);
        setTable.addView(setRowsLayout);
        setTable.setGravity(Gravity.CENTER);
    }

    public int getBoardSize() {
        return boardSize;
    }

    private void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
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

    private RelativeLayout getSetTable() {
        return setTable;
    }

    private void setThisLevel(String level) {
        setLevel(level);
        switch (level) {
            case "EASY":
                setBoardSize(EASY_BOARD_SIZE);
                this.userShips = new Ship[EASY_SHIPS_SIZES.length];
                this.comShips = new Ship[EASY_SHIPS_SIZES.length];
                for (int i = 0; i < EASY_SHIPS_SIZES.length; i++) {
                    this.userShips[i] = new Ship(EASY_SHIPS_SIZES[i]);
                    this.comShips[i] = new Ship(EASY_SHIPS_SIZES[i]);
                }
                break;
            case "MEDIUM":
                setBoardSize(MEDIUM_BOARD_SIZE);
                this.userShips = new Ship[MEDIUM_SHIPS_SIZES.length];
                this.comShips = new Ship[MEDIUM_SHIPS_SIZES.length];
                for (int i = 0; i < MEDIUM_SHIPS_SIZES.length; i++) {
                    this.userShips[i] = new Ship(MEDIUM_SHIPS_SIZES[i]);
                    this.comShips[i] = new Ship(MEDIUM_SHIPS_SIZES[i]);
                }
                break;
            case "HARD":
                setBoardSize(HARD_BOARD_SIZE);
                this.userShips = new Ship[HARD_SHIPS_SIZES.length];
                this.comShips = new Ship[HARD_SHIPS_SIZES.length];
                for (int i = 0; i < HARD_SHIPS_SIZES.length; i++) {
                    this.userShips[i] = new Ship(HARD_SHIPS_SIZES[i]);
                    this.comShips[i] = new Ship(HARD_SHIPS_SIZES[i]);
                }
                break;

        }
    }

    public boolean setShips(int col, int row, Game setShipsScreen) {
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
                    if (checkSizeLeft(Math.abs(row - startPos[1]), setShipsScreen)) {
                        if (startPos[1] > row) {
                            if (checkIfValid(row, startPos[1], col, startPos[0])) {
                                paintCells(row, startPos[1], col, startPos[0]);
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
                            if (checkIfValid(startPos[1], row, col, startPos[0])) {
                                paintCells(startPos[1], row, col, startPos[0]);
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
                    if (checkSizeLeft(Math.abs(col - startPos[0]), setShipsScreen)) {
                        if (startPos[0] > col) {
                            if (checkIfValid(row, startPos[1], col, startPos[0])) {
                                paintCells(row, startPos[1], col, startPos[0]);
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
                            if (checkIfValid(startPos[1], row, startPos[0], col)) {
                                paintCells(startPos[1], row, startPos[0], col);
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
                if (checkIfValid(row, row, col, col)) {
                    startPos[0] = col;
                    startPos[1] = row;
                    paintCells(startPos[1], startPos[1], startPos[0], startPos[0]);
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
                    setShipsScreen.getStartBtn().setClickable(true);
                } else {
                    return true;
                }
            }
        }
        return true;
    }

    private boolean checkSizeLeft(int size, Game game) {

        for (int i = 0; i < shipsSizes.length; i++) {
            if (size + 1 == shipsSizes[i]) {
                currShipSize = shipsSizes[i];
                if (!randomMode) {
                    shipsSizes[i] = -1;
                    placedShips += 1;
                }
                game.getStartBtn().setClickable(true);
                game.getShipsToPlain()[i].setVisibility(View.GONE);
                return true;
            }

        }
        return false;
    }

    private boolean checkIfValid(int startX, int endX, int startY, int endY) {
        if (this.getSetBoard()[startY][startX].isGotShip() || this.getSetBoard()[startY][startX].isBesideShip())
            return false;
        if (startY == endY && startX != endX) {
            for (int i = 0; i < (endX - startX); i++) {
                if (this.getSetBoard()[startY][startX + i + 1].isGotShip() || this.getSetBoard()[startY][startX + i + 1].isBesideShip())
                    return false;
            }
        } else {
            if (startY != endY && startX == endX) {

                for (int i = 0; i < (endY - startY); i++) {
                    if (this.getSetBoard()[startY + i + 1][startX].isGotShip() || this.getSetBoard()[startY + i + 1][startX].isBesideShip())
                        return false;
                }
            }
        }
        return true;
    }

    private Ship getShipIdBySize(int size, Ship[] ships) {
        for (Ship ship : ships) {
            if (ship.getSize() == size && !ship.isPlaced()) {
                return ship;
            }
        }
        return null;
    }

    private void paintCells(int startX, int endX, int startY, int endY) {

        if (startY == endY && startX != endX) {
            Ship currShip = null;
            if (!isCompBoard()) {
                currShip = getShipIdBySize(currShipSize, this.getUserShips());
            } else {
                currShip = getShipIdBySize(currShipSize, this.getComShips());
            }

            this.getSetBoard()[startY][startX].setGotShip(true);
            if (!isCompBoard())
                this.getSetBoard()[startY][startX].setBackgroundResource(R.drawable.blue_cell);
            this.getSetBoard()[startY][startX].setShipID(currShip.getId());

            if (startY != 0) {
                if (!isCompBoard())
                    this.getSetBoard()[startY - 1][startX].setBackgroundResource(R.drawable.box_grey_beside_ship);
                this.getSetBoard()[startY - 1][startX].setBesideShip(true);
            }
            if (startY != (this.getBoardSize() - 1)) {
                if (!isCompBoard())
                    this.getSetBoard()[startY + 1][startX].setBackgroundResource(R.drawable.box_grey_beside_ship);
                this.getSetBoard()[startY + 1][startX].setBesideShip(true);
            }
            if (startX != 0) {
                if (!isCompBoard())
                    this.getSetBoard()[startY][startX - 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                this.getSetBoard()[startY][startX - 1].setBesideShip(true);
                if (startY != 0) {
                    if (!isCompBoard())
                        this.getSetBoard()[startY - 1][startX - 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                    this.getSetBoard()[startY - 1][startX - 1].setBesideShip(true);

                }
                if (startY != (this.getBoardSize() - 1)) {
                    if (!isCompBoard())
                        this.getSetBoard()[startY + 1][startX - 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                    this.getSetBoard()[startY + 1][startX - 1].setBesideShip(true);
                }
            }
            for (int i = 0; i < (endX - startX); i++) {
                if (!isCompBoard())
                    this.getSetBoard()[startY][startX + i + 1].setBackgroundResource(R.drawable.blue_cell);
                this.getSetBoard()[startY][startX + i + 1].setShipID(currShip.getId());
                this.getSetBoard()[startY][startX + i + 1].setGotShip(true);
                if (startY != 0) {
                    if (!isCompBoard())
                        this.getSetBoard()[startY - 1][startX + i + 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                    this.getSetBoard()[startY - 1][startX + i + 1].setBesideShip(true);
                }
                if (startY != (this.getBoardSize() - 1)) {
                    if (!isCompBoard())
                        this.getSetBoard()[startY + 1][startX + i + 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                    this.getSetBoard()[startY + 1][startX + i + 1].setBesideShip(true);
                }
            }
            if (endX != (this.getBoardSize() - 1)) {
                if (!isCompBoard())
                    this.getSetBoard()[startY][endX + 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                this.getSetBoard()[startY][endX + 1].setBesideShip(true);
                if (startY != 0) {
                    if (!isCompBoard())
                        this.getSetBoard()[startY - 1][endX + 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                    this.getSetBoard()[startY - 1][endX + 1].setBesideShip(true);

                }
                if (startY != (this.getBoardSize() - 1)) {
                    if (!isCompBoard())
                        this.getSetBoard()[startY + 1][endX + 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                    this.getSetBoard()[startY + 1][endX + 1].setBesideShip(true);
                }
            }
            currShip.setPlaced(true);
        } else {
            if (startY != endY && startX == endX) {
                Ship currShip = null;
                if (!isCompBoard()) {
                    currShip = getShipIdBySize(currShipSize, this.getUserShips());
                } else {
                    currShip = getShipIdBySize(currShipSize, this.getComShips());
                }
                this.getSetBoard()[startY][startX].setGotShip(true);
                if (!isCompBoard())
                    this.getSetBoard()[startY][startX].setBackgroundResource(R.drawable.blue_cell);
                this.getSetBoard()[startY][startX].setShipID(currShip.getId());
                if (startX != 0) {
                    if (!isCompBoard())
                        this.getSetBoard()[startY][startX - 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                    this.getSetBoard()[startY][startX - 1].setBesideShip(true);

                }
                if (startX != (this.getBoardSize() - 1)) {
                    if (!isCompBoard())
                        this.getSetBoard()[startY][startX + 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                    this.getSetBoard()[startY][startX + 1].setBesideShip(true);
                }

                if (startY != 0) {
                    if (!isCompBoard())
                        this.getSetBoard()[startY - 1][startX].setBackgroundResource(R.drawable.box_grey_beside_ship);
                    this.getSetBoard()[startY - 1][startX].setBesideShip(true);
                    if (startX != 0) {
                        if (!isCompBoard())
                            this.getSetBoard()[startY - 1][startX - 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                        this.getSetBoard()[startY - 1][startX - 1].setBesideShip(true);

                    }
                    if (startX != (this.getBoardSize() - 1)) {
                        if (!isCompBoard())
                            this.getSetBoard()[startY - 1][startX + 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                        this.getSetBoard()[startY - 1][startX + 1].setBesideShip(true);
                    }
                }
                for (int i = 0; i < (endY - startY); i++) {
                    if (!isCompBoard())
                        this.getSetBoard()[startY + i + 1][startX].setBackgroundResource(R.drawable.blue_cell);
                    this.getSetBoard()[startY + i + 1][startX].setShipID(currShip.getId());
                    this.getSetBoard()[startY + i + 1][startX].setGotShip(true);
                    if (startX != 0) {
                        if (!isCompBoard())
                            this.getSetBoard()[startY + i + 1][startX - 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                        this.getSetBoard()[startY + i + 1][startX - 1].setBesideShip(true);
                    }
                    if (startX != (this.getBoardSize() - 1)) {
                        if (!isCompBoard())
                            this.getSetBoard()[startY + i + 1][startX + 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                        this.getSetBoard()[startY + i + 1][startX + 1].setBesideShip(true);
                    }
                }
                if (endY != (this.getBoardSize() - 1)) {
                    if (!isCompBoard())
                        this.getSetBoard()[endY + 1][startX].setBackgroundResource(R.drawable.box_grey_beside_ship);
                    this.getSetBoard()[endY + 1][startX].setBesideShip(true);
                    if (startX != 0) {
                        if (!isCompBoard())
                            this.getSetBoard()[endY + 1][startX - 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                        this.getSetBoard()[endY + 1][startX - 1].setBesideShip(true);

                    }
                    if (startX != (this.getBoardSize() - 1)) {
                        if (!isCompBoard())
                            this.getSetBoard()[endY + 1][startX + 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                        this.getSetBoard()[endY + 1][startX + 1].setBesideShip(true);
                    }
                }
                currShip.setPlaced(true);
            } else {
                if (!isCompBoard())
                    this.getSetBoard()[startY][startX].setBackgroundResource(R.drawable.blue_cell);
            }
        }
    }

    public void setShipsRandom(Game setShipsScreen) {
        boolean OK;
        int count = 0;
        int randFirstCol;
        int randFirstRow;
        for (int i = 0; i < shipsSizes.length; i++) {
            do {
                randFirstCol = (int) (Math.random() * (this.getBoardSize() - 1));
                randFirstRow = (int) (Math.random() * (this.getBoardSize() - 1));
            } while (!setShips(randFirstCol, randFirstRow, setShipsScreen));
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
                if ((randSecondCol < 0 || randSecondCol > this.getBoardSize() - 1) || (randSecondRow < 0 || randSecondRow > this.getBoardSize() - 1) || (count == 10)) {
                    --i;
                    count = 0;
                    OK = false;
                    this.getSetBoard()[startPos[0]][startPos[1]].setBackgroundResource(R.drawable.box_grey);
                    startPos[0] = -1;
                    break;
                } else {
                    count++;
                    OK = true;
                }
            } while (!setShips(randSecondCol, randSecondRow, setShipsScreen));
            if (OK) {
                shipsSizes[i] = -1;
                placedShips += 1;
            }
        }
    }

    public void clearBoard(Game setShipsScreen) {
        this.getSetTable().removeAllViews();
        this.createSetShipsBoard(setShipsScreen);
        String level = setShipsScreen.getIntent().getStringExtra("Level");
        switch (level) {
            case "EASY":
                this.shipsSizes = GameController.EASY_SHIPS_SIZES.clone();

                break;
            case "MEDIUM":
                this.shipsSizes = GameController.MEDIUM_SHIPS_SIZES.clone();
                break;
            case "HARD":
                this.shipsSizes = GameController.HARD_SHIPS_SIZES.clone();
                break;
        }
        placedShips = 0;
        startPos[0] = -1;
        for (Ship ship : userShips
                ) {
            ship.clearShip();
        }
        for (TextView v : setShipsScreen.getShipsToPlain()
                ) {
            v.setVisibility(View.GONE);
        }
    }


    private boolean isRandomMode() {
        return randomMode;
    }

    public void setRandomMode(boolean randomMode) {
        this.randomMode = randomMode;
    }

    private boolean isCompBoard() {
        return compBoard;
    }

    public void setCompBoard(boolean compBoard) {
        this.compBoard = compBoard;
    }


    public void initVars(Game setShipsScreen) {
        startPos = new int[2];
        startPos[0] = -1;
        switch (this.getLevel()) {
            case "EASY":
                this.shipsSizes = GameController.EASY_SHIPS_SIZES.clone();
                break;
            case "MEDIUM":
                this.shipsSizes = GameController.MEDIUM_SHIPS_SIZES.clone();
                break;
            case "HARD":
                this.shipsSizes = GameController.HARD_SHIPS_SIZES.clone();
                break;
        }
    }

    private String getLevel() {
        return level;
    }

    private void setLevel(String level) {
        this.level = level;
    }

    public Cell[][] getUserBoard() {
        return userBoard;
    }


    public boolean handelHit(int col, int row, Game game) {
        if (!this.getSetBoard()[col][row].isGotHit()) {
            this.getSetBoard()[col][row].setGotHit(true);
            if (this.getSetBoard()[col][row].isGotShip()) {
                this.getSetBoard()[col][row].setBackgroundResource(R.drawable.box_grey_ship_hit);
                Ship hitedShip = game.controller.getShipById(game.controller.getSetBoard()[col][row].getShipID(), game.controller.getComShips());
                hitedShip.setNoOfHits();
                if (hitedShip.isSink()) {
                    game.minusNumOfcomShipsLeft();
                    game.getComShipsLeft().setText(Integer.toString(game.getNumOfcomShipsLeft()));
                    if (game.getNumOfcomShipsLeft() == 0) {
                        game.getGameRole().victory(game);
                    } else {
                        final Dialog builder = new Dialog(game);
                        builder.requestWindowFeature(Window.FEATURE_SWIPE_TO_DISMISS);
                        builder.getWindow().setBackgroundDrawable(
                                new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                //nothing;
                            }
                        });
                        ImageView imageView = new ImageView(game);
                        imageView.setImageResource(R.drawable.victory_smiley);
                        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));
                        builder.setCancelable(true);
                        builder.show();
                        /*TranslateAnimation animation = new TranslateAnimation(0, 0, 0, 2000);
                        animation.setDuration(1000);
                        animation.setFillAfter(false);
                        animation.setAnimationListener(new UserAnimationListener());

                        this.getSetTable().startAnimation(animation);*/
                    }
                }
            } else {
                this.getSetBoard()[col][row].setBackgroundResource(R.drawable.box_grey_ship_miss);
            }
            return true;
        }
        return false;
    }


    public void setTextViews(Game game) {
        game.setShipsLeft((TextView) game.findViewById(R.id.shipsLeft));
        game.setComShipsLeft((TextView) game.findViewById(R.id.COMShipsLeft));
        game.setTurn((TextView) game.findViewById(R.id.turn));
        game.getShipsLeft().setText(Integer.toString(this.placedShips));
        game.getComShipsLeft().setText(Integer.toString(this.placedShips));
        game.getTurn().setText(R.string.yourTurn);
        game.getTurn().setTextColor(Color.GREEN);
    }

    public int getPlacedShips() {
        return placedShips;
    }

    public Ship getShipById(int id, Ship[] ships) {
        for (Ship ship : ships) {
            if (id == ship.getId()) {
                return ship;
            }
        }
        return null;
    }

    public Ship[] getComShips() {
        return comShips;
    }

    public Ship[] getUserShips() {
        return userShips;
    }

    public int[] getShipsSizes() {
        return this.shipsSizes;
    }
    private class UserAnimationListener implements Animation.AnimationListener{

        @Override
        public void onAnimationEnd(Animation animation) {
            setTable.clearAnimation();
            setTable.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }

    }
}
