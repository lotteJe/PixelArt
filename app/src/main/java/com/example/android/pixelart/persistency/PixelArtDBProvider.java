package com.example.android.pixelart.persistency;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.PixelCopy;

import com.example.android.pixelart.interfaces.DrawingInterface;

import java.sql.SQLInput;

import static com.example.android.pixelart.persistency.PixelArtDBContract.*;

/**
 * Created by lottejespers.
 */

public class PixelArtDBProvider extends ContentProvider {

    public static final String LOG_TAG = PixelArtDBProvider.class.getSimpleName();

    private static final int DRAWINGS = 100;
    private static final int DRAWING_ID = 101;

    private static final int PIXELS = 200;
    private static final int PIXEL_ID = 201;

    private PixelArtDBHelper mDbHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_PIXELART_DRAWING, DRAWINGS);
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_PIXELART_DRAWING + "/#", DRAWING_ID);

        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_PIXELART_PIXEL, PIXELS);
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_PIXELART_PIXEL + "/#", PIXEL_ID);

    }

    @Override
    public boolean onCreate() {
        mDbHelper = new PixelArtDBHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case DRAWINGS:
                return DrawingEntry.CONTENT_LIST_TYPE;
            case DRAWING_ID:
                return DrawingEntry.CONTENT_ITEM_TYPE;
            case PIXELS:
                return PixelEntry.CONTENT_LIST_TYPE;
            case PIXEL_ID:
                return PixelEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }

    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case DRAWINGS:
                cursor = db.query(DrawingEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case DRAWING_ID:
                selection = DrawingEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(DrawingEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case PIXELS:
                cursor = db.query(PixelEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case PIXEL_ID:
                selection = PixelEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(PixelEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long id;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case DRAWINGS:
                id = db.insert(DrawingEntry.TABLE_NAME, null, values);
                if (id == -1) {
                    Log.e(LOG_TAG, "Failed to insert row for " + uri);
                    return null;
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            case PIXELS:
                id = db.insert(PixelEntry.TABLE_NAME, null, values);
                if (id == -1) {
                    Log.e(LOG_TAG, "Failer to insert rox for " + uri);
                    return null;
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            default:
                throw new IllegalArgumentException("Insertion is not supperted for " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case DRAWINGS:
                // delete all rows that match
                rowsDeleted = db.delete(DrawingEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case DRAWING_ID:
                //delete single drawing
                selection = DrawingEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(DrawingEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case PIXELS:
                rowsDeleted = db.delete(PixelEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case PIXEL_ID:
                selection = PixelEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(PixelEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        //if 1 or more rows were deleted, notify alle listener that data has changes at the given uri
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        //how many rows were deleted
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rowsUpdated;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case DRAWINGS:
                rowsUpdated = db.update(DrawingEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            case DRAWING_ID:
                selection = DrawingEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsUpdated = db.update(DrawingEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            case PIXELS:
                rowsUpdated = db.update(PixelEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            case PIXEL_ID:
                selection = DrawingEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsUpdated = db.update(PixelEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }
}
