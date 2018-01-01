package com.example.android.pixelart.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.pixelart.R;
import com.example.android.pixelart.models.Grid;
import com.example.android.pixelart.utils.CanvasView;

public class MainActivity extends AppCompatActivity {

    private CanvasView canvasView;
    private Button toolboxBtn;
    private Button clearBtn;
    private Grid grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        canvasView = (CanvasView) findViewById(R.id.canvas);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int color = preferences.getInt("color", Color.BLACK);
        String drawStyle = preferences.getString("drawStyle", "free");

        canvasView.setColor(color);
        canvasView.setDrawStyle(drawStyle);

        this.grid = new Grid(40, 40);

        canvasView.setGrid(this.grid);
        toolboxBtn = (Button) findViewById(R.id.toolboxBtn);
        toolboxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ToolboxActivity.class);
                startActivity(intent);
            }
        });
        clearBtn = (Button) findViewById(R.id.clearBtn);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canvasView.clearCanvasDrawing();
            }
        });
    }
}

