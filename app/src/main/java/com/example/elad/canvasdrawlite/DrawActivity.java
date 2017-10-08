package com.example.elad.canvasdrawlite;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.icu.text.LocaleDisplayNames;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DrawActivity extends AppCompatActivity {
    LinearLayout l1;

    SeekBar sk1;

     static Button btnSelect;
    Button btnSave;
    Button btnClear;

    float CenterX;
    float CenterY;

    float yFactor = 0;
    float xFactor = 0;
    float y2Factor = 0;
    float x2Factor = 0;

    TextView t1;
    static int isThereAnyJSON =0;


    String JsonCode;

    List<DrawingObject> UndoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw_xml);
        DrawerClass.SelectedObjects = new ArrayList<SelectedObject>();

        try {
            JsonCode = getIntent().getExtras().getString("JSON");
        } catch (Exception e) {
            JsonCode = "";        }

        if (JsonCode.equals("")) {
           // isThereAnyJSON = 0;
        }
        else {
            DrawerClass.DrawingObjects = new ArrayList<DrawingObject>();
            isThereAnyJSON++;
            JSONArray Jarr1 = null;
            try {
                Jarr1 = new JSONArray(JsonCode);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            for (int i = 0; i < Jarr1.length();i++){
                try {
                    JSONObject jObj = Jarr1.getJSONObject(i);
                    String thisObjString = jObj.toString();
                    DrawingObject db1 = new DrawingObject();
                    db1 = db1.ConvertFromString(thisObjString);
                    DrawerClass.DrawingObjects.add(db1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            JsonCode = null;
        }


        UndoList = new ArrayList<DrawingObject>();


        btnSelect = (Button)findViewById(R.id.btnSelect);
        btnSave = (Button)findViewById(R.id.btnSave);
        btnClear = (Button)findViewById(R.id.btnClear);
        l1 = (LinearLayout)findViewById(R.id.drawer);
        sk1 = (SeekBar)findViewById(R.id.skbrSize);
    //    t1 = (TextView)findViewById(R.id.tvShowColor);

            l1.addView(new DrawerClass(this));

  //      t1.setBackgroundColor(DrawerClass.Color);
      //  t1.setTextColor(DrawerClass.Color);

        sk1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                DrawerClass.Size = progress+20;
                try {
                    DrawerClass.DrawingObjects.get(DrawerClass.DrawingObjects.size() - 1).setSize(DrawerClass.Size);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



    }
    public void OnBtnClick (View v) {
        switch (v.getId()){
            case R.id.btnUndo :

                if (DrawerClass.SelectMode == false) {
                    try {
                        UndoList.add(DrawerClass.DrawingObjects.get(DrawerClass.DrawingObjects.size()-1));

                        DrawerClass.DrawingObjects.remove(DrawerClass.DrawingObjects.size()-1);
                    } catch (Exception e) {
                        Toast.makeText(this, "no more acts to undo", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "undo select", Toast.LENGTH_SHORT).show();

                    CenterX =  GetItemsCenter(DrawerClass.SelectedObjects)[0];
                    CenterY = GetItemsCenter(DrawerClass.SelectedObjects)[1];

                    String res = "";
                    for (int i=0;i<DrawerClass.SelectedObjects.size();i++) {
                        x2Factor = DrawerClass.SelectedObjects.get(i).getX2() - CenterX;
                        y2Factor = DrawerClass.SelectedObjects.get(i).getY2() - CenterY;
                        xFactor = DrawerClass.SelectedObjects.get(i).getX() - CenterX;
                        yFactor = DrawerClass.SelectedObjects.get(i).getY() - CenterY;
                        float Radius = (float )Math.sqrt((double)xFactor*xFactor+yFactor*yFactor);
                        float Radius2 = (float )Math.sqrt((double)x2Factor*x2Factor+y2Factor*y2Factor);

                        DrawerClass.SelectedObjects.get(i).RotateRight(CenterX, CenterY, Radius, Radius2);
                    }


                }


                break;
            case R.id.btnRedo :
                if (DrawerClass.SelectMode == false) {
                    try {
                        DrawerClass.DrawingObjects.add(UndoList.get(UndoList.size() - 1));
                        UndoList.remove(UndoList.size() - 1);
                    } catch (Exception e) {
                        Toast.makeText(this, "no more acts to redo", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    CenterX = GetItemsCenter(DrawerClass.SelectedObjects)[0];
                    CenterY = GetItemsCenter(DrawerClass.SelectedObjects)[1];

                    for (int i = 0; i < DrawerClass.SelectedObjects.size(); i++) {
                        x2Factor = DrawerClass.SelectedObjects.get(i).getX2() - CenterX;
                        y2Factor = DrawerClass.SelectedObjects.get(i).getY2() - CenterY;
                        xFactor = DrawerClass.SelectedObjects.get(i).getX() - CenterX;
                        yFactor = DrawerClass.SelectedObjects.get(i).getY() - CenterY;
                        float Radius = (float) Math.sqrt((double) xFactor * xFactor + yFactor * yFactor);
                        float Radius2 = (float) Math.sqrt((double) x2Factor * x2Factor + y2Factor * y2Factor);

                        DrawerClass.SelectedObjects.get(i).RotateLeft(CenterX, CenterY, Radius, Radius2);


                    }
                }

                break;
            case R.id.btnCircle :
                DrawerClass.Type = "Circle";


                break;
            case R.id.btnSquare :
                DrawerClass.Type = "Square";


                break;
            case R.id.btnColorPicker :
                Intent intent = new Intent(getApplicationContext(),ColorPickerActivity.class);
                startActivityForResult(intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY),1);

                break;

            case R.id.btnClear :
                if (DrawerClass.SelectMode == false) {
                    DrawerClass.DrawingObjects.clear();
                    NoSelectMode();
                    Toast.makeText(this, "Lets start again...", Toast.LENGTH_SHORT).show();
                }
                else {
                    for (int i = DrawerClass.DrawingObjects.size()-1; i>=0; i--) {
                       for (int j =0;j<DrawerClass.selectedPos.size();j++){
                           if (i == DrawerClass.selectedPos.get(j)) {
                               DrawerClass.DrawingObjects.remove(i);
                               Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();
                           }
                       }

                    }
                    DrawerClass.selectedPos.clear();
                    DrawerClass.SelectedObjects.clear();
                    btnSelect.setText("Select ( "+DrawerClass.SelectedObjects.size()+" Selected)");

                    // NoSelectMode();

                }
                break;
            case R.id.btnSave :

                if (DrawerClass.SelectMode == false) {
                    Intent intent1 = new Intent(getApplicationContext(),SavingDrawActivity.class);
                    intent1.putExtra("W",l1.getWidth());
                    intent1.putExtra("H",l1.getHeight());
                    NoSelectMode();

                    startActivity(intent1.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                }
                else {
                    Toast.makeText(this, "save selected", Toast.LENGTH_SHORT).show();
                    for (int i = 0;i<DrawerClass.selectedPos.size();i++) {
                        float newX = DrawerClass.SelectedObjects.get(i).getX();
                        float newY = DrawerClass.SelectedObjects.get(i).getY();
                        float newX2 = DrawerClass.SelectedObjects.get(i).getX2();
                        float newY2 = DrawerClass.SelectedObjects.get(i).getY2();
                        String newType = DrawerClass.SelectedObjects.get(i).getType();
                        int newColor = DrawerClass.SelectedObjects.get(i).getColor();
                        float newSize = DrawerClass.SelectedObjects.get(i).getSize();
                        DrawerClass.DrawingObjects.get(DrawerClass.selectedPos.get(i)).setAll(newX,newY,newX2,newY2,newType,newColor,newSize);
                    }
                    NoSelectMode();
                }

                break;
            case R.id.btnSelect :
                if (DrawerClass.SelectMode == false) {
                    Toast.makeText(this, "touch to select", Toast.LENGTH_SHORT).show();
                    btnSelect.setTextColor(Color.RED);
                    btnSave.setTextColor(Color.RED);
                    btnClear.setTextColor(Color.RED);
                    DrawerClass.SelectMode = true;
                }
                else {

                     NoSelectMode();
                }
                break;

        }
    }



    public void OnNavigationClick (View v) {

        int step = 20;
        if (DrawerClass.DrawingObjects.size() > 0) {
            if (DrawerClass.SelectMode == false) {
                switch (v.getId()) {
                    case R.id.x2left:

                        if (DrawerClass.DrawingObjects.get(DrawerClass.DrawingObjects.size() - 1).getType().equals("Line")) {

                            DrawerClass.DrawingObjects.get(DrawerClass.DrawingObjects.size() - 1).setX2(DrawerClass.DrawingObjects.get(DrawerClass.DrawingObjects.size() - 1).getX2() - step);


                        }


                        break;
                    case R.id.x2right:


                        if (DrawerClass.DrawingObjects.get(DrawerClass.DrawingObjects.size() - 1).getType().equals("Line")) {

                            DrawerClass.DrawingObjects.get(DrawerClass.DrawingObjects.size() - 1).setX2(DrawerClass.DrawingObjects.get(DrawerClass.DrawingObjects.size() - 1).getX2() + step);


                        }

                        break;
                    case R.id.y2up:

                        if (DrawerClass.DrawingObjects.get(DrawerClass.DrawingObjects.size() - 1).getType().equals("Line")) {

                            DrawerClass.DrawingObjects.get(DrawerClass.DrawingObjects.size() - 1).setY2(DrawerClass.DrawingObjects.get(DrawerClass.DrawingObjects.size() - 1).getY2() - step);


                        }

                        break;
                    case R.id.y2down:

                        if (DrawerClass.DrawingObjects.get(DrawerClass.DrawingObjects.size() - 1).getType().equals("Line")) {

                            DrawerClass.DrawingObjects.get(DrawerClass.DrawingObjects.size() - 1).setY2(DrawerClass.DrawingObjects.get(DrawerClass.DrawingObjects.size() - 1).getY2() + step);


                        }
                        break;
                    case R.id.x1left:
                        DrawerClass.DrawingObjects.get(DrawerClass.DrawingObjects.size() - 1).setX(DrawerClass.DrawingObjects.get(DrawerClass.DrawingObjects.size() - 1).getX() - step);


                        break;
                    case R.id.x1right:
                        DrawerClass.DrawingObjects.get(DrawerClass.DrawingObjects.size() - 1).setX(DrawerClass.DrawingObjects.get(DrawerClass.DrawingObjects.size() - 1).getX() + step);


                        break;
                    case R.id.y1up:
                        DrawerClass.DrawingObjects.get(DrawerClass.DrawingObjects.size() - 1).setY(DrawerClass.DrawingObjects.get(DrawerClass.DrawingObjects.size() - 1).getY() - step);


                        break;
                    case R.id.y1down:

                        DrawerClass.DrawingObjects.get(DrawerClass.DrawingObjects.size() - 1).setY(DrawerClass.DrawingObjects.get(DrawerClass.DrawingObjects.size() - 1).getY() + step);

                        break;

                    case R.id.btnMinus:

                        sk1.setProgress(sk1.getProgress() - 20);
                        break;

                    case R.id.btnPlus:

                        sk1.setProgress(sk1.getProgress() + 20);
                        break;

                }
            } else {   // Selection Mode is True
                switch (v.getId()) {
                    case R.id.x1left:
                        for (int i = 0; i < DrawerClass.SelectedObjects.size(); i++) {
                            DrawerClass.SelectedObjects.get(i).setX(DrawerClass.SelectedObjects.get(i).getX() - step);
                            DrawerClass.SelectedObjects.get(i).setX2(DrawerClass.SelectedObjects.get(i).getX2() - step);
                        }

                        break;
                    case R.id.x1right:
                        for (int i = 0; i < DrawerClass.SelectedObjects.size(); i++) {
                            DrawerClass.SelectedObjects.get(i).setX(DrawerClass.SelectedObjects.get(i).getX() + step);
                            DrawerClass.SelectedObjects.get(i).setX2(DrawerClass.SelectedObjects.get(i).getX2() + step);
                        }

                        break;
                    case R.id.y1up:
                        for (int i = 0; i < DrawerClass.SelectedObjects.size(); i++) {
                            DrawerClass.SelectedObjects.get(i).setY(DrawerClass.SelectedObjects.get(i).getY() - step);
                            DrawerClass.SelectedObjects.get(i).setY2(DrawerClass.SelectedObjects.get(i).getY2() - step);
                        }

                        break;
                    case R.id.y1down:

                        for (int i = 0; i < DrawerClass.SelectedObjects.size(); i++) {
                            DrawerClass.SelectedObjects.get(i).setY(DrawerClass.SelectedObjects.get(i).getY() + step);
                            DrawerClass.SelectedObjects.get(i).setY2(DrawerClass.SelectedObjects.get(i).getY2() + step);
                        }

                        break;

                    case R.id.btnMinus:

                        Toast.makeText(this, "Selected Minus", Toast.LENGTH_SHORT).show();

                          CenterX =  GetItemsCenter(DrawerClass.SelectedObjects)[0];
                         CenterY = GetItemsCenter(DrawerClass.SelectedObjects)[1];
                         yFactor = 0;
                         xFactor = 0;
                         y2Factor = 0;
                         x2Factor = 0;
                        for (int i = 0;i<DrawerClass.SelectedObjects.size();i++){

                                x2Factor = DrawerClass.SelectedObjects.get(i).getX2() - CenterX;
                                y2Factor = DrawerClass.SelectedObjects.get(i).getY2() - CenterY;

                                xFactor = DrawerClass.SelectedObjects.get(i).getX() - CenterX;
                                yFactor = DrawerClass.SelectedObjects.get(i).getY() - CenterY;


                            DrawerClass.SelectedObjects.get(i).Shrink(xFactor,yFactor,x2Factor,y2Factor);
                        }
                        break;

                    case R.id.btnPlus:

                        Toast.makeText(this, "Selected plus", Toast.LENGTH_SHORT).show();
                        CenterX =  GetItemsCenter(DrawerClass.SelectedObjects)[0];
                        CenterY = GetItemsCenter(DrawerClass.SelectedObjects)[1];
                        yFactor = 0;
                         xFactor = 0;
                         y2Factor = 0;
                         x2Factor = 0;
                        for (int i = 0;i<DrawerClass.SelectedObjects.size();i++){

                                x2Factor = DrawerClass.SelectedObjects.get(i).getX2() - CenterX;
                                y2Factor = DrawerClass.SelectedObjects.get(i).getY2() - CenterY;

                                xFactor = DrawerClass.SelectedObjects.get(i).getX() - CenterX;
                                yFactor = DrawerClass.SelectedObjects.get(i).getY() - CenterY;


                            DrawerClass.SelectedObjects.get(i).Strach(xFactor,yFactor,x2Factor,y2Factor);
                        }

                        break;
                }

            }
        }
    }

    public float[] GetItemsCenter (List <SelectedObject> obj){
        float[] Centerxy = new float[2];
        float CenterX = 0;
        float CenterY = 0;
        int LinesCounter = 0;
        for (int i = 0;i<obj.size();i++){
            CenterX = CenterX + (obj.get(i).getX());
            CenterY = CenterY + (obj.get(i).getY());
            if (obj.get(i).getType().equals("Line")){
                CenterX = CenterX + (obj.get(i).getX2());
                CenterY = CenterY + (obj.get(i).getY2());
                LinesCounter++;
            }
        }
        Centerxy[0] = CenterX/(DrawerClass.SelectedObjects.size()+LinesCounter);
        Centerxy[1] = CenterY/(DrawerClass.SelectedObjects.size()+LinesCounter);

        return Centerxy;
    }

    public void NoSelectMode () {
        DrawerClass.SelectedObjects.clear();
        DrawerClass.selectedPos.clear();
        DrawerClass.SelectMode = false;
        btnSelect.setText("");
        btnSelect.setTextColor(Color.BLACK);
        btnSave.setTextColor(Color.BLACK);
        btnClear.setTextColor(Color.BLACK);

        btnSelect.setText("Select ( "+DrawerClass.SelectedObjects.size()+" Selected)");


    }

}



