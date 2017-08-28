package com.example.lenovo.envotron;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity1 extends Activity implements SensorEventListener {
    private SensorManager Sm;
    private Sensor lightSensor,accelSensor,proxySensor,tempSensor;
    private TextView  lightValue,accelValue,proxyValue,tempValue;

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        Sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor=Sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        accelSensor=Sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        proxySensor=Sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        tempSensor =Sm.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        if(lightSensor != null)
        {
            Sm.registerListener(this,lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else
        {
            Toast.makeText(this,"light sensor unavailable",Toast.LENGTH_SHORT).show();
        }
        if(accelSensor != null)
        {
            Sm.registerListener(this,accelSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else
        {
            Toast.makeText(this,"accel sensor unavailable",Toast.LENGTH_SHORT).show();
        }
        if(proxySensor != null)
        {
            Sm.registerListener(this,proxySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else
        {
            Toast.makeText(this,"proxy sensor unavailable",Toast.LENGTH_SHORT).show();
        }
        if(tempSensor != null)
        {
            Sm.registerListener(this,tempSensor, SensorManager.SENSOR_DELAY_NORMAL);

        }
        else
        {
            Toast.makeText(this,"temp sensor unavailable",Toast.LENGTH_SHORT).show();
        }
        lightValue = (TextView)findViewById(R.id.light);
        accelValue = (TextView)findViewById(R.id.accel);
        proxyValue = (TextView)findViewById(R.id.proxy);
        tempValue  = (TextView)findViewById(R.id.temp);

    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        int sensorType=event.sensor.getType();
        String t="";
        switch(sensorType) {
            case Sensor.TYPE_LIGHT:
                t=" " +event.values[0];
                lightValue.setText(""+t);
                break;
            case Sensor.TYPE_ACCELEROMETER:
                float magnitude;
                float xval = event.values[0];
                float yval = event.values[1];
                float zval = event.values[2];
                magnitude = (float) Math.sqrt((xval * xval) + (yval * yval) + (zval * zval));
                accelValue.setText(" " + magnitude);
                break;
            case Sensor.TYPE_PROXIMITY:
                t=" "+event.values[0];
                proxyValue.setText(""+t);
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                t=" "+event.values[0];
                tempValue.setText(""+t);
                break;

        }

    }


  /*  @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        Sm.registerListener(this, mPressure, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        Sm.unregisterListener(this);
    }*/
}