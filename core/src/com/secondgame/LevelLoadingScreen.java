package com.secondgame;

import com.badlogic.gdx.ScreenAdapter;

public class LevelLoadingScreen extends ScreenAdapter {
    private SecondGame game;

    public LevelLoadingScreen(SecondGame game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        System.out.println("loading...");
        game.loadScreen(GameState.INTRO);
    }
}
