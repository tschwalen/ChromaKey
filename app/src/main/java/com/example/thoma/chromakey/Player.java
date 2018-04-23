package com.example.thoma.chromakey;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Paint;

/**
 * Created by thoma on 4/22/2018.
 */

public class Player  {

    private enum Direction {
        NORTH, EAST, SOUTH, WEST, NONE
    }
    

    protected int width;
    protected int height;
    protected double x_pos;
    protected double y_pos;

    int speed = 22;
    int tilesize = 88;

    boolean moving;
    Direction movedir;
    int pix_moved = 0;


    protected int color;

    GameSurface gameSurface;

    protected Bitmap image;

    public Player(Bitmap image, int x, int y, GameSurface gameSurface){
        this.image = image;
        this.width = tilesize;
        this.height = tilesize;
        this.x_pos = x;
        this.y_pos = y;
        this.color = Color.CYAN;
        this.gameSurface = gameSurface;
        this.moving = false;
        this.movedir = Direction.NONE;
        this.pix_moved = 0;
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(color);
        Rect rect = new Rect((int)x_pos, (int)y_pos, (int)x_pos + tilesize, (int) y_pos + tilesize);
        canvas.drawRect(rect, paint);
        canvas.drawBitmap(image,(int)x_pos, (int)y_pos, null);
    }

    void update(){
        if(moving)
            move();
    }

    void move(){
        if(movedir == Direction.NONE) {
            moving = false;
            return;
        }
        switch(movedir){
            case EAST:
                x_pos += speed;
                break;
            case WEST:
                x_pos -= speed;
                break;
            case NORTH:
                y_pos -= speed;
                break;
            case SOUTH:
                y_pos += speed;
                break;
        }
        pix_moved += speed;

        if(pix_moved >= tilesize) {

            moving = false;
            movedir = Direction.NONE;
            pix_moved = 0;
            checkDeltaTileEvent();
        }
    }

    private void checkDeltaTileEvent(){
        GameTile current_tile = gameSurface.gameMap.getTileAt((int)x_pos, (int)y_pos);
        if(current_tile.type == TileType.DELTA)
            this.color = current_tile.color;
    }


    public void touchEvent(int x, int y){
        // ignore touch events if in movement
        if(moving)
            return;

        int x_prime = (int)(x - x_pos);
        int y_prime = (int)(y - y_pos);
        if(Math.abs(x_prime) >= Math.abs(y_prime)){
            // if the x component is larger, then it will be east or west
            if(x_prime > 0)
                moveAction(Direction.EAST);
            else
                moveAction(Direction.WEST);
        }
        else{
            // otherwise it will be north or south
            if(y_prime > 0)
                moveAction(Direction.SOUTH);
            else
                moveAction(Direction.NORTH);
        }
    }

    public void moveAction(Direction direction){
        GameTile desiredTile = null;
        double desired_x = x_pos;
        double desired_y = y_pos;
        if(direction == Direction.EAST){
            desiredTile = gameSurface.gameMap.layout[(int)(y_pos / tilesize)][((int)(x_pos / tilesize)) + 1];
            desired_x += tilesize;
            movedir = Direction.EAST;
        }
        else if(direction == Direction.WEST){
            desiredTile = gameSurface.gameMap.layout[(int)(y_pos / tilesize)][((int)(x_pos / tilesize)) - 1];
            desired_x -= tilesize;
            movedir = Direction.WEST;
        }
        else if(direction == Direction.NORTH){
            desiredTile = gameSurface.gameMap.layout[((int)y_pos / tilesize) - 1][((int)(x_pos / tilesize))];
            desired_y -= tilesize;
            movedir = Direction.NORTH;
        }
        else if(direction == Direction.SOUTH){
            desiredTile = gameSurface.gameMap.layout[((int) y_pos / tilesize) + 1][((int)(x_pos / tilesize))];
            desired_y += tilesize;
            movedir = Direction.SOUTH;
        }

        if(desiredTile.color == Color.BLACK || desiredTile.color == color || desiredTile.type == TileType.DELTA){
            //x_pos = desired_x;
            //y_pos = desired_y;
            moving = true;
        }
        else{
            movedir = Direction.NONE;
        }
    }
}
