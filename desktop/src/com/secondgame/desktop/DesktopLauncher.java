package com.secondgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.secondgame.SecondGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "The Trip";
		config.width = 1280; // 40 tiles, 32x32
		config.height = 960; // 30 tiles
		config.pauseWhenBackground = true;
		config.pauseWhenMinimized = true;
		// add in icon later
		new LwjglApplication(new SecondGame(), config);
	}
}
