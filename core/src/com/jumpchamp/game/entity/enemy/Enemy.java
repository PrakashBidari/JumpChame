package com.jumpchamp.game.entity.enemy;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.jumpchamp.game.entity.player.Player;
import com.jumpchamp.game.entity.player.bullet.Bullet;
import com.jumpchamp.game.screen.GameScreen;

public abstract class Enemy extends Sprite {
    protected World world;
    protected GameScreen screen;
    public Body b2body;
    public Vector2 velocity;
    protected MapObject object;


    public Enemy(GameScreen screen, MapObject object, float x, float y) {
        this.screen = screen;
        this.object = object;
        this.world = screen.getWorld();
        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(1.2f, -1.5f);
        //b2body.setActive(false);
    }

    protected abstract void defineEnemy();

    public abstract void update(float dt);

    public abstract void hitOnHead(Player player);


    public void reverseVelocity(boolean x, boolean y) {
        if (x) {
            velocity.x = -velocity.x;
        }
        if (y) {
            velocity.y = -velocity.y;
        }
    }

    public abstract void onBulletHit(Bullet userData);
}
