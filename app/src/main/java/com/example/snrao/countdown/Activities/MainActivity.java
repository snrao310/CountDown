package com.example.snrao.countdown.Activities;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

//    static final String START_DATE="02/11/2016 10:01:40";
//    static final String DO_DATE = "02/11/2017 10:01:40";
//    static final String PREP_DATE = "02/11/2017 10:01:30";

    static final String prepLabel = "Time to Prep: ";
    static final String doLabel = "Time to Do: ";
    static final String elapsedLabel = "Time Elapsed: ";
    static final String prepLabelDone = "CAN PREP NOW";
    static final String doLabelDone = "CAN DO NOW";
    static final String elapsedLabelDone = "CONGRATULATIONS!!\n ONE YEAR!!";

    long DoMillis;
    long PrepMillis;
    long TotalTime;

    CountDownView prepTimerBox;
    CountDownView doTimerBox;
    CountDownView elapsedTimerBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        prepTimerBox.setup(progress,duration,prepLabel);

        progress=(int)((double)(TotalTime-DoMillis)/(double)TotalTime*360); duration=2000;
        doTimerBox.setup(progress,duration,doLabel);

        progress=(int)((double)(TotalTime-DoMillis)/(double)TotalTime*360); duration=2000;
        elapsedTimerBox.setup(progress,duration,elapsedLabel);

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
            }

            @Override
            public void onFinish() {
                doTimerBox.finish(false,doLabelDone);
                elapsedTimerBox.finish(true,elapsedLabelDone);
            }
        };

        //Prep timer countdown
        CountDownTimer PrepTimer=new CountDownTimer(PrepMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String left=getDiffAsString(millisUntilFinished);
                String[] parts=left.split("\t:\t");
                prepTimerBox.update(parts[0],parts[1],parts[2],parts[3]);
            }

            @Override
            public void onFinish() {
                prepTimerBox.finish(false,prepLabelDone);
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
