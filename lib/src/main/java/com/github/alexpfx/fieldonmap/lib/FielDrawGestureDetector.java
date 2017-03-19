package com.github.alexpfx.fieldonmap.lib;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by alexandre on 18/03/2017.
 */
public class FielDrawGestureDetector implements GestureDetector.OnGestureListener {
    private FieldDrawControl control;
    private static final String TAG = "FielDrawGestureDetector";

    public FielDrawGestureDetector(FieldDrawControl control) {
        this.control = control;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.d(TAG, "onDown: "+e.getAction());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {


    }


    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return control.down(e.getX(), e.getY());
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return control.move(e1.getX(), e1.getY());
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return true;
    }

}
