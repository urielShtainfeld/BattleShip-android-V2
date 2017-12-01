package entities;


import java.util.ArrayList;

public class Ship {
    private boolean sink;
    private int size;
    private int ID = 0;
    private int noOfHits;


    public Ship(int size) {
        this.size = size;
        this.ID = ID++;
        this.sink = false;
        this.noOfHits = 0;
        ArrayList<ShipLocation> location = new ArrayList<ShipLocation>();

    }



    public void gotHit(int X, int Y){

    }
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isSink() {
        return sink;
    }

    public void setSink(boolean sink) {
        this.sink = sink;
    }

    public int getID() {
        return ID;
    }

    public int getNoOfHits() {

        return noOfHits;
    }

    public void setNoOfHits(int noOfHits) {
        this.noOfHits = noOfHits;
    }
}

