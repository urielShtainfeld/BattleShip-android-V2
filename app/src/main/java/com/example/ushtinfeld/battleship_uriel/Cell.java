package com.example.ushtinfeld.battleship_uriel;

import android.content.Context;
import android.media.Image;
import android.widget.Button;


public class Cell extends Button {
    private boolean gotShip;
    private boolean gotHit;
    private boolean enabled;
    private int row;
    private int col;
    private Image image;

    public Cell(Context context, int row, int col) {
        super(context);
        this.gotHit = false;
        this.gotShip = false;
        this.row = row;
        this.col = col;

    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isGotShip() {
        return gotShip;
    }

    public void setGotShip(boolean gotShip) {
        this.gotShip = gotShip;
    }

    public boolean isGotHit() {
        return gotHit;
    }

    public void setGotHit(boolean gotHit) {
        this.gotHit = gotHit;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}

