package com.example.android.pixelart.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.pixelart.R;
import com.example.android.pixelart.fragments.DrawingFragment;
import com.example.android.pixelart.fragments.ToolboxFragment;
import com.example.android.pixelart.interfaces.DrawingInterface;
import com.example.android.pixelart.models.Grid;
import com.example.android.pixelart.utils.CanvasView;

public class MainActivity extends AppCompatActivity implements DrawingInterface {

    private DrawingFragment drawingFragment;
    private ToolboxFragment toolboxFragment;

    private Grid grid;
    private int color = Color.BLUE;
    private String drawStyle = "free";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.grid = new Grid(26, 26);

        drawingFragment = new DrawingFragment();
        toolboxFragment = new ToolboxFragment();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, drawingFragment);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ft.add(R.id.fragment_container, toolboxFragment);
        }
        ft.commit();
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        drawingFragment.updateView();
    }

    public String getDrawStyle() {
        return drawStyle;
    }

    public void setDrawStyle(String drawStyle) {
        this.drawStyle = drawStyle;
        drawingFragment.updateView();
    }

    @Override
    public void showDrawingFragment() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, drawingFragment);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

    @Override
    public void showToolboxFragment() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, toolboxFragment);
            ft.addToBackStack(null);
            ft.commit();
        }
    }
}

