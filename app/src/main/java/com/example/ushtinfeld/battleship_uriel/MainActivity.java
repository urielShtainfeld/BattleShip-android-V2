package com.example.ushtinfeld.battleship_uriel;

import android.content.Intent;
import android.net.wifi.aware.PublishConfig;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import entities.GameController;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button easyBtn;
    private Button medBtn;
    private Button hardBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setButtons();
    }
    private void setButtons() {
        easyBtn = (Button) findViewById(R.id.Easy);
        easyBtn.setOnClickListener(this);

        medBtn = (Button) findViewById(R.id.Medium);
        medBtn.setOnClickListener(this);

        hardBtn = (Button) findViewById(R.id.Hard);
        hardBtn.setOnClickListener(this);
    }

    public void onClick(View view) {

        Intent Game = new Intent(this, Game.class);
        int id = view.getId();
        switch (view.getId()) {
            case R.id.Easy:
                Game.putExtra("Level", "EASY");
                break;
            case R.id.Medium:
                Game.putExtra("Level", "MEDIUM");
                break;
            case R.id.Hard:
                Game.putExtra("Level", "HARD");
                break;
        }
        startActivity(Game);
    }
}

