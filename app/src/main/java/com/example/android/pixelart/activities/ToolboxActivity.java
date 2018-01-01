package com.example.android.pixelart.activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.android.pixelart.R;

public class ToolboxActivity extends AppCompatActivity {

    private Button greenBtn;
    private Button blueBtn;
    private Button redBtn;
    private Button purpleBtn;
    private Button squareBtn;
    private Button lineBtn;
    private Button freeBtn;
    private String drawStyle = "free";
    private int color = Color.BLACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbox);

        greenBtn = (Button) findViewById(R.id.greenBtn);
        greenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = Color.GREEN;
                setPreferences();
            }
        });
        blueBtn = (Button) findViewById(R.id.blueBtn);
        blueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = Color.BLUE;
                setPreferences();
            }
        });
        redBtn = (Button) findViewById(R.id.redBtn);
        redBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = Color.RED;
                setPreferences();
            }
        });
        purpleBtn = (Button) findViewById(R.id.purpleBtn);
        purpleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = Color.parseColor("#8A2BE2");
                setPreferences();
            }
        });
        squareBtn = (Button) findViewById(R.id.squareBtn);
        squareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawStyle = "square";
                setPreferences();
            }
        });
        lineBtn = (Button) findViewById(R.id.lineBtn);
        lineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawStyle = "line";
                setPreferences();
            }
        });
        freeBtn = (Button) findViewById(R.id.freeBtn);
        freeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawStyle = "free";
                setPreferences();
            }
        });

    }

    private void setPreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("color", color);
        editor.putString("drawStyle", drawStyle);
        editor.apply();
    }
}
