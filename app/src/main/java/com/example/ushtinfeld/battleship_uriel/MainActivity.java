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
    public void startGame(int id,View view) {
        Intent setShips = new Intent(this, Set_Ships.class);

        switch (view.getId()) {
            case R.id.Easy:
                setShips.putExtra("Level", R.string.easy);
                break;
            case R.id.Medium:
                setShips.putExtra("Level", R.string.medium);
                break;
            case R.id.Hard:
                setShips.putExtra("Level", R.string.hard);
                break;
        }
            startActivity(setShips);
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
        int id = view.getId();
        startGame(id,view);
        }
}

