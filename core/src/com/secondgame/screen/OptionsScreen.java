package com.secondgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.secondgame.TheTrip;
import com.secondgame.resource.GameState;

public class OptionsScreen extends ScreenAdapter {
    private TheTrip game;
    private Stage stage;
    private Label titleLabel;
    private Label musicVolumeLabel;
    private Label musicEnabledLabel;
    private Label startingLevelLabel;
    //private Integer[] startingLevelOptions;



    public OptionsScreen(TheTrip game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());

    }

    public void show() {
        stage.clear();
        Gdx.input.setInputProcessor(stage);
        // create and show menu
        Table optionsMenu = new Table();
        optionsMenu.setFillParent(true);
        //optionsMenu.setDebug(true);
        stage.addActor(optionsMenu);
        // create custom skin later
        Skin skin = new Skin(Gdx.files.internal("skins/neon/skin/neon-ui.json"));
        // create and show option buttons and sliders
        // music volume
        final Slider musicVolumeSlider = new Slider(0f, 1f, 0.01f, false, skin);
        musicVolumeSlider.setValue(game.getOptions().getMusicVolume());
        musicVolumeSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                game.getOptions().setMusicVolume(musicVolumeSlider.getValue());
                return false;
            }
        });
        // music enabled
        final CheckBox musicEnabledCheckBox = new CheckBox(null, skin);
        musicEnabledCheckBox.setChecked(game.getOptions().isMusicEnabled());
        musicEnabledCheckBox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicEnabledCheckBox.isChecked();
                game.getOptions().setMusicEnabled(enabled);
                return false;
            }
        });

        // back to menu
        final TextButton backToMenuButton = new TextButton("Back", skin);
        backToMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.loadScreen(GameState.MENU);
            }
        });

        // level select options
        //startingLevelOptions = new Integer[] {1, 2, 3};
        final SelectBox<Integer> startingLevelSelectBox = new SelectBox<Integer>(skin);
        startingLevelSelectBox.setItems(1, 2, 3);
        startingLevelSelectBox.setSelected(game.getOptions().getStartingLevel());
        startingLevelSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int levelSelected = startingLevelSelectBox.getSelected();
                game.getOptions().setStartingLevel(levelSelected);
            }
        });


        titleLabel = new Label("Options", skin);
        musicVolumeLabel = new Label("Music Volume", skin);
        musicEnabledLabel = new Label("Music Enabled", skin);
        startingLevelLabel = new Label("Starting Level", skin);

        // add everything to menu
        optionsMenu.add(titleLabel).colspan(2); // span across both columns
        optionsMenu.row().pad(10, 0, 0, 10); // new row
        optionsMenu.add(musicEnabledLabel).left(); // add label aligned to left side
        optionsMenu.add(musicEnabledCheckBox).left();
        optionsMenu.row().pad(10, 0, 0, 10);
        optionsMenu.add(musicVolumeLabel).left();
        optionsMenu.add(musicVolumeSlider);
        optionsMenu.row().pad(10, 0, 0, 10);
        optionsMenu.add(startingLevelLabel).left();
        optionsMenu.add(startingLevelSelectBox).left();
        optionsMenu.row().pad(10, 0, 0, 10);
        optionsMenu.add(backToMenuButton).colspan(2);
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
    }
}
