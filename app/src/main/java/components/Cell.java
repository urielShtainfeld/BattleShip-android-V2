package components;

import android.content.Context;
import android.media.Image;
import android.widget.ImageButton;
import android.view.View;


public class Cell extends ImageButton implements View.OnClickListener {

    private boolean gotShip;
    private boolean gotHit;
    private boolean enabled;
    private int row;
    private int col;
    private Image image;
    private CellListener listener;

    public Cell(Context context, int row, int col) {
        super(context);
        this.gotHit = false;
        this.gotShip = false;
        this.row = row;
        this.col = col;

    }
    public void onClick(View v) {
        listener.buttonClicked(this);
    }

    public void setListener(CellListener listener) {
        this.listener = listener;
    }

    public boolean isEnabled() {
        return enabled;
    }

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
