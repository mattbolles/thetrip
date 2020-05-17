package com.secondgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.secondgame.gameobject.GameObject;
import com.secondgame.gameobject.Player;

public class GameScreen extends ScreenAdapter {

    private SecondGame game;
    private Stage stage;
    SpriteBatch spriteBatch;
    OrthographicCamera camera;
    GameMap gameMap;
    float deltaX;
    float deltaY;
    Vector2 cameraPosition;

    public GameScreen(SecondGame game) {
        this.game = game;
        spriteBatch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cameraPosition = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        stage = new Stage(new ScreenViewport(camera));
        camera.update();
        //gameMap = new TiledGameMap(0);
        gameMap = new TiledGameMap(game.getOptions().getStartingLevel());
    }

    //create load stuff here
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();


    }

    //basically the game loop
    @Override
    public void render(float delta) {
        // set color
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        // fill screen with color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
       // System.out.println("FPS: " + 1/delta);

        // if clicked
        if (Gdx.input.isTouched()) {
            // drag gameMap
            camera.translate(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
            camera.update();
        }

        // if clicked - after click finished
        if (Gdx.input.justTouched()) {
            Vector3 clickPositionOnScreen = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            Vector3 clickPositionOnMap = camera.unproject(clickPositionOnScreen);
            TileType currentTileType = gameMap.getTileTypeByLocation(1, clickPositionOnMap.x, clickPositionOnMap.y);
            System.out.println(currentTileType == null);
            if (currentTileType != null) {
                System.out.println("Click on tile id: " + currentTileType.getId() + " name: " + currentTileType.getName() + " is collidable: " + currentTileType.isCollidable() + " damage: " + currentTileType.getDamage());
                System.out.println("ass");

            }
        }
        // align camera with player object
        /*for (GameObject gameObject : gameMap.gameObjects) {
            if (gameObject.isPlayer()) {
                cameraPosition.set(getCameraX((Player) gameObject), getCameraY((Player) gameObject));
                System.out.println(getCameraPosition());
                //cameraPosition.set(gameObject.getCameraPosition());
            }
        };*/

        //camera.translate(cameraPosition);
        camera.update();

        // deltatime is time between last update and now
        gameMap.update(Gdx.graphics.getDeltaTime());
        gameMap.render(camera, spriteBatch);
        //perform actions after input
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        // draw appropriate items
        stage.draw();

    }

    public float getCameraX(Player player) {
        // get 1st tank
        // divide by 4 as each player gets half the screen width originally
        float cameraX = player.getX() - (GameInfo.SCREEN_WIDTH / 4);
        if (cameraX > GameInfo.offsetMaxX) {
            cameraX = GameInfo.offsetMaxX;
        } else if (cameraX < GameInfo.offsetMinX) {
            cameraX = GameInfo.offsetMinX;
        }
        return cameraX;
    }

    public float getCameraY(Player player) {
        float cameraY = player.getY() - (GameInfo.SCREEN_HEIGHT / 2);
        if (cameraY > GameInfo.offsetMaxY) {
            cameraY = GameInfo.offsetMaxY;
        } else if (cameraY < GameInfo.offsetMinY) {
            cameraY = GameInfo.offsetMinY;
        }
        return cameraY;
    }

    public Vector2 getCameraPosition() {
        return cameraPosition;
    }
}
