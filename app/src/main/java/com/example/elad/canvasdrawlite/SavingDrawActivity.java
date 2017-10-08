package com.example.elad.canvasdrawlite;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SavingDrawActivity extends AppCompatActivity {
ImageView img1;
    TextView tv1;
    EditText et1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savingdraw_xml);


        Bundle myBundle = getIntent().getExtras();

        int Width = myBundle.getInt("W");
        int Height = myBundle.getInt("H");

        View v = new DrawerClass(getApplicationContext());

        Bitmap bitmap = Bitmap.createBitmap(Width,Height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        v.draw(canvas);

        img1  = (ImageView) this.findViewById(R.id.imgNewdraw);
        et1 = (EditText) this.findViewById(R.id.etDrawName);

        img1.setImageBitmap(bitmap);


    }

    public void SaveClick(View v) {

        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        JSONArray jnArr1 = new JSONArray();
        for (int i = 0; i < DrawerClass.DrawingObjects.size(); i++) {
            jnArr1.put(DrawerClass.DrawingObjects.get(i).ConvertToJSON());
        }

        String DrawName = et1.getText().toString();
        String JsonCode  = jnArr1.toString();

        InsertPaintToDB(DrawName,JsonCode);

        startActivity(new Intent(getApplicationContext(),MyPaintsActivity.class));




    }




        private void InsertPaintToDB(String DrawName,String JsonCode){
            ContentValues newPaint = new ContentValues();
            newPaint.put(myDB.PAINT_JSON_SRC, JsonCode);
            newPaint.put(myDB.PAINT_NAME, DrawName);

            Uri ThisUri = Uri.parse(myProvider.CURRENT_URI.toString()+"/"+ myDB.TABLE_CLIENT_PAINTS);
            getContentResolver().insert(ThisUri, newPaint);
        }




}
