package com.secondgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

public class TitleScreen extends ScreenAdapter {

    private SecondGame game;

    public TitleScreen(SecondGame game) {
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, .25f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.font.draw(game.batch, "Title Screen!", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .75f);
        game.font.draw(game.batch, "Click the circle to win.", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .5f);
        game.font.draw(game.batch, "Press space to play.", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .25f);
        game.batch.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}
