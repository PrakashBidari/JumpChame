package com.jumpchamp.game.entity.enemy.bullate;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.jumpchamp.game.assets.AssetDescriptors;
import com.jumpchamp.game.config.GameConfig;
import com.jumpchamp.game.entity.player.Player;
import com.jumpchamp.game.screen.GameScreen;

public class Rocket extends EnemyBullate {
    public Body b2body;
    protected Vector2 initialPosition;
    private boolean playerTouch = false;
    private float stateTimer;
    private TextureRegion rocket;
    private TextureRegion rocketPad;


    public Rocket(GameScreen screen, float x, float y) {
        super(screen, x, y);
        initialPosition = new Vector2(x, y);
        TextureAtlas gamePlayAtlas = screen.getGame().getAssetManager().get(AssetDescriptors.ENEMY);
        rocket = gamePlayAtlas.findRegion("rocket");
        rocketPad = gamePlayAtlas.findRegion("rocketLuncher");
        setBounds(0, 0, 40 / GameConfig.PPM, 40 / GameConfig.PPM);
        setRegion(rocket);
    }


    @Override
    public void defineBullate() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.KinematicBody;
        b2body = screen.getWorld().createBody(bdef);
        FixtureDef fdef = new FixtureDef();


        CircleShape shape = new CircleShape();
        shape.setRadius(0.2f);


        fdef.filter.categoryBits = GameConfig.ROCKET_BIT;
        fdef.filter.maskBits = GameConfig.PLAYER_BIT |
                GameConfig.GROUND_BIT;

        fdef.isSensor = true;


        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        shape.dispose();

    }


    @Override
    public void update(float dt) {
        stateTimer += dt;
        Vector2 targetBody = new Vector2(screen.getPlayer().body.getPosition());
        Vector2 bullateBody = new Vector2(b2body.getPosition());

        rotateTowardsPlayer(targetBody, bullateBody);

        followPlayer(screen.getPlayer().body.getPosition(), b2body.getPosition());
        if (playerTouch == true) {
            b2body.setTransform(initialPosition, b2body.getAngle());
            b2body.setAwake(true);
            playerTouch = false;
            stateTimer = 0;
        }

    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        batch.draw(rocketPad, initialPosition.x - 0.28f, initialPosition.y - 0.25f, 0.6f, 0.5f);

    }

    public void rotateTowardsPlayer(Vector2 targetBody, Vector2 bullateBody) {
        Vector2 fromShipToTarget = new Vector2(targetBody).sub(bullateBody);

        float angle = fromShipToTarget.angle();
        setOrigin(getWidth() / 2, getHeight() / 2);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRotation(angle - 95);
    }

    public void followPlayer(Vector2 targetBody, Vector2 bullateBody) {
        Vector2 direction = new Vector2();
        float actualDistance;
        float distance = screen.getPlayer().body.getPosition().x - b2body.getPosition().x;
        if (distance < 0) {
            actualDistance = -distance;
        } else {
            actualDistance = distance;
        }

        if (actualDistance <= 4 && stateTimer <= 5) {
            direction.x = (targetBody.x + 40) - (bullateBody.x + 40);
            direction.y = (targetBody.y + 40) - (bullateBody.y + 40);

            direction.nor();

            b2body.setTransform(b2body.getPosition(), b2body.getAngle() * MathUtils.degreesToRadians);
            b2body.setLinearVelocity(new Vector2(direction.x * 2.5f, direction.y * 2.5f));
        } else {
            b2body.setTransform(initialPosition, b2body.getAngle());
            b2body.setAwake(true);
            playerTouch = false;
            stateTimer = 0;
        }

        System.out.println(stateTimer);

    }


    @Override
    public void onPlayerHit(Player player) {
        playerTouch = true;
        System.out.println("touch");
    }
}
