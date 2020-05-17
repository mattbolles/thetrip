package com.secondgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MenuScreen extends ScreenAdapter {

    private SecondGame game;
    private Stage stage;

    public MenuScreen(SecondGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        // logo
        Texture logoTexture = new Texture(Gdx.files.internal("images/logo.png"));
        Image logo = new Image(logoTexture);
        logo.setPosition(Gdx.graphics.getWidth() / 2 -logo.getWidth() / 2,
                Gdx.graphics.getHeight() * 2 / 3 + logo.getHeight() / 3);

        stage.addActor(logo);
        // add my name
        Label myName = new Label("by Matt Bolles", GameInfo.LABEL_STYLE_18);
        myName.setPosition(Gdx.graphics.getWidth() / 2 - myName.getWidth() / 2,
                logo.getY() - myName.getHeight() * 2);
        stage.addActor(myName);
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
        TextButton startButton = new TextButton("Start", skin);
        TextButton  optionsButton = new TextButton("Options", skin);
        TextButton exitButton = new TextButton("Exit", skin);
        // add buttons
        menu.row().pad(50, 0, 0, 0);
        menu.add(startButton).fillX().uniformX(); // fill table width, align with other buttons
        menu.row().pad(10, 0, 0, 0); // add spacing to bottom of button
        menu.add(optionsButton).fillX().uniformX();
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
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.loadScreen(GameState.GAME);
            }
        });

        optionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.loadScreen(GameState.OPTIONS);
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
    }
}