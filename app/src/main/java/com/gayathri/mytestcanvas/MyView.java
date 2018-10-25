package com.gayathri.mytestcanvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import static android.graphics.Path.Direction.CW;

public class MyView  extends SurfaceView{

    private Random mRandX, mRandY;

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
    ArrayList<MyView.SquareCoor> squareXYList;
    ArrayList<MyView.CircleCoor> circleXYList;
    ArrayList<MyView.TriangleCoor> triangleXYList;

    String currentshape, tempShape, shape_cleared;

    SurfaceHolder surfaceHolder;
    SurfaceView surfaceView;

    Canvas c = null;

    int MaxWidth,MinWidth, MaxHeight,MinHeight;

    private int currentShapeIndex = 0;
    Boolean isDrawing = true;
    Boolean isUndoClicked = true;
    CounterValues values;

    public MyView(Context context) {
        super(context);
        this.setWillNotDraw(false);
        init();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setWillNotDraw(false);
        init();
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        c = new Canvas();

        values = CounterValues.getInstance();
        surfaceView = this;
        surfaceHolder = surfaceView.getHolder();
        surfaceView.setZOrderOnTop(true);surfaceView.getHolder().setFormat(PixelFormat.RGBA_8888);

        drawnList = new ArrayList<String>();
        squareXYList = new ArrayList<MyView.SquareCoor>();
        circleXYList = new ArrayList<MyView.CircleCoor>();
        triangleXYList = new ArrayList<MyView.TriangleCoor>();

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
                        getHolder().unlockCanvasAndPost(canvas);
                    } else{
                        canvas = surfaceHolder.lockCanvas();
                        c.drawPath(circlePath, mPaintCircle);
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
                        c.drawPath(squarePath, mPaintSquare);
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
                        c.drawPath(trianglepath, mPaintTriangle);
                        canvas.drawBitmap(bitmap, 0, 0, null);
                        getHolder().unlockCanvasAndPost(canvas);
                        isDrawing = true;
                    }
                    break;
                case "Empty":
                    canvas = getHolder().lockCanvas();
                    canvas.drawBitmap(bitmap, 0, 0, null);
                    getHolder().unlockCanvasAndPost(canvas);
                    break;
            }
        }
        else {
            if(values.getMaxWidth() == 0 && values.getMaxHeight() == 0) {
                MaxWidth = getMeasuredWidth();
                MinWidth = getMinimumWidth();
                MaxHeight = getMeasuredHeight();
                MinHeight = getMinimumHeight();
                values.setMaxWidth(MaxWidth);
                values.setMaxHeight(MaxHeight);
            }else {
                values.getMaxWidth();
                values.getMaxHeight();
            }
            initializeBitmap();
            return;
        }
    }

    private void initializeBitmap(){
        bitmap = Bitmap.createBitmap(values.MaxWidth, values.MaxHeight, Bitmap.Config.RGB_565);
        c = new Canvas();
        c.setBitmap(bitmap);
        c.drawColor(Color.WHITE);
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
        CircleCoor circleCoor = new CircleCoor();
        synchronized (surfaceView.getHolder()) {
            if (((MinWidth - 50) <= cx && cx >= (MaxWidth - 50)) && ((MinWidth - 50) <= cy && cy >= (MaxWidth - 50))) {
                draw(c);
            } else {
                circlePath.reset();
                generateRandomPoints();
                draw(c);
            }
        }
        circlePath.reset();
        drawnList.add(getCurrentshape());
        values.setDrawnList(drawnList);
        circleCoor.cx = cx;
        circleCoor.cy = cy;
        circleXYList.add(circleCoor);
        values.setCircleXYList(circleXYList);
    }

    public void drawSquare() {
        generateRandomPoints();
        SquareCoor squareCoor = new SquareCoor();
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

        squarePath.reset();
        drawnList.add(getCurrentshape());
        values.setDrawnList(drawnList);
        squareCoor.mleft = mRectSquare.left;
        squareCoor.mtop = mRectSquare.top;
        squareCoor.mright = mRectSquare.right;
        squareCoor.mbottom = mRectSquare.bottom;
        squareXYList.add(squareCoor);
        values.setSquareXYList(squareXYList);
    }

    public void drawTriangle() {
        generateRandomPoints();
        TriangleCoor triangleCoor = new TriangleCoor();
        synchronized (surfaceView.getHolder()) {
            if ( tX >= 50 && tY >= 50) {
                draw(c);
                trianglepath.reset();
            }
            else {
                trianglepath.reset();
                generateRandomPoints();
                draw(c);
            }
        }
        trianglepath.reset();
        drawnList.add(getCurrentshape());
        values.setDrawnList(drawnList);
        triangleCoor.tX = tX;
        triangleCoor.tY = tY;
        triangleXYList.add(triangleCoor);
        values.setTriangleXYList(triangleXYList);
    }

    private void generateRandomPoints() {
        switch (currentshape) {
            case "Circle":
                cx = mRandX.nextInt(MaxWidth - MinWidth + 1) + MinWidth;
                cy = mRandY.nextInt(MaxHeight - MinHeight + 1) + MinHeight;
                circlePath.addCircle(cx, cy, CIRCLE_RADIUS_DEF,CW);
                break;
            case "Square":
                mRectSquare.left = mRandX.nextInt(MaxWidth - MinWidth + 1) + MinWidth;
                mRectSquare.top = mRandY.nextInt(MaxHeight - MinHeight + 1) + MinHeight;
                mRectSquare.right = mRectSquare.left + SQUARE_SIZE_DEF;
                mRectSquare.bottom = mRectSquare.top + SQUARE_SIZE_DEF;
                squarePath.addRect( mRectSquare.left, mRectSquare.top,mRectSquare.right, mRectSquare.bottom, CW);
                break;
            case "Triangle":
                trianglepath.setFillType(Path.FillType.EVEN_ODD);
                tX = mRandX.nextInt(MaxWidth - MinWidth + 1) + MinWidth;
                tY = mRandY.nextInt(MaxHeight - MinHeight + 1) + MinHeight;
                halfWidth = TRIANGLE_WIDTH_DEF/2;
                trianglepath.moveTo(tX,  tY- halfWidth); // Top
                trianglepath.lineTo(tX - halfWidth, tY + halfWidth); // Bottom left
                trianglepath.lineTo(tX + halfWidth, tY + halfWidth); // Bottom right
                trianglepath.lineTo(tX, tY - halfWidth); // Back to Top
                trianglepath.close();
                break;
        }
    }

    public void onUndo() {
        circlePath.rewind();
        squarePath.rewind();
        trianglepath.rewind();
        if(drawnList.size()> 0) {
            tempShape = drawnList.get(drawnList.size() - 1);
            drawnList.remove(drawnList.size() - 1);
        }
        if(!circleXYList.isEmpty() || !squareXYList.isEmpty() || !triangleXYList.isEmpty()) {
            if (isUndoClicked) {
                bitmap = null;
                initializeBitmap();
            }
            isUndoClicked = false;
            switch (tempShape){
                case "Circle":
                    if(circleXYList.size() > 0) {
                        circleXYList.remove(circleXYList.size() - 1);
                        if(values.getmCircleCounter() > 0)
                            values.setmCircleCounter(values.getmSquareCounter() - 1);
                        else
                            values.setmCircleCounter(0);
                    }
                    break;
                case "Square":
                    if(squareXYList.size() > 0) {
                        squareXYList.remove(squareXYList.size() - 1);
                        if(values.getmSquareCounter() > 0)
                            values.setmSquareCounter(values.getmSquareCounter() - 1);
                        else
                            values.setmSquareCounter(0);
                    }
                    break;
                case "Triangle":
                    if(triangleXYList.size() > 0) {
                        triangleXYList.remove(triangleXYList.size() - 1);
                        if(values.getmTriangleCounter() > 0)
                            values.setmTriangleCounter(values.getmTriangleCounter() - 1);
                        else
                            values.setmTriangleCounter(0);
                    }
                    break;
            }
            redrawBitmap();
            isUndoClicked = true;
        }
        else{
            draw(c);
            Toast.makeText(getContext(), "Nothing to Undo", Toast.LENGTH_SHORT).show();
        }
    }

    public void redrawBitmap() {
        if(!circleXYList.isEmpty()) {
            for (int i = 0; i < circleXYList.size(); i++) {
                CircleCoor tempcircle = circleXYList.get(i);
                synchronized (surfaceView.getHolder()) {
                    setCurrentshape("Circle");
                    circlePath.addCircle(tempcircle.cx, tempcircle.cy, CIRCLE_RADIUS_DEF, CW);
                    isDrawing = false;
                    draw(c);
                    circlePath.rewind();
                }
            }
        }
        if(!squareXYList.isEmpty()) {
            for (int j = 0; j < squareXYList.size(); j++) {
                SquareCoor tempsquare = squareXYList.get(j);
                synchronized (surfaceView.getHolder()) {
                    setCurrentshape("Square");
                    squarePath.addRect(tempsquare.mleft, tempsquare.mtop, tempsquare.mright, tempsquare.mbottom, CW);
                    isDrawing = false;
                    draw(c);
                    squarePath.rewind();
                }
            }
        }
        if(!triangleXYList.isEmpty()) {
            for (int k = 0; k < triangleXYList.size(); k++) {
                TriangleCoor temptriangle = triangleXYList.get(k);
                synchronized (surfaceView.getHolder()) {
                    setCurrentshape("Triangle");
                    halfWidth = TRIANGLE_WIDTH_DEF / 2;
                    trianglepath.moveTo(temptriangle.tX, temptriangle.tY - halfWidth);
                    trianglepath.lineTo(temptriangle.tX - halfWidth, temptriangle.tY + halfWidth);
                    trianglepath.lineTo(temptriangle.tX + halfWidth, temptriangle.tY + halfWidth);
                    trianglepath.lineTo(temptriangle.tX, temptriangle.tY - halfWidth);
                    trianglepath.close();
                    isDrawing = false;
                    draw(c);
                    trianglepath.rewind();
                }
            }
        }

        if(drawnList.isEmpty()) {
            bitmap = null;
            initializeBitmap();
            draw(c);
        }
    }

    public void clearshape(){
           if(!drawnList.isEmpty()) {
               bitmap = null;
               initializeBitmap();
               redrawBitmap();
           }
           else {
               setCurrentshape("Empty");
               redrawBitmap();
           }
    }

    public void clearTriangle() {
        if (!values.getTriangleXYList().isEmpty()) {
            triangleXYList = values.getTriangleXYList();
            values.setTriangleXYList(null);
            triangleXYList.clear();
            int i = drawnList.size() - 1;
            while (!drawnList.isEmpty()) {
                if (i >= 0) {
                    Boolean boolvar = drawnList.get(i).equals("Triangle");
                    if (boolvar) {
                        drawnList.remove(i);
                        i = drawnList.size() - 1;
                    } else
                        i--;
                } else
                    break;
            }
        }
    }

    public void clearSquare() {
        squareXYList = values.getSquareXYList();
        if (!values.getSquareXYList().isEmpty()) {
            values.setSquareXYList(null);
            squareXYList.clear();
            int i = drawnList.size() - 1;
            while (!drawnList.isEmpty()) {
                if (i >= 0) {
                    Boolean boolvar = drawnList.get(i).equals("Square");
                    if (boolvar) {
                        drawnList.remove(i);
                        i = drawnList.size() - 1;
                    } else
                        i--;
                } else
                    break;
            }
        }
    }

    public void clearCircle() {
        circleXYList = values.getCircleXYList();
        if (!values.getCircleXYList().isEmpty()) {
            values.setCircleXYList(null);
            circleXYList.clear();
            int i = drawnList.size() - 1;
            while (!drawnList.isEmpty()) {
                if (i >= 0) {
                    Boolean boolvar = drawnList.get(i).equals("Circle");
                    if (boolvar) {
                        drawnList.remove(i);
                        i = drawnList.size() - 1;
                    } else
                        i--;
                } else
                    break;
            }
        }
    }

    public void addOnTouch(int x, int y){
        String tempShape = getCurrentshape();
        CircleCoor circleCoor = new CircleCoor();
        SquareCoor squareCoor = new SquareCoor();
        TriangleCoor triangleCoor = new TriangleCoor();
        int circleCounter = values.getmCircleCounter();
        int squareCounter = values.getmSquareCounter();
        int triangleCounter = values.getmTriangleCounter();
        if(tempShape == "Circle"){
            drawnList.add("Circle");
            values.setDrawnList(drawnList);
            circleCoor.cx = x;
            circleCoor.cy = y;
            circleCounter++;
            circleXYList.add(circleCoor);
            values.setCircleXYList(circleXYList);
            values.setmCircleCounter(circleCounter);
            redrawBitmap();
            setCurrentshape("Circle");
        }
        if(tempShape == "Square") {
            drawnList.add("Square");
            values.setDrawnList(drawnList);
            squareCoor.mleft = x;
            squareCoor.mtop = y;
            squareCoor.mright = x + SQUARE_SIZE_DEF;
            squareCoor.mbottom = y + SQUARE_SIZE_DEF;
            squareCounter++;
            squareXYList.add(squareCoor);
            values.setSquareXYList(squareXYList);
            values.setmSquareCounter(squareCounter);
            redrawBitmap();
            setCurrentshape("Square");
        }
        if(tempShape == "Triangle") {
            drawnList.add("Triangle");
            values.setDrawnList(drawnList);
            triangleCoor.tX = x;
            triangleCoor.tY = y;
            triangleCounter++;
            triangleXYList.add(triangleCoor);
            values.setTriangleXYList(triangleXYList);
            values.setmTriangleCounter(triangleCounter);
            redrawBitmap();
            setCurrentshape("Triangle");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);
        float xF = event.getX();
        float yF = event.getY();
        int x, y;
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                x = (int) xF;
                y = (int) yF;
                addOnTouch(x, y);
                break;
            case MotionEvent.ACTION_UP:
                x = (int) xF;
                y = (int) yF;
                break;
            case MotionEvent.ACTION_MOVE:
                x = (int) xF;
                y = (int) yF;
                break;
        }
        return value;
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
