

package com.example.android.google.wearable.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class qiblah extends Activity implements SensorEventListener, LocationListener {
    private static final String TAG = "MainActivity";

    // define the display assembly compass picture
    private ImageView image1,image2;

    // record the compass picture angle turned
    private float currentDegree = 0f;
    double lon1,lat1,lr,lr1,r1,r2;
    double lat2=Math.toRadians(21.4225);
    double lon2=Math.toRadians(39.75);
    float delta,x,y,brng=0;
    // device sensor manager
    private SensorManager mSensorManager;
    protected LocationManager locationManager;
    private float direction = 0;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private boolean firstDraw;
    TextView tvHeading;private MyCompassView myCompassView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pa);
        //myCompassView = (MyCompassView)findViewById(R.id.mycompassview);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER))
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

        if (locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER))
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        image2 = (ImageView) findViewById(R.id.qiblah);
        // our compass image
        image1 = (ImageView) findViewById(R.id.orng);



        // TextView that will tell the user what degree is he heading
       // tvHeading = (TextView) findViewById(R.id.tvHeading);

        // initialize your android device sensor capabilities
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
       // init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // for the system's orientation sensor registered listeners
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // to stop the listener and save battery
        mSensorManager.unregisterListener(this);
    }


    public void onLocationChanged(Location location) {
        //txtLat = (TextView) findViewById(R.id.textview1);
        lon1=(Math.toRadians(location.getLongitude()));
        lat1=(Math.toRadians(location.getLatitude()));

        delta = (float)(lon2 - lon1);
        float y =(float) (Math.sin(delta) * Math.cos(lat2));
        float x = (float)(Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(delta));
        brng=(float) Math.toDegrees(Math.atan2(y, x));


        //txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude()+ ", Qiblah:" + brng);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Toast.makeText(this,"brng ="+brng,Toast.LENGTH_LONG);
        // get the angle around the z-axis rotated
        float degree = Math.round(event.values[0]);

//        tvHeading.setText("Qiblah: "  + brng);

        // create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // how long the animation will take place
        ra.setDuration(210);

        // set the animation after the end of the reservation status
        ra.setFillAfter(true);

        // Start the animation
        image1.startAnimation(ra);
        RotateAnimation rb = new RotateAnimation(
                -currentDegree,
                -brng,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // how long the animation will take place
        rb.setDuration(210);

        // set the animation after the end of the reservation status
        rb.setFillAfter(true);

        // Start the animation
        image2.startAnimation(rb);

        currentDegree = -degree;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }
    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }



}
