package com.example.ushtinfeld.battleship_uriel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Score extends AppCompatActivity implements View.OnClickListener {

    private TextView whoWin;
    private Button restart, mainScreen;
    String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        String wonOrLose = this.getIntent().getStringExtra("wonOrLose");
        this.level = this.getIntent().getStringExtra("Level");
        RelativeLayout imagePlace = (RelativeLayout) this.findViewById(R.id.imageWhoWin);
        whoWin = this.findViewById(R.id.whoWin);
        restart = this.findViewById(R.id.Restart);
        mainScreen = this.findViewById(R.id.MainScreen);
        switch (wonOrLose) {
            case "WIN":
                whoWin.setText("You WIN");
                imagePlace.setBackgroundResource(R.drawable.victory);
                break;
            case "LOSE":
                whoWin.setText("You Lose");
                imagePlace.setBackgroundResource(R.drawable.sad_smiley);
                break;

        }
        setButtons();
    }

    private void setButtons() {
        restart = (Button) findViewById(R.id.Restart);
        restart.setOnClickListener(this);

        mainScreen = (Button) findViewById(R.id.MainScreen);
        mainScreen.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Restart:
                Intent game = new Intent(this, com.example.ushtinfeld.battleship_uriel.Game.class);
                game.putExtra("Level", this.getLevel());
                this.startActivity(game);
                this.finish();
                break;
            case R.id.MainScreen:
                this.finish();
                break;
        }
    }

    public String getLevel() {
        return level;
    }
}


