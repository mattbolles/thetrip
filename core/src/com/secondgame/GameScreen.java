package com.secondgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
    private ShapeRenderer shapeRenderer;
    boolean playerJustKilled;
    boolean playerCompletelyDead;
    boolean justReset;




    // collisions not working - tiles all come back as null
    public GameScreen(final SecondGame game) {
        this.game = game;
        this.justReset = false;
        spriteBatch = game.batch;
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        //camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, GameInfo.SCREEN_WIDTH, GameInfo.SCREEN_HEIGHT);
        //camera.update();
        hud = new Hud(spriteBatch);
        initialCameraPosition = camera.position;
        System.out.println("from GameScreen: camera.position: " + camera.position.x +
                ", " + camera.position.y);
        System.out.println("from GameScreen: initialCameraPosition: " + initialCameraPosition.x +
                        ", " + initialCameraPosition.y);

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
        //reset health/lives/etc
        this.playerLives = 3;
        this.playerHealth = 100;
        this.playerCompletelyDead = false;
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
                if (justReset) {
                    ((Player) gameObject).revivePlayer();
                    justReset = false;
                }
                playerHealth = ((Player) gameObject).getHealth();
                playerLives = ((Player) gameObject).getLives();
                playerJustKilled = ((Player) gameObject).isJustKilled();
                playerCompletelyDead = ((Player) gameObject).isCompletelyDead();

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
                // respawn

                if (playerPositionX < camera.position.x) {
                    // reach left side
                    if (playerPositionX > GameInfo.SCREEN_WIDTH / 2) {
                        camera.position.x = playerPositionX;
                        camera.update();
                    }


                }

                // reach left side
               /* if (playerPositionX < camera.position.x && (playerPositionX > GameInfo.SCREEN_WIDTH / 2)) {
                    camera.position.x = playerPositionX;
                    camera.update();
                }*/

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

                if (playerJustKilled) {
                    System.out.println("from gameScreen: playerJustKilled");
                    camera.position.x = initialCameraPosition.x;
                    camera.position.y = initialCameraPosition.y;
                    camera.update();
                    ((Player) gameObject).setJustKilled(false);
                    System.out.println("playerJustKilled: " + ((Player) gameObject).isJustKilled());
                }

                if (playerCompletelyDead) {
                    System.out.println("from gameScreen: playerCompletelydead");
                    ((Player) gameObject).setCompletelyDead(false);
                }


                // reset camera upon player death if player not completely dead
                if (((Player) gameObject).isKilled()) {
                    System.out.println("init camera position: " + initialCameraPosition.x + ", " + initialCameraPosition.y);
                    camera.position.x = playerPositionX;
                    camera.position.y = playerPositionY;
                    camera.update();
                }

                if (((Player) gameObject).isCompletelyDead() ) {
                    game.loadScreen(GameState.GAME_OVER);

                }


                //System.out.println(getNewCameraPosition());
            }
        }


        if (playerJustKilled) {
            System.out.println("from gameScreen: playerJustKilled method 2");
            camera.position.x = 400;
            camera.position.y = 300;
            camera.update();
            System.out.println("cam pos: " + camera.position.x + ", " + camera.position.y);
        }

        if (playerCompletelyDead) {
            System.out.println("from gameScreen: playerCompletelyDead method 2");
            // if actually dead
            if (!(playerLives > 0)) {
                justReset = true;
                game.loadScreen(GameState.GAME_OVER);
            }


        }

        //System.out.println("current player position: " + playerPositionX + ", " + playerPositionY);
        //System.out.println("current cam position: " + camera.position.x + ", " + camera.position.y);
        // set color
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        // fill screen with color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


       // System.out.println("FPS: " + 1/delta);
        // if clicked
        stateTime += delta;
        /*if (Gdx.input.isTouched()) {
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
        }*/




        // deltatime is time between last update and now
        camera.update();

        //spriteBatch.setProjectionMatrix(camera.combined);
        /*spriteBatch.draw(moveAnimation[movePosition].getKeyFrame(stateTime, true), playerPositionX,
                playerPositionY,
                GameInfo.PLAYER_WIDTH * 3, GameInfo.PLAYER_HEIGHT * 3);*/

        //spriteBatch.setProjectionMatrix(camera.combined);

        gameMap.update(Gdx.graphics.getDeltaTime());
        gameMap.render(camera, spriteBatch);
        // draw hud background and hud after so it's on top
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(40, GameInfo.WORLD_HEIGHT - 250, 230, 65);
        shapeRenderer.end();

        spriteBatch.setProjectionMatrix(hud.hudStage.getCamera().combined);
        this.hud.setHealth(playerHealth);
        this.hud.setLives(playerLives);
        hud.hudStage.act();
        hud.hudStage.draw();

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
