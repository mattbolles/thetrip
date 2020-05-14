package com.secondgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

//main class that initializes and loads everything
public class SecondGame extends Game {

	private LoadingScreen loadingScreen;
	private IntroScreen introScreen;
	private OptionsScreen optionsScreen;
	private TitleScreen titleScreen;
	private Level1Screen level1Screen;
	private Level2Screen level2Screen;
	private Level3Screen level3Screen;

	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	BitmapFont font;

	@Override
	public void create() {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		setScreen(new TitleScreen(this));
	}

	@Override
	public void dispose() {
		batch.dispose();
		shapeRenderer.dispose();
		font.dispose();
	}

	public void loadScreen(String screenToDisplay) {
		switch(screenToDisplay) {
			case "intro":
			case "menu":
			case "level1":
			case "level2":
			case "level3":
			case "options":
			case "gameover":
			case "endofgame":
		}
	}
}
