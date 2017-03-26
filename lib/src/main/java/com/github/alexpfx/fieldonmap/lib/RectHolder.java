package com.github.alexpfx.fieldonmap.lib;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import junit.framework.Assert;

/**
 * Created by alexandre on 26/03/2017.
 */
//TODO alterar nome.
public class RectHolder {

    private static final float POSITION_TO_LEFT = 10;
    private static final float POSITION_TO_TOP = 10;
    private RectF mRectF;
    private Bitmap mBitmap;
    private boolean mSelected = false;
    private String mLabel;

    public RectHolder(RectF rectF, Bitmap bitmap, String label) {
        Assert.assertNotNull(rectF);
        Assert.assertNotNull(bitmap);
        mRectF = rectF;
        mBitmap = bitmap;
        mLabel = label;
    }


    public float centerX() {
        return mRectF.centerX();
    }

    public float centerY() {
        return mRectF.centerY();
    }

    public boolean isSelected() {
        return mSelected;
    }

    public void setSelected(boolean selected) {
        mSelected = selected;
    }

    public void draw(Canvas canvas, Paint markPaint) {
        canvas.drawText(mLabel, mRectF.left - POSITION_TO_LEFT, mRectF.top + POSITION_TO_TOP, markPaint);
        canvas.drawBitmap(mBitmap, null, mRectF, markPaint);
    }

    public void move(float x, float y) {
        mRectF.offsetTo(x, y);
    }

    public boolean contains (float x, float y){
        return mRectF.contains(x, y);
    }


}
