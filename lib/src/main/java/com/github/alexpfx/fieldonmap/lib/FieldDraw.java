package com.github.alexpfx.fieldonmap.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class FieldDraw extends View implements FieldDrawControl {

    private static final String TAG = "FieldDraw";
    private float mSelectAreaStrokeLineWidth;
    private int mSelectAreaStrokeColor;
    private int mSelectAreaFillAlpha;
    private LocationListener mListener;


    interface LocationListener {
        void onPossibleNewLocation(Point[] points);

        void onShow();

        void onHide();
    }


    private RectHolder holder;

    private Paint mLinePaint;

    private Paint backgroundPaint;

    private InputDetector inputDetector = new InputDetector(this);
    private int mSelectAreaFillColor;

    public FieldDraw(Context context) {
        super(context);
    }

    public void setListener(LocationListener listener) {
        mListener = listener;
    }

    public FieldDraw(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        readAttrs(context, attrs);

    }

    private void readAttrs(Context context, AttributeSet attributeSet) {
        if (attributeSet == null) {
            return;
        }
        setBackground(null);
        TypedArray a = context.obtainStyledAttributes(attributeSet, R.styleable.FieldDraw);
        mSelectAreaStrokeLineWidth = a.getDimension(R.styleable.FieldDraw_selectAreaStrokeWidth, 1f);
        mSelectAreaStrokeColor = a.getColor(R.styleable.FieldDraw_selectAreaStroke, -1);
        mSelectAreaFillColor = a.getColor(R.styleable.FieldDraw_selectAreaFill, -1);
        mSelectAreaFillAlpha = a.getColor(R.styleable.FieldDraw_selectAreaFillAlpha, 50);

        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (holder == null) {
            initHolder(width, height);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initHolder(int width, int height) {
        Paint.Style style = getSelectAreaStyle();
        holder = new RectHolder(width, height, getContext(), mSelectAreaStrokeColor, mSelectAreaFillAlpha, mSelectAreaFillColor, style);
    }

    private Paint.Style getSelectAreaStyle() {
        return mSelectAreaFillColor == -1 ? Paint.Style.STROKE : Paint.Style.FILL_AND_STROKE;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return inputDetector.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        holder.draw(canvas);
    }

    @Override
    public boolean down(float x, float y, int pointerId, int pointerIndex) {
        boolean wasSelected = holder.select(x, y);
        invalidate();
        return wasSelected;
    }

    @Override
    public boolean move(float x, float y, int pointerId, int pointerIndex) {
        final boolean wasMoved = holder.moveSelected(x, y);
        invalidate();
        return wasMoved;
    }

    @Override
    public boolean up(float x, float y, int pointerId, int pointerIndex) {
        if (mListener != null) {
            mListener.onPossibleNewLocation(holder.getPoints());
        }
        return false;
    }

    public void reset() {
        holder.reset();
        invalidate();
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        if (mListener == null){
            return;
        }

        if (VISIBLE == visibility) {
            mListener.onShow();
        } else {
            mListener.onHide();
        }
    }
}
