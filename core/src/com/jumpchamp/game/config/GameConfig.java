package com.jumpchamp.game.config;

import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;

public class GameConfig {
    public static final int V_WIDTH = 1366;
    public static final int V_HEIGHT = 690;
    public static final float PPM = 100;

    public static final short NOTHING_BIT = 0;
    public static final short DESTROY_BIT = 1;
    public static final short GROUND_BIT = 2;
    public static final short PLAYER_BIT = 4;
    public static final short ENEMY_BIT = 8;
    public static final short ENEMY_HEAD_BIT = 16;
    public static final short ENEMY_BLOCK_BIT = 32;
    public static final short PLAYER_LEG_BIT = 64;
    public static final short LAVEL_KEY_BIT = 128;
    public static final short SIMPLE_KEY_BIT = 256;
    public static final short GATE_BIT = 512;
    public static final short MAIN_GATE_BIT = 1024;
    public static final short WATER_BIT = 2048;
    public static final short BOX_BIT = 4096;
    public static final short SAW_BIT = 8192;
    public static final short PLATFORM_BIT = 16384;
    public static final short ITEM_BIT = -1;
    public static final short ROCKET_BIT = -2;
    public static final short BULLET_BIT = -32;


    public static int lavel = 1;
    public static boolean isInPlatform = false;
    public static boolean mGunActive = false;


    public static void setCategoryFilter(short filterBit, Fixture fixture) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }


}
