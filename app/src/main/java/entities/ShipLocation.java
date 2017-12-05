package entities;

public class ShipLocation {
    private int X;
    private int Y;
    private boolean hit;

    public ShipLocation(int x, int y,boolean hit) {
        this.X = x;
        this.Y = y;
        this.hit = hit;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }


    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public void setLocation(int x,int y){
        this.X = x;
        this.Y = y;
    }
}
