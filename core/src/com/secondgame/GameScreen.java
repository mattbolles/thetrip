package com.secondgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.secondgame.gameobject.Bullet;
import com.secondgame.gameobject.Enemy;
import com.secondgame.gameobject.GameObject;
import com.secondgame.gameobject.Player;

import java.util.ArrayList;

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
    ArrayList<Bullet> bulletList;
    float bulletTimer;
    String playerDirection;
    boolean levelBeat;


    // collisions not working - tiles all come back as null
    public GameScreen(final SecondGame game) {
        this.levelBeat = false;
        this.game = game;
        this.justReset = false;
        bulletTimer = 0;
        bulletList = new ArrayList<Bullet>();
        spriteBatch = game.batch;
        shapeRenderer = new ShapeRenderer();
        playerDirection = "right";
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
        //spriteBatch.end();
        // update in relation to player object
        bulletTimer += delta;
        ArrayList<Enemy> enemyToKill = new ArrayList<Enemy>();
        for (GameObject gameObject : gameMap.gameObjects) {
            // align camera with player object

            if (gameObject instanceof Player) {
                // if respawning, reset health etc
                if (justReset) {
                    ((Player) gameObject).revivePlayer();
                    justReset = false;
                }
                playerHealth = ((Player) gameObject).getHealth();
                playerLives = ((Player) gameObject).getLives();
                playerJustKilled = ((Player) gameObject).isJustKilled();
                playerCompletelyDead = ((Player) gameObject).isCompletelyDead();
                playerPositionX = gameObject.getPosition().x;
                playerPositionY = gameObject.getPosition().y;
                playerDirection = ((Player) gameObject).getDirection();
                levelBeat = ((Player) gameObject).isLevelBeat();
                //System.out.println("from GameScreen render, playpos: " + playerPositionX + "," + playerPositionY);
                updateCamera();

                if (playerJustKilled) {
                    System.out.println("from gameScreen: playerJustKilled 1");
                    ((Player) gameObject).setJustKilled(false);
                }

                if (playerCompletelyDead) {
                    System.out.println("from gameScreen: playerCompletelydead 1");
                    ((Player) gameObject).setCompletelyDead(false);
                }
            }

            // if enemy is dead, prepare to remove it

            if (gameObject instanceof Enemy) {
                if (((Enemy) gameObject).isCompletelyDead()) {
                    System.out.println("from Gamescreen: enemy killed");
                    enemyToKill.add((Enemy) gameObject);
                }
            }

        }
        gameMap.gameObjects.removeAll(enemyToKill);



        // E to shoot - add inout handling method later
        // delay shooting
        if (Gdx.input.isKeyPressed(Input.Keys.E) && bulletTimer >= GameInfo.BULLET_COOLDOWN) {
            bulletTimer = 0;
            System.out.println("from bullet key press, playpos: " + playerPositionX + "," + playerPositionY);
            // facing right
            if ("right".equals(playerDirection)) {
                bulletList.add(new Bullet(playerPositionX + 20, playerPositionY + 30, "right", camera, gameMap));
            }

            //facing left
            else {
                bulletList.add(new Bullet(playerPositionX - 5, playerPositionY + 30, "left", camera, gameMap));
            }

        }


        // update bullets
        ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
        for (Bullet bullet : bulletList) {
            bullet.update(delta, 0);
            if (bullet.needToRemove) {
                bulletsToRemove.add(bullet);
            }
        }
        bulletList.removeAll(bulletsToRemove);

        if (playerJustKilled) {
            System.out.println("from gameScreen: playerJustKilled method 2");
            camera.position.x = 400;
            camera.position.y = 300;
            camera.update();
        }

        if (playerCompletelyDead) {
            System.out.println("from gameScreen: playerCompletelyDead method 2");
            // if actually dead
            if (!(playerLives > 0)) {
                justReset = true;
                game.loadScreen(GameState.GAME_OVER);
            }
        }

        // if level beaten
        if (levelBeat) {
            System.out.println("level beat reached in GameScreen");
            if (level < 2) {
                level++;
                game.getOptions().setStartingLevel(level);
                nextLevel();
            }

            else {
                System.out.println("game over reached in gamescreen");
                game.loadScreen(GameState.END_OF_GAME);
            }
        }
        stateTime += delta;
        // after update check for collisions, before rendering
        for (Bullet bullet : bulletList) {
            for (GameObject gameObject : gameMap.gameObjects) {
                if (gameObject instanceof Enemy) {
                    // if bullet hit enemy
                    if (bullet.getHitbox().checkCollision(((Enemy) gameObject).getHitbox())) {
                        System.out.println("from gamescreen: bullet hit enemy");
                        bullet.setNeedToRemove(true);
                        ((Enemy) gameObject).damageEnemy(20);
                    }
                }
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
        gameMap.update(Gdx.graphics.getDeltaTime());

        gameMap.render(camera, spriteBatch);

        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);

        //draw bullets
        spriteBatch.begin();

        for (Bullet bullet : bulletList) {
            if (!bulletList.isEmpty()) {
                bullet.render(spriteBatch);
            }

        }

        spriteBatch.end();
        // draw hud background and hud after so it's on top
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(40, GameInfo.WORLD_HEIGHT - 110, 230, 65);
        shapeRenderer.end();

        //draw health bar
        spriteBatch.setProjectionMatrix(hud.hudStage.getCamera().combined);
        this.hud.setHealth(playerHealth);
        this.hud.setLives(playerLives);
        hud.hudStage.act();
        hud.hudStage.draw();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(200, GameInfo.WORLD_HEIGHT - 100, (float) (playerHealth / 1.7), 20);
        shapeRenderer.end();
    }

    public void updateCamera() {
        if (playerPositionX > camera.position.x) {
            camera.position.x = playerPositionX;
            camera.update();
        }

        if (playerPositionX < camera.position.x) {
            // reach left side
            if (playerPositionX > GameInfo.SCREEN_WIDTH / 2) {
                camera.position.x = playerPositionX;
                camera.update();
            }
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
    }

    public void nextLevel() {
        game.setScreen(new GameScreen(game));
        this.dispose();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        gameMap.dispose();
        soundPlayer.stopMusic();
        super.dispose();
    }
}
