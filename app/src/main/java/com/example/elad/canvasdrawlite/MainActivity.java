package com.example.elad.canvasdrawlite;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static int W,H;
    final GestureDetector gestureDetector2 = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
        public void onLongPress(MotionEvent e) {
            Toast.makeText(MainActivity.this, "Main Menu", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_xml);





        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        W = size.x;
        H  = size.y;


    }
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector2.onTouchEvent(event);
    };
    
    public void OnMenuClick(View v) {
        //Toast.makeText(this, "WWADSAF", Toast.LENGTH_SHORT).show();

        switch (v.getId()){
            case R.id.btnDownloaded:
                Toast.makeText(this, "Downloaded", Toast.LENGTH_SHORT).show();
                startActivity( new Intent(getApplicationContext(),DownloadedListActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));

            break;
            case R.id.btnRecycler:
                Toast.makeText(this, "recycler", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),RecycleListActivity.class));

                break;
            case R.id.btnEdit:
                Toast.makeText(this, "edit", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),DrawActivity.class));
                break;
            case R.id.btnFireBaseList:
                Toast.makeText(this, "Online Paints", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),OnlinePaintsList.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));

                break;
            case R.id.btnMyPaints:
                Toast.makeText(this, "My Paints", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MyPaintsActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));

                break;
            case R.id.btnExit:
                Toast.makeText(this, "Bye Bye", Toast.LENGTH_SHORT).show();

                finish();
                break;
        }
    }
}
