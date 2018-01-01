package com.example.android.pixelart.interfaces;

import android.content.res.Configuration;
import android.support.v4.app.FragmentTransaction;

import com.example.android.pixelart.models.Grid;

/**
 * Created by lottejespers.
 */

public interface DrawingInterface {

    void showDrawingFragment();

    void showToolboxFragment();

    Grid getGrid();

    void setGrid(Grid grid);

    int getColor();

    void setColor(int color);

    String getDrawStyle();

    void setDrawStyle(String drawStyle);

    void dispatchTakePictureIntent();
}
