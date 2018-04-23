package com.example.thoma.chromakey;

/**
 * Created by thoma on 1/7/2018.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;


public class Map {

    final int TILE_SIZE = 88;

    GameTile[][] layout;
    Bitmap wall_block;
    Bitmap delta_block;

    public Map(GameSurface gs){
        this.layout = makeDefaultMap();
        wall_block = BitmapFactory.decodeResource(gs.getResources(),R.drawable.wall_block);
        delta_block = BitmapFactory.decodeResource(gs.getResources(),R.drawable.delta_block);
    }

    public GameTile getTileAt(int x, int y){
        int c = x / TILE_SIZE;
        int r = y / TILE_SIZE;
        if(c < 0 || r < 0 || r >= layout.length || c >= layout[0].length)
            return null;

        return layout[r][c];
    }

    private GameTile[][] makeDefaultMap(){
        int[][] map = {
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1},
                {1,0,2,2,2,2,2,0,0,0,12,0,0,0,0,1,0,0,0,0,1},
                {1,0,2,0,0,0,2,0,0,0,0,0,0,0,0,1,0,0,0,0,1},
                {1,0,2,0,0,0,2,0,0,0,0,0,0,0,0,2,0,0,0,0,1},
                {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,1},
                {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1},
                {1,0,0,0,0,0,0,3,4,3,3,3,3,3,0,1,0,0,0,0,1},
                {1,0,0,0,0,0,0,3,0,0,0,0,0,3,0,1,0,0,0,0,1},
                {1,0,0,0,0,0,0,3,3,3,3,3,3,3,0,1,0,0,0,0,1},
                {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
        };

        GameTile[][] newLayout = new GameTile[map.length][map[0].length];

        for(int r = 0; r < map.length; r++){
            for(int c = 0; c < map[0].length; c++){
                GameTile tile = new GameTile(TileType.FLOOR, Color.BLACK);
                TileType type = map[r][c] < 10 ? TileType.WALL : TileType.DELTA;

                switch (map[r][c] % 10){
                    case 1:
                        tile = new GameTile(type, Color.BLUE);
                        break;
                    case 2:
                        tile = new GameTile(type, Color.RED);
                        break;
                    case 3:
                        tile = new GameTile(type, Color.GREEN);
                        break;
                    case 4:
                        tile = new GameTile(type, Color.CYAN);
                        break;
                }
                newLayout[r][c] = tile;
            }
        }
        return newLayout;
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        for(int r = 0; r < layout.length; r++){
            for (int c = 0; c < layout[0].length; c++){

                paint.setColor(layout[r][c].color);
                Rect rect = new Rect(c * TILE_SIZE, r * TILE_SIZE, (c + 1) * TILE_SIZE, (r + 1) * TILE_SIZE);
                canvas.drawRect(rect, paint);
                if(layout[r][c].type == TileType.DELTA){
                    canvas.drawBitmap(delta_block, c * TILE_SIZE, r * TILE_SIZE, null);
                }
                else
                    canvas.drawBitmap(wall_block, c * TILE_SIZE, r * TILE_SIZE, null);
            }
        }
    }
}
