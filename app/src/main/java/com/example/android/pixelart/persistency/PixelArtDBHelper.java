package com.example.android.pixelart.persistency;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import static com.example.android.pixelart.persistency.PixelArtDBContract.*;

/**
 * Created by lottejespers.
 */

public class PixelArtDBHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = PixelArtDBHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "pixelart.db";

    private static final int DATABASE_VERSION = 1;

    public PixelArtDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_DRAWING_TABLE = "CREATE TABLE " + DrawingEntry.TABLE_NAME + " ("
                + DrawingEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DrawingEntry.COLUMN_NAME + " TEXT NOT NULL);";


        String SQL_CREATE_PIXEL_TABLE = "CREATE TABLE " + PixelEntry.TABLE_NAME + " ("
                + PixelEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PixelEntry.COLUMN_DRAWING_ROW + " INTEGER NOT NULL, "
                + PixelEntry.COLUMN_DRAWING_COLUMN + " INTEGER NOT NULL, "
                + PixelEntry.COLUMN_DRAWING_COLOR + " INTEGER NOT NULL, "
                + PixelEntry.COLUMN_DRAWING_ID + " INTEGER, FOREIGN KEY ("
                + PixelEntry.COLUMN_DRAWING_ID + ") REFERENCES " + DrawingEntry.TABLE_NAME + "(id) ON DELETE CASCADE );";

        db.execSQL(SQL_CREATE_DRAWING_TABLE);
        Log.i(LOG_TAG, "Drawing table created");
        db.execSQL(SQL_CREATE_PIXEL_TABLE);
        Log.i(LOG_TAG, "Pixel table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
