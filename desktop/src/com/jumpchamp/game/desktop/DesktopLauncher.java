package com.jumpchamp.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jumpchamp.game.JumpChampGame;
import com.jumpchamp.game.config.GameConfig;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		config.height = GameConfig.V_HEIGHT;
//		config.width = GameConfig.V_WIDTH;
		new LwjglApplication(new JumpChampGame(), config);
	}
}
