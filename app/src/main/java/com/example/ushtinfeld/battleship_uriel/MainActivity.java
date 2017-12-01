package com.example.ushtinfeld.battleship_uriel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import components.LevelButton;
import entities.Set_Ships;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LevelButton[] levelButtons = new LevelButton[3];
    private Button easyBtn;
    private Button medBtn;
    private Button hardBtn;

    private String lastLevel = null;
    private int easyBS, mediumBS, hardBS;
    private Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void startGame(View view){
        //need to change level

        Intent setShips = new Intent(this, Set_Ships.class);
        startActivity(setShips);
    };

    private void setButtons() {
        easyBtn = (Button) findViewById(R.id.Easy);
        easyBtn.setOnClickListener(this);

        medBtn = (Button) findViewById(R.id.Medium);
        medBtn.setOnClickListener(this);

        hardBtn = (Button) findViewById(R.id.Hard);
        hardBtn.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Easy:

                break;
            case R.id.Medium:

                break;
            case R.id.Hard:

                break;
        }
    }
}
