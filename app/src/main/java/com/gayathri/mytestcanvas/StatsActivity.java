package com.gayathri.mytestcanvas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class StatsActivity extends AppCompatActivity {

    TextView tvCircleCounter, tvSquareCounter, tvTriangleCounter;
    CheckBox cbSquare, cbCircle, cbTriangle;

    public int mCircleCounter;
    public int mSquareCounter;
    public int mTriangleCounter;

    Boolean isSquareSelected = false;
    Boolean isCircleSelected = false;
    Boolean isTriangleSelected = false;
    Boolean isAnyCheckBoxSelected = false;

    CounterValues values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        tvCircleCounter = findViewById(R.id.tv_CircleCount);
        tvSquareCounter = findViewById(R.id.tv_SquareCount);
        tvTriangleCounter = findViewById(R.id.tv_TriangleCount);

        cbCircle = findViewById(R.id.checkbox_Circle);
        cbSquare = findViewById(R.id.checkbox_Square);
        cbTriangle = findViewById(R.id.checkbox_Triangle);

        values = CounterValues.getInstance();

        mCircleCounter = values.getmCircleCounter();
        mSquareCounter = values.getmSquareCounter();
        mTriangleCounter = values.getmTriangleCounter();

        tvCircleCounter.setText(String.valueOf(mCircleCounter));
        tvSquareCounter.setText(String.valueOf(mSquareCounter));
        tvTriangleCounter.setText(String.valueOf(mTriangleCounter));

        if((tvCircleCounter.getText()).equals("0")){
            cbCircle.setEnabled(false);
        }

        if((tvSquareCounter.getText()).equals("0")){
            cbSquare.setEnabled(false);
        }

        if((tvTriangleCounter.getText()).equals("0")){
            cbTriangle.setEnabled(false);
        }

        cbCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cbCircle.isChecked()) {
                    isAnyCheckBoxSelected = true;
                    isCircleSelected = true;
                }
            }
        });

        cbSquare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cbSquare.isChecked()) {
                    isAnyCheckBoxSelected = true;
                    isSquareSelected = true;
                }
            }
        });

        cbTriangle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cbTriangle.isChecked()) {
                    isAnyCheckBoxSelected = true;
                    isTriangleSelected = true;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.statsmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            if(isAnyCheckBoxSelected == true) {
                if (isCircleSelected == true) {
                    if(cbCircle.isChecked()) {
                        tvCircleCounter.setText("0");
                        mCircleCounter = 0;
                        isCircleSelected = false;
                        cbCircle.setChecked(false);
                        cbCircle.setEnabled(false);
                        values.setmCircleCounter(0);
                        values.setGetCircle("Circle");
                    }
                }
                if (isSquareSelected == true) {
                    if(cbSquare.isChecked()) {
                        tvSquareCounter.setText("0");
                        mSquareCounter = 0;
                        isSquareSelected = false;
                        cbSquare.setChecked(false);
                        cbSquare.setEnabled(false);
                        values.setmSquareCounter(0);
                        values.setGetSquare("Square");
                    }
                }
                if (isTriangleSelected == true) {
                    if(cbTriangle.isChecked()) {
                        tvTriangleCounter.setText("0");
                        mTriangleCounter = 0;
                        isTriangleSelected = false;
                        cbTriangle.setChecked(false);
                        cbTriangle.setEnabled(false);
                        values.setmTriangleCounter(0);
                        values.setGetTriangle("Triangle");
                    }
                }
                isAnyCheckBoxSelected = false;
            } else
                Toast.makeText(getApplicationContext(), "Please select shapes to delete!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
