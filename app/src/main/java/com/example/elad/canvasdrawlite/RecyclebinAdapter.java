package com.example.elad.canvasdrawlite;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by elad on 02/07/2017.
 */

public class RecyclebinAdapter extends CursorAdapter {



        String PaintJson = null;
        String PaintName = null;
        String PaintID = null;
        String PaintCreated = null;

        myProvider Provider1 = new myProvider();
        static Uri ThisTableUri = null;


        public void setThisTableUri(Uri thisTableUri) {
            ThisTableUri = thisTableUri;
        }

        public RecyclebinAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View v = null;

                v = LayoutInflater.from(context).inflate(R.layout.recycler_xml, parent, false);

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

            final ImageButton btnErase = (ImageButton) view.findViewById(R.id.btnErase);
            final ImageButton btnUndelete = (ImageButton) view.findViewById(R.id.btnUndelete);


            btnErase.setTag(PaintID);

            btnUndelete.setTag(PaintID);


            btnErase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context, "Paint Erased", Toast.LENGTH_SHORT).show();

                    Cursor thisPaintCursor = Provider1.query(ThisTableUri,null,"_id = " + v.getTag(),null,null);

                    if (thisPaintCursor.moveToFirst()){
                        do{

                            PaintID = thisPaintCursor.getString(thisPaintCursor.getColumnIndex(myDB.PAINT_ID));
                        }while(thisPaintCursor.moveToNext());
                    }
                    thisPaintCursor.close();


                    Provider1.delete(ThisTableUri,"_id = " + PaintID, null);

                }
            });



            btnUndelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context, "Paint Recycle", Toast.LENGTH_SHORT).show();
                    Cursor thisPaintCursor = Provider1.query(ThisTableUri,null,"_id = " + v.getTag(),null,null);

                    if (thisPaintCursor.moveToFirst()){
                        do{
                            PaintName = thisPaintCursor.getString(thisPaintCursor.getColumnIndex(myDB.PAINT_NAME));
                            PaintJson = thisPaintCursor.getString(thisPaintCursor.getColumnIndex(myDB.PAINT_JSON_SRC));
                            PaintCreated = thisPaintCursor.getString(thisPaintCursor.getColumnIndex(myDB.PAINT_CREATED));
                            PaintID = thisPaintCursor.getString(thisPaintCursor.getColumnIndex(myDB.PAINT_ID));                        }while(thisPaintCursor.moveToNext());
                    }
                    thisPaintCursor.close();

                    InsertPaintToMyPaintsTable(PaintName,PaintJson );

                    Provider1.delete(ThisTableUri,"_id = " + PaintID, null);

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


    private void InsertPaintToMyPaintsTable(String DrawName,String JsonCode){
        ContentValues newPaint = new ContentValues();
        newPaint.put(myDB.PAINT_JSON_SRC, JsonCode);
        newPaint.put(myDB.PAINT_NAME, DrawName);

        Uri ThisUri = Uri.parse(myProvider.CURRENT_URI.toString()+"/"+ myDB.TABLE_CLIENT_PAINTS);
        myProvider c1 = new myProvider();
        c1.insert(ThisUri, newPaint);
    }
    }


