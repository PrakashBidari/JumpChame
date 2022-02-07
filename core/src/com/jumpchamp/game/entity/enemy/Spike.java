package com.jumpchamp.game.entity.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.jumpchamp.game.assets.AssetDescriptors;
import com.jumpchamp.game.config.GameConfig;
import com.jumpchamp.game.entity.player.Player;
import com.jumpchamp.game.entity.player.bullet.Bullet;
import com.jumpchamp.game.screen.GameScreen;

public class Spike extends Enemy {
    private TextureRegion spikeRegion;
    private float stateTime;

    public Spike(GameScreen screen, MapObject object, float x, float y) {
        super(screen, object, x, y);
        TextureAtlas gamePlayAtlas = screen.getGame().getAssetManager().get(AssetDescriptors.ENEMY);
        spikeRegion = gamePlayAtlas.findRegion("spike");
        setBounds(getX(), getY(), 0.8f, 0.8f);
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.4f, 0.2f);


        fdef.filter.categoryBits = GameConfig.ENEMY_BIT;
        fdef.filter.maskBits = GameConfig.PLAYER_BIT | GameConfig.BULLET_BIT;


        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        shape.dispose();
    }

    @Override
    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 + 0.055f);
        setRegion(spikeRegion);
    }

    @Override
    public void hitOnHead(Player player) {

    }


    @Override
    public void onBulletHit(Bullet userData) {

    }
}
