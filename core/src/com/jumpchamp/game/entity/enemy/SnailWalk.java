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

public class SnailWalk extends Enemy {
    // private TextureRegion enemyRegion;
    private TextureRegion enemyDeadRegion;
    private boolean setToDestroy;
    private float stateTime;
    private boolean destroy;

    private Animation walkAnimation;


    public SnailWalk(GameScreen screen, MapObject object, float x, float y) {
        super(screen, object, x, y);


        TextureAtlas gamePlayAtlas = screen.getGame().getAssetManager().get(AssetDescriptors.ENEMY);
        Array<TextureRegion> frames = new Array<>();

        frames.add(gamePlayAtlas.findRegion("snailWalk1"));
        frames.add(gamePlayAtlas.findRegion("snailWalk2"));

        walkAnimation = new Animation(0.2f, frames);

        enemyDeadRegion = gamePlayAtlas.findRegion("snailShell_upsidedown");
        setBounds(getX(), getY(), 0.4f, 0.4f);


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
        CircleShape shape = new CircleShape();
        shape.setRadius(20 / GameConfig.PPM);

        fdef.filter.categoryBits = GameConfig.ENEMY_BIT;
        fdef.filter.maskBits = GameConfig.ENEMY_BLOCK_BIT |
                GameConfig.PLAYER_BIT |
                GameConfig.GROUND_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);


        //create a head here
        PolygonShape head = new PolygonShape();
        Vector2[] vertices = new Vector2[4];
        vertices[0] = new Vector2(-12, 20).scl(1 / GameConfig.PPM);
        vertices[1] = new Vector2(12, 20).scl(1 / GameConfig.PPM);
        vertices[2] = new Vector2(-3, 3).scl(1 / GameConfig.PPM);
        vertices[3] = new Vector2(3, 3).scl(1 / GameConfig.PPM);
        head.set(vertices);


        fdef.shape = head;
        fdef.restitution = 1f;
        fdef.filter.categoryBits = GameConfig.ENEMY_HEAD_BIT;
        fdef.filter.maskBits = GameConfig.PLAYER_LEG_BIT | GameConfig.BULLET_BIT;
        b2body.createFixture(fdef).setUserData(this);

        shape.dispose();
    }


    @Override
    public void update(float dt) {
        stateTime += dt;

        if (setToDestroy && !destroy) {
            world.destroyBody(b2body);
            destroy = true;
            setRegion(enemyDeadRegion);
            stateTime = 0;
        } else if (!destroy) {
            b2body.setLinearVelocity(velocity);
            setRegion(getFrame(dt));
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        }

        if (setToDestroy && stateTime >= 1) {
            B2WorldCreator.removeSnail(this);
        }


    }

    private TextureRegion getFrame(float dt) {
        TextureRegion region;
        region = (TextureRegion) walkAnimation.getKeyFrame(stateTime, true);

        if (b2body.getLinearVelocity().x > 0 && region.isFlipX() == false) {
            region.flip(true, false);
        }
        if (b2body.getLinearVelocity().x < 0 && region.isFlipX() == true) {
            region.flip(true, false);
        }

        return region;
    }

    @Override
    public void draw(Batch batch) {
        if (!destroy || stateTime < 1) {
            super.draw(batch);
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
