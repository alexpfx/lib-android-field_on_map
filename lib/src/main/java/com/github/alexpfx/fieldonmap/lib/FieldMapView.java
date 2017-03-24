package com.github.alexpfx.fieldonmap.lib;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alexandre on 19/03/2017.
 */

public class FieldMapView extends FrameLayout implements FieldDraw.LocationListener {
    private static final String TAG = "FieldMapView";

    private FieldDraw fieldDraw;
    private GoogleMap mMap;


    @BindView(R2.id.btn_reset_drawer)
    ImageButton btnReset;


    public FieldMapView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public void setMap(GoogleMap map) {
        mMap = map;
        setupMap();
    }

    private void setupMap() {
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.setTrafficEnabled(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        VisibleRegion visibleRegion = mMap.getProjection().getVisibleRegion();
        Log.d(TAG, "setupMap: " + visibleRegion);
        return super.onTouchEvent(event);

    }


    public void init() {
        inflate(getContext(), R.layout.field_map_view, this);
        fieldDraw = (FieldDraw) findViewById(R.id.field_drawer);
        fieldDraw.setListener(this);
    }

    @Override
    public void onPossibleNewLocation(Point[] points) {
        Projection projection = mMap.getProjection();
        List<LatLng> list = new ArrayList<>();
        for (Point point : points) {
            LatLng latLng = projection.fromScreenLocation(point);
            list.add(latLng);
        }
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : list) {
            builder.include(latLng);
        }
        LatLngBounds latLngBounds = builder.build();
        double area = SphericalUtil.computeArea(list);
        Log.d(TAG, "onPossibleNewLocation: " + area);

        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLngBounds.getCenter()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
    }

    @Override
    public void onShow() {
        btnReset.setVisibility(VISIBLE);
    }

    @Override
    public void onHide() {
        btnReset.setVisibility(INVISIBLE);
    }


    @OnClick(R2.id.btn_reset_drawer)
    void onBtnResetDrawerClick(View view) {
        fieldDraw.reset();
    }


    @OnClick(R2.id.btn_show_drawer)
    void onBtnShowDrawerClick(View view) {
        if (fieldDraw.getVisibility() == VISIBLE) {
            fieldDraw.setVisibility(INVISIBLE);
        } else {
            fieldDraw.setVisibility(VISIBLE);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ButterKnife.bind(this);
    }
}
