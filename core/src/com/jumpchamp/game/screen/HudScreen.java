package com.jumpchamp.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jumpchamp.game.JumpChampGame;
import com.jumpchamp.game.config.GameConfig;
import com.jumpchamp.game.entity.player.Player;
import com.jumpchamp.game.tools.InputHandler;

public class HudScreen implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private static Integer score;

    Label countdownLabel;
    static Label scoreLabel;
    Label timeLabel;
    static Label levelLabel;
    Label worldLabel;
    Label champLabel;

    private Touchpad touchpad;
    GameScreen screen;

    Button upButton, leftButton, rightButton;
    private boolean keyLeft;
    private boolean keyRight;


    public HudScreen(GameScreen screen) {
        this.screen = screen;
        worldTimer = 300;
        timeCount = 0;
        score = 0;

        viewport = new FitViewport(GameConfig.V_WIDTH / 2, GameConfig.V_HEIGHT / 2, new OrthographicCamera());
        stage = new Stage(viewport, screen.batch);

        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.GOLD));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.GOLD));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.GOLD));
        levelLabel = new Label(String.format("%03d", JumpChampGame.lavelNo + 1), new Label.LabelStyle(new BitmapFont(), Color.GOLD));
        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.GOLD));
        champLabel = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.GOLD));

        table.add(champLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);

        table.row();

        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);


        Table tableButton = new Table();
        tableButton.bottom();
        tableButton.setFillParent(true);

        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        Texture buttonTex = new Texture(Gdx.files.internal("sprite/button/up.png"));
        TextureRegion buttonRegion = new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable(buttonRegion);
        upButton = new Button(buttonStyle);


        Button.ButtonStyle leftbButtonStyle = new Button.ButtonStyle();
        Texture leftButtonTex = new Texture(Gdx.files.internal("sprite/button/left.png"));
        TextureRegion leftButtonRegion = new TextureRegion(leftButtonTex);
        leftbButtonStyle.up = new TextureRegionDrawable(leftButtonRegion);
        leftButton = new Button(leftbButtonStyle);


        Button.ButtonStyle rightButtonStyle = new Button.ButtonStyle();
        Texture rightButtonTex = new Texture(Gdx.files.internal("sprite/button/right.png"));
        TextureRegion rightButtonRegion = new TextureRegion(rightButtonTex);
        rightButtonStyle.up = new TextureRegionDrawable(rightButtonRegion);
        rightButton = new Button(rightButtonStyle);

        tableButton.add(leftButton).padLeft(50).padBottom(20);
        tableButton.add(rightButton).padLeft(30).padBottom(20);
        tableButton.add(upButton).expandX().padLeft(330).padBottom(20);


      //  stage.addActor(tableButton);
        inputListeners();

    }


    public void inputListeners() {

        upButton.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (screen.getPlayer().currentState != Player.State.JUMPING && screen.getPlayer().currentState != Player.State.FALLING) {
                    screen.getPlayer().body.applyLinearImpulse(new Vector2(0, 9.2f), screen.getPlayer().body.getWorldCenter(), true);
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                InputHandler.keyDown = false;
                // System.out.println(i);
            }
        });

        leftButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                keyLeft = true;
                InputHandler.keyDown = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                keyLeft = false;
                InputHandler.keyDown = false;
            }
        });

        rightButton.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                keyRight = true;
                InputHandler.keyDown = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                keyRight = false;
                InputHandler.keyDown = false;
            }
        });
    }


    public void update(float dt) {
        timeCount += dt;
        if (timeCount >= 1) {
            worldTimer--;
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }


        if (keyLeft == true && screen.getPlayer().body.getLinearVelocity().x >= -1.8f) {
            screen.getPlayer().body.applyLinearImpulse(new Vector2(-1f, 0), screen.getPlayer().body.getWorldCenter(), true);
        } else if (keyRight == true && screen.getPlayer().body.getLinearVelocity().x <= 1.8f) {
            screen.getPlayer().body.applyLinearImpulse(new Vector2(1f, 0), screen.getPlayer().body.getWorldCenter(), true);
        }

    }

    public static void addScore(int value) {
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    public static void lavelUp(int value) {
        levelLabel.setText(String.format("%03d", value + 1));

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
