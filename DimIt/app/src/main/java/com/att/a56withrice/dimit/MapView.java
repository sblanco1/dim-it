package com.att.a56withrice.dimit;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MapView extends View {

    //drawing path
    private Path drawPath;
    //drawing and canvas paint
    private Paint drawPaint, canvasPaint;
    //initial color
    private int paintColor = 0xFF660000;
    //canvas
    private Canvas drawCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;

    float pointX;
    float pointY;
    float startX;
    float startY;


    public MapView(Context context) {
        super(context);
    }

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    public MapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void setupDrawing(){
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(20);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawRect(startX, startY, pointX, pointY, drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        pointX = event.getX();
        pointY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = pointX;
                startY = pointY;
                return true;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                drawCanvas.drawRect(startX, startY, pointX, pointY, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

}
