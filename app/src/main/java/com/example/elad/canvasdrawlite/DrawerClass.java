package com.example.elad.canvasdrawlite;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.AsyncTask;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elad on 26/06/2017.
 */

public class DrawerClass extends View {
    float x, y, x2, y2;

    static boolean SelectMode = false;

    boolean NoLine;

    static int Size = 10;


    static int Color1 = android.graphics.Color.BLACK;
    static String Type = "Circle";
    String LastType;

    static List <Integer> selectedPos = new ArrayList<Integer>();

    static List<DrawingObject> DrawingObjects;
    static List<SelectedObject> SelectedObjects = new ArrayList<SelectedObject>();


    public DrawerClass(Context context) {
        super(context);

    }




    @Override
    protected void onDraw(Canvas canvas) {


        if (DrawActivity.isThereAnyJSON == 0) {

            DrawingObjects = new ArrayList<DrawingObject>();

            DrawActivity.isThereAnyJSON++;
        }



        super.onDraw(canvas);


        //       canvas.drawCircle(200,200,200,pn1);

        for (int i = 0; i < DrawingObjects.size(); i++) {
            DrawingObject dr5 = DrawingObjects.get(i);

            dr5.DrawObject(canvas);
        }


        if (SelectMode) {
            for (int i = 0; i < SelectedObjects.size(); i++) {
                SelectedObject obj1 = SelectedObjects.get(i);
                obj1.DrawObject(canvas);
            }
        }



        task1 task11 = new task1();
        task11.execute();
    }



        @Override
        public boolean onTouchEvent(MotionEvent event) {


            if (SelectMode == false) {
                if (event.getAction()==MotionEvent.ACTION_DOWN) {
                    x =event.getX();
                    y= event.getY();

                    NoLine = true;
                    //    Toast.makeText(getContext(),event.getActionIndex()+"" , Toast.LENGTH_SHORT).show();


                }
                if (event.getAction()==MotionEvent.ACTION_MOVE) {
                    //      Toast.makeText(getContext(), "Action Move", Toast.LENGTH_SHORT).show();
                    x2 =event.getX();
                    y2= event.getY();

                    //   if (Size<40) {
                    if (x2 > x + 20 || x2 < x - 20 || y2 > y + 20 || y2 < y - 20) {

                        NoLine = false;
                        DrawingObject dr1 = new DrawingObject(x, y, x2, y2, "Line", Color1, Size);

                        DrawingObjects.add(dr1);
                        x = x2;
                        y = y2;
                    }
                }


                if (event.getAction() == MotionEvent.ACTION_UP  && NoLine)  {
                    //   Toast.makeText(getContext(),event.getActionIndex()+"" , Toast.LENGTH_SHORT).show();

                    DrawingObject dr1 = new DrawingObject(x, y, Type, Color1, Size);
                    DrawingObjects.add(dr1);


                    invalidate();
                }

                if (event.getActionMasked()==MotionEvent.ACTION_POINTER_DOWN) {
                    Toast.makeText(getContext(),event.getActionIndex() +"", Toast.LENGTH_SHORT).show();

                    //     Toast.makeText(getContext(), "Action Up", Toast.LENGTH_SHORT).show();
                    int PointerIndex = event.getActionIndex();
                    x2 = event.getX(PointerIndex);
                    y2 = event.getY(PointerIndex);

                    if (x2 > x + 100 || x2 < x - 100 || y2 > y + 100 || y2 < y - 100) {
                        NoLine = false;
                        DrawingObject dr1 = new DrawingObject(x, y, x2, y2, "Line", Color1, Size);

                        DrawingObjects.add(dr1);
                    }
                }
                //    Toast.makeText(getContext(), "Action Down", Toast.LENGTH_SHORT).show();
            }

            if (SelectMode) {
                float x = event.getX();
                float y = event.getY();
                SelectedObject SelectedObject = new SelectedObject();
                boolean addNew = false;

                for (int i = 0;i<DrawingObjects.size();i++){
            //        float ObjectcenterX =  Math.abs(((DrawingObjects.get(i).getX()-DrawingObjects.get(i).getX2()))/2);
           //         float ObjectcenterY =  Math.abs(((DrawingObjects.get(i).getY()-DrawingObjects.get(i).getY2()))/2);
                    if (x>DrawingObjects.get(i).getX()-30 && x < DrawingObjects.get(i).getX()+30 &&  y>DrawingObjects.get(i).getY()-30 && y < DrawingObjects.get(i).getY()+30) {
                        addNew=true;
                        for (int j = 0;j<selectedPos.size();j++) {
                            if (i == selectedPos.get(j)) {
                                addNew = false;
                            }
                        }
                            if (addNew == true || selectedPos.size()==0) {
                                SelectedObject = (SelectedObject) SelectedObject.ConvertFromString ( DrawingObjects.get(i).ConvertToJSON().toString());
                                SelectedObject.setLastColor(0-1711216919);
                                selectedPos.add(i);
                                SelectedObjects.add(SelectedObject);
                                addNew = false;
                            }
                        }
                    }


                        DrawActivity.btnSelect.setText("Select ("+SelectedObjects.size()+" Selected)");

                }



            return true;
        }


    protected void onJsonDraw(Canvas canvas,String JsonSrc) {

        DrawingObjects = new ArrayList<DrawingObject>();


        JSONArray jnarr = new JSONArray();
        try {
            jnarr = new JSONArray(JsonSrc);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject ThisObjectJson = new JSONObject();

        for (int i = 0; i < jnarr.length(); i++) {

            try {
                ThisObjectJson = jnarr.getJSONObject(i);
                DrawingObject ThisDrawingObject = new DrawingObject(10, 10, "hjk", 10, 10);
                ThisDrawingObject = ThisDrawingObject.ConvertFromString(ThisObjectJson.toString());
                ThisDrawingObject.DrawObject(canvas);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    class  task1 extends AsyncTask<Integer,Void,Void>
    {

        @Override
        protected Void doInBackground(Integer... params) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            invalidate();
        }
    }
    }


