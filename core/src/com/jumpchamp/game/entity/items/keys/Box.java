package com.jumpchamp.game.entity.items.keys;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.jumpchamp.game.assets.AssetDescriptors;
import com.jumpchamp.game.config.GameConfig;
import com.jumpchamp.game.entity.player.Player;
import com.jumpchamp.game.screen.GameScreen;

public class Box extends Sprite {
    public Body b2body;
    protected TextureRegion texture;
    private GameScreen screen;


    public Box(GameScreen screen, float x, float y) {
        this.screen = screen;
        setPosition(x, y);
        defineKey();
        TextureAtlas gamePlayAtlas = screen.getGame().getAssetManager().get(AssetDescriptors.ITEMS);
        texture = gamePlayAtlas.findRegion("box");
        setBounds(getX(), getY(), 0.6f, 0.6f);

    }


    protected void defineKey() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = screen.getWorld().createBody(bdef);

        FixtureDef fdef = new FixtureDef();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.3f, 0.3f);

        fdef.filter.categoryBits = GameConfig.BOX_BIT;
        fdef.filter.maskBits = GameConfig.PLAYER_BIT | GameConfig.WATER_BIT | GameConfig.GROUND_BIT;

        fdef.density = 5f;
        fdef.friction = 0.5f;
        fdef.restitution = 0.5f;


        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
        shape.dispose();
    }


    public void update(float dt) {
        setRegion(texture);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }

}
