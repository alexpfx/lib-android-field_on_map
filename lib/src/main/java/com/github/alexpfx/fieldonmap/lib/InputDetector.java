package com.github.alexpfx.fieldonmap.lib;

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
        ev.findPointerIndex()
        int action = ev.getAction();
        float x = ev.getX();
        float y = ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:{
                return control.down(x, y);
            }
            case MotionEvent.ACTION_MOVE:{
                return control.move(x, y);
            }
            case MotionEvent.ACTION_UP:{
                return control.up(x, y);
            }
        }
        return false;
    }
}
