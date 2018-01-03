package com.example.android.pixelart.utils;

/**
 * Created by lottejespers.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.android.pixelart.models.Grid;


public class CanvasView extends View {

    private Grid grid;
    private int gridColumns;
    private int gridRows;
    private int cellWidth, cellHeight;
    private Paint paint = new Paint();
    private Paint paintBlack = new Paint();
    private String drawStyle;
    private int columnBegin;
    private int rowBegin;
    private Grid drawing;
    private int color;

    public CanvasView(Context context) {
        this(context, null);
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setDrawStyle(String drawStyle) {
        this.drawStyle = drawStyle;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
        this.gridColumns = grid.getGridColumns();
        this.gridRows = grid.getGridRows();
        calculateDimensions();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        calculateDimensions();
    }

    private void calculateDimensions() {
        if (this.gridColumns < 1 || this.gridRows < 1) {
            return;
        }

        this.cellWidth = getWidth() / this.gridColumns;
        this.cellHeight = getHeight() / this.gridRows;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        if (this.gridColumns == 0 || this.gridRows == 0) {
            return;
        }

        int width = getWidth();
        int height = getHeight();

        // Girdlijnen op canvas
        for (int i = 1; i < this.gridColumns; i++) {
            canvas.drawLine(i * this.cellWidth, 0, i * this.cellWidth, height, paintBlack);
        }

        for (int i = 1; i < this.gridRows; i++) {
            canvas.drawLine(0, i * this.cellHeight, width, i * this.cellHeight, paintBlack);
        }
        // Cell inkleuren
        for (int i = 0; i < this.gridColumns; i++) {
            for (int j = 0; j < this.gridRows; j++) {
                if (this.grid.isChecked(i, j)) {
                    this.paint.setColor(this.grid.getColor(i, j));
                    canvas.drawRect((float) (this.cellWidth * i), (float) (this.cellHeight * j), (float) ((i + 1) * this.cellWidth), (float) ((j + 1) * this.cellHeight), this.paint);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int column = (int) (event.getX() / ((float) this.cellWidth));
        int row = (int) (event.getY() / ((float) this.cellHeight));
        switch (this.drawStyle) {
            case "ic_square":
                //0: action down
                //2: action move
                switch (event.getAction()) {
                    case 0:
                        this.columnBegin = column;
                        this.rowBegin = row;
                        copyMomentDrawing();
                        cellCheck(column, row);
                        invalidate();
                        break;
                    case 2:
                        drawMomentDrawing();
                        invalidate();
                        drawLine(this.columnBegin, this.rowBegin, column, row);
                        invalidate();
                        break;
                    default:
                        break;
                }
                break;
            case "free":
                switch (event.getAction()) {
                    case 0:
                        cellCheck(column, row);
                        invalidate();
                        break;
                    case 2:
                        cellCheck(column, row);
                        invalidate();
                        break;
                    default:
                        break;
                }
                break;
            case "square":
                switch (event.getAction()) {
                    case 0:
                        this.columnBegin = column;
                        this.rowBegin = row;
                        copyMomentDrawing();
                        cellCheck(column, row);
                        invalidate();
                        break;
                    case 2:
                        drawMomentDrawing();
                        invalidate();
                        drawSquare(this.columnBegin, this.rowBegin, column, row);
                        invalidate();
                        break;
                    default:
                        break;
                }
                break;
            case "erase":
                switch (event.getAction()) {
                    case 0:
                        clear(column, row);
                        invalidate();
                        break;
                    case 2:
                        clear(column, row);
                        invalidate();
                        break;
                    default:
                        break;
                }
        }
        return true;
    }


    private void drawSquare(int columnBegin, int rowBegin, int columnEnd, int rowEnd) {
        if (columnEnd >= 0 && rowEnd >= 0 && columnEnd < this.gridColumns && rowEnd < this.gridRows) {
            int offset = Math.max(Math.abs(columnEnd - columnBegin), Math.abs(rowEnd - rowBegin));
            int columnMin = Math.max(columnBegin - offset, 0);
            int columnMax = Math.min(columnBegin + offset, this.gridColumns - 1);
            int rowMin = Math.max(rowBegin - offset, 0);
            int rowMax = Math.min(rowBegin + offset, this.gridRows - 1);
            for (int x = columnMin; x <= columnMax; x++) {
                this.grid.setCellChecked(x, rowMin, this.color);
                this.grid.setCellChecked(x, rowMax, this.color);
            }
            for (int y = rowMin; y <= rowMax; y++) {
                this.grid.setCellChecked(columnMin, y, this.color);
                this.grid.setCellChecked(columnMax, y, this.color);
            }
        }
    }


    private void drawLine(int columnBegin, int rowBegin, int columnEnd, int rowEnd) {
        if (columnEnd >= 0 && rowEnd >= 0 && columnEnd < this.gridColumns && rowEnd < this.gridRows) {
            int dx = columnEnd - columnBegin;
            int dy = rowEnd - rowBegin;
            int y;
            int x;
            if (dx > 0) {
                if (Math.abs(dy) > Math.abs(dx) && dy > 0) {
                    for (y = rowBegin; y < rowEnd; y++) {
                        this.grid.setCellChecked(columnBegin + (((y - rowBegin) * dx) / dy), y, this.color);
                    }
                } else if (Math.abs(dy) <= Math.abs(dx) || dy >= 0) {
                    for (x = columnBegin; x <= columnEnd; x++) {
                        this.grid.setCellChecked(x, rowBegin + (((x - columnBegin) * dy) / dx), this.color);
                    }
                } else {
                    for (y = rowBegin; y > rowEnd; y--) {
                        this.grid.setCellChecked(columnBegin + (((y - rowBegin) * dx) / dy), y, this.color);
                    }
                }
            } else if (dx < 0) {
                if (Math.abs(dy) > Math.abs(dx) && dy > 0) {
                    for (y = rowBegin; y < rowEnd; y++) {
                        this.grid.setCellChecked(columnBegin + (((y - rowBegin) * dx) / dy), y, this.color);
                    }
                } else if (Math.abs(dy) <= Math.abs(dx) || dy >= 0) {
                    for (x = columnBegin; x >= columnEnd; x--) {
                        this.grid.setCellChecked(x, rowBegin + (((x - columnBegin) * dy) / dx), this.color);
                    }
                } else {
                    for (y = rowBegin; y > rowEnd; y--) {
                        this.grid.setCellChecked(columnBegin + (((y - rowBegin) * dx) / dy), y, this.color);
                    }
                }
            } else if (dx != 0) {
            } else {
                if (dy > 0) {
                    for (y = rowBegin; y <= rowEnd; y++) {
                        this.grid.setCellChecked(columnBegin, y, this.color);
                    }
                } else if (dy < 0) {
                    for (y = rowBegin; y >= rowEnd; y--) {
                        this.grid.setCellChecked(columnBegin, y, this.color);
                    }
                }
            }
        }
    }

    private void cellCheck(int column, int row) {
        if (column >= 0 && row >= 0 && column < this.gridColumns && row < this.gridRows) {
            this.grid.setCellChecked(column, row, this.color);
        }
    }

    private void clear(int column, int row) {
        if (column >= 0 && row >= 0 && column < this.gridColumns && row < this.gridRows) {
            this.grid.clear(column, row);
        }
    }

    public void clearCanvasDrawing() {
        for (int i = 0; i < this.gridColumns; i++) {
            for (int j = 0; j < this.gridRows; j++) {
                this.grid.clear(i, j);
            }
        }
        invalidate();
    }

    private void copyMomentDrawing() {
        this.drawing = new Grid(gridColumns, gridRows);
        for (int i = 0; i < this.gridColumns; i++) {
            for (int j = 0; j < this.gridRows; j++) {
                this.drawing.setCellChecked(i, j, this.grid.getColor(i, j));
            }
        }
    }

    private void drawMomentDrawing() {
        for (int i = 0; i < this.gridColumns; i++) {
            for (int j = 0; j < this.gridRows; j++) {
                if (!this.drawing.isChecked(i, j)) {
                    clear(i, j);
                }
            }
        }
        invalidate();
    }

    public void drawPhoto(Bitmap bitmap) {
        if (bitmap.getWidth() > this.gridColumns || bitmap.getHeight() > this.gridRows) {
            return;
        }
        for (int i = 0; i < bitmap.getWidth(); i++) {
            for (int j = 0; j < bitmap.getHeight(); j++) {
                this.grid.setCellChecked(i, j, bitmap.getPixel(i, j));
            }
        }
        invalidate();
    }

    public void drawSavedImage(Grid grid) {
        if (grid.getGridColumns() > this.gridColumns || grid.getGridRows() > this.gridRows) {
            return;
        }
        this.grid = grid;
        invalidate();
    }
}