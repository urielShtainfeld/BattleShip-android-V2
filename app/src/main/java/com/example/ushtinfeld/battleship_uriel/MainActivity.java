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
    private String lastLevel = null;
    private int easyBS, mediumBS, hardBS;
    private Intent intent;
    public GameController game = new GameController();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setButtons();

    }
    public void startGame(){
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
        int id = view.getId();
        switch (view.getId()) {
            case R.id.Easy:
                    game.setLevel(view.toString());
                    startGame();
                break;
            case R.id.Medium:
                    game.setLevel(view.toString());
                    startGame();
                break;
            case R.id.Hard:
                    game.setLevel(view.toString());
                    startGame();
                break;

        }
    }

    public GameController getGame() {
        return game;
    }
}
