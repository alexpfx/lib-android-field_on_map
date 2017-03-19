package com.github.alexpfx.fieldonmap.lib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by alexandre on 19/03/2017.
 */

public class FieldMapView extends FrameLayout {
    private FieldDraw fieldDraw;

    public FieldMapView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public void init (){
        inflate(getContext(), R.layout.field_map_view, this);
        fieldDraw = (FieldDraw) findViewById(R.id.field_drawer);


    }

}
