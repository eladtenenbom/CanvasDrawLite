package com.example.elad.canvasdrawlite;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by elad on 27/06/2017.
 */

public class myProvider extends ContentProvider {

    public static final String AUTHORIT = "com.example.elad.canvasdrawlite";
    private static final String BASE_PATH = "AllPaints";
    private static final String MYPAINTS_PATH = "MyPaints";
    private static final String DOWNLOADED_PATH = "DownloadedPaints";
    private static final String RECYCLE_PATH = "RecyclePaints";

    public static final Uri CURRENT_URI = Uri.parse("content://" + AUTHORIT + "/" + BASE_PATH);


    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORIT, BASE_PATH, 1);

    }

    public static SQLiteDatabase db;



    @Override
    public boolean onCreate() {
        myDB dbHelper = new myDB(getContext());
        db = dbHelper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String selection, @Nullable String[] strings1, @Nullable String s1) {
        String SelectedTable = uri.getLastPathSegment();

        return db.query(SelectedTable
                , myDB.ALL_COLUMNS
                , selection, null, null, null
                , myDB.PAINT_CREATED + " DESC");
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        String SelectedTable = uri.getLastPathSegment();

        long id = db.insert(SelectedTable, null, contentValues);
        return Uri.parse(BASE_PATH + "/" + id);
    }



    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {


        String SelectedTable = uri.getLastPathSegment();


        int rowsDeleted = db.delete(SelectedTable, selection,selectionArgs);








        return rowsDeleted;


    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {

        int rowsUpdated = db.update(myDB.TABLE_CLIENT_PAINTS, contentValues, selection, selectionArgs);


        return rowsUpdated;
    }


}

