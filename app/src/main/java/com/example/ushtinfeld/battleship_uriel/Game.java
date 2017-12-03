package com.example.ushtinfeld.battleship_uriel;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import components.Cell;
import components.CellListener;
import entities.GameController;

public class Game extends AppCompatActivity implements CellListener, View.OnClickListener {
    public GameController controller;
    public int[] startPos;
    public int[] shipsSizes;
    private RelativeLayout setTable;
    private LinearLayout setRowsLayout;
    private LinearLayout setColsLayout;
    private Cell [][] setBoard;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        String level = this.getIntent().getStringExtra("Level");
        controller = new GameController(level);
        controller.createSetShipsBoard(this,controller);
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
    }

    @Override
    public void buttonClicked(Cell cell) {
        if (controller.isSetMode()) {
            if (startPos[0] > -1) {
                if (cell.getCol() != startPos[0] && cell.getRow() != startPos[1]) {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
                    dlgAlert.setMessage("The second click must be in the same row or collum like the first click");
                    dlgAlert.setTitle("Error Message...");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                    dlgAlert.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                } else {
                    if (cell.getCol() == startPos[0]) {
                        if (checkSizeLeft(Math.abs(cell.getRow() - startPos[1]))) {
                            if (startPos[1] > cell.getRow()) {
                                paintCells(cell.getRow(), startPos[1], cell.getCol(), startPos[0]);
                            } else {
                                paintCells(startPos[1], cell.getRow(), cell.getCol(), startPos[0]);
                            }
                        } else {
                            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
                            dlgAlert.setMessage("you don't have this size anymore");
                            dlgAlert.setTitle("Error Message...");
                            dlgAlert.setPositiveButton("OK", null);
                            dlgAlert.setCancelable(true);
                            dlgAlert.create().show();
                            dlgAlert.setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                        }
                    } else {
                        if (checkSizeLeft(Math.abs(cell.getCol() - startPos[0]))) {
                            if (startPos[0] > cell.getCol()) {
                                paintCells(cell.getRow(), startPos[1], cell.getCol(), startPos[0]);
                            } else {
                                paintCells(startPos[1], cell.getRow(), startPos[0], cell.getCol());
                            }
                        } else {
                            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
                            dlgAlert.setMessage("you don't have this size anymore");
                            dlgAlert.setTitle("Error Message...");
                            dlgAlert.setPositiveButton("OK", null);
                            dlgAlert.setCancelable(true);
                            dlgAlert.create().show();
                            dlgAlert.setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });

                        }

                    }
                }
            } else {
                startPos[0] = cell.getCol();
                startPos[1] = cell.getRow();
                paintCells(startPos[0],startPos[0],startPos[1],startPos[1]);
            }
        }
    }

    public boolean checkSizeLeft(int size) {
        for (int i = 0; i < shipsSizes.length; i++) {
            if (size == shipsSizes[i]) {
                shipsSizes[i] = -1;
                return true;
            }
        }
        return false;
    }

    public void onClick(View v) {


    }

    private void paintCells(int startX, int endX, int startY, int endY) {
        if (startY == endY) {
            for (int i = 0; i == endX-startX; i++) {
                controller.getCellFromSetBoard(startY,i).setBackgroundResource(R.drawable.blue_cell);
                return;
            }
        } else {
            for (int i = 0; i == endY-startY; i++) {
                controller.getCellFromSetBoard(i,startX).setBackgroundResource(R.drawable.blue_cell);
                return;
            }
            controller.getCellFromSetBoard(startY,startX).setBackgroundResource(R.drawable.blue_cell);
        }
    }
}
