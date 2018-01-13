package com.example.ushtinfeld.battleship_uriel;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import data.DataHandler;
import data.Record;
import data.ScoreTable;

public class Score extends AppCompatActivity implements View.OnClickListener {
    private final double UNKNOWN_LONG = 33.356765;
    private final double UNKNOWN_LAT = 32.487563;
    private final String UNKONOWN = "Unknown";
    private TextView whoWin;
    private Button restart, mainScreen;
    String level;
    private ScoreTable table;
    private int result;
    private List<Address> addresses = null;

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
                if(table.isNewRecord(level,result)) {
                    double longitude = getIntent().getDoubleExtra("long", 500);
                    double latitude = getIntent().getDoubleExtra("lat", 500);
                    if (longitude == 500 || latitude == 500) {
                        longitude = UNKNOWN_LONG;
                        latitude = UNKNOWN_LAT;
                    }
                    Geocoder geocoder;
                    geocoder = new Geocoder(this, Locale.getDefault());
                    try {
                        addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    } catch (IOException e) {
                        addresses = new ArrayList<>();
                    }
                    registerUserWithNewRecord(this);
                }
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

    private void registerUserWithNewRecord(final Score screen) {
        final EditText input;
        String name;

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Record!\nEnter your name for hall of fame" );


        input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);



        builder.setPositiveButton("Save Record", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (input.getText().toString().isEmpty()) {
                    Toast.makeText(Score.this, "Player will be saved as Unknown", Toast.LENGTH_SHORT).show();
                    screen.saveRecord(UNKONOWN);
                }
                else{
                    screen.saveRecord(input.getText().toString());
                }
            }
        });

        builder.show();
    }
    public void saveRecord(String name){
        String address;
        if(addresses.size()>0)
            address = addresses.get(0).getAddressLine(0);
        else{
            address = UNKONOWN;
        }
        table.newRecord(new Record(name, result,address, level));
        DataHandler.saveScoreBoard(this,table);
    }
}


