package com.example.elad.canvasdrawlite;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class DownloadedListActivity extends AppCompatActivity {

    final GestureDetector gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
        public void onLongPress(MotionEvent e) {
            Toast.makeText( DownloadedListActivity.this,"Main Menu", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    });

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.downloadedlist_xml);

        //  DrawerClass.DrawingObjects = new ArrayList<>();


        final EditText ed1 = (EditText)this.findViewById(R.id.edittext) ;
        Button b1 = (Button)this.findViewById(R.id.btnRefresh) ;
        LinearLayout lin = (LinearLayout)this.findViewById(R.id.Linear1) ;
        ListView MyPaintsList = (ListView)this.findViewById(R.id.RecycleList) ;


        UpdateDownloadedList();



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DownloadedListActivity.this, "Loading paints...", Toast.LENGTH_SHORT).show();

                UpdateDownloadedList();

            }
        });



    }




    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    };



    public void UpdateDownloadedList () {
        Uri ThisUri = Uri.parse(myProvider.CURRENT_URI.toString()+"/"+ myDB.TABLE_DOWNLOADED_PAINTS);

        Cursor c = getContentResolver().query(ThisUri, null, null, null, null);
        IconAdapter ad1 = new IconAdapter(this,c,0);
        ad1.setThisTableUri(ThisUri);
        ListView list = (ListView)this.findViewById(R.id.DownloadedList);
        list.setAdapter(ad1);
    }

}

