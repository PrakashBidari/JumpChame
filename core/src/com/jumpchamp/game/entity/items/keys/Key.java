package com.jumpchamp.game.entity.items.keys;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.jumpchamp.game.entity.player.Player;
import com.jumpchamp.game.screen.GameScreen;

public abstract class Key extends Sprite {
    protected World world;
    protected GameScreen screen;
    protected TextureRegion texture;


    public Key(World world, GameScreen screen, float x, float y) {
        this.screen = screen;
        this.world = screen.getWorld();
        setPosition(x, y);
        defineKey();
    }

    protected abstract void defineKey();

    public abstract void update(float dt);


    public abstract void onHit(Player userData);

}
