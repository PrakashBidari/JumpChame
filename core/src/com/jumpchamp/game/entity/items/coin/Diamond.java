package com.jumpchamp.game.entity.items.coin;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.jumpchamp.game.assets.AssetDescriptors;
import com.jumpchamp.game.config.GameConfig;
import com.jumpchamp.game.entity.player.Player;
import com.jumpchamp.game.screen.GameScreen;

public class Diamond extends Item {
    public Body b2body;
    private boolean setToDestroy;
    private boolean destroy;

    private Animation diamondAnimation;


    private float stateTime;


    TextureAtlas gamePlayAtlas;

    public Diamond(World world, GameScreen screen, float x, float y) {
        super(world, screen, x, y);

        TextureAtlas gamePlayAtlas = screen.getGame().getAssetManager().get(AssetDescriptors.ITEMS);
        TextureAtlas effectAtlas = screen.getGame().getAssetManager().get(AssetDescriptors.EFFECT1);
        Array<TextureRegion> frames = new Array<>();
        for (int i = 1; i <= 5; i++) {
            frames.add(gamePlayAtlas.findRegion("gem-" + i));
        }
        diamondAnimation = new Animation(0.2f, frames);

        setBounds(getX(), getY(), 0.4f, 0.3f);
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
            // pe.start();
            destroy = true;
        } else if (!destroy) {
            setRegion((TextureRegion) diamondAnimation.getKeyFrame(stateTime, true));
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
        System.out.println("Coin Hit");
    }


}
