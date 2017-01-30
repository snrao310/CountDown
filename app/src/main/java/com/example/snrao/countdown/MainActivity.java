package com.example.snrao.countdown;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.StringTokenizer;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    static final String DO_DATE = "07/24/2017 00:00:00";
    static final String PREP_DATE = "01/29/2017 17:35:00";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView doTimer = (TextView) findViewById(R.id.DoTimer);
        final TextView prepTimer=(TextView) findViewById(R.id.PrepTimer);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        simpleDateFormat.setTimeZone(new SimpleTimeZone(SimpleTimeZone.UTC_TIME, "UTC"));
        Date today = null, DoDate = null, PrepDate = null;
        try {
            String now = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
            today=simpleDateFormat.parse(now); //simpleDateFormat gets it to UTC timezone.
            DoDate = simpleDateFormat.parse(DO_DATE);
            PrepDate=simpleDateFormat.parse(PREP_DATE);
        } catch (Exception ex) {
            Log.e("DATE PARSE ISSUE: ", "Not valid date");
        }
        long DoMillis = DoDate.getTime()-today.getTime();
        long PrepMillis = PrepDate.getTime()-today.getTime();
        CountDownTimer DoTimer = new CountDownTimer(DoMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                doTimer.setText(getDiffAsString(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                doTimer.setText("CAN DO NOW!!");
            }
        };

        CountDownTimer PrepTimer=new CountDownTimer(PrepMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                prepTimer.setText(getDiffAsString(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                prepTimer.setText("CAN PREP NOW!!");
            }
        };
        DoTimer.start();
        PrepTimer.start();
    }

    private String getDiffAsString(long diff) {
        long millisInSec = 1000, millisInMinute = 60 * millisInSec, millisInHour = 60 * millisInMinute, millisInDay = 24 * millisInHour;

        int days = (int) (diff / millisInDay);
        diff %= millisInDay;
        int hours = (int) (diff / millisInHour);
        diff %= millisInHour;
        int minutes = (int) (diff / millisInMinute);
        diff %= millisInMinute;
        int sec = (int) (diff / millisInSec);

        String text = String.format("%02d\t:\t%02d\t:\t%02d\t:\t%02d",days,hours,minutes,sec);
        return text;
    }


}
