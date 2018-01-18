package com.example.ushtinfeld.battleship_uriel.data;

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;


/**
 * Created by ushtinfeld on 12/01/2018.
 */

public class DataHandler {
    HighScoresTable db_sqLiteHelper;
    public void saveScoreBoard(Context context, ScoreTable scoreTable){
        db_sqLiteHelper = HighScoresTable.getInstance(context);
        int i = 1;
        for (Record rec: scoreTable.getEasyScoreTable()) {
            ContentValues values = new ContentValues();
            values.put("Key", i);
            values.put("user_name", rec.getUserName());
            values.put("Score",rec.getScore());
            values.put("longitude",rec.getLongitude());
            values.put("latitude",rec.getLatitude());
            values.put("Level",rec.getLevel());
            db_sqLiteHelper.put(values);
            i++;
        }
        i = 11;
        for (Record rec: scoreTable.getMediumScoreTable()) {
            ContentValues values = new ContentValues();
            values.put("Key", i);
            values.put("user_name", rec.getUserName());
            values.put("Score",rec.getScore());
            values.put("longitude",rec.getLongitude());
            values.put("latitude",rec.getLatitude());
            values.put("Level",rec.getLevel());
            db_sqLiteHelper.put(values);
            i++;
        }
        i = 21;

        for (Record rec: scoreTable.getHardScoreTable()) {
            ContentValues values = new ContentValues();
            values.put("Key", i);
            values.put("user_name", rec.getUserName());
            values.put("Score",rec.getScore());
            values.put("longitude",rec.getLongitude());
            values.put("latitude",rec.getLatitude());
            values.put("Level",rec.getLevel());
            db_sqLiteHelper.put(values);
            i++;
        }

    }

    public ScoreTable getData(Context context){
        ScoreTable st = null;
        db_sqLiteHelper = HighScoresTable.getInstance(context);
        ArrayList<Record> hardLevel  = db_sqLiteHelper.getArrayList("HARD");
        ArrayList<Record> mediumLevel = db_sqLiteHelper.getArrayList("MEDIUM");
        ArrayList<Record> easyLevel = db_sqLiteHelper.getArrayList("EASY");
      /*  try {

            st = new ScoreTable(easyLevel,mediumLevel,hardLevel);
        }
        catch (Exception e)
        {*/
        st = new ScoreTable();
        //}
        if (easyLevel != null)
            st.setEasyScoreTable(easyLevel);
        if (mediumLevel != null)
            st.setEasyScoreTable(mediumLevel);
        if (hardLevel != null)
            st.setEasyScoreTable(hardLevel);
        st.sortTables();
        return st;
    }


}
