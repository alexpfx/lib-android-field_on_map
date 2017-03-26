package com.github.alexpfx.fieldonmap.lib;

import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by alexandre on 18/03/2017.
 */

public class InputDetector {

    private static final String TAG = "InputDetector";
    private FieldDrawControl control;


    public InputDetector(FieldDrawControl control) {
        this.control = control;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        int actionIndex = ev.getActionIndex();
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                return dispatchActionDown(ev, actionIndex);
            case MotionEvent.ACTION_MOVE:
                return dispatchMove(ev);
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                return dispatchUp(ev, actionIndex);
        }
        return false;
    }


    private boolean dispatchUp(MotionEvent ev, int actionIndex) {
        Log.d(TAG, "dispatchUp: "+actionIndex);
        int pointerId = ev.getPointerId(actionIndex);
        int pointerIndex = ev.findPointerIndex(pointerId);
        control.up(ev.getX(pointerId), ev.getY(pointerId), pointerId, pointerIndex);
        return false;
    }

    private boolean dispatchMove(MotionEvent ev) {
        for (int i = 0; i < ev.getPointerCount(); i++) {
            int pointerId = ev.getPointerId(i);
            int pointerIndex = ev.findPointerIndex(pointerId);
            control.move(ev.getX(pointerIndex), ev.getY(pointerIndex), pointerId, pointerIndex);
        }

        return true;
    }

    private boolean dispatchActionDown(MotionEvent ev, int actionIndex) {

        int pointerId = ev.getPointerId(actionIndex);
        int pointerIndex = ev.findPointerIndex(pointerId);
        float x = ev.getX(pointerIndex);
        float y = ev.getY(pointerIndex);

        control.down(x, y, pointerId, pointerIndex);
        return true;
    }


}
