package entities;

import android.content.Intent;

import com.example.ushtinfeld.battleship_uriel.Game;
import com.example.ushtinfeld.battleship_uriel.R;

public class GameRoles {
    private int leftShips;
    private int comLeftShips;

    public GameRoles(int leftShips, int comLeftShips) {
        this.leftShips = leftShips;
        this.comLeftShips = comLeftShips;
    }

    public int getLeftShips() {
        return leftShips;
    }

    public void setLeftShips(int leftShips) {
        this.leftShips = leftShips;
    }

    public int getComLeftShips() {
        return comLeftShips;
    }

    public void setComLeftShips(int comLeftShips) {
        this.comLeftShips = comLeftShips;
    }

    public void victory(Game game) {

        Intent Score = new Intent(game, com.example.ushtinfeld.battleship_uriel.Score.class);
        Score.putExtra("wonOrLose", "WIN");
        Score.putExtra("Level", game.getLevel());
        game.startActivity(Score);
        game.finish();
    }

    public void lose(Game game) {
        Intent Score = new Intent(game, com.example.ushtinfeld.battleship_uriel.Score.class);
        Score.putExtra("wonOrLose", "LOSE");
        Score.putExtra("timer", game.getTime().getText());
        game.startActivity(Score);
        game.finish();
    }
}
