package com.example.android.google.wearable.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.wearable.view.CardFrame;
import android.support.wearable.view.CardScrollView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class prayers extends Activity
        implements LocationListener {
    private static final String TAG = "MainActivity";
globvar g1=new globvar();
    double lon1,lat1;
    protected LocationManager locationManager;



    /*  private void getPrayerTime(ArrayList prayerTimes) {
        Calendar cal = Calendar.getInstance();
        String fajrString = (String) prayerTimes.get(0);
        String[] fajrSplit = fajrString.split(":");
        cal.set(Calendar.HOUR, Integer.parseInt(fajrSplit[0]));
        cal.set(Calendar.MINUTE, Integer.parseInt(fajrSplit[1]));
        fajrTime = cal.getTime();
        String DString = (String) prayerTimes.get(2);
        String[] DSplit = DString.split(":");
        cal.set(Calendar.HOUR, Integer.parseInt(DSplit[0]));
        cal.set(Calendar.MINUTE, Integer.parseInt(DSplit[1]));
        DT = cal.getTime();
        String AString = (String) prayerTimes.get(3);
        String[] ASplit = AString.split(":");
        cal.set(Calendar.HOUR, Integer.parseInt(ASplit[0]));
        cal.set(Calendar.MINUTE, Integer.parseInt(ASplit[1]));
        AT = cal.getTime();
        String MString = (String) prayerTimes.get(5);
        String[] MSplit = MString.split(":");
        cal.set(Calendar.HOUR, Integer.parseInt(MSplit[0]));
        cal.set(Calendar.MINUTE, Integer.parseInt(MSplit[1]));
        MT = cal.getTime();
        String IString = (String) prayerTimes.get(6);
        String[] ISplit = IString.split(":");
        cal.set(Calendar.HOUR, Integer.parseInt(ISplit[0]));
        cal.set(Calendar.MINUTE, Integer.parseInt(ISplit[1]));
        IT = cal.getTime();
    }
*/
    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.rpa);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER))
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

        if (locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER))
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        CardScrollView cardScrollView =
                (CardScrollView) findViewById(R.id.card_scroll_view);
        cardScrollView.setVerticalScrollBarEnabled(true);
//        lon1=loc.getLongitude();
  //      lat1=loc.getLatitude();

        CardFrame cardFrame = (CardFrame) findViewById(R.id.card_frame);
        cardFrame.setExpansionEnabled(true);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_linear_layout);
        // TODO Auto-generated method stub
        double timezone = (Calendar.getInstance().getTimeZone()
                .getOffset(Calendar.getInstance().getTimeInMillis()))
                / (1000 * 60 * 60);

        PrayerTime prayers = new PrayerTime();

        prayers.setTimeFormat(prayers.Time12);
        prayers.setCalcMethod(prayers.ISNA);
        prayers.setAsrJuristic(prayers.Hanafi);
        prayers.setAdjustHighLats(prayers.AngleBased);
        int[] offsets = { 0, 0, 0, 0, 0, 0, 0 }; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
        prayers.tune(offsets);

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);

        g1.ac=this;
        ArrayList prayerTimes = prayers.getPrayerTimes(cal, 37.4051150,
                -122.1145240, timezone);
        ArrayList prayerNames = prayers.getTimeNames();
        //Long time=prayerTimes.get(0);

        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        Calendar cur_cal = new GregorianCalendar();
        cur_cal.setTimeInMillis(System.currentTimeMillis());
        Calendar cal_f = new GregorianCalendar();
        Calendar cal_d = new GregorianCalendar();
        Calendar cal_a = new GregorianCalendar();
        Calendar cal_m = new GregorianCalendar();
        Calendar cal_i = new GregorianCalendar();



        for(int i = 0; i < 7; i++ ) {
            if (i== 1 || i == 4) { continue;}
            if (i==0){
                TextView newTextView = new TextView(prayers.this);
                newTextView.setText("Fajr");
                newTextView.setTextSize(20);
                newTextView.setTextColor(Color.parseColor("#636363"));

                linearLayout.addView(newTextView);

                TextView newTextView2 = new TextView(prayers.this);
                newTextView2.setText("" + prayerTimes.get(i));
                newTextView2.setTextSize(15);
                newTextView2.setTextColor(Color.parseColor("#636363"));
                newTextView2.setPadding(0, 0, 0 , 20);

                newTextView2.setCompoundDrawablesWithIntrinsicBounds(
                        0, 0, 0, R.drawable.line);

                linearLayout.addView(newTextView2);

                try {
                    String[] splitTime = String.format("%s", prayerTimes.get(i)).replace(" ", "").split(":");

                    cal_f.set(Calendar.HOUR_OF_DAY, Integer.parseInt(splitTime[0]));
                    cal_f.set(Calendar.MINUTE,Integer.parseInt(splitTime[1]));
                    if (cal_f.before(cur_cal)) {// if its in the past increment
                        cal_f.add(Calendar.DATE, 1);
                    }

                } catch (Exception e) {
                    System.out.println("Unable to set alarm: " + e.getMessage());
                }
            }

            if (i==2){
                TextView newTextView = new TextView(prayers.this);
                newTextView.setText("Dhuhr");
                newTextView.setTextSize(20);
                newTextView.setTextColor(Color.parseColor("#636363"));

                linearLayout.addView(newTextView);

                // LinearLayout.addView(img);
                TextView newTextView2 = new TextView(prayers.this);
                newTextView2.setText("" + prayerTimes.get(i));
                newTextView2.setTextSize(15);
                newTextView2.setTextColor(Color.parseColor("#636363"));
                newTextView2.setPadding(0, 0, 0 , 20);
                newTextView2.setCompoundDrawablesWithIntrinsicBounds(
                        0, 0, 0, R.drawable.line);
                linearLayout.addView(newTextView2);

                try {
                    String[] splitTime = String.format("%s", prayerTimes.get(i)).replace(" ", "").split(":");

                    cal_d.set(Calendar.HOUR_OF_DAY, Integer.parseInt(splitTime[0]));
                    cal_d.set(Calendar.MINUTE, Integer.parseInt(splitTime[1]));
                    if (cal_d.before(cur_cal)) {// if its in the past increment
                        cal_d.add(Calendar.DATE, 1);
                    }

                } catch (Exception e) {
                    System.out.println("Unable to set alarm: " + e.getMessage());
                }
            }
            if (i==3){
                TextView newTextView = new TextView(prayers.this);
                newTextView.setText("Asr");
                newTextView.setTextSize(20);
                newTextView.setTextColor(Color.parseColor("#636363"));

                linearLayout.addView(newTextView);

                TextView newTextView2 = new TextView(prayers.this);
                newTextView2.setText("" + prayerTimes.get(i));
                newTextView2.setTextSize(15);
                newTextView2.setTextColor(Color.parseColor("#636363"));
                newTextView2.setPadding(0, 0, 0 , 20);

                newTextView2.setCompoundDrawablesWithIntrinsicBounds(
                        0, 0, 0, R.drawable.line);

                linearLayout.addView(newTextView2);
                try {
                    String[] splitTime = String.format("%s", prayerTimes.get(i)).replace(" ", "").split(":");

                    cal_a.set(Calendar.HOUR_OF_DAY, Integer.parseInt(splitTime[0]));
                    cal_a.set(Calendar.MINUTE, Integer.parseInt(splitTime[1]));
                    if (cal_a.before(cur_cal)) {// if its in the past increment
                        cal_a.add(Calendar.DATE, 1);
                    }

                } catch (Exception e) {
                    System.out.println("Unable to set alarm: " + e.getMessage());
                }
            }
            if (i==5){
                TextView newTextView = new TextView(prayers.this);
                newTextView.setText("Maghrib");
                newTextView.setTextSize(20);
                newTextView.setTextColor(Color.parseColor("#636363"));

                linearLayout.addView(newTextView);

                TextView newTextView2 = new TextView(prayers.this);
                newTextView2.setText("" + prayerTimes.get(i));
                newTextView2.setTextSize(15);
                newTextView2.setTextColor(Color.parseColor("#636363"));
                newTextView2.setPadding(0, 0, 0 , 20);

                newTextView2.setCompoundDrawablesWithIntrinsicBounds(
                        0, 0, 0, R.drawable.line);

                linearLayout.addView(newTextView2);
                try {
                    String[] splitTime = String.format("%s", prayerTimes.get(i)).replace(" ", "").split(":");

                    cal_m.set(Calendar.HOUR_OF_DAY, Integer.parseInt(splitTime[0]));
                    cal_m.set(Calendar.MINUTE, Integer.parseInt(splitTime[1]));
                    if (cal_m.before(cur_cal)) {// if its in the past increment
                        cal_m.add(Calendar.DATE, 1);
                    }

                } catch (Exception e) {
                    System.out.println("Unable to set alarm: " + e.getMessage());
                }
            }
            if (i==6){
                TextView newTextView = new TextView(prayers.this);
                newTextView.setText("Ishaa");
                newTextView.setTextSize(20);
                newTextView.setTextColor(Color.parseColor("#636363"));

                linearLayout.addView(newTextView);


                TextView newTextView2 = new TextView(prayers.this);
                newTextView2.setText("" + prayerTimes.get(i));
                newTextView2.setTextSize(15);
                newTextView2.setTextColor(Color.parseColor("#636363"));
                newTextView2.setPadding(0, 0, 0 , 20);

                newTextView2.setCompoundDrawablesWithIntrinsicBounds(
                        0, 0, 0, R.drawable.line);

                linearLayout.addView(newTextView2);
                try {
                    String[] splitTime = String.format("%s", prayerTimes.get(i)).replace(" ", "").split(":");

                    cal_i.set(Calendar.HOUR_OF_DAY, Integer.parseInt(splitTime[0]));
                    cal_i.set(Calendar.MINUTE, Integer.parseInt(splitTime[1]));
                    if (cal_i.before(cur_cal)) {// if its in the past increment
                        cal_i.add(Calendar.DATE, 1);
                    }


                } catch (Exception e) {
                    System.out.println("Unable to set alarm: " + e.getMessage());
                }
            }}
    }


    public void onLocationChanged(Location location) {
        //txtLat = (TextView) findViewById(R.id.textview1);
        lon1 = location.getLongitude();
        lat1 = location.getLatitude();
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


    public void current_azan_time(double latitude, double longitude) {
        // TODO Auto-generated method stub


        // TODO Auto-generated method stub
        double timezone = (Calendar.getInstance().getTimeZone()
                .getOffset(Calendar.getInstance().getTimeInMillis()))
                / (1000 * 60 * 60);

        PrayerTime prayers = new PrayerTime();

        prayers.setTimeFormat(prayers.Time12);
        prayers.setCalcMethod(prayers.Karachi);
        prayers.setAsrJuristic(prayers.Shafii);
        prayers.setAdjustHighLats(prayers.AngleBased);
        int[] offsets = { 0, 0, 0, 0, 0, 0, 0 }; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
        prayers.tune(offsets);

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        g1.ac=this;
        ArrayList prayerTimes = prayers.getPrayerTimes(cal, g1.lati,
                g1.longi, timezone);
        ArrayList prayerNames = prayers.getTimeNames();}






    private void scroll(final int scrollDirection) {
        final ScrollView scrollView = (ScrollView) findViewById(R.id.card_scroll_view);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(scrollDirection);
            }
        });
    }
}