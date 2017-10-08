package com.example.elad.canvasdrawlite;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by elad on 02/07/2017.
 */

public class FireBaseAdapter extends ArrayAdapter<MyPaintObject>{


    public FireBaseAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View myView = LayoutInflater.from(getContext()).inflate(R.layout.firebaseadapter_xml,parent,false);
        MyPaintObject obj1 = getItem(position);


        ImageButton btnDownload = (ImageButton) myView.findViewById(R.id.btnDownloadPaint);
        String PaintID = obj1.getId();
        String PaintCreated = obj1.getTime();
        String PaintJson = obj1.getJsoncode();

        String PaintName = obj1.getName();

        String PaintData = " Name: " + PaintName + " ,  Creation Date -  \n"+PaintCreated;

        TextView tvPaintdata= (TextView)myView.findViewById(R.id.tvNamePaint);
        TextView tvID= (TextView)myView.findViewById(R.id.tvID);
        ImageView img1 =(ImageView)myView.findViewById(R.id.img1) ;

        tvID.setText(PaintID);

        tvPaintdata.setText(PaintData);



        int Width = MainActivity.W;
        int Height = MainActivity.H;



        DrawerClass v = new DrawerClass(getContext());

        Bitmap bitmap = Bitmap.createBitmap(Width,Height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);


        //canvas.drawCircle(0,0,500,new Paint());

        v.onJsonDraw(canvas,PaintJson);
        img1.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));


        img1.setImageBitmap(Bitmap.createScaledBitmap(bitmap, img1.getMeasuredWidth(), img1.getMeasuredHeight(), false));

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Download file...", Toast.LENGTH_SHORT).show();
                MyPaintObject thisPaint = OnlinePaintsList.MyOnlinePaintsList.get(position);
                InsertPaintToDownloadedTable(thisPaint.getName(),thisPaint.getJsoncode());

            }
        });


        return myView;
    }

    private void InsertPaintToDownloadedTable(String DrawName,String JsonCode){
        ContentValues newPaint = new ContentValues();
        newPaint.put(myDB.PAINT_JSON_SRC, JsonCode);
        newPaint.put(myDB.PAINT_NAME, DrawName);

        Uri ThisUri = Uri.parse(myProvider.CURRENT_URI.toString()+"/"+ myDB.TABLE_DOWNLOADED_PAINTS);
        myProvider c1 = new myProvider();
        c1.insert(ThisUri, newPaint);
    }
}
