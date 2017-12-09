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
    private Button startBtn;
    private Button randomBtn;
    private Button clearBtn;
    private int currShipSize = 0;
    Ship[] userShips;
    Ship[] compShips;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        String level = this.getIntent().getStringExtra("Level");
        controller = new GameController(level);
        controller.createSetShipsBoard(this, controller);
        controller.initVars(this,controller);
        setButtons();
        controller.setRandomMode(false);
    }

    private void setButtons() {
        startBtn = (Button) findViewById(R.id.Start);
        startBtn.setOnClickListener(this);
        //startBtn.setEnabled(false);

        randomBtn = (Button) findViewById(R.id.Random);
        randomBtn.setOnClickListener(this);

        clearBtn = (Button) findViewById(R.id.Clear);
        clearBtn.setOnClickListener(this);
    }

    public void buttonClicked(Cell cell) {
        if (controller.isSetMode()) {
            int col = cell.getCol();
            int row = cell.getRow();
            controller.setShips(col, row,this,controller);
        }
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Clear:
                controller.clearBoard(this,controller);
                break;
            case R.id.Random:
                controller.clearBoard(this,controller);
                controller.setRandomMode(true);
                controller.setShipsRandom(this,controller);
                controller.setRandomMode(false);
                break;
            case R.id.Start:
                controller.setSetMode(false);
                controller.createUserBoard(this,controller);
               /*controller.clearBoard(this,controller);
                controller.setRandomMode(true);
                controller.setCompBoard(true);
                controller.setShipsRandom(this,controller);
                controller.setRandomMode(false);
                controller.setCompBoard(false);
                */
                break;
        }
    }



}
