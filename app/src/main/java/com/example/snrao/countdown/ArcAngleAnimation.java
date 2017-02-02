package com.example.snrao.countdown;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by S N Rao on 2/1/2017.
 */
public class ArcAngleAnimation extends Animation {

    private ArcView arcView;

    private float oldAngle;
    private float newAngle;

    public ArcAngleAnimation(ArcView arcView, int newAngle) {
        this.oldAngle = arcView.getArcAngle();
        this.newAngle = newAngle;
        this.arcView = arcView;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        float angle = 0 + ((newAngle - oldAngle) * interpolatedTime);

        arcView.setArcAngle(angle);
        arcView.requestLayout();
    }
}