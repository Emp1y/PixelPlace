package com.example.bobrovskii.pixelplace;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

public class CharacterSprite {

    private Bitmap image;
    public float x, y;



    public CharacterSprite(Bitmap bmp)
    {
        image = bmp;
        x = 100;
        y = 100;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, x, y, null);
    }


    public void update(){

    }
}
