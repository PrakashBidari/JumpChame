package com.jumpchamp.game.entity.player;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
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
import com.jumpchamp.game.JumpChampGame;
import com.jumpchamp.game.assets.AssetDescriptors;
import com.jumpchamp.game.config.GameConfig;
import com.jumpchamp.game.screen.GameScreen;
import com.jumpchamp.game.screen.HudScreen;
import com.jumpchamp.game.tools.InputHandler;

public class Player extends Sprite {

    private boolean runningRight;
    private TextureRegion playerJump;
    private Animation playerRun;
    private TextureRegion playerStand;
    private boolean GateHit = false;

    public boolean isRunningRight() {
        return runningRight;
    }

    public enum State {STANDING, RUNNING, JUMPING, FALLING}

    public State currentState;
    private State previousState;
    private float x, y;

    public World world;
    public Body body;

    private float stateTimer;
    private AssetManager assetManager;

    public int simpleKey = 5;
    public int lavelKey = 5;
    GameScreen screen;

    Array<Fixture> fixtures;

    public Player(GameScreen screen, float x, float y) {
        this.x = x;
        this.y = y;
        this.assetManager = screen.getGame().getAssetManager();
        this.screen = screen;
        currentState = State.STANDING;
        previousState = State.STANDING;
        this.world = screen.getWorld();
        stateTimer = 0;

        definePlayer();
        init();
        fixtures = body.getFixtureList();


    }

    private void init() {
        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.PLAYER);
        Array<TextureRegion> frames = new Array<>();
        playerStand = gamePlayAtlas.findRegion("p_idal");

        for (int i = 1; i < 11; i++) {
            frames.add(gamePlayAtlas.findRegion("p_walk" + i));
        }
        playerRun = new Animation(0.1f, frames);
        frames.clear();

        playerJump = gamePlayAtlas.findRegion("p_jump");


        setBounds(0, 0, 60 / GameConfig.PPM, 65 / GameConfig.PPM);
        setRegion(playerStand);
    }


    public void update(float dt) {
        setRegion(getFrame(dt));
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2 + 0.06f);
    }

    private TextureRegion getFrame(float dt) {
        stateTimer += dt;
        currentState = getState();

        TextureRegion region;

        switch (currentState) {
            case JUMPING:
                region = playerJump;
                break;
            case RUNNING:
                region = (TextureRegion) playerRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = playerStand;
                break;
        }

        if ((body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;

        return region;
    }

    public float getStateTimer() {
        return stateTimer;
    }

    private State getState() {
        if ((body.getLinearVelocity().y > 0 || (body.getLinearVelocity().y < 0 && previousState == State.JUMPING)) && !GameConfig.isInPlatform) {
            return State.JUMPING;
        } else if (body.getLinearVelocity().y < 0 && !GameConfig.isInPlatform) {
            return State.FALLING;
        } else if ((body.getLinearVelocity().x != 0 && !GameConfig.isInPlatform) || (InputHandler.keyDown == true && GameConfig.isInPlatform)) {
            return State.RUNNING;
        } else {
            return State.STANDING;
        }

    }

    FixtureDef fixtureDef;

    public FixtureDef getFixtureDef() {
        return fixtureDef;
    }

    private void definePlayer() {
        // System.out.println(getX() + "  " + getY());
        BodyDef bodyDef = new BodyDef();
        // bodyDef.position.set(400 / GameConfig.PPM, 300 / GameConfig.PPM);
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);


        fixtureDef = new FixtureDef();
//        CircleShape shape = new CircleShape();
//        shape.setRadius(30 / GameConfig.PPM);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.2f, 0.25f);

        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = GameConfig.PLAYER_BIT;
        fixtureDef.filter.maskBits = GameConfig.ENEMY_BIT | GameConfig.GROUND_BIT
                | GameConfig.GATE_BIT | GameConfig.SIMPLE_KEY_BIT
                | GameConfig.LAVEL_KEY_BIT | GameConfig.MAIN_GATE_BIT |
                GameConfig.WATER_BIT | GameConfig.ITEM_BIT | GameConfig.BOX_BIT |
                GameConfig.ROCKET_BIT
        ;
        fixtureDef.friction = 1f;


        body.createFixture(fixtureDef).setUserData(this);

        //create a leg here
        PolygonShape leg = new PolygonShape();
        Vector2[] vertices = new Vector2[4];
        vertices[0] = new Vector2(-19, -25).scl(1 / GameConfig.PPM);
        vertices[1] = new Vector2(19, -25).scl(1 / GameConfig.PPM);
        vertices[2] = new Vector2(-19, -24).scl(1 / GameConfig.PPM); // -12,22
        vertices[3] = new Vector2(19, -24).scl(1 / GameConfig.PPM);//12,22
        leg.set(vertices);


        fixtureDef.shape = leg;
        fixtureDef.filter.categoryBits = GameConfig.PLAYER_LEG_BIT;
        fixtureDef.filter.maskBits = GameConfig.ENEMY_HEAD_BIT | GameConfig.PLATFORM_BIT;
        fixtureDef.friction = 1f;


        body.createFixture(fixtureDef).setUserData(this);

        shape.dispose();
    }


    public void onMainGateHit() {
        JumpChampGame.lavelNo += 1;
        System.out.println(JumpChampGame.lavelNo);
        screen.getGame().setScreen(new GameScreen(screen.getGame(), JumpChampGame.lavelNo));
        HudScreen.lavelUp(JumpChampGame.lavelNo);
    }


}
