package com.jumpchamp.game.tools;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.jumpchamp.game.assets.AssetDescriptors;
import com.jumpchamp.game.config.GameConfig;
import com.jumpchamp.game.entity.enemy.Enemy;
import com.jumpchamp.game.entity.enemy.Saw;
import com.jumpchamp.game.entity.enemy.bullate.Rocket;
import com.jumpchamp.game.entity.items.coin.Item;
import com.jumpchamp.game.entity.items.keys.LavelKey;
import com.jumpchamp.game.entity.items.keys.SimpleKey;
import com.jumpchamp.game.entity.object.gate.Gate;
import com.jumpchamp.game.entity.object.water.Pair;
import com.jumpchamp.game.entity.object.water.Water;
import com.jumpchamp.game.entity.platform.Platform;
import com.jumpchamp.game.entity.player.Player;
import com.jumpchamp.game.entity.player.bullet.Bullet;
import com.jumpchamp.game.screen.GameScreen;

public class WorldContactListener implements ContactListener {


    GameScreen screen;

    public WorldContactListener(GameScreen screen) {
        this.screen = screen;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case GameConfig.ENEMY_BIT | GameConfig.ENEMY_BLOCK_BIT:
                if (fixA.getFilterData().categoryBits == GameConfig.ENEMY_BIT) {
                    System.out.println("Touch");
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                } else {
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                }
                break;
            case GameConfig.ENEMY_HEAD_BIT | GameConfig.PLAYER_LEG_BIT:
                if (fixA.getFilterData().categoryBits == GameConfig.ENEMY_HEAD_BIT) {
                    screen.getGame().getAssetManager().get(AssetDescriptors.ENEMY_KILL_SOUND1).play();
                    ((Enemy) fixA.getUserData()).hitOnHead((Player) fixB.getUserData());
                } else {
                    screen.getGame().getAssetManager().get(AssetDescriptors.ENEMY_KILL_SOUND1).play();
                    ((Enemy) fixB.getUserData()).hitOnHead((Player) fixA.getUserData());
                }
                break;
            case GameConfig.PLAYER_BIT | GameConfig.GATE_BIT:
                if (fixA.getFilterData().categoryBits == GameConfig.GATE_BIT) {
                    screen.getGame().getAssetManager().get(AssetDescriptors.GATE).play();
                    ((Gate) fixA.getUserData()).onHit((Player) fixB.getUserData());
                } else {
                    screen.getGame().getAssetManager().get(AssetDescriptors.GATE).play();
                    ((Gate) fixB.getUserData()).onHit((Player) fixA.getUserData());
                }
                break;
            case GameConfig.BULLET_BIT | GameConfig.GATE_BIT:
                if (fixA.getFilterData().categoryBits == GameConfig.BULLET_BIT) {
                    ((Bullet) fixA.getUserData()).onGroundtHit();
                } else {
                    ((Bullet) fixB.getUserData()).onGroundtHit();
                }
                break;
            case GameConfig.PLAYER_BIT | GameConfig.MAIN_GATE_BIT:
                if (fixA.getFilterData().categoryBits == GameConfig.PLAYER_BIT) {
                    if (screen.getPlayer().lavelKey > 0) {
                        ((Player) fixA.getUserData()).onMainGateHit();
                    }

                } else {
                    if (screen.getPlayer().lavelKey > 0) {
                        ((Player) fixB.getUserData()).onMainGateHit();
                    }
                }
                break;
            case GameConfig.PLAYER_BIT | GameConfig.SIMPLE_KEY_BIT:
                if (fixA.getFilterData().categoryBits == GameConfig.SIMPLE_KEY_BIT) {
                    ((SimpleKey) fixA.getUserData()).onHit((Player) fixB.getUserData());
                } else {
                    ((SimpleKey) fixB.getUserData()).onHit((Player) fixA.getUserData());
                }
                break;
            case GameConfig.PLAYER_BIT | GameConfig.LAVEL_KEY_BIT:
                if (fixA.getFilterData().categoryBits == GameConfig.LAVEL_KEY_BIT) {
                    ((LavelKey) fixA.getUserData()).onHit((Player) fixB.getUserData());
                } else {
                    ((LavelKey) fixB.getUserData()).onHit((Player) fixA.getUserData());
                }
                break;
            case GameConfig.PLAYER_BIT | GameConfig.ITEM_BIT:
                System.out.println("coin");
                if (fixA.getFilterData().categoryBits == GameConfig.ITEM_BIT) {
                    System.out.println("coin");
                    ((Item) fixA.getUserData()).onHit((Player) fixB.getUserData());
                } else {
                    ((Item) fixB.getUserData()).onHit((Player) fixA.getUserData());
                }
                break;
            case GameConfig.SAW_BIT | GameConfig.ENEMY_BLOCK_BIT:
                if (fixA.getFilterData().categoryBits == GameConfig.SAW_BIT) {
                    ((Saw) fixA.getUserData()).reverseVelocity(true, false);
                } else {
                    ((Saw) fixB.getUserData()).reverseVelocity(true, false);
                }
                break;

            case GameConfig.PLATFORM_BIT | GameConfig.ENEMY_BLOCK_BIT:
                if (fixA.getFilterData().categoryBits == GameConfig.PLATFORM_BIT) {
                    ((Platform) fixA.getUserData()).reverseVelocity(true, false);
                } else {
                    ((Platform) fixB.getUserData()).reverseVelocity(true, false);
                }
                break;
            case GameConfig.PLAYER_BIT | GameConfig.ROCKET_BIT:
                if (fixA.getFilterData().categoryBits == GameConfig.ROCKET_BIT) {
                    ((Rocket) fixA.getUserData()).onPlayerHit((Player) fixB.getUserData());
                } else {
                    ((Rocket) fixB.getUserData()).onPlayerHit((Player) fixA.getUserData());
                }
                break;
            case GameConfig.ENEMY_BIT | GameConfig.BULLET_BIT:
                if (fixA.getFilterData().categoryBits == GameConfig.ENEMY_BIT) {
                    screen.getGame().getAssetManager().get(AssetDescriptors.ENEMY_KILL_SOUND1).play();
                    ((Enemy) fixA.getUserData()).onBulletHit((Bullet) fixB.getUserData());
                } else {
                    screen.getGame().getAssetManager().get(AssetDescriptors.ENEMY_KILL_SOUND1).play();
                    ((Enemy) fixB.getUserData()).onBulletHit((Bullet) fixA.getUserData());
                }
                break;
            case GameConfig.GROUND_BIT | GameConfig.BULLET_BIT:
                if (fixA.getFilterData().categoryBits == GameConfig.ENEMY_BIT) {
                    ((Bullet) fixA.getUserData()).onGroundtHit();
                } else {
                    ((Bullet) fixB.getUserData()).onGroundtHit();
                }
                break;
            case GameConfig.PLAYER_LEG_BIT | GameConfig.PLATFORM_BIT:
                GameConfig.isInPlatform = true;
                break;

        }
        waterContactBegin(contact);


    }

    private void waterContactBegin(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getBody().getUserData() instanceof Water && fixtureB.getBody().getType() == BodyDef.BodyType.DynamicBody) {
            Water water = (Water) fixtureA.getBody().getUserData();
            System.out.println(water.getDensity());
            water.getFixturePairs().add(new Pair<Fixture, Fixture>(fixtureA, fixtureB));
        } else if (fixtureB.getBody().getUserData() instanceof Water && fixtureA.getBody().getType() == BodyDef.BodyType.DynamicBody) {
            Water water = (Water) fixtureB.getBody().getUserData();
            System.out.println(water.getDensity());
            water.getFixturePairs().add(new Pair<Fixture, Fixture>(fixtureB, fixtureA));
        }


    }


    private void waterContactEnd(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getBody().getUserData() instanceof Water && fixtureB.getBody().getType() == BodyDef.BodyType.DynamicBody) {
            Water water = (Water) fixtureA.getBody().getUserData();
            water.getFixturePairs().remove(new Pair<Fixture, Fixture>(fixtureA, fixtureB));
        } else if (fixtureB.getBody().getUserData() instanceof Water && fixtureA.getBody().getType() == BodyDef.BodyType.DynamicBody) {
            Water water = (Water) fixtureB.getBody().getUserData();
            water.getFixturePairs().add(new Pair<Fixture, Fixture>(fixtureA, fixtureB));
        }
    }


    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case GameConfig.PLAYER_LEG_BIT | GameConfig.PLATFORM_BIT:
                GameConfig.isInPlatform = false;
                break;
        }
        waterContactEnd(contact);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
