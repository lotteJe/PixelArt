package com.example.android.pixelart.activities;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.android.pixelart.R;
import com.example.android.pixelart.adapters.DrawingCursorAdapter;
import com.example.android.pixelart.fragments.DrawingFragment;
import com.example.android.pixelart.fragments.LibraryFragment;
import com.example.android.pixelart.fragments.ToolboxFragment;
import com.example.android.pixelart.interfaces.DrawingInterface;
import com.example.android.pixelart.models.Grid;

import static com.example.android.pixelart.persistency.PixelArtDBContract.*;

public class MainActivity extends AppCompatActivity implements DrawingInterface,
        LoaderManager.LoaderCallbacks<Cursor> {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private static final int DRAWING_LOADER = 0;
    private static final int PIXEL_LOADER = 1;

    DrawingCursorAdapter mCursorAdapter;

    private Uri currentDrawingUri;
    private boolean showDrawing;

    private DrawingFragment drawingFragment;
    private ToolboxFragment toolboxFragment;
    private LibraryFragment libraryFragment;

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
        libraryFragment = new LibraryFragment();

        mCursorAdapter = new DrawingCursorAdapter(this, null);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, drawingFragment);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ft.add(R.id.fragment_container, toolboxFragment);
        }
        ft.commit();

        getLoaderManager().initLoader(DRAWING_LOADER, null, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (photo) {
            drawingFragment.drawPhoto(bitmap);
        }
    }

    public Grid getGrid() {
        return this.grid;
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
        if (showDrawing && currentDrawingUri != null) {
            getLoaderManager().initLoader(PIXEL_LOADER, null, this);
            showDrawing = false;
            currentDrawingUri = null;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, drawingFragment);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ft.add(R.id.fragment_container, toolboxFragment);
        }
        ft.addToBackStack(null);
        ft.commit();
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

    @Override
    public void showLibraryFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, libraryFragment);
        ft.addToBackStack(null);
        ft.commit();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 1, menuIconWithText(getResources().getDrawable(R.drawable.ic_save), getResources().getString(R.string.action_save_drawing)));
        menu.add(0, 2, 2, menuIconWithText(getResources().getDrawable(R.drawable.ic_folder), getResources().getString(R.string.action_open_library)));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Name drawing");
                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveDrawing(input.getText().toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
                return true;
            case 2:
                showLibraryFragment();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //source https://stackoverflow.com/a/41569543
    //icons next to text in menu items
    private CharSequence menuIconWithText(Drawable r, String title) {
        r.setBounds(0, 0, r.getIntrinsicWidth(), r.getIntrinsicHeight());
        SpannableString sb = new SpannableString("    " + title);
        ImageSpan imageSpan = new ImageSpan(r, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

    private void saveDrawing(String m_Text) {
        ContentValues values = new ContentValues();
        values.put(DrawingEntry.COLUMN_NAME, m_Text);
        Uri drawing = getContentResolver().insert(DrawingEntry.CONTENT_URI, values);
        values = new ContentValues();
        for (int i = 0; i < this.grid.getGridColumns(); i++) {
            for (int j = 0; j < this.grid.getGridRows(); j++) {
                if (this.grid.isChecked(i, j)) {
                    values.put(PixelEntry.COLUMN_DRAWING_ROW, j);
                    values.put(PixelEntry.COLUMN_DRAWING_COLUMN, i);
                    values.put(PixelEntry.COLUMN_DRAWING_COLOR, this.grid.getColor(i, j));
                    values.put(PixelEntry.COLUMN_DRAWING_ID, drawing.getLastPathSegment());
                    Uri pixel = getContentResolver().insert(PixelEntry.CONTENT_URI, values);
                }
            }
        }
    }


    public DrawingCursorAdapter getmCursorAdapter() {
        return mCursorAdapter;
    }

    @Override
    public void setCurrentDrawingUri(Uri currentDrawingUri) {
        this.currentDrawingUri = currentDrawingUri;
    }

    @Override
    public void setShowDrawing(boolean showDrawing) {
        this.showDrawing = showDrawing;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection;
        switch (id) {
            case 0:
                projection = new String[]{
                        DrawingEntry._ID,
                        DrawingEntry.COLUMN_NAME
                };
                return new CursorLoader(this, DrawingEntry.CONTENT_URI, projection,
                        null, null, null);

            case 1:
                projection = new String[]{
                        PixelEntry._ID,
                        PixelEntry.COLUMN_DRAWING_ROW,
                        PixelEntry.COLUMN_DRAWING_COLUMN,
                        PixelEntry.COLUMN_DRAWING_COLOR,
                        PixelEntry.COLUMN_DRAWING_ID
                };
                String selection = PixelEntry.COLUMN_DRAWING_ID + "=?";
                String[] selectionArgs = {String.valueOf(currentDrawingUri.getLastPathSegment())};
                return new CursorLoader(this, PixelEntry.CONTENT_URI, projection, selection, selectionArgs, null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        switch (loader.getId()) {
            case 0:
                mCursorAdapter.swapCursor(data);
                break;
            case 1:
                this.grid.clearGrid();
                if (data.moveToFirst()) {
                    do {
                        int columnColumnIndex = data.getColumnIndex(PixelEntry.COLUMN_DRAWING_COLUMN);
                        int column = data.getInt(columnColumnIndex);
                        int rowColumnIndex = data.getColumnIndex(PixelEntry.COLUMN_DRAWING_ROW);
                        int row = data.getInt(rowColumnIndex);
                        int colorColumnIndex = data.getColumnIndex(PixelEntry.COLUMN_DRAWING_COLOR);
                        int color = data.getInt(colorColumnIndex);
                        this.grid.setCellChecked(column, row, color);
                    } while (data.moveToNext());
                }
                drawingFragment.drawSavedImage(this.grid);
                getLoaderManager().destroyLoader(PIXEL_LOADER);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}

