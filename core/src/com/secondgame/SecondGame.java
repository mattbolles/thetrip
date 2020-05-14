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
    private EndScreen endScreen;
    private GameOverScreen gameOverScreen;
    private MenuScreen menuScreen;

    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    BitmapFont font;

    @Override
    public void create() {
        setScreen(new LoadingScreen(this));
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
                if (introScreen == null) {
                    introScreen = new IntroScreen(this);
                }
                this.setScreen(introScreen);
                break;

            case "menu":
                if (menuScreen == null) {
                    menuScreen = new MenuScreen(this);
                }
                this.setScreen(menuScreen);
                break;

            case "level1":
                if (level1Screen == null) {
                    level1Screen = new Level1Screen(this);
                }
                this.setScreen(introScreen);
                break;

            case "level2":
                if (level2Screen == null) {
                    level2Screen = new Level2Screen(this);
                }
                this.setScreen(level2Screen);
                break;

            case "level3":
                if (level3Screen == null) {
                    level3Screen = new Level3Screen(this);
                }
                this.setScreen(level3Screen);
                break;

            case "options":
                if (optionsScreen == null) {
                    optionsScreen = new OptionsScreen(this);
                }
                this.setScreen(optionsScreen);
                break;

            case "gameover":
                if (gameOverScreen == null) {
                    gameOverScreen = new GameOverScreen(this);
                }
                this.setScreen(gameOverScreen);
                break;

            case "endofgame":
                if (endScreen == null) {
                    endScreen = new EndScreen(this);
                }
                this.setScreen(endScreen);
                break;

        }
    }
}
