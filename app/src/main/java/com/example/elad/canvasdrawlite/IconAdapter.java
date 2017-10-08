package com.example.elad.canvasdrawlite;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by elad on 27/06/2017.
 */

public class IconAdapter extends CursorAdapter {


     String PaintJson = null;
     String PaintName = null;
     String PaintID = null;
     String PaintCreated = null;

    myProvider Provider1 = new myProvider();
    static Uri ThisTableUri = null;


    public void setThisTableUri(Uri thisTableUri) {
        ThisTableUri = thisTableUri;
    }

    public IconAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View v = null;
         if (ThisTableUri.getLastPathSegment().equals(myDB.TABLE_CLIENT_PAINTS)) {
             v = LayoutInflater.from(context).inflate(R.layout.iconadapter_xml, parent, false);
         }
            if (ThisTableUri.getLastPathSegment().equals(myDB.TABLE_DOWNLOADED_PAINTS)) {
                v = LayoutInflater.from(context).inflate(R.layout.downloadedadapter_xml, parent, false);
            }
            if (ThisTableUri.getLastPathSegment().equals(myDB.TABLE_RECYCLE_PAINTS)) {
                v = LayoutInflater.from(context).inflate(R.layout.iconadapter_xml, parent, false);
            }
            return v;
        }


    @Override
        public void bindView(View view, final Context context, Cursor cursor) {

            try {
                PaintJson = cursor
                        .getString(cursor.getColumnIndex(myDB.PAINT_JSON_SRC));
                PaintID = cursor
                        .getString(cursor.getColumnIndex(myDB.PAINT_ID));
                PaintName = cursor
                        .getString(cursor.getColumnIndex(myDB.PAINT_NAME));
                PaintCreated = cursor
                        .getString(cursor.getColumnIndex(myDB.PAINT_CREATED));
            } catch (Exception e) {
                e.printStackTrace();
            }

            String PaintData = " Name: " + PaintName + " ,  Creation Date -  \n"+PaintCreated;



        myDB db = new myDB(context);
        db.getWritableDatabase();

            TextView tvPaintName= (TextView)view.findViewById(R.id.tvNamePaint);

            TextView tvID= (TextView)view.findViewById(R.id.tvID);
           ImageView img1 = (ImageView)view.findViewById(R.id.img1);

            final ImageButton btnDelete = (ImageButton) view.findViewById(R.id.btnDelete);
        final ImageButton btnUpload = (ImageButton) view.findViewById(R.id.btnUpload);
        final ImageButton btnShowCode = (ImageButton) view.findViewById(R.id.btnShowCode);
        final ImageButton btnEdit = (ImageButton) view.findViewById(R.id.btnEdit);

        btnDelete.setTag(PaintID);
        try {
            btnUpload.setTag(PaintID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        btnShowCode.setTag(PaintID);
        btnEdit.setTag(PaintID);


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(context, "Delete Paint", Toast.LENGTH_SHORT).show();

                Cursor thisPaintCursor = Provider1.query(ThisTableUri,null,"_id = " + v.getTag(),null,null);

                if (thisPaintCursor.moveToFirst()){
                    do{
                        PaintName = thisPaintCursor.getString(thisPaintCursor.getColumnIndex(myDB.PAINT_NAME));
                        PaintJson = thisPaintCursor.getString(thisPaintCursor.getColumnIndex(myDB.PAINT_JSON_SRC));
                        PaintCreated = thisPaintCursor.getString(thisPaintCursor.getColumnIndex(myDB.PAINT_CREATED));
                        PaintID = thisPaintCursor.getString(thisPaintCursor.getColumnIndex(myDB.PAINT_ID));
                    }while(thisPaintCursor.moveToNext());
                }
                thisPaintCursor.close();

                InsertPaintToRecycleTable(PaintName ,PaintJson);

                Provider1.delete(ThisTableUri,"_id = " + PaintID, null);

            }
        });

        try {
            btnUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context, "Upload Paint Online...", Toast.LENGTH_SHORT).show();

                    Cursor thisPaintCursor = Provider1.query(ThisTableUri,null,"_id = " + v.getTag(),null,null);

                    if (thisPaintCursor.moveToFirst()){
                        do{
                            PaintName = thisPaintCursor.getString(thisPaintCursor.getColumnIndex(myDB.PAINT_NAME));
                            PaintJson = thisPaintCursor.getString(thisPaintCursor.getColumnIndex(myDB.PAINT_JSON_SRC));
                            PaintCreated = thisPaintCursor.getString(thisPaintCursor.getColumnIndex(myDB.PAINT_CREATED));
                            PaintID = thisPaintCursor.getString(thisPaintCursor.getColumnIndex(myDB.PAINT_ID));
                        }while(thisPaintCursor.moveToNext());
                    }
                    thisPaintCursor.close();


                    MyPaintObject thisPaint = new MyPaintObject(PaintJson,PaintID,PaintCreated,PaintName);
                    OnlinePaintsList.myRef.child("Paints").push().setValue(thisPaint);


                }
            });
        } catch (Exception e) {


        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "Edit paint", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,DrawActivity.class);

                Cursor thisPaintCursor = Provider1.query(ThisTableUri,null,"_id = " + v.getTag(),null,null);

                if (thisPaintCursor.moveToFirst()){
                    do{
                        PaintJson = thisPaintCursor.getString(thisPaintCursor.getColumnIndex(myDB.PAINT_JSON_SRC));
                    }while(thisPaintCursor.moveToNext());
                }
                thisPaintCursor.close();

               intent.putExtra("JSON",PaintJson);


                context.startActivity(intent);
            }
        });

        btnShowCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "Code for Android.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,ShowCodeActivity.class);

                Cursor thisPaintCursor = Provider1.query(ThisTableUri,null,"_id = " + v.getTag(),null,null);

                if (thisPaintCursor.moveToFirst()){
                    do{
                        PaintJson = thisPaintCursor.getString(thisPaintCursor.getColumnIndex(myDB.PAINT_JSON_SRC));
                    }while(thisPaintCursor.moveToNext());
                }
                thisPaintCursor.close();

                intent.putExtra("JSON",PaintJson);

                context.startActivity(intent);
            }
        });


            tvID.setText(cursor.getPosition()+1+"");

            tvPaintName.setText(PaintData);



            int Width = MainActivity.W;
            int Height = MainActivity.H;

            DrawerClass v = new DrawerClass(context);

            Bitmap bitmap = Bitmap.createBitmap(Width,Height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);


            //canvas.drawCircle(0,0,500,new Paint());

           v.onJsonDraw(canvas,PaintJson);

            img1.setImageBitmap(bitmap);

        //    tvCardata.setText(CarData);



    //        new DownloadImageTask(CarPic).execute(CarUrl);





            // Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);
            // ImageLoader.getInstance().displayImage(str, iv);


   //         MainActivity.test++;
        }

    private void InsertPaintToRecycleTable(String DrawName,String JsonCode){
        ContentValues newPaint = new ContentValues();
        newPaint.put(myDB.PAINT_JSON_SRC, JsonCode);
        newPaint.put(myDB.PAINT_NAME, DrawName);

        Uri ThisUri = Uri.parse(myProvider.CURRENT_URI.toString()+"/"+ myDB.TABLE_RECYCLE_PAINTS);
        myProvider c1 = new myProvider();
        c1.insert(ThisUri, newPaint);
    }
}
