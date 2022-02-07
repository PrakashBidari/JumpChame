package com.jumpchamp.game.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetDescriptors {

    //TextureAtlas
    public static final AssetDescriptor<TextureAtlas> PLAYER = new AssetDescriptor<TextureAtlas>(AssetsPaths.PLAYER, TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> ENEMY = new AssetDescriptor<TextureAtlas>(AssetsPaths.ENEMY, TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> KEYS = new AssetDescriptor<TextureAtlas>(AssetsPaths.KEYS, TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> PLATFORM = new AssetDescriptor<TextureAtlas>(AssetsPaths.PLATFORM, TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> BACKGROUND = new AssetDescriptor<TextureAtlas>(AssetsPaths.BACKGROUND, TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> COINS = new AssetDescriptor<TextureAtlas>(AssetsPaths.COINS, TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> EFFECT1 = new AssetDescriptor<TextureAtlas>(AssetsPaths.EFFECT1, TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> ITEMS = new AssetDescriptor<TextureAtlas>(AssetsPaths.ITEMS, TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> BULLET = new AssetDescriptor<TextureAtlas>(AssetsPaths.BULLET, TextureAtlas.class);





    //Sound
    public static final AssetDescriptor<Sound> COIN_SOUND = new AssetDescriptor<>(AssetsPaths.COIN_SOUND, Sound.class);
    public static final AssetDescriptor<Sound> DIAMOND_SOUND = new AssetDescriptor<>(AssetsPaths.DIAMOND_SOUND, Sound.class);
    public static final AssetDescriptor<Sound> ENEMY_KILL_SOUND = new AssetDescriptor<>(AssetsPaths.ENEMY_KILL_SOUND, Sound.class);
    public static final AssetDescriptor<Sound> PLAYER_JUMP_SOUND = new AssetDescriptor<>(AssetsPaths.PLAYER_JUMP_SOUND, Sound.class);
    public static final AssetDescriptor<Sound> ENEMY_KILL_SOUND1 = new AssetDescriptor<>(AssetsPaths.ENEMY_KILL_SOUND1, Sound.class);
    public static final AssetDescriptor<Sound> GATE = new AssetDescriptor<>(AssetsPaths.GATE, Sound.class);



    private AssetDescriptors() {

    }
}
