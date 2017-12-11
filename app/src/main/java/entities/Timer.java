package entities;


import android.os.Handler;

public class Timer extends Thread implements Runnable {
    private Handler handler;
    private boolean active;

    public Timer(Handler handler) {
        this.handler = handler;
        this.active = true;
    }

    @Override
    public void run() {
        while (active) {
            handler.sendEmptyMessage(0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopTimer() {
        this.active = false;
    }

}