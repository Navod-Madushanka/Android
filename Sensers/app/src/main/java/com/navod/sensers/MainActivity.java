package com.navod.sensers;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private static final String TAG = MainActivity.class.getName();
    private SensorManager sensorManager;
    private Sensor sensor;
    private TextView textViewX, textViewY, textViewZ, textViewStepCount;
    private int stepCont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermissions(new String[]{
                Manifest.permission.ACTIVITY_RECOGNITION
        },100);

        textViewX = findViewById(R.id.textViewX);
        textViewY = findViewById(R.id.textViewY);
        textViewZ = findViewById(R.id.textViewZ);
        textViewStepCount = findViewById(R.id.stepCount);

        sensorManager =(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager!=null){
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            Log.i(TAG, sensor.toString());
        }

        if(sensor != null){
            sensorManager.registerListener(MainActivity.this, sensor, SensorManager.SENSOR_DELAY_UI );
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_STEP_COUNTER){
            int x = (int) event.values[0];

            textViewX.setText("X:"+x);

            textViewStepCount.setText("StepCount: "+x);
//            Log.i(TAG, "X: "+x+" Y: "+y+" Z: "+z);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}