package com.github.alexpfx.fieldonmap.lib;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by alexandre on 26/03/2017.
 */
//TODO alterar nome.
public class MapRect {
    private List<RectHolder> mRectHolderList;
    private Path path = new Path();

    public void add(RectHolder rectHolder) {
        mRectHolderList.add(rectHolder);
    }

    public MapRect(RectHolder[] rectHolderList) {
        mRectHolderList = Arrays.asList(rectHolderList);
    }
    public MapRect() {
        mRectHolderList = new ArrayList<>();
    }


    public void drawMarks(Canvas canvas, Paint markPaint) {
        for (RectHolder holder : mRectHolderList) {
            holder.draw(canvas, markPaint);
        }
    }

    public void drawPath(Canvas canvas, Paint pathPaint) {
        updatePath();

        canvas.drawPath(path, pathPaint);
    }

    private void updatePath() {
        path.reset();
        Iterator<RectHolder> iterator = mRectHolderList.iterator();
        RectHolder first = iterator.next();
        path.moveTo(first.centerX(), first.centerY());
        while (iterator.hasNext()) {
            RectHolder value = iterator.next();
            path.lineTo(value.centerX(), value.centerY());
        }
        path.lineTo(first.centerX(), first.centerY());
    }


    public boolean moveSelected(float x, float y) {
        boolean hasMoved = false;
        for (RectHolder rectHolder : mRectHolderList) {
            if (rectHolder.isSelected()) {
                hasMoved = true;
                rectHolder.move(x, y);
            }
        }
        return hasMoved;
    }

    public boolean unSelect(float x, float y){
        for (RectHolder rectHolder : mRectHolderList) {
            if (rectHolder.contains(x, y)) {
                rectHolder.setSelected(false);
                return true;
            }
        }
        return false;
    }

    public boolean select(float x, float y) {
        for (RectHolder rectHolder : mRectHolderList) {
            if (rectHolder.contains(x, y)) {
                rectHolder.setSelected(true);
                return true;
            }
        }
        return false;
    }
}


