package com.github.alexpfx.fieldonmap.lib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;

public class RectHolder {
    private static final String TAG = "RectHolder";

    private final float baseWidth;
    private final float baseHeight;
    private final Context context;
    private int pathStrokeColor;
    private int pathAlpha;
    private int pathFillColor;

    private float percent = 0.14f;
    private float markScale = 0.12f;
    private float markSize;
    private Bitmap markBitmap;
    private RectF[] markRects = new RectF[]{new RectF(), new RectF(), new RectF(), new RectF()};
    private RectF selectedMark = null;
    private Paint markPaint;

    private Path path = new Path();
    private Paint pathPaint;
    private Paint.Style pathStyle = Paint.Style.FILL;

    public RectHolder(float mBaseWidth, float mBaseHeight, Context context, int pathStrokeColor, int pathAlpha, int pathFillColor, Paint.Style pathStyle) {
        this.baseWidth = mBaseWidth;
        this.baseHeight = mBaseHeight;
        this.context = context;
        this.pathStrokeColor = pathStrokeColor;
        this.pathAlpha = pathAlpha;
        this.pathFillColor = pathFillColor;
        this.pathStyle = pathStyle;
        init(context);
    }

    public void init(Context context) {
        setupMarkRects();

        markPaint = createMarkPaint();
        pathPaint = createPathPaint();
        markBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.aim);

    }

    private Paint createPathPaint() {
        Paint p = new Paint();
        p.setColor(pathStrokeColor);
        p.setAlpha(pathAlpha);
        p.setStyle(pathStyle);
        p.setColorFilter(new PorterDuffColorFilter(pathFillColor, PorterDuff.Mode.SRC_ATOP));
        return p;
    }

    private void setupMarkRects() {
        final float xMarkMargin = baseWidth * percent;
        final float yMarkMargin = baseHeight * percent;
        markSize = Math.min(baseWidth, baseHeight) * markScale;

        markRects[0].set(xMarkMargin, yMarkMargin, xMarkMargin + markSize, yMarkMargin + markSize);
        markRects[1].set(baseWidth - (xMarkMargin + markSize), yMarkMargin, baseWidth - xMarkMargin, yMarkMargin + markSize);
        markRects[2].set(baseWidth - (xMarkMargin + markSize), baseHeight - (yMarkMargin + markSize), baseWidth - xMarkMargin, baseHeight - yMarkMargin);
        markRects[3].set(xMarkMargin, baseHeight - (yMarkMargin + markSize), xMarkMargin + markSize, baseHeight - yMarkMargin);
    }

    private Paint createMarkPaint() {
        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL);
        p.setAntiAlias(true);
        return p;
    }

    public void updatePath() {
        path.reset();

        for (int i = 0; i < markRects.length; i++) {
            if (i == 0) {
                path.moveTo(markRects[i].centerX(), markRects[i].centerY());
                continue;
            }
            path.lineTo(markRects[i].centerX(), markRects[i].centerY());
            if (i == markRects.length - 1) {
                path.lineTo(markRects[0].centerX(), markRects[0].centerY());
            }
        }
    }

    public void draw(Canvas canvas) {
        updatePath();
        canvas.drawPath(path, pathPaint);
        drawMarks(canvas);
    }

    private void drawMarks(Canvas canvas) {
        for (RectF p : markRects) {
            if (isSelected(p)) {
                canvas.drawBitmap(markBitmap, null, p, markPaint);
            } else {
                canvas.drawBitmap(markBitmap, null, p, markPaint);
            }
        }
    }

    private boolean isSelected(RectF r) {
        if (selectedMark == null) {
            return false;
        }
        return r.equals(selectedMark);
    }

    public void select(float x, float y) {
        for (RectF r : markRects) {
            if (r.contains(x, y)) {
                selectedMark = r;
                return;
            }
        }
        selectedMark = null;
    }


    private boolean getSelectedMark() {
        return selectedMark != null;
    }

    public void moveSelected(float x, float y) {
        if (!getSelectedMark()) {
            return;
        }

        selectedMark.offsetTo(x, y);
    }

}
