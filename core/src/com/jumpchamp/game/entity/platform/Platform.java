package com.jumpchamp.game.entity.platform;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.jumpchamp.game.assets.AssetDescriptors;
import com.jumpchamp.game.config.GameConfig;
import com.jumpchamp.game.screen.GameScreen;

public class Platform extends Sprite {

    protected float x;
    protected float y;
    protected GameScreen screen;
    public Body b2body;
    public Vector2 velocity;
    protected MapObject object;
    private final Vector2 initialPosition;
    Array<Fixture> fixtures;

    private TextureRegion brownPlatform;

    public Platform(GameScreen screen, MapObject object, float x, float y) {
        this.x = x;
        this.y = y;
        this.screen = screen;
        this.object = object;
        TextureAtlas gamePlayAtlas = screen.getGame().getAssetManager().get(AssetDescriptors.PLATFORM);
        brownPlatform = gamePlayAtlas.findRegion("Brown Off");
        initialPosition = new Vector2();
        initialPosition.set(x, y);
        setPosition(x, y);
        if (object.getProperties().containsKey("upDown1")) {
            velocity = new Vector2(0, 0.8f);
        } else {
            velocity = new Vector2(1, 0);
        }
        definePlatform();
        fixtures = b2body.getFixtureList();
        b2body.setLinearVelocity(velocity);

        setBounds(0, 0, 105 / GameConfig.PPM, 25 / GameConfig.PPM);
        setRegion(brownPlatform);
    }

    public void definePlatform() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.KinematicBody;
        b2body = screen.getWorld().createBody(bdef);
        FixtureDef fdef = new FixtureDef();


        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f, 0.05f);

        fdef.filter.categoryBits = GameConfig.PLATFORM_BIT;
        fdef.filter.maskBits = GameConfig.PLAYER_LEG_BIT |
                GameConfig.ENEMY_BLOCK_BIT | GameConfig.GROUND_BIT;
        fdef.friction = 1;


        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        shape.dispose();
    }


    public void update(float dt) {
        setRegion(brownPlatform);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

        if (object.getProperties().containsKey("upDown1")) {
            if (b2body.getPosition().y >= initialPosition.y + 1) {
                b2body.setLinearVelocity(new Vector2(0, -0.8f));
            } else if (b2body.getPosition().y <= initialPosition.y - 1) {
                b2body.setLinearVelocity(new Vector2(0, 0.8f));
            }
        } else {
            if (b2body.getPosition().x >= initialPosition.x + 1.5) {
                b2body.setLinearVelocity(new Vector2(-1, 0));
            } else if (b2body.getPosition().x <= initialPosition.x - 1.5) {
                b2body.setLinearVelocity(new Vector2(1, 0));
            }

        }
        if (screen.getPlayer().body.getLinearVelocity().y > 1) {
            for (Fixture fixture : fixtures)
                fixture.setSensor(true);
            b2body.resetMassData();
        } else {
            for (Fixture fixture : fixtures)
                fixture.setSensor(false);
            b2body.resetMassData();
        }


        if (GameConfig.isInPlatform) {

        }
    }

    public void reverseVelocity(boolean x, boolean y) {
        if (x) {
            velocity.x = -velocity.x;
        }
        if (y) {
            velocity.y = -velocity.y;
        }
    }

}
