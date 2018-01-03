package com.example.android.pixelart.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.pixelart.R;
import com.example.android.pixelart.persistency.PixelArtDBContract;

/**
 * Created by lottejespers.
 */

public class DrawingCursorAdapter extends CursorAdapter {

    public DrawingCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView nameTv = (TextView) view.findViewById(R.id.name_drawing);

        int nameColumnIndex = cursor.getColumnIndex(PixelArtDBContract.DrawingEntry.COLUMN_NAME);
        String name = cursor.getString(nameColumnIndex);
        nameTv.setText(name);

    }
}
