package com.sensores.vilches.vilchesensores;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

public class MainActivity  extends AppCompatActivity implements SensorEventListener {
     private  SensorManager Manajedor;
     private  Sensor        miSensor;
    private static final int SENSOR_SENSITIVITY = 1 / 4;
    PowerManager.WakeLock wakeLock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //[SENSOR DETECT VARS]
        Manajedor = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        miSensor = Manajedor.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        //[CLOSE ]
        ImageView boton = (ImageView) findViewById(R.id.power);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activa();
            }

        });
        //[DETECTOR LOCKING]

        //[CLOSE DETECTOR LOCKING
        //[SCREEN]

        //[CLOSE SCREEN]
    }

    public  void aqcuireWakeLock(){
        PowerManager mPowerManager = (PowerManager)this.getSystemService(Context.POWER_SERVICE);
        wakeLock = mPowerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "tag");
        wakeLock.acquire();
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


    }
    public  void aqcuireWakeLockOn(){
        PowerManager mPowerManager = (PowerManager)this.getSystemService(Context.POWER_SERVICE);
        wakeLock = mPowerManager.newWakeLock(PowerManager.RELEASE_FLAG_WAIT_FOR_NO_PROXIMITY, "tag");
        wakeLock.acquire();
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


    }
    private void unlockScreen() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //[ACTIONS]
    @Override
    protected void onResume() {
        super.onResume();

    }
    public  void activa(){
        Manajedor.registerListener(this, miSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }
    @Override
    protected void onPause() {
        super.onPause();
        Manajedor.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (event.values[0] >= -SENSOR_SENSITIVITY && event.values[0] <= SENSOR_SENSITIVITY) {
                //near

                Toast.makeText(getApplicationContext(), "Activado", Toast.LENGTH_SHORT).show();
                this.aqcuireWakeLock();
            } else {
                //far
                Toast.makeText(getApplicationContext(), "Desactivado", Toast.LENGTH_SHORT).show();
                this.aqcuireWakeLockOn();

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    void customSnackBar(String text, int color) {

    }

    //[CLOSE]
}
