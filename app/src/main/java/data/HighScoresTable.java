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
    private SQLiteDatabase dataBase;

    public  HighScoresTable(Context context) {
        // The reason of passing null is you want the standard SQLiteCursor behaviour
        super(context, context.getResources().getString(R.string.app_name) + "_db", null, DATABASE_VERSION);
    }
    private String HIGH_SCORES_TABLE_CREATE(int KEY, String USER_NAME , String SCORE , double LONGITUDE, double LATITUDE) {
        return "CREATE TABLE " + HIGH_SCORES_TABLE_NAME + " ( " + KEY + " INTEGER PRIMARY KEY, " + USER_NAME + " TEXT, " + SCORE + " TEXT, "+
        LONGITUDE +" DOUBLE," + LATITUDE + "DOUBLE)";
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HIGH_SCORES_TABLE_CREATE(0, "UserName","",0,0));
        this.dataBase = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public long put(ContentValues values) {
        long rowId;

        SQLiteDatabase database = this.getWritableDatabase();

        rowId = database.insertWithOnConflict(HIGH_SCORES_TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        database.close();

        return rowId;
    }


    public long put(int key, String userName,String score,double longitude,double latitude) {
        ContentValues values = new ContentValues();
        values.put("Key", key);
        values.put("user_name", userName);
        values.put("Score",score);
        values.put("longitude",longitude);
        values.put("latitude",latitude);
        return this.put(values);
    }

    public String get(String key, String defaultValue) {
        String returnValue = defaultValue;
        Cursor cursor = getCursor(key);

        if (cursor.moveToFirst()) {
            returnValue = cursor.getString(0);
        }

        return returnValue;
    }

    public Cursor getCursor(String key) {
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "SELECT User_Name,Score,longitude,latitude FROM " + HIGH_SCORES_TABLE_NAME + " where Key = '" + key + "'"; // Instead of "SELECT * FROM"
        Cursor cursor = database.rawQuery(selectQuery, null);

        return cursor;
    }
}
