package com.example.ushtinfeld.battleship_uriel;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import com.example.ushtinfeld.battleship_uriel.data.HighScoresTable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener , GoogleApiClient.ConnectionCallbacks , com.google.android.gms.location.LocationListener {
    private final int PERMISSION_LOCATION = 111;

    private Button easyBtn;
    private Button medBtn;
    private Button hardBtn;
    private Button scoreBtn;
    private int easyBS, mediumBS, hardBS;
    private Location location;
    private GoogleApiClient googleApiClient;

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

        scoreBtn = (Button) findViewById(R.id.Scores);
        scoreBtn.setOnClickListener(this);

    }

    public void onClick(View view) {

        Intent Game = new Intent(this, Game.class);
        Intent ScoreFrag = new Intent(this,ScoreScreen.class);
        int id = view.getId();
        switch (view.getId()) {
            case R.id.Easy:
                Game.putExtra("Level", "EASY");
                startActivity(Game);
                break;
            case R.id.Medium:
                Game.putExtra("Level", "MEDIUM");
                startActivity(Game);
                break;
            case R.id.Hard:
                Game.putExtra("Level", "HARD");
                startActivity(Game);
                break;
            case R.id.Scores:
                startActivity(ScoreFrag);
                break;
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_LOCATION);
        }else{
            startLocationServices();
        }
    }
    public void startLocationServices() {
        Log.d("Maps" ,"starting location services called");
        try {
            LocationRequest req = LocationRequest.create().
                    setPriority(LocationRequest.PRIORITY_LOW_POWER);
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, req ,this);
            Log.d("Maps" ,"location services called");
        }catch (SecurityException exception){
            Toast.makeText(MainActivity.this, "Cant get location until we get persmission :(", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("Map" , "Lon:" + location.getLongitude()  + location.getLatitude());
        this.location = location;
    }
}

