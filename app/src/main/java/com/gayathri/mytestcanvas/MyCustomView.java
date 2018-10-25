package com.gayathri.mytestcanvas;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

import static android.graphics.Path.Direction.CW;

public class MyCustomView extends SurfaceView {

    Random mRandX, mRandY;

    private static final int SQUARE_SIZE_DEF = 100;
    private static final int CIRCLE_RADIUS_DEF = 50;
    private static final int TRIANGLE_WIDTH_DEF = 100;

    private Rect mRectSquare;
    private Paint mPaintSquare, mPaintCircle, mPaintTriangle, mPaint;

    private Path circlePath, squarePath, trianglepath;
    private int tX, tY, halfWidth;

    private int cx, cy;
    Bitmap bitmap;

    ArrayList<String> drawnList;
    ArrayList<SquareCoor> squareXYList;
    ArrayList<CircleCoor> circleXYList;
    ArrayList<TriangleCoor> triangleXYList;

    String currentshape;

    SurfaceHolder surfaceHolder;
    SurfaceView surfaceView = this;
    Canvas c = null;

    int MaxWidth,MinWidth, MaxHeight,MinHeight;

    private int currentShapeIndex = 0;
    Boolean isDrawing = true;

    public MyCustomView(Context context) {
        super(context);
        this.setWillNotDraw(false);
        init();
    }

    public MyCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setWillNotDraw(false);
        init();
    }

    public MyCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setWillNotDraw(false);
        init();
    }

    public String getCurrentshape() {
        return currentshape;
    }

    public void setCurrentshape(String currentshape) {
        this.currentshape = currentshape;
    }

    public void init(){
        surfaceHolder = getHolder();
        surfaceView = this;
        surfaceView.setZOrderOnTop(true);
        surfaceView.getHolder().setFormat(PixelFormat.RGBA_8888);

        drawnList = new ArrayList<String>();
        squareXYList = new ArrayList<SquareCoor>();
        circleXYList = new ArrayList<CircleCoor>();
        triangleXYList = new ArrayList<TriangleCoor>();

        mRectSquare = new Rect();
        squarePath = new Path();
        mPaintSquare = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintSquare.setColor(Color.YELLOW);

        circlePath = new Path();
        mPaintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintCircle.setColor(Color.BLUE);

        trianglepath = new Path();
        mPaintTriangle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintTriangle.setColor(Color.RED);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);

        mRandX = new Random();
        mRandY = new Random();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("TAG", "cancas onDraw = "+canvas);
        if(currentshape!=null) {
            switch (getCurrentshape()) {
                case "Circle":
                    if(isDrawing) {
                        canvas = getHolder().lockCanvas();
                        canvas.save();
                        c.drawPath(circlePath, mPaintCircle);
                        canvas.drawBitmap(bitmap, 0, 0, null);
                        canvas.save();
                        getHolder().unlockCanvasAndPost(canvas);
                    } else{
                        canvas = getHolder().lockCanvas();
                        canvas.save();
                        c.drawPath(circlePath, mPaint);
                        canvas.drawBitmap(bitmap, 0, 0, null);
                        getHolder().unlockCanvasAndPost(canvas);
                        isDrawing = true;
                    }
                    break;
                case "Square":
                    if(isDrawing) {
                        canvas = getHolder().lockCanvas();
                        canvas.save();
                        c.drawPath(squarePath, mPaintSquare);
                        canvas.drawBitmap(bitmap, 0, 0, null);
                        getHolder().unlockCanvasAndPost(canvas);
                    } else {
                        canvas = getHolder().lockCanvas();
                        canvas.save();
                        c.drawPath(squarePath, mPaint);
                        canvas.drawBitmap(bitmap, 0, 0, null);
                        getHolder().unlockCanvasAndPost(canvas);
                        isDrawing = true;
                    }
                    break;
                case "Triangle":
                    if(isDrawing) {
                        canvas = getHolder().lockCanvas();
                        canvas.save();
                        c.drawPath(trianglepath, mPaintTriangle);
                        canvas.drawBitmap(bitmap, 0, 0, null);
                        getHolder().unlockCanvasAndPost(canvas);
                    }else{
                        canvas = getHolder().lockCanvas();
                        canvas.save();
                        c.drawPath(trianglepath, mPaint);
                        canvas.drawBitmap(bitmap, 0, 0, null);
                        getHolder().unlockCanvasAndPost(canvas);
                        isDrawing = true;
                    }
                    break;
            }
        }
        else {
            MaxWidth = getMeasuredWidth();
            MinWidth = getMinimumWidth();
            MaxHeight = getMeasuredHeight();
            MinHeight = getMinimumHeight();
            bitmap = Bitmap.createBitmap(MaxWidth, MaxHeight, Config.RGB_565);
            c = new Canvas();
            c.setBitmap(bitmap);
            c.drawColor(Color.WHITE);
            return;
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putInt("currentShapeIndex", this.currentShapeIndex);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            this.currentShapeIndex = bundle.getInt("currentShapeIndex");
            state = bundle.getParcelable("instanceState");
        }
        super.onRestoreInstanceState(state);
    }

    public void drawCircle() {
        generateRandomPoints();
        synchronized (surfaceView.getHolder()) {
            if (((MinWidth - 50) <= cx && cx >= (MaxWidth - 50)) && ((MinWidth - 50) <= cy && cy >= (MaxWidth - 50))) {
                draw(c);
            } else {
                circlePath.reset();
                generateRandomPoints();
                draw(c);
            }
        }
        drawnList.add(getCurrentshape());
    }

    public void drawSquare() {
        generateRandomPoints();
        synchronized (surfaceView.getHolder()) {
            if (0 < mRectSquare.left && mRectSquare.top > 0) {
                draw(c);
            }
            else {
                squarePath.reset();
                generateRandomPoints();
                draw(c);
            }
        }
        drawnList.add(getCurrentshape());
    }

    public void drawTriangle() {
        generateRandomPoints();
        synchronized (surfaceView.getHolder()) {
            if ( tX >= 50 && tY >= 50) {
                draw(c);
            }
            else {
                trianglepath.reset();
                generateRandomPoints();
                draw(c);
            }
        }
        drawnList.add(getCurrentshape());
    }
    private void generateRandomPoints() {
        switch (currentshape) {
            case "Circle":
                CircleCoor circleCoor = new CircleCoor();
                cx = mRandX.nextInt(MaxWidth - MinWidth + 1) + MinWidth;
                cy = mRandY.nextInt(MaxHeight - MinHeight + 1) + MinHeight;
                circlePath.addCircle(cx, cy, CIRCLE_RADIUS_DEF,CW);
                circleCoor.cx = cx;
                circleCoor.cy = cy;
                circleXYList.add(circleCoor);
                break;
            case "Square":
                SquareCoor squareCoor = new SquareCoor();
                mRectSquare.left = mRandX.nextInt(MaxWidth - MinWidth + 1) + MinWidth;
                mRectSquare.top = mRandY.nextInt(MaxHeight - MinHeight + 1) + MinHeight;
                mRectSquare.right = mRectSquare.left + SQUARE_SIZE_DEF;
                mRectSquare.bottom = mRectSquare.top + SQUARE_SIZE_DEF;
                squarePath.addRect( mRectSquare.left, mRectSquare.top,mRectSquare.right, mRectSquare.bottom, CW);
                squareCoor.mleft = mRectSquare.left;
                squareCoor.mtop = mRectSquare.top;
                squareCoor.mright = mRectSquare.right;
                squareCoor.mbottom = mRectSquare.bottom;
                squareXYList.add(squareCoor);
                break;
            case "Triangle":
                TriangleCoor triangleCoor = new TriangleCoor();
                trianglepath.setFillType(Path.FillType.EVEN_ODD);
                tX = mRandX.nextInt(MaxWidth - MinWidth + 1) + MinWidth;
                tY = mRandY.nextInt(MaxHeight - MinHeight + 1) + MinHeight;
                halfWidth = TRIANGLE_WIDTH_DEF/2;
                trianglepath.moveTo(tX,  tY- halfWidth); // Top
                trianglepath.lineTo(tX - halfWidth, tY + halfWidth); // Bottom left
                trianglepath.lineTo(tX + halfWidth, tY + halfWidth); // Bottom right
                trianglepath.lineTo(tX, tY - halfWidth); // Back to Top
                trianglepath.close();
                triangleCoor.tX = tX;
                triangleCoor.tY = tY;
                triangleXYList.add(triangleCoor);
                break;
        }
    }
    public void onUndo() {
        String temp = drawnList.get(drawnList.size()-1);
        drawnList.remove(drawnList.get(drawnList.size()-1));
        circlePath.rewind();
        squarePath.rewind();
        trianglepath.rewind();
        switch (temp){
            case "Circle":
                CircleCoor tempcircle = circleXYList.get(circleXYList.size()-1);
                synchronized (surfaceView.getHolder()) {
                    setCurrentshape("Circle");
                    circlePath.addCircle(tempcircle.cx, tempcircle.cy, CIRCLE_RADIUS_DEF,CW);
                    isDrawing = false;
                    draw(c);
                }
                circleXYList.remove(circleXYList.get(circleXYList.size()-1));
                break;
            case "Square":
                SquareCoor tempsquare = squareXYList.get(squareXYList.size()-1);
                synchronized (surfaceView.getHolder()) {
                    setCurrentshape("Square");
                    squarePath.addRect( tempsquare.mleft, tempsquare.mtop,tempsquare.mright, tempsquare.mbottom, CW);
                    isDrawing = false;
                    draw(c);
                }
                squareXYList.remove(squareXYList.get(squareXYList.size()-1));
                break;
            case "Triangle":
                TriangleCoor temptriangle = triangleXYList.get(triangleXYList.size()-1);
                synchronized (surfaceView.getHolder()) {
                    setCurrentshape("Triangle");
                    halfWidth = TRIANGLE_WIDTH_DEF/2;
                    trianglepath.moveTo(temptriangle.tX,  temptriangle.tY- halfWidth);
                    trianglepath.lineTo(temptriangle.tX - halfWidth, temptriangle.tY + halfWidth);
                    trianglepath.lineTo(temptriangle.tX + halfWidth, temptriangle.tY + halfWidth);
                    trianglepath.lineTo(temptriangle.tX, temptriangle.tY - halfWidth);
                    trianglepath.close();
                    isDrawing = false;
                    draw(c);
                }
                triangleXYList.remove(triangleXYList.get(triangleXYList.size()-1));
                break;
        }
    }


    class CircleCoor{
        public int cx, cy;

    }

    class SquareCoor{
        public int mleft, mtop, mright, mbottom;
    }

    class TriangleCoor {
        public int tX, tY;
    }
}