package com.jumpchamp.game.config;

import com.badlogic.gdx.utils.Array;

public class Lavels {

    public static Array<String> lavels = new Array<>();

    public Lavels() {
        lavels.add("tileset/lavel1.tmx");
        lavels.add("tileset/lavel2.tmx");
        lavels.add("tileset/lavel3.tmx");
        lavels.add("tileset/lavel4.tmx");
        lavels.add("tileset/lavel5.tmx");
    }

    public static Array<String> getLavels() {
        return lavels;
    }
}
