package bound;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;

/**
 * Created by ushtinfeld on 11/01/2018.
 */

public class BoundService extends Service implements SensorEventListener {

    private boolean isInitial = true;
    private int startX, startY, startZ;
    private int lastX,lastY,lastZ;

    private long lastMillis = 0;
    private ServiceBinder serviceBinder;
    private SensorManager sensorManager;
    private BoundServiceListener listener;

    @Override
    public IBinder onBind(Intent intent) {
        serviceBinder = new ServiceBinder();
        return serviceBinder;
    }

    public void startListening() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void setListener(BoundServiceListener listenter){
        this.listener = listenter;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if(isInitial){
                isInitial = false;
                startX = (int)Math.round(event.values[0]);;
                startY = (int)Math.round(event.values[1]);
                startZ = (int)Math.round(event.values[2]);
                lastX = startX;
                lastY = startY;
                lastZ = startZ;
                lastMillis = System.currentTimeMillis();
            }
            else{
                if(System.currentTimeMillis()- lastMillis > 1000){
                    int x = (int)Math.round(event.values[0]);
                    int y = (int)Math.round(event.values[1]);
                    int z = (int)Math.round(event.values[2]);

                    double lastDistance = Math.sqrt(Math.pow(startX-lastX,2)+Math.pow(startY-lastY,2)+Math.pow(startZ-lastZ,2));
                    double currentDistance = Math.sqrt(Math.pow(startX-x,2)+Math.pow(startY-y,2)+Math.pow(startZ-z,2));
                    boolean isRecovering = currentDistance<lastDistance || (lastDistance==0 && currentDistance==0) ;
                    if (Math.abs(startX - x) > 10 || Math.abs(startY- y) > 10 || Math.abs(startZ - z) > 10) {
                        lastX = x;
                        lastY = y;
                        lastZ = z;
                        listener.onAngelChange(isRecovering);
                        lastMillis = System.currentTimeMillis();
                    }
                }
            }
        }
    }

    public void stopListening() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    public class ServiceBinder extends Binder {
        public BoundService getService() {
            return BoundService.this;
        }
    }
}