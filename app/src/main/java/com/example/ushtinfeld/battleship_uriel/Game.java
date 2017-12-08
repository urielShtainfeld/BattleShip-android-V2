package com.example.ushtinfeld.battleship_uriel;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import components.Cell;
import components.CellListener;
import entities.GameController;
import entities.Ship;

public class Game extends AppCompatActivity implements CellListener, View.OnClickListener {
    public GameController controller;
    public int[] startPos;
    public int[] shipsSizes;
    private Button startBtn;
    private Button randomBtn;
    private Button clearBtn;
    private int placedShips = 0;
    private int currShipSize = 0;
    Ship[] userShips;
    Ship[] compShips;
    boolean randomMode;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        String level = this.getIntent().getStringExtra("Level");
        controller = new GameController(level);
        controller.createSetShipsBoard(this, controller);
        startPos = new int[2];
        startPos[0] = -1;
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
        setButtons();
        setRandomMode(false);
    }

    private void setButtons() {
        startBtn = (Button) findViewById(R.id.Start);
        startBtn.setOnClickListener(this);
        startBtn.setEnabled(false);

        randomBtn = (Button) findViewById(R.id.Random);
        randomBtn.setOnClickListener(this);

        clearBtn = (Button) findViewById(R.id.Clear);
        clearBtn.setOnClickListener(this);
    }

    @Override
    public void buttonClicked(Cell cell) {
        if (controller.isSetMode()) {
            int col = cell.getCol();
            int row = cell.getRow();
            setShips(col, row);
        }
    }

    public boolean setShips(int col, int row) {

        if (startPos[0] > -1) {
            if (col != startPos[0] && row != startPos[1]) {
                if (!isRandomMode()) {
                    AlertDialog.Builder notSameColRowAlert = new AlertDialog.Builder(this);
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
                }
                return false;
            } else {
                if (col == startPos[0]) {
                    if (checkSizeLeft(Math.abs(row - startPos[1]))) {
                        if (startPos[1] > row) {
                            if (checkIfValid(row, startPos[1], col, startPos[0])) {
                                paintCells(row, startPos[1], col, startPos[0]);
                                startPos[0] = -1;
                            } else {
                                if (!isRandomMode()) {
                                    AlertDialog.Builder notValidAlert = new AlertDialog.Builder(this);
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
                                }
                                return false;
                            }
                        } else {
                            if (checkIfValid(startPos[1], row, col, startPos[0])) {
                                paintCells(startPos[1], row, col, startPos[0]);
                                startPos[0] = -1;
                            } else {
                                if (!isRandomMode()) {
                                    AlertDialog.Builder notValidAlert = new AlertDialog.Builder(this);
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
                                }
                                return false;
                            }

                        }
                    } else {
                        if (placedShips != shipsSizes.length) {
                            if (!isRandomMode()) {
                                AlertDialog.Builder notThisSizeAlert = new AlertDialog.Builder(this);
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
                            }
                            return false;
                        }
                    }
                } else {
                    if (checkSizeLeft(Math.abs(col - startPos[0]))) {
                        if (startPos[0] > col) {
                            if (checkIfValid(row, startPos[1], col, startPos[0])) {
                                paintCells(row, startPos[1], col, startPos[0]);
                                startPos[0] = -1;
                            } else {
                                if (!isRandomMode()) {
                                    AlertDialog.Builder notValidAlert = new AlertDialog.Builder(this);
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
                                }
                                return false;
                            }
                        } else {
                            if (checkIfValid(startPos[1], row, startPos[0], col)) {
                                paintCells(startPos[1], row, startPos[0], col);
                                startPos[0] = -1;
                            } else {
                                if (!isRandomMode()) {
                                    AlertDialog.Builder notValidAlert = new AlertDialog.Builder(this);
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

                                }
                                return false;
                            }

                        }
                    } else {
                        if (placedShips != shipsSizes.length) {
                            if (!isRandomMode()) {
                                AlertDialog.Builder notThisSizeAlert = new AlertDialog.Builder(this);
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
                            }
                            return false;
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
                        AlertDialog.Builder notValidAlert = new AlertDialog.Builder(this);
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
                    }
                    return false;
                }
            } else {
                startBtn.setEnabled(true);
                if (!isRandomMode()) {
                    AlertDialog.Builder noMoreShipsAlert = new AlertDialog.Builder(this);
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
                }
                return false;
            }
        }
        return true;
    }

    public boolean checkSizeLeft(int size) {

        for (int i = 0; i < shipsSizes.length; i++) {
            if (size + 1 == shipsSizes[i]) {
                currShipSize = size + 1;
                shipsSizes[i] = -1;
                placedShips += 1;
                return true;
            }

        }
        return false;
    }


    public void onClick(View view) {
        int id = view.getId();
        switch (view.getId()) {
            case R.id.Clear:
                controller.getSetTable().removeAllViews();
                controller.createSetShipsBoard(this, controller);
                String level = this.getIntent().getStringExtra("Level");
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
                break;
            case R.id.Random:
                setRandomMode(true);
                setShipsRandom();
                setRandomMode(false);
                break;
            case R.id.Start:

                break;
        }
    }

    private void setShipsRandom() {
        for (int i = 0; i < shipsSizes.length; i++) {
            int randFirstCol;
            int randFirstRow;
            do {
                randFirstCol = (int) (Math.random() * controller.getBoardSize());
                randFirstRow = (int) (Math.random() * controller.getBoardSize());
            } while (!setShips(randFirstCol, randFirstRow));
            int randSecondCol = 0;
            int randSecondRow = 0;
            do {
                int verOrHor = (int) Math.random() * 2;
                int plusOrMinus = (int) Math.random() * 2;
                switch (verOrHor) {
                    case 0:

                        randSecondRow = randFirstRow;
                        switch (plusOrMinus){
                            case 0:
                                randSecondCol = randFirstCol-shipsSizes[i];

                                break;
                            case 1:
                                randSecondCol = randFirstCol-shipsSizes[i];
                                break;
                        }
                        break;
                    case 1:
                        randSecondCol = randFirstCol;
                        switch (plusOrMinus){
                            case 0:
                                randSecondRow = randFirstRow-shipsSizes[i];
                                break;
                            case 1:
                                randSecondRow = randFirstRow-shipsSizes[i];
                                break;
                        }
                        break;
                }

              if ((randSecondCol < 0 || randSecondCol > controller.getBoardSize()) || (randSecondRow < 0 || randSecondRow > controller.getBoardSize())){
                    break;
              }
            } while (!setShips(randSecondCol, randSecondRow));
        }
    }

    public boolean checkIfValid(int startX, int endX, int startY, int endY) {
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

    private void paintCells(int startX, int endX, int startY, int endY) {
        if (startY == endY && startX != endX) {
            controller.getSetBoard()[startY][startX].setGotShip(true);
            controller.getSetBoard()[startY][startX].setBackgroundResource(R.drawable.blue_cell);
            if (startY != 0) {
                controller.getSetBoard()[startY - 1][startX].setBackgroundResource(R.drawable.box_grey_beside_ship);
                controller.getSetBoard()[startY - 1][startX].setBesideShip(true);
            }
            if (startY != (controller.getBoardSize() - 1)) {
                controller.getSetBoard()[startY + 1][startX].setBackgroundResource(R.drawable.box_grey_beside_ship);
                controller.getSetBoard()[startY + 1][startX].setBesideShip(true);
            }
            if (startX != 0) {
                controller.getSetBoard()[startY][startX - 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                controller.getSetBoard()[startY][startX - 1].setBesideShip(true);
                if (startY != 0) {
                    controller.getSetBoard()[startY - 1][startX - 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                    controller.getSetBoard()[startY - 1][startX - 1].setBesideShip(true);

                }
                if (startY != (controller.getBoardSize() - 1)) {
                    controller.getSetBoard()[startY + 1][startX - 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                    controller.getSetBoard()[startY + 1][startX - 1].setBesideShip(true);
                }
            }
            for (int i = 0; i < (endX - startX); i++) {
                controller.getSetBoard()[startY][startX + i + 1].setBackgroundResource(R.drawable.blue_cell);
                controller.getSetBoard()[startY][startX + i + 1].setGotShip(true);
                if (startY != 0) {
                    controller.getSetBoard()[startY - 1][startX + i + 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                    controller.getSetBoard()[startY - 1][startX + i + 1].setBesideShip(true);
                }
                if (startY != (controller.getBoardSize() - 1)) {
                    controller.getSetBoard()[startY + 1][startX + i + 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                    controller.getSetBoard()[startY + 1][startX + i + 1].setBesideShip(true);
                }
            }
            if (endX != (controller.getBoardSize() - 1)) {
                controller.getSetBoard()[startY][endX + 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                controller.getSetBoard()[startY][endX + 1].setBesideShip(true);
                if (startY != 0) {
                    controller.getSetBoard()[startY - 1][endX + 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                    controller.getSetBoard()[startY - 1][endX + 1].setBesideShip(true);

                }
                if (startY != (controller.getBoardSize() - 1)) {
                    controller.getSetBoard()[startY + 1][endX + 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                    controller.getSetBoard()[startY + 1][endX + 1].setBesideShip(true);
                }
            }
        } else {
            if (startY != endY && startX == endX) {
                controller.getSetBoard()[startY][startX].setGotShip(true);
                controller.getSetBoard()[startY][startX].setBackgroundResource(R.drawable.blue_cell);
                if (startX != 0) {
                    controller.getSetBoard()[startY][startX - 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                    controller.getSetBoard()[startY][startX - 1].setBesideShip(true);

                }
                if (startX != (controller.getBoardSize() - 1)) {
                    controller.getSetBoard()[startY][startX + 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                    controller.getSetBoard()[startY][startX + 1].setBesideShip(true);
                }

                if (startY != 0) {
                    controller.getSetBoard()[startY - 1][startX].setBackgroundResource(R.drawable.box_grey_beside_ship);
                    controller.getSetBoard()[startY - 1][startX].setBesideShip(true);
                    if (startX != 0) {
                        controller.getSetBoard()[startY - 1][startX - 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                        controller.getSetBoard()[startY - 1][startX - 1].setBesideShip(true);

                    }
                    if (startX != (controller.getBoardSize() - 1)) {
                        controller.getSetBoard()[startY - 1][startX + 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                        controller.getSetBoard()[startY - 1][startX + 1].setBesideShip(true);
                    }
                }
                for (int i = 0; i < (endY - startY); i++) {

                    controller.getSetBoard()[startY + i + 1][startX].setBackgroundResource(R.drawable.blue_cell);
                    controller.getSetBoard()[startY + i + 1][startX].setGotShip(true);
                    if (startX != 0) {
                        controller.getSetBoard()[startY + i + 1][startX - 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                        controller.getSetBoard()[startY + i + 1][startX - 1].setBesideShip(true);
                    }
                    if (startX != (controller.getBoardSize() - 1)) {
                        controller.getSetBoard()[startY + i + 1][startX + 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                        controller.getSetBoard()[startY + i + 1][startX + 1].setBesideShip(true);
                    }
                }
                if (endY != (controller.getBoardSize() - 1)) {
                    controller.getSetBoard()[endY + 1][startX].setBackgroundResource(R.drawable.box_grey_beside_ship);
                    controller.getSetBoard()[endY + 1][startX].setBesideShip(true);
                    if (startX != 0) {
                        controller.getSetBoard()[endY + 1][startX - 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                        controller.getSetBoard()[endY + 1][startX - 1].setBesideShip(true);

                    }
                    if (startX != (controller.getBoardSize() - 1)) {
                        controller.getSetBoard()[endY + 1][startX + 1].setBackgroundResource(R.drawable.box_grey_beside_ship);
                        controller.getSetBoard()[endY + 1][startX + 1].setBesideShip(true);
                    }
                }
            } else {
                controller.getSetBoard()[startY][startX].setBackgroundResource(R.drawable.blue_cell);
            }
        }
    }

    public boolean isRandomMode() {
        return randomMode;
    }

    public void setRandomMode(boolean randomMode) {
        this.randomMode = randomMode;
    }
}
