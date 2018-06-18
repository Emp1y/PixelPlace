package com.example.bobrovskii.pixelplace;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Toast;

import java.util.logging.Logger;

import static java.lang.Math.abs;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private MainThread thread;
    private CharacterSprite characterSprite;
    private int step = 150; //в пикселях
    public GameView(Context context)
    {
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        setOnTouchListener(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        characterSprite = new CharacterSprite(BitmapFactory.decodeResource(getResources(), R.drawable.avdgreen));


        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry = true;
        while (retry)
        {
            try {
                thread.setRunning(false);
                thread.join();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update()
    {
        characterSprite.update();

    }


    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        if (canvas!=null)
        {
            characterSprite.draw(canvas);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        int X = (int) event.getX();
        int Y = (int) event.getY();
        int eventaction = event.getAction();

        float diffX = abs(X-characterSprite.x); //разница для определения приоритета направления движения и блокировки хода наискосок
        float diffY = abs(Y-characterSprite.y);

        switch (eventaction) {
            case MotionEvent.ACTION_DOWN: //нажатие
                if (diffX>diffY)
                {
                    if (X<characterSprite.x) characterSprite.x -= step;
                    else if (X>characterSprite.x) characterSprite.x += step;
                }
               else if (diffX<diffY)
                {
                    if (Y<characterSprite.y) characterSprite.y -=step;
                    else if (Y>characterSprite.y) characterSprite.y +=step;
                }
                else
                break;

            case MotionEvent.ACTION_MOVE: //движение


                break;

            case MotionEvent.ACTION_UP:// отпускание


                break;
        }
        return false;
    }
}
