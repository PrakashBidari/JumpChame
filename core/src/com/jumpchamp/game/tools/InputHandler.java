package com.jumpchamp.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.jumpchamp.game.assets.AssetDescriptors;
import com.jumpchamp.game.config.GameConfig;
import com.jumpchamp.game.entity.items.keys.Key;
import com.jumpchamp.game.entity.player.Player;
import com.jumpchamp.game.entity.player.bullet.Bullet;
import com.jumpchamp.game.entity.player.bullet.MachineGun;
import com.jumpchamp.game.screen.GameScreen;

public class InputHandler extends InputAdapter {
    public static boolean keyDown;
    private GameScreen screen;

    private boolean move = false;

    static private Array<MachineGun> machineGuns;


    public InputHandler(GameScreen screen) {
        this.screen = screen;
        machineGuns = new Array<>();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.LEFT) {
            keyDown = true;
        } else if (keycode == Input.Keys.RIGHT) {
            keyDown = true;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT) {
            keyDown = false;
        } else if (keycode == Input.Keys.RIGHT) {
            keyDown = false;
        }
        return true;
    }


    float stateTimer;

    public void update(float dt) {
        stateTimer += dt;
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && screen.getPlayer().currentState != Player.State.JUMPING && screen.getPlayer().currentState != Player.State.FALLING) {
            screen.getGame().getAssetManager().get(AssetDescriptors.PLAYER_JUMP_SOUND).play();
            screen.getPlayer().body.applyLinearImpulse(new Vector2(0, 9.5f), screen.getPlayer().body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && screen.getPlayer().body.getLinearVelocity().x <= 2) {
            screen.getPlayer().body.applyLinearImpulse(new Vector2(1f, 0), screen.getPlayer().body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && screen.getPlayer().body.getLinearVelocity().x >= -2) {
            screen.getPlayer().body.applyLinearImpulse(new Vector2(-1f, 0), screen.getPlayer().body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && GameConfig.mGunActive) {
            machineGuns.add(new MachineGun(screen, screen.getPlayer().body.getPosition().x, screen.getPlayer().body.getPosition().y));
            stateTimer = 0;
        }
    }

    public Array<Bullet> getBullet() {
        Array<Bullet> bullets = new Array<>();
        bullets.addAll(machineGuns);

        return bullets;
    }

    public static void removeMachineGun(MachineGun machineGun) {
        machineGuns.removeValue(machineGun, true);
    }
}
