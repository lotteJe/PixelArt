package com.example.android.pixelart.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
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

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private DrawingFragment drawingFragment;
    private ToolboxFragment toolboxFragment;

    private Grid grid;
    private int color = Color.BLUE;
    private String drawStyle = "free";
    private Bitmap bitmap;
    private boolean photo;

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

    @Override
    protected void onStart() {
        super.onStart();
        if(photo){
            drawingFragment.drawPhoto(bitmap);
        }
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

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            bitmap = getResizedBitmap(this.bitmap, 26, 26);

        }
        this.photo = true;
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / ((float) width);
        float scaleHeight = ((float) newHeight) / ((float) height);
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
}

