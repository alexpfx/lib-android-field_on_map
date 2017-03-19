package com.github.alexpfx.fieldonmap.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FieldDraw extends View implements FieldDrawControl, OnMapReadyCallback {

    private static final String TAG = "FieldDraw";
    private float mSelectAreaStrokeLineWidth;
    private int mSelectAreaStrokeColor;
    private int mSelectAreaFillAlpha;
    private MapView mMapView;

    private RectHolder holder;

    private Paint mLinePaint;

    private Paint backgroundPaint;

    private InputDetector inputDetector = new InputDetector(this);
    private int mSelectAreaFillColor;

    public FieldDraw(Context context) {
        super(context);
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
    public boolean down(float x, float y) {
        boolean wasSelected = holder.select(x, y);
        invalidate();
        return wasSelected;
    }

    @Override
    public boolean move(float x, float y) {
        final boolean wasMoved = holder.moveSelected(x, y);
        invalidate();
        return wasMoved;
    }

    public void setMapView(MapView mapView) {
        mMapView = mapView;
        Log.d(TAG, "setMapView: "+mapView);
        if (mMapView != null){
            mMapView.getMapAsync(this);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(12, 25);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        googleMap.addMarker(new MarkerOptions().position(latLng));
    }
}
