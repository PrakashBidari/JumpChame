package com.jumpchamp.game.entity.player.bullet;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.jumpchamp.game.screen.GameScreen;

public abstract class Bullet extends Sprite {
    protected float x;
    protected float y;
    protected Vector2 velocity;
    protected boolean setToDestroy;
    protected boolean destroy;
    protected float stateTime;
    protected GameScreen screen;


    public Bullet(GameScreen screen, float x, float y) {
        this.x = x;
        this.y = y;
        this.screen = screen;
        setPosition(x, y);
        defineEnemy();
        setToDestroy = false;
        destroy = false;
    }

    public abstract void defineEnemy();

    public abstract void update(float dt);

    public void setSetToDestroy(boolean setToDestroy) {
        this.setToDestroy = setToDestroy;
    }

    public abstract void onGroundtHit();
}
