package com.jumpchamp.game.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class AssetPacker {
    private static final boolean DRAW_DEBUG_OUTLINE = false;

    private static final String RAW_ASSETS_PATH = "desktop/assets-raw";
    private static final String ASSETS_PATH = "android/assets";

    public static void main(String[] args) {
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.debug = DRAW_DEBUG_OUTLINE;
//        settings.pot = false;
//        settings.maxWidth = 484;
//        settings.maxHeight = 804;


        TexturePacker.process(settings, RAW_ASSETS_PATH + "/sprite/player",
                ASSETS_PATH + "/sprite/player", "players");

        TexturePacker.process(settings, RAW_ASSETS_PATH + "/sprite/enemy",
                ASSETS_PATH + "/sprite/enemy", "enemies");

        TexturePacker.process(settings, RAW_ASSETS_PATH + "/sprite/keys",
                ASSETS_PATH + "/sprite/keys", "keys");

        TexturePacker.process(settings, RAW_ASSETS_PATH + "/sprite/platform",
                ASSETS_PATH + "/sprite/platform", "platform");

        TexturePacker.process(settings, RAW_ASSETS_PATH + "/sprite/background",
                ASSETS_PATH + "/sprite/background", "background");

        TexturePacker.process(settings, RAW_ASSETS_PATH + "/sprite/item/item",
                ASSETS_PATH + "/sprite/item/item", "item");

        TexturePacker.process(settings, RAW_ASSETS_PATH + "/sprite/particleEffect",
                ASSETS_PATH + "/sprite/particleEffect", "effects1");

        TexturePacker.process(settings, RAW_ASSETS_PATH + "/sprite/player/bullet",
                ASSETS_PATH + "/sprite/player/bullet", "bullet");




    }


}
