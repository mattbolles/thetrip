package com.secondgame;

import com.badlogic.gdx.ScreenAdapter;
import com.secondgame.resource.GameState;

public class LoadingScreen extends ScreenAdapter {
    private SecondGame game;

    public LoadingScreen(SecondGame game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        System.out.println("loading...");
        game.loadScreen(GameState.INTRO);
    }
}
