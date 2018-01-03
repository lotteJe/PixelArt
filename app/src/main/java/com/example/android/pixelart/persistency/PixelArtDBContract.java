package com.example.android.pixelart.persistency;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;
import android.widget.BaseAdapter;

/**
 * Created by lottejespers.
 */

public class PixelArtDBContract {

    public PixelArtDBContract() {
    }

    public static final String CONTENT_AUTHORITY = "com.example.android.pixelart";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_PIXELART_DRAWING = "drawings";
    public static final String PATH_PIXELART_PIXEL = "pixels";

    public static final class DrawingEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PIXELART_DRAWING);

        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH_PIXELART_DRAWING;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH_PIXELART_DRAWING;

        public static final String TABLE_NAME = "drawings";

        public static final String _ID = BaseColumns._ID;

        public static final String COLUMN_NAME = "name";
    }

    public static final class PixelEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PIXELART_PIXEL);

        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH_PIXELART_PIXEL;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH_PIXELART_PIXEL;

        public static final String TABLE_NAME = "pixels";

        public static final String _ID = BaseColumns._ID;

        public static final String COLUMN_DRAWING_ID = "drawingId";
        public static final String COLUMN_DRAWING_ROW = "row";
        public static final String COLUMN_DRAWING_COLUMN = "column";
        public static final String COLUMN_DRAWING_COLOR = "color";
    }
}
