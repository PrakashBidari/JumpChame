package com.jumpchamp.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jumpchamp.game.JumpChampGame;
import com.jumpchamp.game.config.GameConfig;

public class LavelChangeScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private JumpChampGame game;

    public LavelChangeScreen(JumpChampGame game) {
        this.game = game;
        viewport = new FitViewport(GameConfig.V_WIDTH, GameConfig.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.getBatch());

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.CHARTREUSE);
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label lavelCompleteLavel = new Label("LAVEL COMPLETE", font);
        Label nextLavellLabel = new Label("CLICK TO PLAY NEXT LAVEL", font);


        table.add(lavelCompleteLavel).expandX();
        table.row();
        table.add(nextLavellLabel).expandX().padTop(10f);


        stage.addActor(table);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            game.setScreen(new GameScreen(game, JumpChampGame.lavelNo));
            // game.setScreen(new LavelChangeScreen(game));
            dispose();
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        stage.dispose();
    }
}
