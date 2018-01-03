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

        findViewById(R.id.greenBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = Color.GREEN;
                setPreferences();
            }
        });
        findViewById(R.id.blueBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = Color.BLUE;
                setPreferences();
            }
        });
        findViewById(R.id.redBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = Color.RED;
                setPreferences();
            }
        });
        findViewById(R.id.purpleBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = Color.parseColor("#8A2BE2");
                setPreferences();
            }
        });
        findViewById(R.id.squareBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawStyle = "square";
                setPreferences();
            }
        });
        findViewById(R.id.lineBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawStyle = "ic_square";
                setPreferences();
            }
        });
        findViewById(R.id.freeBtn).setOnClickListener(new View.OnClickListener() {
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
