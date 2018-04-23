package com.example.thoma.chromakey;

/**
 * Created by thoma on 1/7/2018.
 */


import android.graphics.Color;

public class GameTile {
    TileType type;
    int color;

    // might need?
    int row; int col;
    //

    public  GameTile(TileType type, int color){
        this.type = type;
        this.color = color;
    }



}
