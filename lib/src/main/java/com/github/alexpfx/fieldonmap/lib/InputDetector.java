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
        int action = ev.getAction();
        float x = ev.getX();
        float y = ev.getY();
//        Log.d(TAG, "onTouchEvent: "+x);
//        Log.d(TAG, "onTouchEvent: "+y);
//        Log.d(TAG, "onTouchEvent: "+action);
        switch (action) {
            case MotionEvent.ACTION_DOWN:{
                control.down(x, y);
            }
            case MotionEvent.ACTION_MOVE:{
                control.move(x, y);
                Log.d(TAG, "onTouchEvent: move");
                return true;
            }

        }
        return false;
    }
}
