package com.jumpchamp.game.entity.object.gate;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.jumpchamp.game.assets.AssetDescriptors;
import com.jumpchamp.game.config.GameConfig;
import com.jumpchamp.game.entity.player.Player;
import com.jumpchamp.game.screen.GameScreen;

public class Gate extends Sprite {
    private World world;
    private Body b2body;
    protected TextureRegion texture;
    public Vector2 velocity;
    float movedDistance;
    private boolean playerContact = false;
    private float totalMove;
    private GameScreen screen;
    Array<Fixture> fixtures;


    public Gate(World world, GameScreen screen, float x, float y) {
        this.world = world;
        setPosition(x, y);
        this.screen = screen;
        TextureAtlas gamePlayAtlas = screen.getGame().getAssetManager().get(AssetDescriptors.KEYS);
        texture = gamePlayAtlas.findRegion("lock");
        defineGate();
        fixtures = b2body.getFixtureList();
        velocity = new Vector2(0, 0);
        movedDistance = getY();
    }

    private void defineGate() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.KinematicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.35f, 0.34f);

        fdef.filter.categoryBits = GameConfig.GATE_BIT;
        fdef.filter.maskBits = GameConfig.PLAYER_BIT | GameConfig.BULLET_BIT;


        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        shape.dispose();
    }

    public void update(float dt) {
        b2body.setLinearVelocity(velocity);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setBounds(getX(), getY(), 0.69f, 0.69f);
        setRegion(texture);
        moveGate();
    }


    public void moveGate() {
        totalMove = getY() - movedDistance;
        if (playerContact && totalMove < 0.35f) {
            velocity = new Vector2(0, 1);
            if (totalMove >= 0.33f) {
                System.out.println("here");
                b2body.setActive(false);
            }
        } else {
            velocity = new Vector2(0, 0);


        }
    }

    public void onHit(Player player) {
        if (player.simpleKey > 0 && totalMove < 0) {
            playerContact = true;
            player.simpleKey--;
        }
    }

}
