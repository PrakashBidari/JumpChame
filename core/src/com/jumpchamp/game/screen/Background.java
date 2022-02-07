package com.jumpchamp.game.screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jumpchamp.game.JumpChampGame;
import com.jumpchamp.game.assets.AssetDescriptors;
import com.jumpchamp.game.config.GameConfig;

public class Background {

    private Viewport viewport;
    private float x;
    private float y;
    private float width;
    private float height;
    GameScreen screen;
    OrthographicCamera camera;

    private TextureRegion backgroundRegion;

    public Background(GameScreen screen) {
        this.screen = screen;
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.V_WIDTH / 2, GameConfig.V_HEIGHT / 2, new OrthographicCamera());
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        TextureAtlas gamePlayAtlas = screen.getGame().getAssetManager().get(AssetDescriptors.BACKGROUND);
        if(JumpChampGame.lavelNo==0){
            backgroundRegion = gamePlayAtlas.findRegion("back");
        }else if(JumpChampGame.lavelNo==1){
            backgroundRegion = gamePlayAtlas.findRegion("ocean");
        }else{
            backgroundRegion = gamePlayAtlas.findRegion("sky");
        }

    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;


    }

    public void setSize(float width, float height) {
        //viewport.apply();
        this.width = width;
        this.height = height;

    }

    public void render(float dt) {
        screen.batch.setProjectionMatrix(camera.combined);
        screen.batch.begin();
        screen.batch.draw(backgroundRegion, -1f, -0.8f,
                2f, 1.6f);
        screen.batch.end();
    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

}
