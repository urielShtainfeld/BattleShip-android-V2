package com.example.ushtinfeld.battleship_uriel;


import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.google.android.gms.maps.model.LatLng;

import entities.MapListener;

public class ScoreScreen extends FragmentActivity implements highScoresFragment.OnListFragmentInteractionListener, MapListener {


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private ScoresMapFragment myScoreMap;
    private highScoresFragment scoreListview;
    private boolean didAlreadyRequestLocationPermission;

    public void mapReadyNotification() {
        LatLng latLng = new LatLng(getIntent().getDoubleExtra("lat", 200), getIntent().getDoubleExtra("long", 200));
        myScoreMap.markUserLocation(latLng);
    }


    ScoreTablesAdapter mScoreTablesAdapter;
    ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        scoreListview = highScoresFragment.newInstance();
        myScoreMap = ScoresMapFragment.newInstance();
        myScoreMap.setListener(this);
        bindUi();
    }

    private void bindUi() {
        mScoreTablesAdapter = new ScoreTablesAdapter(getSupportFragmentManager(), myScoreMap, scoreListview);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mScoreTablesAdapter);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

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
