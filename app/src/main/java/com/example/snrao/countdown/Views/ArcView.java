package com.example.snrao.countdown.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.snrao.countdown.R;

/**
 * Created by S N Rao on 2/1/2017.
 */
public class ArcView extends View {

    private final Paint mPaint;
    private final Paint tPaint;
    private final Paint cPaint;
    private float arcAngle;
    private String text="365";

    public ArcView(Context context, AttributeSet attrs) {

        super(context, attrs);

        // Set Angle to 0 initially
        arcAngle = 0;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        mPaint.setColor(getResources().getColor(R.color.colorAccentGreen));

        tPaint = new Paint();
        tPaint.setStrokeWidth(5);
        tPaint.setTextSize(110);
        tPaint.setColor(Color.WHITE);
        tPaint.setTextAlign(Paint.Align.CENTER);




        cPaint = new Paint();
        cPaint.setStrokeWidth(8);
        cPaint.setColor(Color.WHITE);
        cPaint.setAlpha(50);
        cPaint.setStyle(Paint.Style.STROKE);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int diameter=Math.min(getBottom(),getRight());
        RectF mRect = new RectF(10,10,diameter-10,diameter-10);
        canvas.drawCircle(diameter/2,diameter/2,diameter/2-10,cPaint);
        canvas.drawArc(mRect, 90, arcAngle, false, mPaint);
        int xPos = (diameter / 2);
        int yPos = (int) ((diameter / 2) - ((tPaint.descent() + tPaint.ascent()) / 2)) ;
        tPaint.setTextSize(110);
        canvas.drawText(text, xPos,yPos  , tPaint);
        tPaint.setTextSize(50);
        String days=(Integer.parseInt(text)==1)?"Day":"Days";
        canvas.drawText("Days", diameter-120,diameter-100  , tPaint);
        int h=getHeight();

    }


    public float getArcAngle() {
        return arcAngle;
    }

    public void setArcAngle(float arcAngle) {
        this.arcAngle = arcAngle;
    }

    public void setText(String text){this.text=text;requestLayout();}

    public void finish(){
        arcAngle=360;
        text="00";
        requestLayout();
    }
}