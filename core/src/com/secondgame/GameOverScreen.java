package com.secondgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.secondgame.resource.GameInfo;
import com.secondgame.resource.GameState;

public class GameOverScreen extends ScreenAdapter {
    private SecondGame game;
    private Stage stage;

    public GameOverScreen(SecondGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Label gameOver = new Label("GAME OVER", GameInfo.LABEL_STYLE_36);
        gameOver.setPosition(Gdx.graphics.getWidth() / 2 - gameOver.getWidth() / 2,
                Gdx.graphics.getHeight() * 2 / 3 + gameOver.getHeight() / 3);

        stage.addActor(gameOver);
        // create and show menu
        Table menu =  new Table();

        menu.setFillParent(true);
        //menu.setDebug(true);
        /*menu.setPosition(Gdx.graphics.getWidth() / 2 - menu.getWidth() / 2,
                Gdx.graphics.getHeight() * 2 / 3 - menu.getHeight() / 2);*/
        stage.addActor(menu);
        // creat and show menu buttons
        // create custom skin later
        Skin skin = new Skin(Gdx.files.internal("skins/neon/skin/neon-ui.json"));
        TextButton retryButton = new TextButton("Retry", skin);
        TextButton  menuButton = new TextButton("Menu", skin);
        TextButton exitButton = new TextButton("Exit", skin);
        // add buttons
        menu.row().pad(50, 0, 0, 0);
        menu.add(retryButton).fillX().uniformX(); // fill table width, align with other buttons
        menu.row().pad(10, 0, 0, 0); // add spacing to bottom of button
        menu.add(menuButton).fillX().uniformX();
        menu.row().pad(10, 0, 0, 0); // add spacing
        menu.add(exitButton).fillX().uniformX();

        // add exit button action
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        // add start button action - change after option given to select level
        retryButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game));
                //game.loadScreen(GameState.GAME);
            }
        });

        menuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.loadScreen(GameState.MENU);
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //perform actions after input
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        // draw appropriate items
        stage.draw();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        super.dispose();
    }
}
