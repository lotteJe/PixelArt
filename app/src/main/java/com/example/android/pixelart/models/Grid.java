package com.example.android.pixelart.models;

/**
 * Created by lottejespers.
 */

public class Grid {
    private boolean[][] grid;
    private int gridRows;
    private int gridColumns;
    private int[][] colorGrid;


    public Grid(int columns, int rows) {
        setGridRows(rows);
        setGridColumns(columns);
        colorGrid = new int[columns][rows];
        grid = new boolean[columns][rows];
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                grid[i][j] = false;
                colorGrid[i][j] = 0;
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

    public void setCellChecked(boolean check, int i, int j, int color) {
        grid[i][j] = check;
        colorGrid[i][j] = color;
    }

    public int getColor(int column, int row) {
        return colorGrid[column][row];
    }
}
