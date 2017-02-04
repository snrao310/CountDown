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

    static final String START_DATE="02/24/2016 19:12:00";
    static final String DO_DATE = "02/24/2017 19:12:00";
    static final String PREP_DATE = "02/03/2017 19:12:00";
    static int DaysToPrep=0;
    static int DaysToDo=0;
    static int DaysFromStart=0;

    long DoMillis;
    long PrepMillis;
    long TotalTime;

    //@sameertodo: delete these once final one is done.
    TextView doTimer;
    TextView prepTimer;
    TextView elapsedTimer;
    ArcView arcView;

    CountDownView prepTimerBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        doTimer = (TextView) findViewById(R.id.DoTimer);
        prepTimer=(TextView) findViewById(R.id.PrepTimer);
        elapsedTimer=(TextView) findViewById(R.id.ElapsedTimer);
        arcView = (ArcView) findViewById(R.id.archView);
        prepTimerBox=(CountDownView) findViewById(R.id.PrepTimerBox);


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


        //change this to settings view
        ArcAngleAnimation animation = new ArcAngleAnimation(arcView, 300);
        animation.setDuration(5000);
        arcView.startAnimation(animation);
        String left=getDiffAsString(PrepMillis);
        DaysToPrep=Integer.parseInt(left.split("\t:\t")[0]);
        arcView.setText(DaysToPrep+"");

        int progress=(int)((double)(TotalTime-PrepMillis)/(double)TotalTime*360), duration=5000;
        prepTimerBox.setup(progress,duration,DaysToPrep+"");

        setUpTimers();

    }

    private void setUpTimers(){
        //Do timer countdown
        CountDownTimer DoTimer = new CountDownTimer(DoMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                doTimer.setText("CAN DO IN:\t\t\t\t\t"+getDiffAsString(millisUntilFinished));
                elapsedTimer.setText("ELAPSED:\t\t\t\t\t\t"+getDiffAsString(TotalTime-millisUntilFinished));

            }

            @Override
            public void onFinish() {
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
                int days=Integer.parseInt(left.split("\t:\t")[0]);
                //@sameertodo: when 1 day, change to "Day"
                //@sameertodo: when 0, remove the view and show.
                if(days!=DaysToPrep) {
                    DaysToPrep=days;
                    arcView.setText(days+"");
                }
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
