package components;

import android.content.Context;
import android.widget.Button;
import android.view.View;

public class LevelButton extends Button implements View.OnClickListener {

    int rows, cols, ships;
    Button button;
    private LevelButtonListener listener;

    public LevelButton(Context context) {
        super(context);
        setOnClickListener(this);
    }
    public void onClick(View v) {
        listener.buttonClicked(this);
    }

    public void setListener(LevelButtonListener listener) {
        this.listener = listener;
    }

    public LevelButton(Context context, int rows, int cols, int ships) {
        super(context);
        setLevel(rows, cols, ships);
        setOnClickListener(this);
    }

    public void setLevel(int rows, int cols, int ships) {
        this.rows = rows;
        this.cols = cols;
        this.ships = ships;

    }
}
