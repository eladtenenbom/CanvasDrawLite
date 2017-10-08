package com.example.elad.canvasdrawlite;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by elad on 26/06/2017.
 */

public class DrawingObject {
    float x,y,x2,y2;
    String Type;
    int Color;
    float Size;

    public DrawingObject() {
    }

    public DrawingObject(float x, float y, String type, int color, float size) {
        this.x = x;
        this.y = y;
        Type = type;
        Color = color;
        Size = size;
    }

    public DrawingObject(float x, float y, float x2, float y2, String type, int color, float size) {
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
        Type = type;
        Color = color;
        Size = size;
    }

    public void setAll(float x, float y, float x2, float y2, String type, int color, float size) {
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
        Type = type;
        Color = color;
        Size = size;
    }


    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX2() {
        return x2;
    }

    public void setX2(float x2) {
        this.x2 = x2;
    }

    public float getY2() {
        return y2;
    }

    public void setY2(float y2) {
        this.y2 = y2;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getColor() {
        return Color;
    }

    public void setColor(int color) {
        Color = color;
    }

    public float getSize() {
        return Size;
    }

    public void setSize(float size) {
        Size = size;
    }



    public void DrawObject (Canvas canvas){

        Paint paint = new Paint();
        paint.setColor(Color);

        switch (Type) {
            case "Circle":
                paint.setStrokeWidth(1);
                canvas.drawCircle(x, y, Size, paint);
                break;
            case "Square":
                paint.setStrokeWidth(Size);
                canvas.drawPoint(x, y, paint);

                break;
            case "Line":

                    paint.setStrokeWidth(Size);
                    canvas.drawLine(x, y,x2,y2, paint);


                break;

        }

    }

    public JSONObject ConvertToJSON () {

        JSONObject jn1 = new JSONObject();
        try {
            jn1.put("x",getX());
            jn1.put("y",getY());
            jn1.put("Size",getSize());
            jn1.put("Color",getColor());
            jn1.put("Type",getType());
            jn1.put("x2",getX2());
            jn1.put("y2",getY2());


        } catch (JSONException e) {
            e.printStackTrace();
        }



        return jn1;
    }
    public DrawingObject ConvertFromString (String Object) {

        DrawingObject a1 = new DrawingObject();

        JSONObject jn1 = null;
        try {
            jn1 = new JSONObject(Object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {

            a1.setX(Float.parseFloat(jn1.get("x").toString()));
            a1.setY(Float.parseFloat(jn1.get("y").toString()));
            a1.setSize(Float.parseFloat(jn1.get("Size").toString()));
            a1.setColor(Integer.parseInt(jn1.get("Color").toString()));
            a1.setType(jn1.get("Type").toString());
            a1.setX2(Float.parseFloat(jn1.get("x2").toString()));
            a1.setY2(Float.parseFloat(jn1.get("y2").toString()));

        } catch (JSONException e) {
            e.printStackTrace();
        }



        return a1;
    }
}

