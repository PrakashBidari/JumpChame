package com.jumpchamp.game.entity.player.bullet;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.jumpchamp.game.assets.AssetDescriptors;
import com.jumpchamp.game.config.GameConfig;
import com.jumpchamp.game.screen.GameScreen;
import com.jumpchamp.game.tools.InputHandler;

public class MachineGun extends Bullet {

    private Animation moveAnimation;
    Body b2body;

    public MachineGun(GameScreen screen, float x, float y) {
        super(screen, x, y);
        TextureAtlas gamePlayAtlas = screen.getGame().getAssetManager().get(AssetDescriptors.BULLET);
        Array<TextureRegion> frames = new Array<>();

        for (int i = 1; i <= 5; i++) {
            frames.add(gamePlayAtlas.findRegion("FB00" + i));
        }

        moveAnimation = new Animation(0.05f, frames);


        setBounds(getX(), getY(), 1f, 0.25f);

        if (screen.getPlayer().isRunningRight()) {
            b2body.setLinearVelocity(new Vector2(5, 0));
        } else {
            b2body.setLinearVelocity(new Vector2(-5, 0));
        }

        b2body.setGravityScale(0);


    }

    @Override
    public void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = screen.getWorld().createBody(bdef);
        FixtureDef fdef = new FixtureDef();


        CircleShape shape = new CircleShape();
        shape.setRadius(0.11f);

        fdef.filter.categoryBits = GameConfig.BULLET_BIT;
        fdef.filter.maskBits = GameConfig.ENEMY_BIT |
                GameConfig.GROUND_BIT | GameConfig.GATE_BIT;

        fdef.isSensor = true;


        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        shape.dispose();
    }

    @Override
    public void update(float dt) {
        if (setToDestroy && !destroy) {
            screen.getWorld().destroyBody(b2body);
            InputHandler.removeMachineGun(this);
            destroy = true;
            stateTime = 0;
        } else if (!destroy) {
            setRegion(getFrame(dt));
            if (b2body.getLinearVelocity().x > 0) {
                setPosition(b2body.getPosition().x - getWidth() / 2 - 0.11f, b2body.getPosition().y - getHeight() / 2 - 0.01f);
            }
            if (b2body.getLinearVelocity().x < 0) {
                setPosition(b2body.getPosition().x - getWidth() / 2 + 0.11f, b2body.getPosition().y - getHeight() / 2 - 0.01f);

            }
        }

        if (stateTime > 1.5) {
            setToDestroy = true;
        }


    }

    @Override
    public void onGroundtHit() {
        setToDestroy = true;
    }

    protected TextureRegion getFrame(float dt) {
        stateTime += dt;
        TextureRegion region;
        region = (TextureRegion) moveAnimation.getKeyFrame(stateTime, true);

        if (b2body.getLinearVelocity().x > 0 && region.isFlipX() == true) {
            region.flip(true, false);

        }
        if (b2body.getLinearVelocity().x < 0 && region.isFlipX() == false) {
            region.flip(true, false);

        }

        return region;
    }

    @Override
    public void draw(Batch batch) {
        if (!destroy) {
            super.draw(batch);
        }

    }
}
