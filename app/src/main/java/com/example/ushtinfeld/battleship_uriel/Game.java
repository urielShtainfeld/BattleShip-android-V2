package com.example.ushtinfeld.battleship_uriel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.ushtinfeld.battleship_uriel.R;

import components.Cell;
import components.CellListener;
import entities.GameController;

public class Game extends AppCompatActivity implements CellListener,View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        String level = this.getIntent().getStringExtra("Level");
        GameController controller = new GameController(level);
        controller.createSetShipsBoard(this);

    }

    @Override
    public void buttonClicked(Cell cellListener) {

    }

    @Override
    public void onClick(View v) {

    }
}
