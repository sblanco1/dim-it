package com.att.a56withrice.dimit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


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

    boolean addingLight = false;

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

    public void addLight() {
        addingLight = true;

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
        if (w == 0 || h == 0) return;
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

        if (addingLight){
            drawPath.reset();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    System.out.println("Adding light");
                    Paint drawPaintCircle = new Paint();
                    drawPaintCircle.setColor(Color.parseColor("yellow"));
                    drawCanvas.drawCircle(event.getX(), event.getY(),50,drawPaintCircle);
                    return true;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                        addingLight = false;
                    drawPath.reset();
                    break;
                default:
                    return false;
            }

        }else{
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
        }

        invalidate();
        return true;
    }

}
