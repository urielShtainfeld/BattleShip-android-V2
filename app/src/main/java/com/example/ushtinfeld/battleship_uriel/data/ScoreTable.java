package com.example.ushtinfeld.battleship_uriel.data;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ushtinfeld on 12/01/2018.
 */

public class ScoreTable {
    private static String EASY = "EASY";
    private static String MEDIUM = "MEDIUM";
    private static String HARD = "HARD";
    private static int MAX_LENGTH = 10;
    private ArrayList<Record> hardLevel, mediumLevel, easyLevel;
    public ScoreTable(){
        hardLevel  = new ArrayList<>();
        mediumLevel = new ArrayList<>();
        easyLevel = new ArrayList<>();
    }

    public ScoreTable(ArrayList<Record> hard, ArrayList<Record> medium, ArrayList<Record> easy){
        setEasyScoreTable(easy);
        setMediumScoreTable(medium);
        setHardScoreTable(hard);
    }

    public void sortTables(){
        Collections.sort(easyLevel);
        Collections.sort(mediumLevel);
        Collections.sort(hardLevel);
    }

    public ArrayList<Record> getScoreTable(){
        ArrayList<Record>appendLevels = new ArrayList();
        appendLevels.addAll(hardLevel);
        appendLevels.addAll(mediumLevel);
        appendLevels.addAll(easyLevel);
        Collections.sort(appendLevels);
        return appendLevels;
    }

    public void newRecord(Record record){
        if(record.getLevel().equals(EASY))
            setNewRecord(record, this.easyLevel);
        else if(record.getLevel().equals(MEDIUM))
            setNewRecord(record, this.mediumLevel);
        else if(record.getLevel().equals(HARD))
            setNewRecord(record, this.hardLevel);

    }



    public boolean isNewRecord(String level, int result){
        if(level.equals(EASY))
            return checkForRecord(result, this.easyLevel);
        if(level.equals(MEDIUM))
            return checkForRecord(result, this.mediumLevel);
        if(level.equals(HARD))
            return checkForRecord(result,this.hardLevel);
        return  false;
    }

    private void setNewRecord(Record record, ArrayList<Record> table) {
        if(table.size()>=MAX_LENGTH)
            table.remove(table.size()-1);
        table.add(record);
        Collections.sort(table);
    }

    private boolean checkForRecord(int result, ArrayList<Record> table) {
        if(table.size()<MAX_LENGTH)
            return true;
        for(Record ro : table)
            if(ro.getScore()>result)
                return true;
        return  false;
    }

    public ArrayList<Record> getHardScoreTable(){
        return hardLevel;
    }

    public ArrayList<Record> getMediumScoreTable(){
        return mediumLevel;
    }

    public ArrayList<Record> getEasyScoreTable() {
        return easyLevel;
    }

    public void setEasyScoreTable(ArrayList<Record> easyLevel) {
        this.easyLevel = easyLevel;
        Collections.sort(easyLevel);
    }

    public void setMediumScoreTable(ArrayList<Record> mediumLevel) {
        this.mediumLevel = mediumLevel;
        Collections.sort(mediumLevel);
    }

    public void setHardScoreTable(ArrayList<Record> hardLevel) {
        this.hardLevel = hardLevel;
        Collections.sort(hardLevel);
    }
}

