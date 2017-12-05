package entities;


import java.util.ArrayList;

public class Ship {
    private boolean sink;
    private int size;
    private int noOfHits;
    ShipLocation[] locations;

    public Ship(int size) {
        this.size = size;
        this.sink = false;
        this.noOfHits = 0;
        locations = new ShipLocation[size];
        for (ShipLocation location:locations) {
            location = new ShipLocation(-1,-1,false);
        }
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

    public int getNoOfHits() {
        return noOfHits;
    }
    public void setNoOfHits(int noOfHits) {
        this.noOfHits = noOfHits;
    }

    public ShipLocation[] getLocations() {
        return locations;
    }

    public void setLocations(ShipLocation[] locations) {
        this.locations = locations;
    }

    public void clearShip(){
        this.sink = false;
        this.noOfHits = 0;
        for (ShipLocation location:locations) {
            location.setLocation(-1,-1);
        }
    }
}

