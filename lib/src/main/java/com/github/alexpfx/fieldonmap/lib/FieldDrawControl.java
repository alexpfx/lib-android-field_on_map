package com.github.alexpfx.fieldonmap.lib;

/**
 * Created by alexandre on 18/03/2017.
 */

public interface FieldDrawControl {
    boolean down(float x, float y, int pointerId, int pointerIndex);
    boolean move(float x, float y, int pointerId, int pointerIndex);
    boolean up(float x, float y, int pointerId, int pointerIndex);
}
