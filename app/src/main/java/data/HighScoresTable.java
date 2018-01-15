package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ushtinfeld.battleship_uriel.R;

import java.util.ArrayList;

/**
 * Created by ushtinfeld on 06/01/2018.
 */

public class HighScoresTable extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String HIGH_SCORES_TABLE_NAME = "HighScores";
    private SQLiteDatabase dataBase;

    public  HighScoresTable(Context context) {
        // The reason of passing null is you want the standard SQLiteCursor behaviour
        super(context, context.getResources().getString(R.string.app_name) + "_db", null, DATABASE_VERSION);
    }
    private String HIGH_SCORES_TABLE_CREATE(String KEY, String USER_NAME , String SCORE , String LONGITUDE, String LATITUDE,String LEVEL) {
        return "CREATE TABLE " + HIGH_SCORES_TABLE_NAME + " ( " + KEY + " INTEGER PRIMARY KEY, " + USER_NAME + " TEXT, " + SCORE + " INTEGER, "+
        LONGITUDE +" DOUBLE," + LATITUDE + "DOUBLE,"+LEVEL+"TEXT)";
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HIGH_SCORES_TABLE_CREATE("Key","user_name","Score","longitude","latitude" ,"Level"));
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
        Cursor cursor = getCursor(Integer.toString(key));

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
    public ArrayList<Record> getArrayList(String level) {

        ArrayList<Record> recList = null;

        Cursor cursor = null;
        try {
            String query = "";
            switch (level){
                case "EASY":
                    query = "SELECT  * from  FROM " + HIGH_SCORES_TABLE_NAME + "WHERE Key = (SELECT MAX(Key)  FROM TABLE) AND Key<11;";
                    break;
                case "MEDIUM":
                    query = "SELECT  * from  FROM " + HIGH_SCORES_TABLE_NAME + "WHERE Key = (SELECT MAX(Key)  FROM TABLE) AND Key<21 AND Key>10;";
                    break;
                case "HARD":
                    query = "SELECT  * from  FROM " + HIGH_SCORES_TABLE_NAME + "WHERE Key = (SELECT MAX(Key)  FROM TABLE) AND Key>20;";
                    break;
            }
            //your query here
            cursor = dataBase.rawQuery(query,null);
            if (cursor != null && cursor.moveToFirst()) {
                recList = new ArrayList<Record>();
                do {recList.add(new Record(cursor.getString(2),cursor.getInt(3),cursor.getDouble(4),cursor.getDouble(5),
                        cursor.getString(5),cursor.getInt(6)));

                   // namesList.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            recList = null;
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.deactivate();
                cursor.close();
                cursor = null;
            }
            close();
        }
        return recList;
    }
    public void close() {
        try {
            if (dataBase != null && dataBase.isOpen()) {
                dataBase.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
