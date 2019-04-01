package com.example.sensors;

import android.app.Service;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "MainActivity";
    private SensorManager sensorManager;
    private Sensor mGyroscope, mLight;
    TextView xGyroValue, yGyroValue, zGyroValue, light;
    /*TextView textView;
    SensorManager sensorManager;
    Sensor sensor;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xGyroValue = (TextView) findViewById(R.id.xGyroValue);
        yGyroValue = (TextView) findViewById(R.id.yGyroValue);
        zGyroValue = (TextView) findViewById(R.id.zGyroValue);

        light = (TextView) findViewById(R.id.light);
        //gyro = (TextView) findViewById(R.id.gyro);

        Log.d(TAG, "onCreate:Initializing Sensor Services");
        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);

        mGyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (mGyroscope!=null){
            sensorManager.registerListener(MainActivity.this,mGyroscope,SensorManager.SENSOR_DELAY_NORMAL);
            Log.d(TAG, "onCreate: Registered Gyroscope Listener");
        } else{
            xGyroValue.setText("Gyroscope Not Supported");
            yGyroValue.setText("Gyroscope Not Supported");
            zGyroValue.setText("Gyroscope Not Supported");
        }
        mLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (mLight!=null){
            sensorManager.registerListener(MainActivity.this,mLight,SensorManager.SENSOR_DELAY_NORMAL);
            Log.d(TAG, "onCreate: Registered Light Listener");
        } else{
            light.setText("Gyroscope Not Supported");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        if(sensor.getType()==Sensor.TYPE_GYROSCOPE){
            Log.d(TAG, "onSensorChanged: X:"+sensorEvent.values[0]+"Y:"+sensorEvent.values[1]+"Z:"+sensorEvent.values[2]);

            //gyro.setText("Gyroscope:");
            xGyroValue.setText("xValue: " + sensorEvent.values[0]);
            yGyroValue.setText("yValue: " + sensorEvent.values[1]);
            zGyroValue.setText("zValue: " + sensorEvent.values[2]);
        }else if(sensor.getType()==Sensor.TYPE_LIGHT){
            light.setText("Light: "+sensorEvent.values[0]);
        }
    }
}