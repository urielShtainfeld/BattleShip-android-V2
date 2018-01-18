package com.example.ushtinfeld.battleship_uriel;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
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

import com.example.ushtinfeld.battleship_uriel.data.DataHandler;
import com.example.ushtinfeld.battleship_uriel.data.Record;
import com.example.ushtinfeld.battleship_uriel.data.ScoreTable;

import entities.GameController;

public class Score extends AppCompatActivity implements View.OnClickListener {
    private final double UNKNOWN_LONG = 33.356765;
    private final double UNKNOWN_LAT = 32.487563;
    private final String UNKONOWN = "Unknown";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    DataHandler dh = new DataHandler();
    private TextView whoWin;
    private Button restart, mainScreen;
    String level;
    int result, place;
    private ScoreTable table;
    private List<Address> addresses = null;
    private boolean didAlreadyRequestLocationPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        try {
            table = dh.getData(this);
        } catch (Exception e) {
            table = new ScoreTable();
        }
        String wonOrLose = this.getIntent().getStringExtra("wonOrLose");
        this.level = this.getIntent().getStringExtra("Level");
        RelativeLayout imagePlace = (RelativeLayout) this.findViewById(R.id.imageWhoWin);
        whoWin = this.findViewById(R.id.whoWin);
        restart = this.findViewById(R.id.Restart);
        mainScreen = this.findViewById(R.id.MainScreen);
        switch (wonOrLose) {
            case "WIN":
                whoWin.setText("You WIN");
                result = this.getIntent().getIntExtra("Score", 0);
                switch (level) {
                    case "EASY":
                        result = 100 - (result - GameController.EASY_MIN_HITS);
                        break;
                    case "MEDIUM":
                        result = 110 - (result - GameController.MEDIUM_MIN_HITS);
                        break;
                    case "HARD":
                        result = 120 - (result - GameController.HARD_MIN_HITS);
                        break;

                }
                if (table.isNewRecord(level, result)) {
                    double longitude = UNKNOWN_LONG;
                    double latitude = UNKNOWN_LAT;
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
        builder.setTitle("New Record!\nEnter your name for hall of fame");


        input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);


        builder.setPositiveButton("Save Record", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (input.getText().toString().isEmpty()) {
                    Toast.makeText(Score.this, "Player will be saved as Unknown", Toast.LENGTH_SHORT).show();
                    screen.saveRecord(UNKONOWN);
                } else {
                    screen.saveRecord(input.getText().toString());
                }
            }
        });

        builder.show();
    }

    public void saveRecord(String name) {
        if (addresses.size() > 0)
            table.newRecord(new Record(name, result, addresses.get(0).getLongitude(), addresses.get(0).getLatitude(), level, place));
        else {
            table.newRecord(new Record(name, result, UNKNOWN_LONG, UNKNOWN_LAT, level, place));
        }

        dh.saveScoreBoard(this, table);
    }

    private boolean requestLocationPermissionsIfNeeded(boolean byUserAction) {
        boolean isAccessGranted;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String fineLocationPermission = android.Manifest.permission.ACCESS_FINE_LOCATION;
            String coarseLocationPermission = android.Manifest.permission.ACCESS_COARSE_LOCATION;
            isAccessGranted = getApplicationContext().checkSelfPermission(fineLocationPermission) == PackageManager.PERMISSION_GRANTED &&
                    getApplicationContext().checkSelfPermission(coarseLocationPermission) == PackageManager.PERMISSION_GRANTED;
            if (!isAccessGranted) { // The user blocked the location services of THIS app / not yet approved

                if (!didAlreadyRequestLocationPermission || byUserAction) {
                    didAlreadyRequestLocationPermission = true;
                    String[] permissionsToAsk = new String[]{fineLocationPermission, coarseLocationPermission};
                    // IllegalArgumentException: Can only use lower 16 bits for requestCode
                    ActivityCompat.requestPermissions(this, permissionsToAsk, LOCATION_PERMISSION_REQUEST_CODE);
                }
            }
        } else {
            // Because the user's permissions started only from Android M and on...
            isAccessGranted = true;
        }

        return isAccessGranted;
    }
}


