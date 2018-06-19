package com.example.bobrovskii.pixelplace;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.View;


import static java.lang.Math.abs;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private MainThread thread;
    private CharacterSprite characterSprite;
    private int step = 150; //еличина перемещения на один шаг в пикселях
    private int scrHeight, scrWidth;


    public GameView(Context context)
    {
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        setOnTouchListener(this);

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics(); //определяем размеры экрана, чтобы игрок не выходил за них

         scrHeight = displayMetrics.widthPixels; //TODO:: понятия высоты и ширины немного перепутаны, разобраться
         scrWidth = displayMetrics.heightPixels;
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
            canvas.drawColor(Color.WHITE);  //цвет фона поля
            characterSprite.draw(canvas);

        }
    }

    public boolean collision(int position, int screenAxis) //проверяем, не зайдём ли мы за край экрана и если да - не позволяем этого
    {
        if (position>screenAxis || position<0) return false;
        return true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        int X = (int) event.getX();
        int Y = (int) event.getY();
        int eventaction = event.getAction();

        int playerX = (int)characterSprite.x;
        int  playerY = (int)characterSprite.y;

        int diffX = abs(X-playerX); //разница для определения приоритета направления движения и блокировки хода наискосок
        int diffY = abs(Y-playerY);

        switch (eventaction) {
            case MotionEvent.ACTION_DOWN: //нажатие
                if (diffX>diffY)
                {
                    if (X<playerX && collision(playerX-step, scrHeight)) characterSprite.x -= step; //изменяем само положение игрока, а не переменную playerX.Y
                    else if (X>playerX && collision(playerX+step, scrHeight)) characterSprite.x += step;
                }
               else if (diffX<diffY)
                {
                    if (Y<playerY && collision(playerY-step, scrWidth)) characterSprite.y -=step;
                    else if (Y>playerY && collision(playerY+step, scrWidth)) characterSprite.y +=step;
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
