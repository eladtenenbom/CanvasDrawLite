package com.example.elad.canvasdrawlite;

import android.content.ClipboardManager;
import android.graphics.Paint;
import android.support.annotation.Size;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShowCodeActivity extends AppCompatActivity {

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
            menu.add(0, v.getId(), 0, "Copy All Class ( Android.)");

            TextView yourTextView = (TextView) v;

            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            clipboard.setText(yourTextView.getText());
        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showcode_xml);
        TextView TextViewcode = (TextView)findViewById(R.id.tvCode);
        registerForContextMenu(TextViewcode);

        String JsonCode = getIntent().getExtras().getString("JSON");

        JSONArray ThisJsonArray = null;
        try {
           ThisJsonArray = new JSONArray(JsonCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String Part1 = "\n" +
                "public class PaintName {\n" +
                "    float Top;\n" +
                "    float Left;\n" +
                "    Paint paint = new Paint();\n" +
                "    int Size;  // Adjust between 1 - 50. \n" +
                "\n" +
                "    public PaintName( float top, float left, int size) {\n" +
                "        Top = top;\n" +
                "        Left = left;\n" +
                "        Size = size;}\n" +
                "    \n" +
                "\n" +
                "    public int getSize() {\n" +
                "        return Size;}\n" +
                "    \n" +
                "\n" +
                "    public void setSize(int size) {\n" +
                "        Size = size;}\n" +
                "    \n" +
                "    public float getTop() {\n" +
                "        return Top;}\n" +
                "    \n" +
                "\n" +
                "    public void setTop(float top) {\n" +
                "        Top = top;}\n" +
                "    \n" +
                "\n" +
                "    public float getLeft() {\n" +
                "        return Left;}\n" +
                "    \n" +
                "\n" +
                "    public void setLeft(float left) {\n" +
                "        Left = left;}\n" +
                "    ";


        String Part2 = "    public void DrawPaint (Canvas canvas) {\n";
        String Part3 = "";

        float Top = MainActivity.H;
        float Left = MainActivity.W;

         for (int i=0;i< ThisJsonArray.length();i++) {

             JSONObject thisObject = null;
             try {
                 thisObject = ThisJsonArray.getJSONObject(i);
             } catch (JSONException e) {
                 e.printStackTrace();
             }
             float thisX = 0;
             float thisY = 0;
             try {
                 thisX = Float.parseFloat(thisObject.get("x").toString());
                 thisY = Float.parseFloat(thisObject.get("y").toString());
             } catch (JSONException e) {
                 e.printStackTrace();
             }


             if (thisX <Top)  {
                     Top = thisX;
                 }

                 if (thisY<Left)  {
                     Left = thisY;
                 }


         }




        for (int i=0;i< ThisJsonArray.length();i++) {
            JSONObject thisObject = null;

            String ObjectPart = "";
            try {
                thisObject = ThisJsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
               ObjectPart += "paint.setColor(0+("+ thisObject.get("Color").toString() +"));\n";
            } catch (JSONException e) {
                e.printStackTrace();
            }
            float newX = 0;
            float newY = 0;
            float newX2 = 0;
            float newY2 = 0;
            try {
                newX = Float.parseFloat(thisObject.get("x").toString())-Left;
                newY = Float.parseFloat(thisObject.get("y").toString())-Top;
                newX2 = Float.parseFloat(thisObject.get("x2").toString())-Left;
                newY2 = Float.parseFloat(thisObject.get("y2").toString())-Top;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                switch (thisObject.get("Type").toString()) {
                    case "Circle":
                        ObjectPart += "paint.setStrokeWidth(1);\n";
                        ObjectPart += "canvas.drawCircle((float) (("+newX+"+Left)/20*Size), (float) (("+newY+"+Top)/20*Size), ("+thisObject.get("Size").toString()+"/20*Size), paint);\n";

                        break;
                    case "Square":
                        ObjectPart += "paint.setStrokeWidth("+thisObject.get("Size").toString()+"/20*Size);\n";
                        ObjectPart += "canvas.drawPoint((float) (("+newX+"+Left)/20*Size), (float) (("+newY+"+Top)*(20/Size), paint);\n";

                        break;
                    case "Line":

                        ObjectPart += "paint.setStrokeWidth("+thisObject.get("Size").toString()+"/20*Size);\n";
                        ObjectPart += "canvas.drawLine((float) (("+newX+"+Left)/20*Size), (float) (("+newY+"+Top)/20*Size),(float) (("+newX2+"+Left)/20*Size),(float) (("+newY2+"+Top)/20*Size), paint);\n";


                        break;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Part2 = Part2 +"\n\n"+ ObjectPart;
        }


Part3 = "}  \n   }  \n";




        String SumOfAll = Part1 + "\n" + Part2 + "\n" + Part3;

        TextViewcode.setText(SumOfAll);
    }
}
