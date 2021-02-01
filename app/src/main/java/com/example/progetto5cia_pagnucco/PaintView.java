package com.example.progetto5cia_pagnucco;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Path;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class PaintView extends View{

    public static int BRUSH_SIZE=10;
    public static final int DEFAULT_COLOR = Color.BLACK;
    public static final int DEFAULT_BG_COLOR = Color.WHITE;
    public static final float TOUCH_TOLERANCE = 4;
    public float mX, mY;
    private Path mPath;
    private Paint mPaint;
    private ArrayList<FingerPath> paths = new ArrayList<>();
    private int currentColor;
    private int backgroundColor = DEFAULT_BG_COLOR;
    private int strokeWidth;
    private boolean fill;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mBitmapPaint = new Paint (Paint.DITHER_FLAG);
    private static boolean activated=true;

    public PaintView(Context context) {
        this(context, null);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(DEFAULT_COLOR);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xff);
    }

    public void init(DisplayMetrics metrics) {
        int heigth = metrics.heightPixels;
        int width = metrics.widthPixels;

        mBitmap = Bitmap.createBitmap(width, heigth, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        currentColor = DEFAULT_COLOR;
        strokeWidth = BRUSH_SIZE;
    }

    public void normal() {
        fill = false;
        currentColor=Color.BLACK;
    }

    public void fill() {
        fill = true;
    }

    public void rubber() { currentColor = backgroundColor; }

    public void black() {
        currentColor=Color.BLACK;
    }

    public void lightGray() { currentColor=Color.LTGRAY; }

    public void gray() { currentColor = Color.GRAY; }

    public void darkGray() { currentColor=Color.DKGRAY; }

    public void red() {
        currentColor=Color.RED;
    }

    public void yellow() { currentColor=Color.YELLOW; }

    public void blue() { currentColor=Color.BLUE; }

    public void green() { currentColor=Color.GREEN; }

    public void magenta() { currentColor=Color.MAGENTA; }

    public void cyan() { currentColor=Color.CYAN; }

    public void brown() { currentColor=R.color.brown; }

    public void pinkSkin() { currentColor=R.color.pink_skin; }

    public void orange() { currentColor=R.color.orange; }

    public void clear() {
        if(activated) {
            backgroundColor = DEFAULT_BG_COLOR;
            paths.clear();
            normal();
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(activated) {
            canvas.save();
            mCanvas.drawColor(backgroundColor);

            for (FingerPath fp : paths) {
                mPaint.setColor(fp.color);
                mPaint.setStrokeWidth(fp.strokeWidth);
                mPaint.setMaskFilter(null);
                mPaint.setStyle(Paint.Style.STROKE);

                if (fp.fill)
                    mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

                mCanvas.drawPath(fp.path, mPaint);
            }

            canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
            canvas.restore();
        }
    }

    private void touchStart (float x, float y) {
        mPath = new Path();
        FingerPath fp = new FingerPath(currentColor, fill, strokeWidth, mPath);
        paths.add(fp);

        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touchMove(float x, float y) {
        float dX = Math.abs(x-mX);
        float dY = Math.abs(y-mY);

        if(dY>=TOUCH_TOLERANCE || dY>=TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x+mX) /2, (y+mY) /2);
            mX=x;
            mY=y;
        }
    }

    private void touchUp() {
        mPath.lineTo(mX, mY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(activated) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touchStart(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    touchMove(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touchUp();
                    invalidate();
                    break;
            }
        }
        return true;
    }

    public Bitmap getBitmap()
    {
        //this.measure(100, 100);
        //this.layout(0, 0, 100, 100);
        this.setDrawingCacheEnabled(true);
        this.buildDrawingCache();
        Bitmap bmp = Bitmap.createBitmap(this.getDrawingCache());
        this.setDrawingCacheEnabled(false);


        return bmp;
    }

    public static void enable() {
        activated=true;
    }

    public static void disable() {
        activated=false;
    }
}
