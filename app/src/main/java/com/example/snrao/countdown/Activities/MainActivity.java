package com.example.snrao.countdown.Activities;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.snrao.countdown.Views.ArcAngleAnimation;
import com.example.snrao.countdown.Views.ArcView;
import com.example.snrao.countdown.R;
import com.example.snrao.countdown.Views.CountDownView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

public class MainActivity extends AppCompatActivity {


    static final String START_DATE="07/22/2016 00:00:00";
    static final String DO_DATE = "07/22/2017 00:00:00";
    static final String PREP_DATE = "07/01/2017 00:00:00";

    long DoMillis;
    long PrepMillis;
    long TotalTime;

    CountDownView prepTimerBox;
    CountDownView doTimerBox;
    CountDownView elapsedTimerBox;

    //@sameertodo: delete these once final one is done.
    TextView doTimer;
    TextView prepTimer;
    TextView elapsedTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        doTimer = (TextView) findViewById(R.id.DoTimer);
        prepTimer=(TextView) findViewById(R.id.PrepTimer);
        elapsedTimer=(TextView) findViewById(R.id.ElapsedTimer);
        prepTimerBox=(CountDownView) findViewById(R.id.PrepTimerBox);
        doTimerBox=(CountDownView) findViewById(R.id.DoTimerBox);
        elapsedTimerBox=(CountDownView) findViewById(R.id.ElapsedTimerBox);


        //Gets current date, dodate, prepdate and startdate, and finds time to dodate and prepdate. Also
        // finds total time, which is an year
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        simpleDateFormat.setTimeZone(new SimpleTimeZone(SimpleTimeZone.UTC_TIME, "UTC"));
        Date today = null, start=null, DoDate = null, PrepDate = null;
        try {
            String now = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
            today=simpleDateFormat.parse(now); //simpleDateFormat gets it to UTC timezone.
            start = simpleDateFormat.parse(START_DATE);
            DoDate = simpleDateFormat.parse(DO_DATE);
            PrepDate=simpleDateFormat.parse(PREP_DATE);
        } catch (Exception ex) {
            Log.e("DATE PARSE ISSUE: ", "Not valid date");
        }
        DoMillis = DoDate.getTime()-today.getTime();
        PrepMillis = PrepDate.getTime()-today.getTime();
        TotalTime=DoDate.getTime()-start.getTime();



        int progress=(int)((double)(TotalTime-PrepMillis)/(double)TotalTime*360), duration=2000;
        prepTimerBox.setup(progress,duration);

        progress=(int)((double)(TotalTime-DoMillis)/(double)TotalTime*360); duration=2000;
        doTimerBox.setup(progress,duration);

        progress=(int)((double)(TotalTime-DoMillis)/(double)TotalTime*360); duration=2000;
        elapsedTimerBox.setup(progress,duration);

        setUpTimers();

    }

    private void setUpTimers(){
        //Do timer countdown
        CountDownTimer DoTimer = new CountDownTimer(DoMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String left=getDiffAsString(millisUntilFinished);
                String[] parts=left.split("\t:\t");
                doTimerBox.update(parts[0],parts[1],parts[2],parts[3]);

                left=getDiffAsString(TotalTime-millisUntilFinished);
                parts=left.split("\t:\t");
                elapsedTimerBox.update(parts[0],parts[1],parts[2],parts[3]);

                doTimer.setText("CAN DO IN:\t\t\t\t\t"+getDiffAsString(millisUntilFinished));
                elapsedTimer.setText("ELAPSED:\t\t\t\t\t\t"+getDiffAsString(TotalTime-millisUntilFinished));

            }

            @Override
            public void onFinish() {
                doTimerBox.finish();

                doTimer.setText("CAN DO NOW!!");
                elapsedTimer.setText("AN YEAR! You've done it!");
            }
        };

        //Prep timer countdown
        CountDownTimer PrepTimer=new CountDownTimer(PrepMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String left=getDiffAsString(millisUntilFinished);
                String[] parts=left.split("\t:\t");
                prepTimerBox.update(parts[0],parts[1],parts[2],parts[3]);
                prepTimer.setText("CAN PREP IN:\t\t\t"+left);
            }

            @Override
            public void onFinish() {
                prepTimer.setText("CAN PREP NOW!!");
                prepTimerBox.finish();
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
