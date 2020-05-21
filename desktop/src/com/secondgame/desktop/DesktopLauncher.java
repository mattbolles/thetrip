package com.secondgame.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.secondgame.screen.TheTrip;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "The Trip";
		config.width = 1280; // 40 tiles, 32x32
		config.height = 960; // 30 tiles
		config.resizable = false;
		config.addIcon("images/icon128.png", Files.FileType.Internal);
		config.addIcon("images/icon64.png", Files.FileType.Internal);
		config.addIcon("images/icon32.png", Files.FileType.Internal);
		config.addIcon("images/icon16.png", Files.FileType.Internal);
		config.pauseWhenBackground = true;
		config.pauseWhenMinimized = true;
		// add in icon later
		new LwjglApplication(new TheTrip(), config);
	}
}
