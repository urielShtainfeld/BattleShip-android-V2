package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ushtinfeld.battleship_uriel.R;

/**
 * Created by ushtinfeld on 06/01/2018.
 */

public class HighScoresTable extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String HIGH_SCORES_TABLE_NAME = "HighScores";
    public static final String DATABASE_NAME = "HighScores.db";
    private SQLiteDatabase dataBase;

    public  HighScoresTable(Context context) {
        // The reason of passing null is you want the standard SQLiteCursor behaviour
        super(context, context.getResources().getString(R.string.app_name) + "_db", null, DATABASE_VERSION);
    }
    private String HIGH_SCORES_TABLE_CREATE(int KEY, String USER_NAME , int SCORE , double LONGITUDE, double LATITUDE,String LEVEL) {
        return "CREATE TABLE " + HIGH_SCORES_TABLE_NAME + " ( " + KEY + " INTEGER PRIMARY KEY, " + USER_NAME + " TEXT, " + SCORE + " INTEGER, "+
        LONGITUDE +" DOUBLE," + LATITUDE + "DOUBLE,"+LEVEL+"TEXT)";
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HIGH_SCORES_TABLE_CREATE(0, "UserName",0,0,0,""));
        this.dataBase = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + HIGH_SCORES_TABLE_NAME);

        // Create tables again
        onCreate(db);
    }
    public long put(ContentValues values) {
        long rowId;

        SQLiteDatabase database = this.getWritableDatabase();

        rowId = database.insertWithOnConflict(HIGH_SCORES_TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        database.close();

        return rowId;
    }


    public long put(int key, String userName,int score,double longitude,double latitude,String level) {
        ContentValues values = new ContentValues();
        values.put("Key", key);
        values.put("user_name", userName);
        values.put("Score",score);
        values.put("longitude",longitude);
        values.put("latitude",latitude);
        values.put("Level",level);
        return this.put(values);
    }

    public String get(int key, String defaultValue) {
        String returnValue = defaultValue;
        Cursor cursor = getCursor(key);

        if (cursor.moveToFirst()) {
            returnValue = cursor.getString(0);
        }

        return returnValue;
    }

    public Cursor getCursor(int key) {
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "SELECT User_Name,Score,longitude,latitude FROM " + HIGH_SCORES_TABLE_NAME + " where Key = '" + key + "'"; // Instead of "SELECT * FROM"
        Cursor cursor = database.rawQuery(selectQuery, null);

        return cursor;
    }
    public boolean isNewRecord(String level,int result){
        String selectQuery = "";
        SQLiteDatabase database = this.getReadableDatabase();
        switch (level){
            case "EASY":
                selectQuery = "SELECT  * from  FROM " + HIGH_SCORES_TABLE_NAME + "WHERE Key = (SELECT MAX(Key)  FROM TABLE) AND Key<11;";
                break;
            case "MEDIUM":
                selectQuery = "SELECT  * from  FROM " + HIGH_SCORES_TABLE_NAME + "WHERE Key = (SELECT MAX(Key)  FROM TABLE) AND Key<21 AND Key>10;";
                break;
            case "HARD":
                selectQuery = "SELECT  * from  FROM " + HIGH_SCORES_TABLE_NAME + "WHERE Key = (SELECT MAX(Key)  FROM TABLE) AND Key>20;";
                break;
        }
        Cursor cursor = database.rawQuery(selectQuery, null);
        try{
            int lastScore = cursor.getInt(2);
            if (lastScore<result){
                return true;
            }
            return false;
        }
        catch (Exception e){
            return true;
        }

    }

}
