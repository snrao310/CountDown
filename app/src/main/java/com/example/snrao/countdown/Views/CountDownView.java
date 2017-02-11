package com.example.snrao.countdown.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.snrao.countdown.R;

/**
 * Created by S N Rao on 2/3/2017.
 */
public class CountDownView extends LinearLayout{

    ArcView arcView;
    TextView hoursText;
    TextView minutesText;
    TextView secondsText;
    int days=-1;

    public CountDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.views_countdown,this,true);
        arcView=(ArcView) findViewById(R.id.arcView);
        hoursText=(TextView) findViewById(R.id.hours);
        minutesText=(TextView) findViewById(R.id.minutes);
        secondsText=(TextView) findViewById(R.id.seconds);
    }

    public void setup(int progress,int duration){
        ArcAngleAnimation animation = new ArcAngleAnimation(arcView, progress);
        animation.setDuration(duration);
        arcView.startAnimation(animation);
    }

    public void update(String days, String hours, String minutes, String seconds){
        int newDays=Integer.parseInt(days);
        if(this.days!=newDays){
            this.days=newDays;
            arcView.setText(days);
        }
        hoursText.setText(hours);
        minutesText.setText(minutes);
        secondsText.setText(seconds);
    }

    public void finish(){
        arcView.finish();
        hoursText.setText("00");
        minutesText.setText("00");
        secondsText.setText("00");
    }
}
