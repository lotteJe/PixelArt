package com.example.android.pixelart.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lottejespers.
 */

public class Grid {
    private int[][] grid;
    private int gridRows;
    private int gridColumns;

    public Grid(int columns, int rows) {
        setGridRows(rows);
        setGridColumns(columns);
        grid = new int[columns][rows];
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                grid[i][j] = 0;
            }
        }
    }

    public int getGridRows() {
        return gridRows;
    }

    public void setGridRows(int gridRows) {
        this.gridRows = gridRows;
    }

    public int getGridColumns() {
        return gridColumns;
    }

    public void setGridColumns(int gridColumns) {
        this.gridColumns = gridColumns;
    }

    public void clear(int column, int row) {
        grid[column][row] = 0;
    }

    public boolean isChecked(int column, int row) {
        return grid[column][row] != 0;
    }

    public void setCellChecked(int i, int j, int color) {
        grid[i][j] = color;
    }

    public int getColor(int column, int row) {
        return grid[column][row];
    }

    public void clearGrid() {
        for (int i = 0; i < gridColumns; i++) {
            for (int j = 0; j < gridRows; j++) {
                grid[i][j] = 0;
            }
        }
    }
}
