package com.example.bobrovskii.pixelplace;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

public class CharacterSprite {

    private Bitmap image;
    public float x, y;



    public CharacterSprite(Bitmap bmp)
    {
        image = bmp;
        image = Bitmap.createScaledBitmap(image, 20, 20, false); //масштабируем игрока

        x = 50; //начальное положение игрока
        y = 50;
    }

    public void draw(Canvas canvas)
    {

        canvas.drawBitmap(image, x, y, null);
    }


    public void update(){

    }
}
