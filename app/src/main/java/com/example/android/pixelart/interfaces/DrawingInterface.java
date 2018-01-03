package com.example.android.pixelart.interfaces;

import android.net.Uri;

import com.example.android.pixelart.adapters.DrawingCursorAdapter;
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

    void showLibraryFragment();

    DrawingCursorAdapter getmCursorAdapter();

    void setCurrentDrawingUri(Uri currentDrawingUri);

    void setShowDrawing(boolean showDrawing);
}
