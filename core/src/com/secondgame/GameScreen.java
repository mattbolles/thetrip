package com.secondgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.loaders.SoundLoader;
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
import com.badlogic.gdx.utils.viewport.Viewport;
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
    Vector3 initialCameraPosition;
    float playerPositionX;
    float playerPositionY;
    float spawnX;
    float spawnY;
    float stateTime;
    SoundPlayer soundPlayer;
    int level;
    float playerHealth;
    int playerLives;
    private Hud hud;
    private Viewport viewport;




    // collisions not working - tiles all come back as null
    public GameScreen(final SecondGame game) {
        this.game = game;
        spriteBatch = game.batch;
        hud = new Hud(spriteBatch);
        camera = new OrthographicCamera();
        //camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, GameInfo.SCREEN_WIDTH, GameInfo.SCREEN_HEIGHT);
        camera.update();
        initialCameraPosition = camera.position;
        soundPlayer = new SoundPlayer(game);
        playerPositionX = 40; // initial player position
        playerPositionY = 320;
        spawnX = 40;
        spawnY = 320;
        //newCameraPosition = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //stage = new Stage(new ScreenViewport(camera), spriteBatch);
        //gameMap = new TiledGameMap(0);
        int movePosition = 2; // in middle, 0 and 1 is left, 3 and 4 is right
        level = game.getOptions().getStartingLevel();
        gameMap = new TiledGameMap(level);

    }

    //this stuff only happens when screen is shown
    @Override
    public void show() {
        System.out.println("Start GameScreen show");
        //Gdx.input.setInputProcessor(stage);
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        soundPlayer.playMusic(level);

    }

    //basically the game loop
    @Override
    public void render(float delta) {
        // update in relation to player object
        for (GameObject gameObject : gameMap.gameObjects) {
            // align camera with player object
            if (gameObject instanceof Player) {
                playerHealth = ((Player) gameObject).getHealth();
                playerLives = ((Player) gameObject).getLives();
                //System.out.println("player pos: " + gameObject.getPosition());
                //newCameraPosition.set(getCameraX((Player) gameObject), getCameraY((Player) gameObject));
                //System.out.println("new cam pos:" + newCameraPosition);
                //System.out.println("old cam pos:" + camera.position);
                //newCameraPosition.set(camera.position.x - newCameraPosition.x,
                //camera.position.y + newCameraPosition.y);
                playerPositionX = gameObject.getPosition().x;
                playerPositionY = gameObject.getPosition().y;
                //System.out.println("player at: " + playerPositionX + ", " + playerPositionY);
                //System.out.println("camera at: " + camera.position.x + ", " + camera.position.y);
                if (playerPositionX > camera.position.x) {
                    camera.position.x = playerPositionX;
                    camera.update();
                }

                // reach left side
                if (playerPositionX < camera.position.x && (playerPositionX > GameInfo.SCREEN_WIDTH / 2)) {
                    camera.position.x = playerPositionX;
                    camera.update();
                }

                //reach right side
                if (camera.position.x > GameInfo.WORLD_WIDTH - GameInfo.SCREEN_WIDTH / 2) {
                    camera.position.x = GameInfo.WORLD_WIDTH - GameInfo.SCREEN_WIDTH / 2;
                    camera.update();
                }

                if (playerPositionY > camera.position.y) {
                    camera.position.y = playerPositionY;
                    camera.update();
                }

                if (playerPositionY < camera.position.y && playerPositionY > GameInfo.SCREEN_HEIGHT / 2) {
                    camera.position.y = playerPositionY;
                    camera.update();
                }

                if (camera.position.y > GameInfo.WORLD_HEIGHT - GameInfo.SCREEN_HEIGHT / 2) {
                    camera.position.y = GameInfo.WORLD_HEIGHT - GameInfo.SCREEN_HEIGHT / 2;
                    camera.update();
                }

                // reset camera upon player death if player not completely dead
                if (playerHealth == 0 && !((Player) gameObject).isCompletelyDead()) {
                    System.out.println("init camera position: " + initialCameraPosition.x + ", " + initialCameraPosition.y);
                    camera.position.x = initialCameraPosition.x;
                    camera.position.y = initialCameraPosition.y;
                }

                if (((Player) gameObject).isCompletelyDead() ) {
                    game.loadScreen(GameState.GAME_OVER);
                    ((Player) gameObject).revivePlayer();
                }


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

            }
        }




        // deltatime is time between last update and now
        camera.update();

        //spriteBatch.setProjectionMatrix(camera.combined);
        /*spriteBatch.draw(moveAnimation[movePosition].getKeyFrame(stateTime, true), playerPositionX,
                playerPositionY,
                GameInfo.PLAYER_WIDTH * 3, GameInfo.PLAYER_HEIGHT * 3);*/

        //spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
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
        soundPlayer.stopMusic();
        super.dispose();
    }
}
