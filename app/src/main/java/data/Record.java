package data;

/**
 * Created by ushtinfeld on 12/01/2018.
 */

public class Record implements Comparable<Record>{

    private String userName;
    private int score;
    private String location;


    private String level;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getLocation() {
        return location;
    }

    public String getLevel() {
        return level;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Record(String userName, int score, String location, String level) {

        this.userName = userName;
        this.score = score;
        this.location = location;
        this.level = level;
    }

    @Override
    public String toString() {
        return
                "userName: " + userName + '\'' +
                        ", time: " + score +
                        ", location: " + location + '\'' +
                        ", level: " + level
                ;
    }

    @Override
    public int compareTo(Record record) {
        if(this.getLevel().equals(record.getLevel()))
            return this.getScore()-record.getScore();
        if(this.getLevel().equals("hard"))
            return -1;
        else if(this.getLevel().equals("medium")){
            if(record.getLevel().equals("hard"))
                return 1;
            else
                return -1;
        }
        else
            return 1;
    }
}
