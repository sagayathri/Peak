package com.gayathri.mytestcanvas;

import java.util.ArrayList;

public class CounterValues{

    public static CounterValues sInstance;

    public int mCircleCounter = 0;
    public int mSquareCounter = 0;
    public int mTriangleCounter = 0;

    public String getCircle, getSquare, getTriangle;

    int MaxWidth,MaxHeight;

    ArrayList<String> drawnList;
    ArrayList<MyView.SquareCoor> squareXYList;
    ArrayList<MyView.CircleCoor> circleXYList;
    ArrayList<MyView.TriangleCoor> triangleXYList;

    boolean isClearedCircle;
    boolean isClearedSquare;
    boolean isClearedTriangle;

    public CounterValues() {
        drawnList = new ArrayList<String>();
        squareXYList = new ArrayList<MyView.SquareCoor>();
        circleXYList = new ArrayList<MyView.CircleCoor>();
        triangleXYList = new ArrayList<MyView.TriangleCoor>();
    }

    public static CounterValues getInstance() {
        if(sInstance == null){
            sInstance = new CounterValues();
        }
        return sInstance;
    }

    public int getmCircleCounter() {
        return mCircleCounter;
    }

        public void setmCircleCounter(int mCircleCounter) {
            this.mCircleCounter = mCircleCounter;
    }

        public int getmSquareCounter() {
            return mSquareCounter;
    }

        public void setmSquareCounter(int mSquareCounter) {
            this.mSquareCounter = mSquareCounter;
    }

        public int getmTriangleCounter() {
            return mTriangleCounter;
    }

        public void setmTriangleCounter(int mTriangleCounter) {
            this.mTriangleCounter = mTriangleCounter;
        }

    public int getMaxWidth() {
        return MaxWidth;
    }

    public void setMaxWidth(int maxWidth) {
        MaxWidth = maxWidth;
    }

    public int getMaxHeight() {
        return MaxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        MaxHeight = maxHeight;
    }

    public ArrayList<String> getDrawnList() {
        return drawnList;
    }

    public void setDrawnList(ArrayList<String> drawnList) {
        this.drawnList = drawnList;
    }

    public ArrayList<MyView.SquareCoor> getSquareXYList() {
        return squareXYList;
    }

    public void setSquareXYList(ArrayList<MyView.SquareCoor> squareXYList) {
        this.squareXYList = squareXYList;
    }

    public ArrayList<MyView.CircleCoor> getCircleXYList() {
        return circleXYList;
    }

    public void setCircleXYList(ArrayList<MyView.CircleCoor> circleXYList) {
        this.circleXYList = circleXYList;
    }

    public ArrayList<MyView.TriangleCoor> getTriangleXYList() {
        return triangleXYList;
    }

    public void setTriangleXYList(ArrayList<MyView.TriangleCoor> triangleXYList) {
        this.triangleXYList = triangleXYList;
    }

    public String getGetCircle() {
        return getCircle;
    }

    public void setGetCircle(String getCircle) {
        this.getCircle = getCircle;
    }

    public String getGetSquare() {
        return getSquare;
    }

    public void setGetSquare(String getSquare) {
        this.getSquare = getSquare;
    }

    public String getGetTriangle() {
        return getTriangle;
    }

    public void setGetTriangle(String getTriangle) {
        this.getTriangle = getTriangle;
    }

    public boolean getisClearedCircle() {
        return isClearedCircle;
    }

    public void setClearedCircle(boolean clearedCircle) {
        isClearedCircle = clearedCircle;
    }

    public boolean getisClearedSquare() {
        return isClearedSquare;
    }

    public void setClearedSquare(boolean clearedSquare) {
        isClearedSquare = clearedSquare;
    }

    public boolean getisClearedTriangle() {
        return isClearedTriangle;
    }

    public void setClearedTriangle(boolean clearedTriangle) {
        isClearedTriangle = clearedTriangle;
    }
}

