package com.example.ushtinfeld.battleship_uriel;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class ScoreTablesAdapter extends FragmentStatePagerAdapter {
    private ScoresMapFragment map;
    private highScoresFragment scoresListView;

    public ScoreTablesAdapter(FragmentManager fm , ScoresMapFragment map , highScoresFragment scoresListView) {
        super(fm);
        this.map = map;
        this.scoresListView = scoresListView;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return this.scoresListView;
            case 1:
                return this.map;
            default:
                return null;
        }
    }

    @Override
    public int getCount () {
        return 2;
    }

    @Override
    public CharSequence getPageTitle ( int position){
        if (position == 0)
            return ("Score Table");
        else
            return ("Map View");
    }

}
