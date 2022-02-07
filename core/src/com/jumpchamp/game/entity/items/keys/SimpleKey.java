package com.jumpchamp.game.entity.items.keys;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.jumpchamp.game.assets.AssetDescriptors;
import com.jumpchamp.game.config.GameConfig;
import com.jumpchamp.game.entity.player.Player;
import com.jumpchamp.game.screen.GameScreen;
import com.jumpchamp.game.tools.B2WorldCreator;

public class SimpleKey extends Key {
    public Body b2body;
    private boolean setToDestroy;
    private boolean destroy;


    public SimpleKey(World world, GameScreen screen, float x, float y) {
        super(world, screen, x, y);
        TextureAtlas gamePlayAtlas = screen.getGame().getAssetManager().get(AssetDescriptors.KEYS);
        texture = gamePlayAtlas.findRegion("key2");
        setBounds(getX(), getY(), 0.4f, 0.3f);
        setToDestroy = false;
        destroy = false;
    }


    @Override
    protected void defineKey() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(18 / GameConfig.PPM);

        fdef.filter.categoryBits = GameConfig.SIMPLE_KEY_BIT;
        fdef.filter.maskBits = GameConfig.PLAYER_BIT;
        fdef.isSensor = true;


        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        shape.dispose();
    }

    @Override
    public void update(float dt) {

        if (setToDestroy && !destroy) {
            world.destroyBody(b2body);
           // B2WorldCreator.removeSimpleKey(this);
            b2body = null;
            destroy = true;
        } else if (!destroy) {
            setRegion(texture);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        }


    }

    @Override
    public void draw(Batch batch) {
        if (!destroy) {
            super.draw(batch);
        }
    }

    @Override
    public void onHit(Player player) {
        setToDestroy = true;
        player.simpleKey += 1;
        System.out.println("simpleKey = " + player.simpleKey);
    }



}
