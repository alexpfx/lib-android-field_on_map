package com.github.alexpfx.fieldonmap.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.google.android.gms.maps.MapView;

/**
 * Created by alexandre on 19/03/2017.
 */

public class FieldMapView extends FrameLayout {
    private static final String TAG = "FieldMapView";

    private FieldDraw fieldDraw;
    private MapView mMapView;
    private int mMapViewId;

    public FieldMapView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        readAttrs(context, attrs);
        init();
    }

    private void readAttrs(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FieldMapView);
        mMapViewId = a.getResourceId(R.styleable.FieldMapView_mapView, -1);

        getRootView().getParent();
        a.recycle();
    }


    public void init (){
        inflate(getContext(), R.layout.field_map_view, this);
        fieldDraw = (FieldDraw) findViewById(R.id.field_drawer);

    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mMapView = (MapView) getRootView().findViewById(mMapViewId);
        fieldDraw.setMapView(mMapView);
    }
}
