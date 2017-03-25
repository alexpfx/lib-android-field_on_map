package com.github.alexpfx.fieldonmap.lib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
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
    private Point[] points = new Point[]{new Point(), new Point(), new Point(), new Point()};
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

        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.point_name_font_size);
        p.setTextSize(dimensionPixelSize);
        return p;
    }

    private void updatePath() {
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

    private static final String[] POINT_NAME = new String[]{"A", "B", "C", "D"};

    private void drawMarks(Canvas canvas) {
        int i = 0;
        for (RectF p : markRects) {

            canvas.drawText(POINT_NAME[i++], p.right - 10, p.top - 10, markPaint);
            canvas.drawBitmap(markBitmap, null, p, markPaint);
        }
    }

    private boolean isSelected(RectF r) {
        if (selectedMark == null) {
            return false;
        }
        return r.equals(selectedMark);
    }

    public boolean select(float x, float y) {
        for (RectF r : markRects) {
            if (r.contains(x, y)) {
                selectedMark = r;
                return true;
            }
        }
        selectedMark = null;
        return false;
    }


    private boolean getSelectedMark() {
        return selectedMark != null;
    }

    public boolean moveSelected(float x, float y) {
        if (!getSelectedMark()) {
            return false;
        }
        selectedMark.offsetTo(x, y);
        return true;
    }


    public Point[] getPoints() {
        //copy instead instance.
        Point[] points = new Point[markRects.length];

        for (int i = 0; i < markRects.length; i++) {
            RectF markRect = markRects[i];
//            points[i].set((int) markRect.centerX(), (int)markRect.centerY());
            points[i] = new Point((int) markRect.centerX(), (int) markRect.centerY());
        }

        return points;
    }

    public void reset() {
        setupMarkRects();
    }

}
