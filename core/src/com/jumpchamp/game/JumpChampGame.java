package com.jumpchamp.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.jumpchamp.game.assets.AssetsPaths;
import com.jumpchamp.game.screen.GameScreen;

public class JumpChampGame extends Game {
    private AssetManager assetManager;
    private SpriteBatch batch;
    public static int lavelNo = 0;


    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        assetManager = new AssetManager();
        assetManager.getLogger().setLevel(Logger.DEBUG);

        batch = new SpriteBatch();
        setScreen(new GameScreen(this,lavelNo));

    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    @Override
    public void dispose() {
        batch.dispose();
        assetManager.dispose();
    }


}
