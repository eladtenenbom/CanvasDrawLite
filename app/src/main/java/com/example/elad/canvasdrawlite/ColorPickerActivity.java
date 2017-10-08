package com.example.elad.canvasdrawlite;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

public class ColorPickerActivity extends AppCompatActivity {
    static int FinalColor;

    static int skR,skG,skB,skA;
    LinearLayout linFinal;
    SeekBar skbrRed,skbrGreen,skbrBlue,skbrAlpha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colorpicker_xml);



        linFinal = (LinearLayout)findViewById(R.id.linFinalColor);

        skbrRed = (SeekBar)findViewById(R.id.skbrRed);
        skbrGreen = (SeekBar)findViewById(R.id.skbrGreen);
        skbrBlue = (SeekBar)findViewById(R.id.skbrBlue);
        skbrAlpha = (SeekBar)findViewById(R.id.skbrAlpha);

        skbrRed.setProgress(skR);
        skbrGreen.setProgress(skG);
        skbrBlue.setProgress(skB);
        skbrAlpha.setProgress(skA);

        linFinal.setBackgroundColor(Color.argb(skA,skR,skG,skB));

        skbrRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                skR = progress;
                ChangeTestColor ();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        skbrGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                skG = progress;
                ChangeTestColor ();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        skbrBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                skB = progress;
                ChangeTestColor ();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        skbrAlpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                skA = progress;
                ChangeTestColor ();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });






    }
    public void ChangeTestColor () {


        FinalColor = Color.argb(skA,skR,skG,skB);

        linFinal.setBackgroundColor(FinalColor);
    }

    public void OnBtnClick (View v) {
        DrawerClass.Color1 = FinalColor;
        try {
            DrawerClass.DrawingObjects.get(DrawerClass.DrawingObjects.size() - 1).setColor(FinalColor);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = new Intent();
        intent.putExtra("Fc", FinalColor);
        setResult(DrawActivity.RESULT_OK, intent);
        finish();

    }
}
