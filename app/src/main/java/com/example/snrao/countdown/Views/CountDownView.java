package com.example.snrao.countdown.Views;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.snrao.countdown.Activities.DetailsActivity;
import com.example.snrao.countdown.R;

/**
 * Created by S N Rao on 2/3/2017.
 */
public class CountDownView extends LinearLayout{

    ArcView arcView;
    TextView hoursText;
    TextView minutesText;
    TextView secondsText;
    TextView label;
    int days=-1;

    public CountDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.views_countdown,this,true);
        arcView=(ArcView) findViewById(R.id.arcView);
        hoursText=(TextView) findViewById(R.id.hours);
        minutesText=(TextView) findViewById(R.id.minutes);
        secondsText=(TextView) findViewById(R.id.seconds);
        label=(TextView) findViewById(R.id.timerLabel);
        arcView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"ok",Toast.LENGTH_LONG).show();
                Intent i=new Intent(getContext(), DetailsActivity.class);
                getContext().startActivity(i);
            }
        });
    }

    public void setup(int progress,int duration,String labelText){
        ArcAngleAnimation animation = new ArcAngleAnimation(arcView, progress);
        animation.setDuration(duration);
        arcView.startAnimation(animation);
        label.setText(labelText);
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

    public void finish(boolean countingForward, String doneText){
        arcView.finish(countingForward);
        hoursText.setText("00");
        minutesText.setText("00");
        secondsText.setText("00");
        label.setText(doneText);
        label.setTextSize(25);
        label.setTextColor(getResources().getColor(R.color.colorAccentGreen));
    }
}
