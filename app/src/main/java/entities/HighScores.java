package entities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ushtinfeld.battleship_uriel.R;

/**
 * Created by ushtinfeld on 06/01/2018.
 */

public class HighScores extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String HIGH_SCORES_TABLE_NAME = "HighScores";
    private SQLiteDatabase dataBase;

    public  HighScores(Context context) {
        // The reason of passing null is you want the standard SQLiteCursor behaviour
        super(context, context.getResources().getString(R.string.app_name) + "_db", null, DATABASE_VERSION);
    }
    private String HIGH_SCORES_TABLE_CREATE(String KEY_WORD, String KEY_DEFINITION) {
        return "CREATE TABLE " + HIGH_SCORES_TABLE_NAME + " ( " + KEY_WORD + " TEXT PRIMARY KEY, " + KEY_DEFINITION + " TEXT)";
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HIGH_SCORES_TABLE_CREATE("Key", "Value"));
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


    public long put(String key, String value) {
        ContentValues values = new ContentValues();
        values.put("Key", key);
        values.put("Value", value);

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
        String selectQuery = "SELECT Value FROM " + HIGH_SCORES_TABLE_NAME + " where Key = '" + key + "'"; // Instead of "SELECT * FROM"
        Cursor cursor = database.rawQuery(selectQuery, null);

        return cursor;
    }
}
