package com.jumpchamp.game.entity.items.coin;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.jumpchamp.game.assets.AssetDescriptors;
import com.jumpchamp.game.config.GameConfig;
import com.jumpchamp.game.entity.player.Player;
import com.jumpchamp.game.screen.GameScreen;

public class MachineGunSwapwnner extends Item {
    public Body b2body;
    private boolean setToDestroy;
    private boolean destroy;

    private TextureRegion gunRegion;


    private float stateTime;


    TextureAtlas gamePlayAtlas;

    public MachineGunSwapwnner(World world, GameScreen screen, float x, float y) {
        super(world, screen, x, y);

        TextureAtlas gamePlayAtlas = screen.getGame().getAssetManager().get(AssetDescriptors.ITEMS);
        gunRegion = gamePlayAtlas.findRegion("pistol");

        setBounds(getX(), getY(), 0.8f, 0.6f);
        setToDestroy = false;
        destroy = false;
    }

    @Override
    protected void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(15 / GameConfig.PPM);

        fdef.filter.categoryBits = GameConfig.ITEM_BIT;
        fdef.filter.maskBits = GameConfig.PLAYER_BIT;
        fdef.isSensor = true;


        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        shape.dispose();
    }

    @Override
    public void update(float dt) {
        stateTime += dt;
        if (setToDestroy && !destroy) {
            world.destroyBody(b2body);
            destroy = true;
        } else if (!destroy) {
            setRegion(gunRegion);
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
        screen.getGame().getAssetManager().get(AssetDescriptors.DIAMOND_SOUND).play();
        GameConfig.mGunActive = true;
    }


}
