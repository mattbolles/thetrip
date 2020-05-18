package com.secondgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.secondgame.gameobject.GameObject;
import com.secondgame.gameobject.Player;

public class GameScreen extends ScreenAdapter {

    final SecondGame game;
    private Stage stage;
    SpriteBatch spriteBatch;
    OrthographicCamera camera;
    GameMap gameMap;
    float deltaX;
    float deltaY;
    Vector2 newCameraPosition;
    float playerPositionX;
    float playerPositionY;
    float stateTime;




    // collisions not working - tiles all come back as null
    public GameScreen(final SecondGame game) {
        this.game = game;
        spriteBatch = game.batch;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        playerPositionX = 40; // initial player position
        playerPositionY = 320;
        //newCameraPosition = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //stage = new Stage(new ScreenViewport(camera), spriteBatch);
        //gameMap = new TiledGameMap(0);
        int movePosition = 2; // in middle, 0 and 1 is left, 3 and 4 is right

        gameMap = new TiledGameMap(game.getOptions().getStartingLevel());

    }

    //create load stuff here
    @Override
    public void show() {
        System.out.println("Start GameScreen show");
        //Gdx.input.setInputProcessor(stage);
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();


    }

    //basically the game loop
    @Override
    public void render(float delta) {
        // align camera with player object
        for (GameObject gameObject : gameMap.gameObjects) {
            if (gameObject.isPlayer()) {
                //System.out.println("player pos: " + gameObject.getPosition());
                //newCameraPosition.set(getCameraX((Player) gameObject), getCameraY((Player) gameObject));
                //System.out.println("new cam pos:" + newCameraPosition);
                //System.out.println("old cam pos:" + camera.position);
                //newCameraPosition.set(camera.position.x - newCameraPosition.x,
                //camera.position.y + newCameraPosition.y);
                if (gameObject.getPosition().x > camera.position.x) {
                    camera.position.x = gameObject.getPosition().x;
                    camera.update();
                }

                // reach left side
                if (gameObject.getPosition().x < camera.position.x && (gameObject.getPosition().x > GameInfo.SCREEN_WIDTH / 2)) {
                    camera.position.x = gameObject.getPosition().x;
                    camera.update();
                }

                //reach right side
                if (camera.position.x > GameInfo.WORLD_WIDTH - GameInfo.SCREEN_WIDTH / 2) {
                    camera.position.x = GameInfo.WORLD_WIDTH - GameInfo.SCREEN_WIDTH / 2;
                    camera.update();
                }
                playerPositionX = gameObject.getPosition().x;
                playerPositionY = gameObject.getPosition().y;
                //System.out.println(getNewCameraPosition());
            }
        }
        // set color
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        // fill screen with color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
       // System.out.println("FPS: " + 1/delta);
        // if clicked
        stateTime += delta;
        if (Gdx.input.isTouched()) {
            // drag gameMap
            camera.translate(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
            camera.update();
        }

        // if tiles not working properly check tile set and make sure tile IDs match the tile type class
        // if clicked - after click finished
        if (Gdx.input.justTouched()) {
            Vector3 clickPositionOnScreen = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            Vector3 clickPositionOnMap = camera.unproject(clickPositionOnScreen);
            // 0 = 1st layer as index starts at 0
            TileType currentTileType = gameMap.getTileTypeByLocation(0, clickPositionOnMap.x, clickPositionOnMap.y);
            System.out.println(currentTileType == null);
            if (currentTileType != null) {
                System.out.println("Click on tile id: " + currentTileType.getId() + " name: " + currentTileType.getName() + " is collidable: " + currentTileType.isCollidable() + " damage: " + currentTileType.getDamage());
                System.out.println("ass");

            }
        }




        // deltatime is time between last update and now
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
        /*spriteBatch.draw(moveAnimation[movePosition].getKeyFrame(stateTime, true), playerPositionX,
                playerPositionY,
                GameInfo.PLAYER_WIDTH * 3, GameInfo.PLAYER_HEIGHT * 3);*/

        //spriteBatch.setProjectionMatrix(camera.combined);
        gameMap.update(Gdx.graphics.getDeltaTime());
        gameMap.render(camera, spriteBatch);
        //camera.translate(newCameraPosition);

        //perform actions after input
        //stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        // draw appropriate items
        //stage.draw();

    }

    public Vector2 getNewCameraPosition() {
        return newCameraPosition;
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        gameMap.dispose();
    }
}
