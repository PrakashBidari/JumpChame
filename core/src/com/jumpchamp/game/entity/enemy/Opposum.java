package com.jumpchamp.game.entity.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.jumpchamp.game.assets.AssetDescriptors;
import com.jumpchamp.game.config.GameConfig;
import com.jumpchamp.game.entity.player.Player;
import com.jumpchamp.game.entity.player.bullet.Bullet;
import com.jumpchamp.game.screen.GameScreen;
import com.jumpchamp.game.screen.HudScreen;
import com.jumpchamp.game.tools.B2WorldCreator;

public class Opposum extends Enemy {
    private Animation enemyDeadAnimation;
    private boolean setToDestroy;
    private float stateTime;
    private boolean destroy;

    public enum State {DEAD, RUNNING}

    private Animation walkAnimation;


    public Opposum(GameScreen screen, MapObject object, float x, float y) {
        super(screen, object, x, y);


        TextureAtlas gamePlayAtlas = screen.getGame().getAssetManager().get(AssetDescriptors.ENEMY);
        TextureAtlas effectAtlas = screen.getGame().getAssetManager().get(AssetDescriptors.EFFECT1);
        Array<TextureRegion> frames = new Array<>();
        for (int i = 1; i <= 6; i++) {
            frames.add(gamePlayAtlas.findRegion("opossum-" + i));
        }
        walkAnimation = new Animation(0.2f, frames);
        frames.clear();

        for (int i = 1; i <= 6; i++) {
            frames.add(effectAtlas.findRegion("enemy-death-" + i));
        }
        enemyDeadAnimation = new Animation(0.1f, frames);

        setBounds(getX(), getY(), 0.8f, 0.8f);


        stateTime = 0;
        setToDestroy = false;
        destroy = false;


    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.26f, 0.20f);

        fdef.filter.categoryBits = GameConfig.ENEMY_BIT;
        fdef.filter.maskBits = GameConfig.ENEMY_BLOCK_BIT |
                GameConfig.PLAYER_BIT |
                GameConfig.GROUND_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);


        //create a head here
        PolygonShape head = new PolygonShape();
        Vector2[] vertices = new Vector2[4];
        vertices[0] = new Vector2(-22, 20).scl(1 / GameConfig.PPM);
        vertices[1] = new Vector2(22, 20).scl(1 / GameConfig.PPM);
        vertices[2] = new Vector2(-3, 3).scl(1 / GameConfig.PPM);
        vertices[3] = new Vector2(3, 3).scl(1 / GameConfig.PPM);
        head.set(vertices);


        fdef.shape = head;
        fdef.restitution = 1f;
        fdef.filter.categoryBits = GameConfig.ENEMY_HEAD_BIT;
        fdef.filter.maskBits = GameConfig.PLAYER_LEG_BIT |
                GameConfig.BULLET_BIT |
                GameConfig.ENEMY_BIT;
        b2body.createFixture(fdef).setUserData(this);

        shape.dispose();
    }


    @Override
    public void update(float dt) {
        stateTime += dt;

        if (setToDestroy && !destroy) {
            world.destroyBody(b2body);
            destroy = true;
            stateTime = 0;
        } else if (!destroy) {
            b2body.setLinearVelocity(velocity);
            setRegion(getFrame(dt));
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 + 0.2f);
        }

        if (setToDestroy && stateTime >= 1) {
            B2WorldCreator.removeOpposum(this);
        }

    }

    private TextureRegion getFrame(float dt) {
        TextureRegion region;
        if (!destroy) {
            region = (TextureRegion) walkAnimation.getKeyFrame(stateTime, true);

            if (b2body.getLinearVelocity().x > 0 && region.isFlipX() == false) {
                region.flip(true, false);
            }
            if (b2body.getLinearVelocity().x < 0 && region.isFlipX() == true) {
                region.flip(true, false);
            }
        } else {
            region = (TextureRegion) enemyDeadAnimation.getKeyFrame(stateTime, true);
        }


        return region;
    }

    @Override
    public void draw(Batch batch) {
        if (!destroy || stateTime < 0.5) {
            super.draw(batch);
        }
        if (setToDestroy) {
            setRegion(getFrame(stateTime));
        }

    }

    @Override
    public void hitOnHead(Player player) {
        setToDestroy = true;
        HudScreen.addScore(10);
    }


    @Override
    public void onBulletHit(Bullet bullet) {
        setToDestroy = true;
        bullet.setSetToDestroy(true);

    }
}
