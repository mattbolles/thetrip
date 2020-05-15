package com.secondgame;

import com.badlogic.gdx.ScreenAdapter;

public class LoadingScreen extends ScreenAdapter {
    private SecondGame game;

    public LoadingScreen(SecondGame game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        game.loadScreen(GameState.MENU);
    }
}
