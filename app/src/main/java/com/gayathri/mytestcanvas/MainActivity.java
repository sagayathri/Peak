package com.gayathri.mytestcanvas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MyView mCustomView;
    Button bCircle, bSquare, bTriangle;
    CounterValues values;
    public int mCircleCounter, mSquareCounter, mTriangleCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCustomView = (MyView) findViewById(R.id.customView);
        bCircle = findViewById(R.id.btn_Circle);
        bSquare = findViewById(R.id.btn_Square);
        bTriangle = findViewById(R.id.btn_Triangle);

        values = CounterValues.getInstance();

        bCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomView.setCurrentshape("Triangle");
                mCustomView.drawTriangle();
                if(values.getmTriangleCounter() == 0) {
                    mTriangleCounter = 0;
                    mTriangleCounter++;
                    values.setmTriangleCounter(mTriangleCounter);
                }else{
                    mTriangleCounter++;
                    values.setmTriangleCounter(mTriangleCounter);
                }
            }
        });

        bSquare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomView.setCurrentshape("Circle");
                mCustomView.drawCircle();
                if(values.getmCircleCounter() == 0) {
                    mCircleCounter = 0;
                    mCircleCounter++;
                    values.setmCircleCounter(mCircleCounter);
                }else{
                    mCircleCounter++;
                    values.setmCircleCounter(mCircleCounter);
                }
            }
        });

        bTriangle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomView.setCurrentshape("Square");
                mCustomView.drawSquare();
                if(values.getmSquareCounter() == 0) {
                    mSquareCounter = 0;
                    mSquareCounter++;
                    values.setmSquareCounter(mSquareCounter);
                }else{
                    mSquareCounter++;
                    values.setmSquareCounter(mSquareCounter);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_stats) {
            Intent intent = new Intent(this, StatsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_undo) {
            mCustomView.onUndo();
            return true;
        }
        if (id == R.id.action_refresh) {
            if(values.getmCircleCounter() == 0 && values.getmSquareCounter() == 0 && values.getmTriangleCounter() == 0) {
                if(values.getCircleXYList() != null)
                    mCustomView.clearCircle();
                else {
                    values.setClearedCircle(true);
                }
                if(values.getSquareXYList() != null)
                    mCustomView.clearSquare();
                else {
                    values.setClearedSquare(true);
                }
                if(values.getTriangleXYList() != null)
                    mCustomView.clearTriangle();
                else {
                    values.setClearedTriangle(true);
                }
            }else {
                if (values.getCircleXYList() != null && values.getmCircleCounter() == 0) {
                    mCustomView.clearCircle();
                    values.setClearedCircle(false);
                } else {
                    values.setClearedCircle(true);
                }
                if (values.getSquareXYList() != null && values.getmSquareCounter() == 0) {
                    mCustomView.clearSquare();
                    values.setClearedSquare(false);
                } else {
                    values.setClearedSquare(true);
                }
                if (values.getTriangleXYList() != null && values.getmTriangleCounter() == 0) {
                    mCustomView.clearTriangle();
                    values.setClearedTriangle(false);
                } else {
                    values.setClearedTriangle(true);
                }
            }
            mCustomView.clearshape();
            Toast.makeText(getApplicationContext(), "Canvas Updated!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.action_exit) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
