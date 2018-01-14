package com.example.ushtinfeld.battleship_uriel;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import com.google.android.gms.maps.model.LatLng;
import entities.MapListener;

public class ScoreScreen extends FragmentActivity implements highScoresFragment.OnListFragmentInteractionListener, MapListener {



    private ScoresMapFragment  myScoreMap;
    private highScoresFragment scoreListview;

    public void mapReadyNotification() {
        LatLng latLng = new LatLng(getIntent().getDoubleExtra("lat",200),getIntent().getDoubleExtra("long",200));
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
        mScoreTablesAdapter =  new ScoreTablesAdapter(getSupportFragmentManager(),myScoreMap, scoreListview);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mScoreTablesAdapter);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
