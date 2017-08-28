package com.example.lenovo.envotron;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class GraphPlot extends AppCompatActivity implements SensorEventListener {


    int [] sensorList;
    String [] sensorNames;
    int [] sensors;
    LineGraphSeries<DataPoint>[] sensorViews;
    private final int maxData = 200;
    int [] lastx;
    private SensorManager Sm;
    private Sensor lightSensor,accelSensor,proxySensor,tempSensor;
    private TextView  lightValue,accelValue,proxyValue,tempValue;
    public LineGraphSeries<DataPoint> series, series2, series3, series4;
    public int a=0;
    public int b=0;
    public int c=0;
    public int d=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_plot);
        sensorList = new int[]{-1, -1, -1, -1};
        lastx = new int[]{0, 0, 0, 0};
        sensors = new int[]{Sensor.TYPE_LIGHT, Sensor.TYPE_PRESSURE, Sensor.TYPE_AMBIENT_TEMPERATURE, Sensor.TYPE_RELATIVE_HUMIDITY, Sensor.TYPE_ACCELEROMETER, Sensor.TYPE_GYROSCOPE, Sensor.TYPE_MAGNETIC_FIELD, Sensor.TYPE_PROXIMITY};
        sensorNames = new String[]{"Light", "Pressure", "Temperature", "Humidity", "Accelerometer", "Gyroscope", "Magnetometer", "Proximity"};
        sensorViews = new LineGraphSeries[4];
    }

    @Override
    protected void onResume() {
        super.onResume();
        int count = 0;
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        int [] range = {0, 1, 2, 3, 4, 5, 6, 7};
        for(int i: range) {
            if (sensorManager != null && sensorManager.getDefaultSensor(sensors[i]) != null && count < 4) {
                sensorList[count] = i;
                count++;
                Sensor sensor = sensorManager.getDefaultSensor(sensors[i]);
                sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
            }
            if(count==4) break;
        }

        series = new LineGraphSeries<>();
        series2 = new LineGraphSeries<>();
        series3 = new LineGraphSeries<>();
        series4 = new LineGraphSeries<>();
        GraphView g1, g2, g3, g4;
        g1 =(GraphView) findViewById(R.id.g1);
        g2 =(GraphView) findViewById(R.id.g2);
        g3 =(GraphView) findViewById(R.id.g3);
        g4 =(GraphView) findViewById(R.id.g4);
        g1.addSeries(series);
        g2.addSeries(series2);
        g3.addSeries(series3);
        g4.addSeries(series4);
        g1.getViewport().setXAxisBoundsManual(true);
        g1.getViewport().setMinX(0);
        g1.getViewport().setMaxX(maxData);
        g2.getViewport().setXAxisBoundsManual(true);
        g2.getViewport().setMinX(0);
        g2.getViewport().setMaxX(maxData);
        g3.getViewport().setXAxisBoundsManual(true);
        g3.getViewport().setMinX(0);
        g3.getViewport().setMaxX(maxData);
        g4.getViewport().setXAxisBoundsManual(true);
        g4.getViewport().setMinX(0);
        g4.getViewport().setMaxX(maxData);
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
    protected void onPause() {
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        for(int i: sensorList) {
            if (sensorManager != null && sensorManager.getDefaultSensor(i) != null) {
                Sensor sensor = sensorManager.getDefaultSensor(i);
                sensorManager.unregisterListener(this, sensor);
            }
            else {
                Toast.makeText(getApplicationContext(), sensorNames[i]+" Error", Toast.LENGTH_LONG).show();
                System.exit(1);
            }
        }
        super.onPause();
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType=event.sensor.getType();

        float value;
        switch(sensorType) {
            case Sensor.TYPE_LIGHT:
                series.appendData(new DataPoint(event.values[0],a),true,1000);
                a++;
                break;
            case Sensor.TYPE_ACCELEROMETER:
                float magnitude;
                float xval = event.values[0];
                float yval = event.values[1];
                float zval = event.values[2];
                magnitude = (float) Math.sqrt((xval * xval) + (yval * yval) + (zval * zval));

                series.appendData(new DataPoint(event.values[0],b),true,1000);
                b++;
                break;
            case Sensor.TYPE_PROXIMITY:
                series.appendData(new DataPoint(event.values[0],c),true,1000);
                c++;
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                series.appendData(new DataPoint(event.values[0],d),true,1000);
                d++;
                break;

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


}