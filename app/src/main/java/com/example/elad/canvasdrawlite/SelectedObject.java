package com.example.elad.canvasdrawlite;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.widget.Toast;

import org.json.JSONObject;

/**
 * Created by elad on 03/07/2017.
 */

public class SelectedObject extends DrawingObject {

    int LastColor;

    public int getLastColor() {
        return LastColor;
    }

    public void setLastColor(int lastColor) {
        LastColor = lastColor;
    }

    public SelectedObject() {
    }

    public SelectedObject(float x, float y, String type, int color, float size) {
        super(x, y, type, color, size);
    }

    public SelectedObject(float x, float y, float x2, float y2, String type, int color, float size) {
        super(x, y, x2, y2, type, color, size);
    }

    @Override
    public float getX() {
        return super.getX();
    }

    @Override
    public void setX(float x) {
        super.setX(x);
    }

    @Override
    public float getY() {
        return super.getY();
    }

    @Override
    public void setY(float y) {
        super.setY(y);
    }

    @Override
    public float getX2() {
        return super.getX2();
    }

    @Override
    public void setX2(float x2) {
        super.setX2(x2);
    }

    @Override
    public float getY2() {
        return super.getY2();
    }

    @Override
    public void setY2(float y2) {
        super.setY2(y2);
    }

    @Override
    public String getType() {
        return super.getType();
    }

    @Override
    public void setType(String type) {
        super.setType(type);
    }

    @Override
    public int getColor() {
        return super.getColor();
    }

    @Override
    public void setColor(int color) {
        super.setColor(color);
    }

    @Override
    public float getSize() {
        return super.getSize();
    }

    @Override
    public void setSize(float size) {
        super.setSize(size);
    }

    @Override
    public void DrawObject(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(LastColor);

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
public void Shrink(float xFactor, float yFactor,   float x2Factor,  float y2Factor){
    x = x - (xFactor/20);
    y = y - (yFactor/20);
    if (Type.equals("Line")) {
        x2 = x2 - (x2Factor / 20);
        y2 = y2 - (y2Factor / 20);

    }

    Size = Size - Size/20;

}

    public double RotateLeft (float CenterX, float CenterY, float Radius,float Radius2){

        float angle = (float)Math.atan2(y - CenterY, x - CenterX);
        float angle2 = (float)Math.atan2(y2 - CenterY, x2 - CenterX);

        angle += 0.1;
        angle2 += 0.1;

        x = (float)(CenterX + Math.cos(angle)*Radius);
        y = (float)(CenterY + Math.sin(angle)*Radius);
        x2 = (float)(CenterX + Math.cos(angle2)*Radius2);
        y2 = (float)(CenterY + Math.sin(angle2)*Radius2);

        return angle;

    }
    public double RotateRight (float CenterX, float CenterY, float Radius,float Radius2){

        float angle = (float)Math.atan2(y - CenterY, x - CenterX);
        float angle2 = (float)Math.atan2(y2 - CenterY, x2 - CenterX);

        angle -= 0.1;
        angle2 -= 0.1;

        x = (float)(CenterX + Math.cos(angle)*Radius);
        y = (float)(CenterY + Math.sin(angle)*Radius);
        x2 = (float)(CenterX + Math.cos(angle2)*Radius2);
        y2 = (float)(CenterY + Math.sin(angle2)*Radius2);

        return angle;

    }
    public void Strach (float xFactor, float yFactor,   float x2Factor,  float y2Factor){
        x = x + (xFactor/20);
        y = y + (yFactor/20);

        if (Type.equals("Line")) {

            x2 = x2 + (x2Factor / 20);
            y2 = y2 + (y2Factor / 20);
        }
        Size = Size + Size/20;

    }
    @Override
    public JSONObject ConvertToJSON() {
        return super.ConvertToJSON();
    }

    @Override
    public DrawingObject ConvertFromString(String Object) {

        return new SelectedObject(super.ConvertFromString(Object).getX(),
                super.ConvertFromString(Object).getY(),
                super.ConvertFromString(Object).getX2(),
                super.ConvertFromString(Object).getY2(),
                super.ConvertFromString(Object).getType(),
                super.ConvertFromString(Object).getColor(),
        super.ConvertFromString(Object).getSize());
    }
}
