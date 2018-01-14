package data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ushtinfeld on 12/01/2018.
 */

public class DataHandler {
    public static String DB_NAME = "High_Scores";
    public static String DB_TABLE = "table";

    public static void saveScoreBoard(Context context, ScoreTable scoreTable){
        HighScoresTable db = new HighScoresTable(context);
        int i = 1;
        for (Record rec: scoreTable.getEasyScoreTable()) {
            db.put(i,rec.getUserName(),rec.getScore(),rec.getLongitude(),rec.getLatitude(),rec.getLevel());
            i++;
        }
        i = 11;
        for (Record rec: scoreTable.getMediumScoreTable()) {
            db.put(i,rec.getUserName(),rec.getScore(),rec.getLongitude(),rec.getLatitude(),rec.getLevel());
            i++;
        }
        i = 21;

        for (Record rec: scoreTable.getHardScoreTable()) {
            db.put(i,rec.getUserName(),rec.getScore(),rec.getLongitude(),rec.getLatitude(),rec.getLevel());
            i++;
        }

    }

    public static ScoreTable getData(Context context){
        HighScoresTable db = new HighScoresTable(context);

        return  null;
    }
}
