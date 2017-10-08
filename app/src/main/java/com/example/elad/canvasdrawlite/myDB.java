package com.example.elad.canvasdrawlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by elad on 27/06/2017.
 */

public class myDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "PaintsDB";
    private static final int DATABASE_VERSION = 6;

    public static final String TABLE_CLIENT_PAINTS = "MyPaints";
    public static final String TABLE_DOWNLOADED_PAINTS = "DownloadedPaints";
    public static final String TABLE_RECYCLE_PAINTS = "RecyclePaints";
    public static final String PAINT_ID = "_id";
    public static final String PAINT_JSON_SRC = "JsonCode";
    public static final String PAINT_CREATED = "PaintCreated";
    public static final String PAINT_NAME = "PaintName";


    public static final String[] ALL_COLUMNS = { PAINT_ID, PAINT_JSON_SRC, PAINT_CREATED,PAINT_NAME};

    public static final String TABLE_CREATE =  " (" +
            PAINT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PAINT_JSON_SRC + " TEXT, " +
            PAINT_NAME + " TEXT, " +
            PAINT_CREATED + " TEXT default CURRENT_TIMESTAMP" + ")";



    public myDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_CLIENT_PAINTS +TABLE_CREATE);
        db.execSQL("CREATE TABLE " + TABLE_DOWNLOADED_PAINTS +TABLE_CREATE);
        db.execSQL("CREATE TABLE " + TABLE_RECYCLE_PAINTS +TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENT_PAINTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOWNLOADED_PAINTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECYCLE_PAINTS);
        onCreate(db);
    }
}


