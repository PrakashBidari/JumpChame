package com.jumpchamp.game.entity.enemy.bullate;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.jumpchamp.game.entity.player.Player;
import com.jumpchamp.game.screen.GameScreen;

public abstract class EnemyBullate extends Sprite {
    protected float x;
    protected float y;
    protected GameScreen screen;
    protected TextureRegion region;
    protected Vector2 velocity;

    public EnemyBullate( GameScreen screen,float x, float y) {
        this.x = x;
        this.y = y;
        this.screen = screen;
        setPosition(x,y);
        defineBullate();
    }

    public abstract void defineBullate();
    public abstract void update(float dt);
    public abstract void onPlayerHit(Player player);
}
