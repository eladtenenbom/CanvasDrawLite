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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

public class OnlinePaintsList extends AppCompatActivity {
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference myRef = database.getReference("message");

   static List<MyPaintObject> MyOnlinePaintsList = new ArrayList<MyPaintObject>();
    static FireBaseAdapter FireAd1 ;
    static ListView list;

    final GestureDetector gestureDetector3 = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
        public void onLongPress(MotionEvent e) {
            Toast.makeText(OnlinePaintsList.this, "Main Menu", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    });


    List<MyPaintObject> List1 = new ArrayList<MyPaintObject>();

        @Override
        protected void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.onlinelist_xml);


            //  DrawerClass.DrawingObjects = new ArrayList<>();


            final EditText ed1 = (EditText)this.findViewById(R.id.edittext) ;
            Button b1 = (Button)this.findViewById(R.id.btnRefresh) ;
            LinearLayout lin = (LinearLayout)this.findViewById(R.id.Linear1) ;
            ListView MyPaintsList = (ListView)this.findViewById(R.id.OnlineList) ;


            UpdateOnlineList();



        }



    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector3.onTouchEvent(event);
    };





        public void UpdateOnlineList () {
           // Uri ThisUri = Uri.parse(myProvider.CURRENT_URI.toString()+"/"+ myDB.TABLE_DOWNLOADED_PAINTS);

          //  Cursor c = getContentResolver().query(ThisUri, null, null, null, null);

            myRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        MyPaintObject thisPaint = snapshot.getValue(MyPaintObject.class);
                        List1.add (thisPaint);
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            MyOnlinePaintsList = List1;
            list= (ListView)this.findViewById(R.id.OnlineList);
            FireAd1 = new FireBaseAdapter(this, 0, 0, MyOnlinePaintsList);

        }
public void RefrechClick(View v){
    Toast.makeText(this, "Loading Paints...", Toast.LENGTH_SHORT).show();
    list.setAdapter(FireAd1);
}
    }

