package com.github.alexpfx.fieldonmap.lib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;

public class MarkDrawer {
    private static final String TAG = "MarkDrawer";

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
    private Paint mMarkPaint;
    private Paint mPathPaint;
    private Paint.Style pathStyle = Paint.Style.FILL;
    private MapRect mMapRect;

    public MarkDrawer(float mBaseWidth, float mBaseHeight, Context context, int pathStrokeColor, int pathAlpha, int pathFillColor, Paint.Style pathStyle) {
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
        mMarkPaint = createMarkPaint();
        mPathPaint = createPathPaint();
        markBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.aim);
        setupMarks();

    }

    private Paint createMarkPaint() {
        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL);
        p.setAntiAlias(true);

        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.point_name_font_size);
        p.setTextSize(dimensionPixelSize);
        return p;
    }


    private Paint createPathPaint() {
        Paint p = new Paint();
        p.setColor(pathStrokeColor);
        p.setAlpha(pathAlpha);
        p.setStyle(pathStyle);
        p.setColorFilter(new PorterDuffColorFilter(pathFillColor, PorterDuff.Mode.SRC_ATOP));
        return p;
    }

    private void setupMarks() {
        final float xMarkMargin = baseWidth * percent;
        final float yMarkMargin = baseHeight * percent;
        markSize = Math.min(baseWidth, baseHeight) * markScale;
        mMapRect = new MapRect(
                new RectHolder[]{
                        new RectHolder(
                                new RectF(xMarkMargin, yMarkMargin, xMarkMargin + markSize, yMarkMargin + markSize), markBitmap, "A"),
                        new RectHolder(
                                new RectF(baseWidth - (xMarkMargin + markSize), yMarkMargin, baseWidth - xMarkMargin, yMarkMargin + markSize), markBitmap, "B"),
                        new RectHolder(
                                new RectF(baseWidth - (xMarkMargin + markSize), baseHeight - (yMarkMargin + markSize), baseWidth - xMarkMargin, baseHeight - yMarkMargin), markBitmap, "C"),
                        new RectHolder(
                                new RectF(xMarkMargin, baseHeight - (yMarkMargin + markSize), xMarkMargin + markSize, baseHeight - yMarkMargin), markBitmap, "D")
                }
        );
    }

    public void draw(Canvas canvas) {
        mMapRect.drawMarks(canvas, mMarkPaint);
        mMapRect.drawPath(canvas, mPathPaint);
    }

    public boolean select(float x, float y) {
        return mMapRect.select(x, y);
    }

    public boolean unSelect (float x, float y){
        return mMapRect.unSelect(x, y);
    }

    public boolean moveSelected(float x, float y) {
        return mMapRect.moveSelected(x, y);
    }

    public void reset() {
        setupMarks();
    }

}
