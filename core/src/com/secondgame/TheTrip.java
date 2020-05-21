package com.secondgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.secondgame.resource.GameOptions;

//main class that initializes and loads everything
public class TheTrip extends Game {

    private GameOptions gameOptions;
    private IntroScreen introScreen;
    private OptionsScreen optionsScreen;
    private EndScreen endScreen;
    private GameOverScreen gameOverScreen;
    private MenuScreen menuScreen;
    private GameScreen gameScreen;

    public SpriteBatch batch;
    public BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        gameOptions = new GameOptions();
        setScreen(new IntroScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    public GameOptions getOptions() {
        return this.gameOptions;
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

            case "gamescreen":
                if (gameScreen == null) {
                    gameScreen = new GameScreen(this);
                }
                this.setScreen(gameScreen);
                break;


        }
    }
}
