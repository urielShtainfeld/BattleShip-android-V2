package data;

/**
 * Created by ushtinfeld on 12/01/2018.
 */

public class Record implements Comparable<Record>{

    private String userName;
    private int score;
    double longitude;
    double latitude;
    private String level;
    private int place;

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

    public String getLevel() {
        return level;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Record(String userName, int score, double longitude, double latitude, String level, int place) {

        this.userName = userName;
        this.score = score;
        this.longitude = longitude;
        this.latitude = latitude;
        this.level = level;
        this.place = place;
    }

    @Override
    public String toString() {
        return
                "place:"+place+'\''+
                "userName: " + userName + '\'' +
                ", time: " + score +
                ", location: " + latitude+","+longitude + '\'' +
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
