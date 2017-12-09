package com.example.ushtinfeld.battleship_uriel;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import components.Cell;
import components.CellListener;
import entities.GameController;
import entities.Ship;
import entities.Timer;

public class Game extends AppCompatActivity implements CellListener, View.OnClickListener {
    public GameController controller;
    private Button startBtn;
    private Button randomBtn;
    private Button clearBtn;
    private int currShipSize = 0;
    private Timer timer;
    private android.os.Handler handler;
    private int timeCounter = 0;
    private TextView shipsLeft;
    private TextView comShipsLeft ;
    private TextView turn ;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        String level = this.getIntent().getStringExtra("Level");
        controller = new GameController(level);
        controller.createSetShipsBoard(this);
        controller.initVars(this);
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
            controller.setShips(col, row,this);
        }else{
            int col = cell.getCol();
            int row = cell.getRow();
            if (controller.handelHit(col, row,this)){
                controller.computerTurn();
            }

        }
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Clear:
                controller.clearBoard(this);
                break;
            case R.id.Random:
                controller.clearBoard(this);
                controller.setRandomMode(true);
                controller.setShipsRandom(this);
                controller.setRandomMode(false);
                break;
            case R.id.Start:
                startBtn.setVisibility(View.GONE);
                randomBtn.setVisibility(View.GONE);
                clearBtn.setVisibility(View.GONE);
                controller.setSetMode(false);
                controller.createUserBoard(this);
                controller.clearBoard(this);
                controller.setRandomMode(true);
                controller.setCompBoard(true);
                controller.setShipsRandom(this);
                controller.setRandomMode(false);
                controller.setCompBoard(false);
                controller.setTextViews(this);
                timerRun();
                break;

        }
    }

    private void timerRun() {
        // Timer running
        handler = new Handler() {
            public void handleMessage (Message message){
                TextView time = (TextView)findViewById(R.id.chronometer);
                time.setText("Time: " + (timeCounter/60<10?"0":"")+timeCounter/60+":"+(timeCounter%60<10?"0":"")+timeCounter%60);
                timeCounter++;
            }
        };
        timer = new Timer(handler);
        timer.start();
    }

    public TextView getShipsLeft() {
        return shipsLeft;
    }

    public TextView getComShipsLeft() {
        return comShipsLeft;
    }

    public TextView getTurn() {
        return turn;
    }

    public void setShipsLeft(TextView shipsLeft) {
        this.shipsLeft = shipsLeft;
    }

    public void setComShipsLeft(TextView comShipsLeft) {
        this.comShipsLeft = comShipsLeft;
    }

    public void setTurn(TextView turn) {
        this.turn = turn;
    }
}
