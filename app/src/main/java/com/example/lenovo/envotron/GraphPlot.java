package com.example.lenovo.envotron;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.hardware.SensorManager;
import android.view.View;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class GraphPlot extends AppCompatActivity implements SensorEventListener {


    private final int maxData = 200;
    private SensorManager Sm;
    private Sensor lightSensor,accelSensor,proxySensor,tempSensor;
    public LineGraphSeries<DataPoint> series, series2, series3, series4;
    public int a=0;
    public int b=0;
    public int c=0;
    public int d=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_plot);
        series = new LineGraphSeries<>();
        series2 = new LineGraphSeries<>();
        series3 = new LineGraphSeries<>();
        series4 = new LineGraphSeries<>();
        GraphView g11, g22, g33, g44;
        g11 =(GraphView) findViewById(R.id.g1);
        g22 =(GraphView) findViewById(R.id.g2);
        g33 =(GraphView) findViewById(R.id.g3);
        g44 =(GraphView) findViewById(R.id.g4);
        g11.addSeries(series);
        g22.addSeries(series2);
        g33.addSeries(series3);
        g44.addSeries(series4);
        g11.getViewport().setXAxisBoundsManual(true);
        g11.getViewport().setMinX(0);
        g11.getViewport().setMaxX(maxData);
        g22.getViewport().setXAxisBoundsManual(true);
        g22.getViewport().setMinX(0);
        g22.getViewport().setMaxX(maxData);
        g33.getViewport().setXAxisBoundsManual(true);
        g33.getViewport().setMinX(0);
        g33.getViewport().setMaxX(maxData);
        g44.getViewport().setXAxisBoundsManual(true);
        g44.getViewport().setMinX(0);
        g44.getViewport().setMaxX(maxData);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        super.onPause();
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType=event.sensor.getType();

        float value;
        switch(sensorType) {
            case Sensor.TYPE_LIGHT:
                series.appendData(new DataPoint(a++,event.values[0]),true,1000);
                break;
            case Sensor.TYPE_ACCELEROMETER:
                float magnitude;
                float xval = event.values[0];
                float yval = event.values[1];
                float zval = event.values[2];
                magnitude = (float) Math.sqrt((xval * xval) + (yval * yval) + (zval * zval));
                series2.appendData(new DataPoint(b++,magnitude),true,1000);
                break;
            case Sensor.TYPE_PROXIMITY:
                series3.appendData(new DataPoint(c++,event.values[0]),true,1000);
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                series4.appendData(new DataPoint(d++,event.values[0]),true,1000);
                break;

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


}