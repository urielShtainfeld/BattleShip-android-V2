package entities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.ushtinfeld.battleship_uriel.Game;
import com.example.ushtinfeld.battleship_uriel.R;

public class ComTurn extends Thread implements Runnable {
    Handler handler;
    Game game;

    public ComTurn(Handler handler, Game game) {
        this.handler = handler;
        this.game = game;

    }


    public void run() {
        int row = (int) (Math.random() * game.controller.getBoardSize());
        int col = (int) (Math.random() * game.controller.getBoardSize());
        if (!game.controller.getUserBoard()[col][row].isGotHit()) {
            game.controller.getUserBoard()[col][row].setGotHit(true);
            if (game.controller.getUserBoard()[col][row].isGotShip()) {
                game.controller.getUserBoard()[col][row].setBackgroundResource(R.drawable.red15x15);
                Ship hitShip = game.controller.getShipById(game.controller.getUserBoard()[col][row].getShipID(), game.controller.getUserShips());
                hitShip.setNoOfHits();
                if (hitShip.isSink()) {
                    game.minusNumOfShipsLeft();
                    game.getShipsLeft().setText(Integer.toString(game.getNumOfShipsLeft()));
                    if (game.getNumOfShipsLeft() == 0) {
                        game.getGameRole().lose(game);
                    }

                    final Dialog builder = new Dialog(game);
                    builder.requestWindowFeature(Window.FEATURE_SWIPE_TO_DISMISS);
                    builder.getWindow().setBackgroundDrawable(
                            new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            //nothing;
                        }
                    });
                    ImageView imageView = new ImageView(game);
                    imageView.setImageResource(R.drawable.sad_smiley);
                    builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
                    builder.setCancelable(true);
                    builder.show();
                    new CountDownTimer(1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            builder.dismiss();
                        }
                    }.start();
                }
            } else {
                game.controller.getUserBoard()[col][row].setBackgroundResource(R.drawable.green15x15);
            }
        }


        game.getTurn().setText(R.string.yourTurn);
        game.getTurn().setTextColor(Color.GREEN);
        game.setAllButtons(true);

    }


}

