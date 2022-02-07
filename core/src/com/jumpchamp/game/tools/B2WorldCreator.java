package com.jumpchamp.game.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.jumpchamp.game.config.GameConfig;
import com.jumpchamp.game.entity.enemy.Eagle;
import com.jumpchamp.game.entity.enemy.Enemy;
import com.jumpchamp.game.entity.enemy.Fly;
import com.jumpchamp.game.entity.enemy.Opposum;
import com.jumpchamp.game.entity.enemy.Saw;
import com.jumpchamp.game.entity.enemy.SnailWalk;
import com.jumpchamp.game.entity.enemy.Spike;
import com.jumpchamp.game.entity.enemy.bullate.EnemyBullate;
import com.jumpchamp.game.entity.enemy.bullate.Rocket;
import com.jumpchamp.game.entity.items.coin.Coin;
import com.jumpchamp.game.entity.items.coin.Diamond;
import com.jumpchamp.game.entity.items.coin.Item;
import com.jumpchamp.game.entity.items.coin.MachineGunSwapwnner;
import com.jumpchamp.game.entity.items.keys.Box;
import com.jumpchamp.game.entity.items.keys.Key;
import com.jumpchamp.game.entity.items.keys.LavelKey;
import com.jumpchamp.game.entity.items.keys.SimpleKey;
import com.jumpchamp.game.entity.object.gate.Gate;
import com.jumpchamp.game.entity.object.water.Water;
import com.jumpchamp.game.entity.platform.Platform;
import com.jumpchamp.game.entity.player.Player;
import com.jumpchamp.game.screen.GameScreen;

public class B2WorldCreator {

    protected Array<Player> player;
    protected static Array<SnailWalk> snailWalks;
    protected static Array<Saw> saws;
    protected static Array<Platform> platforms;

    protected static Array<SimpleKey> simpleKeys;
    protected static Array<LavelKey> lavelKeys;

    protected static Array<Gate> gates;

    protected static Array<Spike> spikes;

    protected static Array<Fly> flies;

    protected static Array<Opposum> opposums;

    protected static Array<Eagle> eagles;

    protected static Array<Coin> coins;

    protected static Array<Diamond> diamonds;


    protected static Array<Water> waters;

    protected static Array<Box> boxes;

    protected static Array<Rocket> rockets;

    protected static Array<MachineGunSwapwnner> machineGunSwapwnners;


    public B2WorldCreator(GameScreen screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //ground
        for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / GameConfig.PPM, (rect.getY() + rect.getHeight() / 2) / GameConfig.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / GameConfig.PPM, rect.getHeight() / 2 / GameConfig.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = GameConfig.GROUND_BIT;
            body.createFixture(fdef);
        }
        //player
        player = new Array<>();
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            player.add(new Player(screen, (rect.getX() + rect.getWidth() / 2) / GameConfig.PPM, (rect.getY() + rect.getHeight() / 2) / GameConfig.PPM));

        }

        //enemyBlocker
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / GameConfig.PPM, (rect.getY() + rect.getHeight() / 2) / GameConfig.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / GameConfig.PPM, rect.getHeight() / 2 / GameConfig.PPM);
            fdef.filter.categoryBits = GameConfig.ENEMY_BLOCK_BIT;
            fdef.filter.maskBits = GameConfig.ENEMY_BIT;


            fdef.shape = shape;
            body.createFixture(fdef);
        }


        //enemies
        snailWalks = new Array<>();
        saws = new Array<>();
        spikes = new Array<>();
        flies = new Array<>();
        opposums = new Array<>();
        eagles = new Array<>();
        rockets = new Array<>();

        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            if (object.getProperties().containsKey("snailwalk")) {
                snailWalks.add(new SnailWalk(screen, object, (rect.getX() + rect.getWidth() / 2) / GameConfig.PPM, (rect.getY() + rect.getHeight() / 2) / GameConfig.PPM));
            } else if (object.getProperties().containsKey("spike")) {
                spikes.add(new Spike(screen, object, (rect.getX() + rect.getWidth() / 2) / GameConfig.PPM, (rect.getY() + rect.getHeight() / 2) / GameConfig.PPM));
            } else if (object.getProperties().containsKey("flies")) {
                flies.add(new Fly(screen, object, (rect.getX() + rect.getWidth() / 2) / GameConfig.PPM, (rect.getY() + rect.getHeight() / 2) / GameConfig.PPM));
            } else if (object.getProperties().containsKey("opposum")) {
                opposums.add(new Opposum(screen, object, (rect.getX() + rect.getWidth() / 2) / GameConfig.PPM, (rect.getY() + rect.getHeight() / 2) / GameConfig.PPM));
            } else if (object.getProperties().containsKey("eagle")) {
                eagles.add(new Eagle(screen, object, (rect.getX() + rect.getWidth() / 2) / GameConfig.PPM, (rect.getY() + rect.getHeight() / 2) / GameConfig.PPM));
            } else if (object.getProperties().containsKey("rocket")) {
                rockets.add(new Rocket(screen, (rect.getX() + rect.getWidth() / 2) / GameConfig.PPM, (rect.getY() + rect.getHeight() / 2) / GameConfig.PPM));
            }

        }
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            saws.add(new Saw(screen, object, (rect.getX() + rect.getWidth() / 2) / GameConfig.PPM, (rect.getY() + rect.getHeight() / 2) / GameConfig.PPM));

        }

        //object
        gates = new Array<>();
        waters = new Array<>();
        boxes = new Array<>();
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            if (object.getProperties().containsKey("maingate")) {
                bdef.type = BodyDef.BodyType.StaticBody;
                bdef.position.set((rect.getX() + rect.getWidth() / 2) / GameConfig.PPM, (rect.getY() + rect.getHeight() / 2) / GameConfig.PPM);

                body = world.createBody(bdef);

                shape.setAsBox(rect.getWidth() / 2 / GameConfig.PPM, rect.getHeight() / 2 / GameConfig.PPM);
                fdef.filter.categoryBits = GameConfig.MAIN_GATE_BIT;
                fdef.filter.maskBits = GameConfig.PLAYER_BIT;
                fdef.isSensor = true;


                fdef.shape = shape;
                body.createFixture(fdef);
            }
            if (object.getProperties().containsKey("simplegate")) {
                gates.add(new Gate(world, screen, (rect.getX() + rect.getWidth() / 2) / GameConfig.PPM, (rect.getY() + rect.getHeight() / 2) / GameConfig.PPM));
            }

            if (object.getProperties().containsKey("water")) {
                waters.add(new Water(screen.getWorld(), (rect.getX() + rect.getWidth() / 2) / GameConfig.PPM, (rect.getY() + rect.getHeight() / 2) / GameConfig.PPM, rect.getWidth() / GameConfig.PPM, rect.getHeight() / GameConfig.PPM));
            }
            if (object.getProperties().containsKey("box1")) {
                boxes.add(new Box(screen, (rect.getX() + rect.getWidth() / 2) / GameConfig.PPM, (rect.getY() + rect.getHeight() / 2) / GameConfig.PPM));
            }


        }

        //items
        lavelKeys = new Array<>();
        simpleKeys = new Array<>();
        coins = new Array<>();
        diamonds = new Array<>();
        machineGunSwapwnners = new Array<>();
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            if (object.getProperties().containsKey("mainkey")) {
                lavelKeys.add(new LavelKey(world, screen, (rect.getX() + rect.getWidth() / 2) / GameConfig.PPM, (rect.getY() + rect.getHeight() / 2) / GameConfig.PPM));
            }
            if (object.getProperties().containsKey("simplekey")) {
                simpleKeys.add(new SimpleKey(world, screen, (rect.getX() + rect.getWidth() / 2) / GameConfig.PPM, (rect.getY() + rect.getHeight() / 2) / GameConfig.PPM));
            }
            if (object.getProperties().containsKey("coin")) {
                coins.add(new Coin(world, screen, (rect.getX() + rect.getWidth() / 2) / GameConfig.PPM, (rect.getY() + rect.getHeight() / 2) / GameConfig.PPM));
            }
            if (object.getProperties().containsKey("diamond")) {
                diamonds.add(new Diamond(world, screen, (rect.getX() + rect.getWidth() / 2) / GameConfig.PPM, (rect.getY() + rect.getHeight() / 2) / GameConfig.PPM));
            }
            if (object.getProperties().containsKey("mGun")) {
                machineGunSwapwnners.add(new MachineGunSwapwnner(world, screen, (rect.getX() + rect.getWidth() / 2) / GameConfig.PPM, (rect.getY() + rect.getHeight() / 2) / GameConfig.PPM));
            }

        }

        //platform
        platforms = new Array<>();
        for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            platforms.add(new Platform(screen, object, (rect.getX() + rect.getWidth() / 2) / GameConfig.PPM, (rect.getY() + rect.getHeight() / 2) / GameConfig.PPM));

        }


        //water
//        waters = new Array<>();
//        for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
//            Rectangle rect = ((RectangleMapObject) object).getRectangle();
//
//            waters.add(new Water(screen.getWorld(), (rect.getX() + rect.getWidth() / 2) / GameConfig.PPM, (rect.getY() + rect.getHeight() / 2) / GameConfig.PPM, rect.getWidth() / GameConfig.PPM, rect.getHeight() / GameConfig.PPM));
//
//        }

    }


    //player
    public Array<Player> getPlayers() {
        Array<Player> players = new Array<>();

        players.addAll(player);
        return players;
    }


    public Array<Enemy> getEnemies() {
        Array<Enemy> enemies = new Array<>();

        enemies.addAll(snailWalks);
        enemies.addAll(saws);
        enemies.addAll(flies);
        enemies.addAll(spikes);
        enemies.addAll(opposums);
        enemies.addAll(eagles);
        return enemies;
    }

    public Array<EnemyBullate> getEnemyBullate() {
        Array<EnemyBullate> enemyBullates = new Array<>();

        enemyBullates.addAll(rockets);
        return enemyBullates;
    }


    //keys
    public Array<Key> getKey() {
        Array<Key> keys = new Array<>();

        keys.addAll(simpleKeys);
        keys.addAll(lavelKeys);
        return keys;
    }

    //gates
    public Array<Gate> getGate() {
        Array<Gate> gate = new Array<>();

        gate.addAll(gates);
        return gate;
    }

    //items
    public Array<Item> getItems() {
        Array<Item> item = new Array<>();

        item.addAll(machineGunSwapwnners);
        item.addAll(coins);
        item.addAll(diamonds);

        return item;

    }


    //platforms
    public Array<Platform> getPlatform() {
        Array<Platform> platform = new Array<>();

        platform.addAll(platforms);
        return platform;
    }

    //water
    public Array<Water> getWaters() {
        Array<Water> water = new Array<>();

        water.addAll(waters);
        return water;
    }

    public Array<Box> getBoxes() {
        Array<Box> box = new Array<>();

        box.addAll(boxes);
        return box;
    }


    public static void removeFly(Fly fly) {
        flies.removeValue(fly, true);
    }

    public static void removeEagle(Eagle eagle) {
        eagles.removeValue(eagle, true);
    }

    public static void removeOpposum(Opposum opposum) {
        opposums.removeValue(opposum, true);
    }

    public static void removeSnail(SnailWalk snailWalk) {
        snailWalks.removeValue(snailWalk, true);
    }


}
