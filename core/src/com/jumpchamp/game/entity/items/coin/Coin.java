package com.jumpchamp.game.entity.items.coin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
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
import com.jumpchamp.game.screen.HudScreen;

public class Coin extends Item {
    public Body b2body;
    private boolean setToDestroy;
    private boolean destroy;

    ParticleEffect pe;
    private float stateTime;

    ParticleEffectPool pool;

    TextureAtlas gamePlayAtlas;

    public Coin(World world, GameScreen screen, float x, float y) {
        super(world, screen, x, y);

        pe = new ParticleEffect();
        gamePlayAtlas = screen.getGame().getAssetManager().get(AssetDescriptors.COINS);
        texture = gamePlayAtlas.findRegion("gold");

        pe.load(Gdx.files.internal("sprite/item/coin/coin_collected.p"), gamePlayAtlas);
        pe.getEmitters().first().setPosition(getX(), getY());

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
        shape.setRadius(18 / GameConfig.PPM);

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
        pe.update(dt);
        if (setToDestroy && !destroy) {
            world.destroyBody(b2body);
            // pe.start();
            destroy = true;
            stateTime = 0;
        } else if (!destroy) {
            setRegion(texture);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        }


    }


    int i = 0;

    @Override
    public void draw(Batch batch) {
        if (!destroy) {
            super.draw(batch);
        }

        if (setToDestroy && i <= 25) {
            pe.draw(screen.getGame().getBatch());
            i++;
        }

        if (pe.isComplete()) {
            pe.reset();
        }


    }

    @Override
    public void onHit(Player player) {
        HudScreen.addScore(10);
        setToDestroy = true;
        screen.getGame().getAssetManager().get(AssetDescriptors.COIN_SOUND).play();
    }


}
