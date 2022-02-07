package com.jumpchamp.game.entity.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.jumpchamp.game.assets.AssetDescriptors;
import com.jumpchamp.game.config.GameConfig;
import com.jumpchamp.game.entity.player.Player;
import com.jumpchamp.game.entity.player.bullet.Bullet;
import com.jumpchamp.game.screen.GameScreen;

public class Saw extends Enemy {
    private TextureRegion sawRegion;
    private Animation walkAnimation;
    private float stateTime;


    public Saw(GameScreen screen, MapObject object, float x, float y) {
        super(screen, object, x, y);
        Array<TextureRegion> frames = new Array<>();
        TextureAtlas gamePlayAtlas = screen.getGame().getAssetManager().get(AssetDescriptors.ENEMY);
        sawRegion = gamePlayAtlas.findRegion("saw");
        for (int i = 0; i < 8; i++) {
            frames.add(new TextureRegion(sawRegion, i * 38, 0, 38, 38));
        }

        walkAnimation = new Animation(0.04f, frames);

        setBounds(getX(), getY(), 0.8f, 0.8f);

    }

    BodyDef bdef;

    @Override
    protected void defineEnemy() {
        bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        if (object.getProperties().containsKey("move")) {
            bdef.type = BodyDef.BodyType.DynamicBody;
            b2body = world.createBody(bdef);
        } else {
            bdef.type = BodyDef.BodyType.StaticBody;
            b2body = world.createBody(bdef);
        }
        FixtureDef fdef = new FixtureDef();

        CircleShape shape = new CircleShape();
        shape.setRadius(40 / GameConfig.PPM);


        fdef.filter.categoryBits = GameConfig.ENEMY_BIT;
        fdef.filter.maskBits = GameConfig.PLAYER_BIT |
                GameConfig.ENEMY_BLOCK_BIT | GameConfig.GROUND_BIT | GameConfig.BULLET_BIT;


        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        shape.dispose();
    }

    @Override
    public void update(float dt) {
        stateTime += dt;
        setRegion((TextureRegion) walkAnimation.getKeyFrame(stateTime, true));
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if (object.getProperties().containsKey("move")) {
            b2body.setLinearVelocity(velocity);
        }

    }

    @Override
    public void hitOnHead(Player player) {

    }


    @Override
    public void onBulletHit(Bullet userData) {

    }


}
