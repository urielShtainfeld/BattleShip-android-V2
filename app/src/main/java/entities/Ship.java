package entities;

import java.util.concurrent.atomic.AtomicInteger;

public class Ship {
    private boolean sink;
    private int size;
    private int noOfHits;
    private int id;
    private boolean placed;
    private static final AtomicInteger counter = new AtomicInteger();

    public Ship(int size) {
        this.size = size;
        this.sink = false;
        this.noOfHits = 0;
        this.placed = false;
        this.id = counter.getAndIncrement();

    }

    public int getSize() {
        return size;
    }

    public void setSink(boolean sink) {
        this.sink = sink;
    }

    public boolean isSink() {
        return sink;
    }

    public void setNoOfHits() {
        this.noOfHits++;
        if (noOfHits == size) {
            setSink(true);
        }
    }

    public void clearShip() {
        this.sink = false;
        this.noOfHits = 0;
        this.placed = false;
    }

    public boolean isPlaced() {
        return placed;
    }

    public void setPlaced(boolean placed) {
        this.placed = placed;
    }

    public int getId() {
        return id;
    }
}

