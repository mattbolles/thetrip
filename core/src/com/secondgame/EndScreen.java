package com.secondgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.secondgame.resource.GameInfo;
import com.secondgame.resource.GameState;

public class EndScreen extends ScreenAdapter {
    private TheTrip game;
    private Stage stage1;
    private Stage stage2;
    float timer;

    public EndScreen(TheTrip game) {
        this.game = game;
        stage1 = new Stage(new ScreenViewport());
        stage2 = new Stage(new ScreenViewport());

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage1);
        Gdx.input.setInputProcessor(stage2);
        Label intro1 = new Label("CONGRATULATIONS... YOU WIN", GameInfo.LABEL_STYLE_18);
        intro1.setPosition(Gdx.graphics.getWidth() / 2 - intro1.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - intro1.getHeight() / 2);
        stage1.addActor(intro1);

        Label intro2 = new Label("BUT THE ENEMY WAS ONLY WITHIN", GameInfo.LABEL_STYLE_18);
        intro2.setPosition(Gdx.graphics.getWidth() / 2 - intro2.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - intro2.getHeight() / 2);
        stage2.addActor(intro2);

    }

    @Override
    public void render(float delta) {
        timer++;
        System.out.println("timer: " + timer);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //skip intro if any key pressed or mouse clicked
        /*if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            game.loadScreen(GameState.MENU);
        }*/

        if (timer < 250) {
            stage1.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            stage1.draw();
        }
        if (timer > 250) {
            stage1.clear();

            if (timer < 500) {
                stage2.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
                stage2.draw();
            }
        }
        if (timer > 500) {
            stage2.clear();
            game.loadScreen(GameState.MENU);
        }

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage1.dispose();
        stage2.dispose();
        super.dispose();
    }
}
