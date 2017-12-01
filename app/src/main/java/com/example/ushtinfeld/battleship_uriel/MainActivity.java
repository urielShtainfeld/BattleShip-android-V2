package com.example.ushtinfeld.battleship_uriel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void startGame(View view){
        //need to change level

        Intent setShips = new Intent(this,Set_Ships.class);
        startActivity(setShips);
    };
}
