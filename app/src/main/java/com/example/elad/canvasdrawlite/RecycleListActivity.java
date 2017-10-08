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

public class RecycleListActivity extends AppCompatActivity {

    final GestureDetector gestureDetector1 = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
        public void onLongPress(MotionEvent e) {
            Toast.makeText(RecycleListActivity.this, "Main Menu", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    });
    Uri ThisUri;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclelist_xml);

        //  DrawerClass.DrawingObjects = new ArrayList<>();


        final EditText ed1 = (EditText)this.findViewById(R.id.edittext) ;
        Button b1 = (Button)this.findViewById(R.id.btnRefresh) ;
        LinearLayout lin = (LinearLayout)this.findViewById(R.id.Linear1) ;
        ListView MyPaintsList = (ListView)this.findViewById(R.id.RecycleList) ;


        UpdateRecycleList();


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecycleListActivity.this, "Erase All..", Toast.LENGTH_SHORT).show();

                myProvider Provider1 = new myProvider();
                Provider1.delete(ThisUri,null,null);

                UpdateRecycleList();

            }
        });



    }





    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector1.onTouchEvent(event);
    };


    public void UpdateRecycleList () {
        ThisUri = Uri.parse(myProvider.CURRENT_URI.toString()+"/"+ myDB.TABLE_RECYCLE_PAINTS);

        Cursor c = getContentResolver().query(ThisUri, null, null, null, null);
        RecyclebinAdapter RecAdp = new RecyclebinAdapter(this,c,0);
        RecAdp.setThisTableUri(ThisUri);
        ListView list = (ListView)this.findViewById(R.id.RecycleList);
        list.setAdapter(RecAdp);
    }

}

