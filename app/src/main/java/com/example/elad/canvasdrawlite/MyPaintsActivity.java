package com.example.elad.canvasdrawlite;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.app.Fragment;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyPaintsActivity extends AppCompatActivity {

    final GestureDetector gestureDetector4 = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
        public void onLongPress(MotionEvent e) {
            Toast.makeText(MyPaintsActivity.this, "Main Menu", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    });

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypaints_xml);

      //  DrawerClass.DrawingObjects = new ArrayList<>();


        final EditText ed1 = (EditText)this.findViewById(R.id.edittext) ;
        Button b1 = (Button)this.findViewById(R.id.btnRefresh) ;
        LinearLayout lin = (LinearLayout)this.findViewById(R.id.Linear1) ;
        ListView MyPaintsList = (ListView)this.findViewById(R.id.l1) ;


        UpdateMyPaintsList();


        b1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Toast.makeText(MyPaintsActivity.this, "Loading Paints", Toast.LENGTH_SHORT).show();

      UpdateMyPaintsList();

    }
});



     }





    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector4.onTouchEvent(event);
    };


     public void UpdateMyPaintsList () {
         Uri ThisUri = Uri.parse(myProvider.CURRENT_URI.toString()+"/"+ myDB.TABLE_CLIENT_PAINTS);

         Cursor c = getContentResolver().query(ThisUri, null, null, null, null);
         IconAdapter ad1 = new IconAdapter(this,c,0);
         ad1.setThisTableUri(ThisUri);
         ListView list = (ListView)this.findViewById(R.id.l1);
         list.setAdapter(ad1);
     }

}
