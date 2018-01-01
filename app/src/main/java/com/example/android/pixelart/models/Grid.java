package com.example.android.pixelart.models;

/**
 * Created by lottejespers on 1/01/18.
 */

public class Grid {
    private boolean[][] grid;
    private int gridRows;
    private int gridColumns;


    public Grid(int columns, int rows) {
        setGridRows(rows);
        setGridColumns(columns);
        grid = new boolean[columns][rows];
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                grid[i][j] = false;
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
        grid[column][row] = false;
    }

    public boolean isChecked(int i, int j) {
        return grid[i][j];
    }

    public void setCellChecked(boolean check, int i, int j) {
        grid[i][j] = check;
    }
}
