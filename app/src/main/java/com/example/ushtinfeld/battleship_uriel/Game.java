package com.example.ushtinfeld.battleship_uriel;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


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
                shipsSizes = GameController.EASY_SHIPS_SIZE;
                break;
            case "MEDIUM":
                shipsSizes = GameController.MEDIUM_SHIPS_SIZE;
                break;
            case "HARD":
                shipsSizes = GameController.HARD_SHIPS_SIZE;
                break;
        }
        setButtons();
    }

    private void setButtons() {
        startBtn = (Button) findViewById(R.id.Start);
        startBtn.setOnClickListener(this);

        randomBtn = (Button) findViewById(R.id.Random);
        randomBtn.setOnClickListener(this);

        clearBtn = (Button) findViewById(R.id.Clear);
        clearBtn.setOnClickListener(this);
    }

    @Override
    public void buttonClicked(Cell cell) {
        if (controller.isSetMode()) {
            setShips(cell);
        }
    }

    public void setShips(Cell cell) {
        int col = cell.getCol();
        int row = cell.getRow();
        if (startPos[0] > -1) {
            if (cell.getCol() != startPos[0] && cell.getRow() != startPos[1]) {
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
            } else {
                if (cell.getCol() == startPos[0]) {
                    if (checkSizeLeft(Math.abs(cell.getRow() - startPos[1]))) {
                        if (startPos[1] > cell.getRow()) {
                            if (checkIfValid(cell.getRow(), startPos[1], cell.getCol(), startPos[0])) {
                                paintCells(cell.getRow(), startPos[1], cell.getCol(), startPos[0]);
                                startPos[0] = -1;
                            } else {
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
                        } else {
                            if (checkIfValid(startPos[1], cell.getRow(), cell.getCol(), startPos[0])) {
                                paintCells(startPos[1], cell.getRow(), cell.getCol(), startPos[0]);
                                startPos[0] = -1;
                            } else {
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
                        }
                    } else {
                        if (placedShips != shipsSizes.length) {
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
                        } else {
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
                    }
                } else {
                    if (checkSizeLeft(Math.abs(cell.getCol() - startPos[0]))) {
                        if (startPos[0] > cell.getCol()) {
                            if (checkIfValid(cell.getRow(), startPos[1], cell.getCol(), startPos[0])) {
                                paintCells(cell.getRow(), startPos[1], cell.getCol(), startPos[0]);
                                startPos[0] = -1;
                            } else {
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
                        } else {
                            if (checkIfValid(startPos[1], cell.getRow(), startPos[0], cell.getCol())) {
                                paintCells(startPos[1], cell.getRow(), startPos[0], cell.getCol());
                                startPos[0] = -1;
                            } else {
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

                        }

                    } else {
                        if (placedShips != shipsSizes.length) {
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
                        } else {
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

                    }

                }
            }
        } else {
            if (placedShips != shipsSizes.length) {
                startPos[0] = cell.getCol();
                startPos[1] = cell.getRow();
                paintCells(startPos[1], startPos[1], startPos[0], startPos[0]);
            } else {
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
        }
    }

    public boolean checkSizeLeft(int size) {

        for (int i = 0; i < shipsSizes.length; i++) {
            if (size + 1 == shipsSizes[i]) {
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
                        shipsSizes = GameController.EASY_SHIPS_SIZE;
                        break;
                    case "MEDIUM":
                        shipsSizes = GameController.MEDIUM_SHIPS_SIZE;
                        break;
                    case "HARD":
                        shipsSizes = GameController.HARD_SHIPS_SIZE;
                        break;
                }
                for (Ship ship:controller.getShips()) {
                    ship.clearShip();
                }
                break;

        }
    }

    public boolean checkIfValid(int startX, int endX, int startY, int endY) {
        if (startY == endY && startX != endX) {
            for (int i = 0; i < (endX - startX); i++) {
                if (controller.getSetBoard()[startY][startX + i + 1].isGotShip())
                    return false;
            }
        } else {
            if (startY != endY && startX == endX) {
                for (int i = 0; i < (endY - startY); i++) {
                    if (controller.getSetBoard()[startY + i + 1][startX].isGotShip())
                        return false;
                }
            }
        }
        return true;
    }

    private void paintCells(int startX, int endX, int startY, int endY) {
        if (startY == endY && startX != endX) {
            controller.getSetBoard()[startY][startX].setGotShip(true);
            for (int i = 0; i < (endX - startX); i++) {
                controller.getSetBoard()[startY][startX + i + 1].setBackgroundResource(R.drawable.blue_cell);
                controller.getSetBoard()[startY][startX + i + 1].setGotShip(true);
            }
        } else {
            if (startY != endY && startX == endX) {
                controller.getSetBoard()[startY][startX].setGotShip(true);
                for (int i = 0; i < (endY - startY); i++) {
                    controller.getSetBoard()[startY + i + 1][startX].setBackgroundResource(R.drawable.blue_cell);
                    controller.getSetBoard()[startY + i + 1][startX].setGotShip(true);
                }
            } else {
                controller.getSetBoard()[startY][startX].setBackgroundResource(R.drawable.blue_cell);
                controller.getSetBoard()[startY][startX].setGotShip(true);
            }
        }
    }
}
