package com.github.alexpfx.fieldonmap.lib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by alexandre on 19/03/2017.
 */

public class FieldMapView extends FrameLayout {
    private static final String TAG = "FieldMapView";

    private FieldDraw fieldDraw;
    private GoogleMap mMap;


    public FieldMapView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public void setMap(GoogleMap map) {
        mMap = map;
        setupMap (mMap);
    }

    private void setupMap(GoogleMap map) {
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setAllGesturesEnabled(true);
        map.setTrafficEnabled(true);

    }

    public void init() {
        inflate(getContext(), R.layout.field_map_view, this);
        fieldDraw = (FieldDraw) findViewById(R.id.field_drawer);
    }

}
