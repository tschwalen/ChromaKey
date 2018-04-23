package com.example.thoma.chromakey;

/**
 * Created by thoma on 1/7/2018.
 */

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;


public class Map {

    final int TILE_SIZE = 88;

    GameTile[][] layout;

    public Map(){
        this.layout = makeDefaultMap();
    }


    private GameTile[][] makeDefaultMap(){
        int[][] map = {
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                {1,0,2,2,2,2,2,0,0,0,0,0,0,0,0,1},
                {1,0,2,0,0,0,2,0,0,0,0,0,0,0,0,1},
                {1,0,2,0,0,0,2,0,0,0,0,0,0,0,0,1},
                {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                {1,0,0,0,0,0,0,3,4,3,3,3,3,3,0,1},
                {1,0,0,0,0,0,0,3,0,0,0,0,0,3,0,1},
                {1,0,0,0,0,0,0,3,3,3,3,3,3,3,0,1},
                {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
        };

        GameTile[][] newLayout = new GameTile[map.length][map[0].length];

        for(int r = 0; r < map.length; r++){
            for(int c = 0; c < map[0].length; c++){
                GameTile tile = new GameTile(TileType.FLOOR, Color.BLACK);
                switch (map[r][c]){
                    case 1:
                        tile = new GameTile(TileType.WALL, Color.BLUE);
                        break;
                    case 2:
                        tile = new GameTile(TileType.WALL, Color.RED);
                        break;
                    case 3:
                        tile = new GameTile(TileType.WALL, Color.GREEN);
                        break;
                    case 4:
                        tile = new GameTile(TileType.WALL, Color.CYAN);
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
            }
        }
    }
}
