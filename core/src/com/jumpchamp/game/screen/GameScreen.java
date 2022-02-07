package com.jumpchamp.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jumpchamp.game.JumpChampGame;
import com.jumpchamp.game.assets.AssetDescriptors;
import com.jumpchamp.game.config.GameConfig;
import com.jumpchamp.game.config.Lavels;
import com.jumpchamp.game.entity.enemy.Enemy;
import com.jumpchamp.game.entity.enemy.bullate.EnemyBullate;
import com.jumpchamp.game.entity.items.coin.Item;
import com.jumpchamp.game.entity.items.keys.Box;
import com.jumpchamp.game.entity.items.keys.Key;
import com.jumpchamp.game.entity.object.gate.Gate;
import com.jumpchamp.game.entity.object.water.Water;
import com.jumpchamp.game.entity.platform.Platform;
import com.jumpchamp.game.entity.player.Player;
import com.jumpchamp.game.entity.player.bullet.Bullet;
import com.jumpchamp.game.tools.B2WorldCreator;
import com.jumpchamp.game.tools.InputHandler;
import com.jumpchamp.game.tools.WorldContactListener;
import com.jumpchamp.game.utils.GdxUtils;

public class GameScreen implements Screen {

    SpriteBatch batch;
    private JumpChampGame game;

    private OrthographicCamera gamecam;
    private Viewport gameport;

    private World world;

    private HudScreen hudScreen;

    private Background background;
    private TextureRegion backgroundRegion;


    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private AssetManager assetManager;


    private InputHandler inputHandler;


    private Box2DDebugRenderer b2dr;
    public B2WorldCreator creator;

    public String lavel;

    public Lavels lavels;

    private Touchpad touchpad;

    Bullet bullet;

    //  Water water;


    public void setWorld(World world) {
        this.world = world;
    }

    public GameScreen(JumpChampGame game, int lavelNo) {
        this.game = game;
        this.batch = game.getBatch();
        this.assetManager = game.getAssetManager();
        loadAssetManager();

        gamecam = new OrthographicCamera();
        gameport = new StretchViewport(GameConfig.V_WIDTH / GameConfig.PPM, GameConfig.V_HEIGHT / GameConfig.PPM, gamecam);

        hudScreen = new HudScreen(this);




        mapLoader = new TmxMapLoader();
        //  lavel = "tileset/lavel1.tmx";
        lavels = new Lavels();
        map = mapLoader.load(lavels.lavels.get(lavelNo));

        renderer = new OrthogonalTiledMapRenderer(map, 1 / GameConfig.PPM);
        gamecam.position.set(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2, 0);


        world = new World(new Vector2(0, -24), true);
        b2dr = new Box2DDebugRenderer();

        creator = new B2WorldCreator(this);


        background = new Background(this);


        background.setPosition(0, 0);
        background.setSize(GameConfig.V_WIDTH / 100, GameConfig.V_HEIGHT / 100);


        inputHandler = new InputHandler(this);

        world.setContactListener(new WorldContactListener(this));


//          water = new Water(world, 6f, 1, 8, 2);
//         water.createBody(world, 6f, 1, 8, 2);

        Gdx.input.setInputProcessor(inputHandler);

    }


    @Override
    public void show() {

    }

    private void loadAssetManager() {
        //TextureAtlas
        assetManager.load(AssetDescriptors.PLAYER);
        assetManager.load(AssetDescriptors.ENEMY);
        assetManager.load(AssetDescriptors.KEYS);
        assetManager.load(AssetDescriptors.PLATFORM);
        assetManager.load(AssetDescriptors.BACKGROUND);
        assetManager.load(AssetDescriptors.COINS);
        assetManager.load(AssetDescriptors.ITEMS);
        assetManager.load(AssetDescriptors.EFFECT1);
        assetManager.load(AssetDescriptors.BULLET);


        //Sound
        assetManager.load(AssetDescriptors.COIN_SOUND);
        assetManager.load(AssetDescriptors.DIAMOND_SOUND);
        assetManager.load(AssetDescriptors.ENEMY_KILL_SOUND);
        assetManager.load(AssetDescriptors.ENEMY_KILL_SOUND1);
        assetManager.load(AssetDescriptors.PLAYER_JUMP_SOUND);
        assetManager.load(AssetDescriptors.GATE);
        assetManager.finishLoading();
    }


    @Override
    public void render(float dt) {
        update(dt);
        GdxUtils.clearScreen(Color.SKY);

        background.render(dt);
        renderer.render();

        b2dr.render(world, gamecam.combined);


//        if (Gdx.input.justTouched()) {
//            createBody();
//        }

        batch.setProjectionMatrix(gamecam.combined);
        batch.begin();


        for (Player player : creator.getPlayers()) {
            player.draw(game.getBatch());
        }
        for (Enemy enemy : creator.getEnemies()) {
            enemy.draw(game.getBatch());
        }

        for (EnemyBullate enemyBullate : creator.getEnemyBullate()) {
            enemyBullate.draw(game.getBatch());
        }
        for (Key key : creator.getKey()) {
            key.draw(game.getBatch());
        }
        for (Gate gate : creator.getGate()) {
            gate.draw(game.getBatch());
        }
        for (Item item : creator.getItems()) {
            item.draw(game.getBatch());
        }

        for (Platform platform : creator.getPlatform()) {
            platform.draw(game.getBatch());
        }

        for (Box box : creator.getBoxes()) {
            box.draw(game.getBatch());
        }

        for (Bullet bullet : inputHandler.getBullet()) {
            bullet.draw(game.getBatch());
        }




        batch.end();

        for (Water water : creator.getWaters()) {
            water.draw(gamecam);
        }
        game.getBatch().setProjectionMatrix(hudScreen.stage.getCamera().combined);
        hudScreen.stage.draw();
    }


    public void update(float dt) {
        world.step(1 / 60f, 6, 2);
        System.out.println("FPS: "+ Gdx.graphics.getFramesPerSecond());

        gamecam.update();
        renderer.setView(gamecam);

        hudScreen.update(dt);
        inputHandler.update(dt);
        for (Player player : creator.getPlayers()) {
            player.update(dt);
        }
        gameCamPosition();


        for (Bullet bullet : inputHandler.getBullet()) {
            bullet.update(dt);
        }

        for (Enemy enemy : creator.getEnemies()) {
            enemy.update(dt);
        }
        for (EnemyBullate enemyBullate : creator.getEnemyBullate()) {
            enemyBullate.update(dt);
        }
        for (Key key : creator.getKey()) {
            key.update(dt);
        }
        for (Gate gate : creator.getGate()) {
            gate.update(dt);
        }
        for (Item item : creator.getItems()) {
            item.update(dt);
        }
        for (Platform platform : creator.getPlatform()) {
            platform.update(dt);
        }

        for (Water water : creator.getWaters()) {
            water.update();
        }
        for (Box box : creator.getBoxes()) {
            box.update(dt);
        }


    }


    private void gameCamPosition() {
        TiledMapTileLayer mainLayer = (TiledMapTileLayer) map.getLayers().get(0);
        int tileSize = (int) mainLayer.getTileWidth();
        float mapWidth = mainLayer.getWidth() * tileSize;
        float mapHeight = mainLayer.getHeight() * tileSize;

        float playerX = 0;
        float playerY = 0;
        for (Player player : creator.getPlayers()) {
            playerX = player.body.getPosition().x;
            playerY = player.body.getPosition().y;
        }


        float visibleW = gameport.getWorldWidth() / 2 + (float) gameport.getScreenX() / (float) gameport.getScreenWidth() * gameport.getWorldWidth();//half of world visible
        float visibleH = gameport.getWorldHeight() / 2 + (float) gameport.getScreenY() / (float) gameport.getScreenHeight() * gameport.getWorldHeight();

        float cameraPosx = 0;
        float cameraPosy = 0;

        if (playerX < visibleW) {
            cameraPosx = visibleW;
        } else if (playerX > GameConfig.V_WIDTH - visibleW) {
            cameraPosx = GameConfig.V_WIDTH - visibleW;
        } else {
            cameraPosx = playerX;
        }

        if (playerY < visibleH) {
            cameraPosy = visibleH;
        } else if (playerY > GameConfig.V_HEIGHT - visibleH) {
            cameraPosy = GameConfig.V_HEIGHT - visibleH;
        } else {
            cameraPosy = playerY;
        }

        if (mapWidth / 100 - cameraPosx >= 6.85) {
            gamecam.position.x = cameraPosx;
        } else {
            gamecam.position.x = mapWidth / 100 - 6.85f;
        }
    }


    @Override
    public void resize(int width, int height) {
        gameport.apply();
        gameport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
//        for (Water water : creator.getWaters()) {
//            water.dispose();
//        }
        world.dispose();
        b2dr.dispose();
        map.dispose();
        renderer.dispose();
    }

    public JumpChampGame getGame() {
        return game;
    }

    public World getWorld() {
        return world;
    }

    public TiledMap getMap() {
        return map;
    }

    public Player getPlayer() {
        return creator.getPlayers().first();
    }

    public void setLavel(int lavel) {
        GameConfig.lavel = lavel;
    }
}
