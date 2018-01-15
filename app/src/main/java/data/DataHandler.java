package data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by ushtinfeld on 12/01/2018.
 */

public class DataHandler extends ContentProvider {
    static final String PROVIDER_NAME = "com.ustinfeld.battleship_uriel.provider";
    static final String URL = "content://" + PROVIDER_NAME + "/string/Key";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final String KEY = "Key";

    static final UriMatcher uriMatcher;
    private static final int URI_MATCH_KEY_VALUE = 1;

    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, KEY, URI_MATCH_KEY_VALUE);
    }
    private HighScoresTable db_sqLiteHelper;
    private SQLiteDatabase db;
    public void saveScoreBoard(Context context, ScoreTable scoreTable){
        //HighScoresTable db = new HighScoresTable(context);
        int i = 1;
        for (Record rec: scoreTable.getEasyScoreTable()) {
            ContentValues values = new ContentValues();
            values.put("Key", i);
            values.put("user_name", rec.getUserName());
            values.put("Score",rec.getScore());
            values.put("longitude",rec.getLongitude());
            values.put("latitude",rec.getLatitude());
            values.put("Level",rec.getLevel());
            insert(CONTENT_URI,values);
            //db.put(i,rec.getUserName(),rec.getScore(),rec.getLongitude(),rec.getLatitude(),rec.getLevel());
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
            insert(CONTENT_URI,values);
            //db.put(i,rec.getUserName(),rec.getScore(),rec.getLongitude(),rec.getLatitude(),rec.getLevel());
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
            insert(CONTENT_URI,values);
            //db.put(i,rec.getUserName(),rec.getScore(),rec.getLongitude(),rec.getLatitude(),rec.getLevel());
            i++;
        }

    }

    public ScoreTable getData(Context context){
        ScoreTable st;
       // HighScoresTable db = new HighScoresTable(context);
        try {

            st = new ScoreTable(db_sqLiteHelper.getArrayList("EASY"), db_sqLiteHelper.getArrayList("MEDIUM"),db_sqLiteHelper.getArrayList("HARD"));
        }
        catch (Exception e)
        {
            st = new ScoreTable();
        }
        return null;
        //st.sortTables();
        //eturn st;
    }

    @Override
    public boolean onCreate() {
        this.db_sqLiteHelper = new HighScoresTable(this.getContext());
        this.db = db_sqLiteHelper.getWritableDatabase();
        return this.db != null;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;

        switch (DataHandler.uriMatcher.match(uri)) {
            case URI_MATCH_KEY_VALUE:
                cursor = this.db_sqLiteHelper.getCursor(selection);
                break;
            default: // Unknown URI
                cursor = this.db_sqLiteHelper.getCursor(selection);
                //throw new IllegalArgumentException("Unknown URI " + uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long rowID = db_sqLiteHelper.put(values);

        if (rowID > 0)
        {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
