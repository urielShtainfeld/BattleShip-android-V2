package com.example.ushtinfeld.battleship_uriel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import components.Cell;
import components.CellListener;

public class Set_Ships extends AppCompatActivity implements CellListener {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_ships);

    }
    public void startAfterSet(View view){

        Intent game = new Intent(this, Game.class);
        startActivity(game);
    }

    public void SetRandomShips(View view) {

    }

    @Override
    public void buttonClicked(Cell cellListener) {

    }
}
