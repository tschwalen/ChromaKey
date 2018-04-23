package com.example.thoma.chromakey;

import android.view.SurfaceHolder;
import android.view.SurfaceView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by thoma on 1/7/2018.
 * Main game surface class
 */

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback{

    public Map gameMap = new Map();
    private GameThread gameThread;

    Player player;

    public GameSurface(Context context){
       super(context);
        // Make Game Surface focusable so it can handle events.
        this.setFocusable(true);

        // Set callback.
        this.getHolder().addCallback(this);

    }

    public void update(){
        player.update();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int)event.getX();
            int y = (int)event.getY();

            player.touchEvent(x, y);

            return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        gameMap.draw(canvas);
        player.draw(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Bitmap chromaBitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.chroma2);
        player = new Player(chromaBitmap, 88, 88 * 5, this);

        this.gameThread = new GameThread(this,holder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {}



}
