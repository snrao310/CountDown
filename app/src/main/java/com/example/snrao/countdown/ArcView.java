package com.example.snrao.countdown;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by S N Rao on 2/1/2017.
 */
public class ArcView extends View {

    private final Paint mPaint;
    private final RectF mRect;
    private float arcAngle;

    public ArcView(Context context, AttributeSet attrs) {

        super(context, attrs);

        // Set Angle to 0 initially
        arcAngle = 0;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        mPaint.setColor(Color.RED);
        mRect = new RectF(20, 20, 200, 200);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(mRect, 90, arcAngle, false, mPaint);
    }

    public float getArcAngle() {
        return arcAngle;
    }

    public void setArcAngle(float arcAngle) {
        this.arcAngle = arcAngle;
    }
}