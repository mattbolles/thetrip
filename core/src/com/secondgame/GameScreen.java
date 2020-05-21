package com.secondgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.secondgame.gameobject.Boss;
import com.secondgame.gameobject.Enemy;
import com.secondgame.gameobject.GameObject;
import com.secondgame.gameobject.Player;
import com.secondgame.map.GameMap;
import com.secondgame.map.TiledGameMap;
import com.secondgame.resource.GameInfo;
import com.secondgame.resource.GameState;
import com.secondgame.resource.Hud;
import com.secondgame.resource.SoundPlayer;
import com.secondgame.weapon.Bullet;

import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {

    final TheTrip game;
    SpriteBatch spriteBatch;
    OrthographicCamera camera;
    GameMap gameMap;
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
    boolean playerJustKilled;
    boolean playerCompletelyDead;
    boolean justReset;
    ArrayList<Bullet> bulletList;
    float bulletTimer;
    float damageTimer;
    String playerDirection;
    boolean levelBeat;
    private Hud hud;
    private ShapeRenderer shapeRenderer;


    public GameScreen(final TheTrip game) {
        this.game = game;
        this.justReset = false;
        levelBeat = false;
        bulletTimer = 0;
        damageTimer = 0;
        bulletList = new ArrayList<Bullet>();
        spriteBatch = game.batch;
        shapeRenderer = new ShapeRenderer();
        playerDirection = "right";
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameInfo.SCREEN_WIDTH, GameInfo.SCREEN_HEIGHT);
        hud = new Hud(spriteBatch);
        initialCameraPosition = camera.position;
        soundPlayer = new SoundPlayer(game);
        playerPositionX = 40; // initial player position
        playerPositionY = 320;
        spawnX = 40;
        spawnY = 320;
        level = game.getOptions().getStartingLevel();
        gameMap = new TiledGameMap(level);

    }

    //this stuff happens when screen is shown
    @Override
    public void show() {
        //reset health/lives/etc
        this.playerLives = 3;
        this.playerHealth = 100;
        this.playerCompletelyDead = false;
        soundPlayer.playMusic(level);

    }

    //game loop
    @Override
    public void render(float delta) {
        bulletTimer += delta;
        damageTimer += delta;
        stateTime += delta;
        updateGameObjects();
        processInput();
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
            // reset camera upon death
            camera.position.x = 400;
            camera.position.y = 300;
            camera.update();
        }

        if (playerCompletelyDead) {
            // if actually dead
            if (!(playerLives > 0)) {
                justReset = true;
                soundPlayer.stopMusic();
                game.loadScreen(GameState.GAME_OVER);
            }
        }

        // if level beaten
        if (levelBeat) {
            if (level < 3) {
                level++;
                game.getOptions().setStartingLevel(level);
                nextLevel();
            } else {
                // if final level beaten
                game.getOptions().setStartingLevel(1);
                soundPlayer.stopMusic();
                game.loadScreen(GameState.END_OF_GAME);
            }
        }


        // after update check for collisions, before rendering
        processBullets();
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // deltatime is time between last update and now
        gameMap.update(Gdx.graphics.getDeltaTime());
        gameMap.render(camera, spriteBatch);
        spriteBatch.setProjectionMatrix(camera.combined);

        drawBullets();
        // draw hud background and hud after so it's on top
        drawHud();
    }

    public void processInput() {
        // E to shoot
        // delay shooting
        if (Gdx.input.isKeyPressed(Input.Keys.E) && bulletTimer >= GameInfo.BULLET_COOLDOWN) {
            bulletTimer = 0;
            // facing right
            if ("right".equals(playerDirection)) {
                bulletList.add(new Bullet(playerPositionX + 20, playerPositionY + 30, "right", camera, gameMap));
            }

            //facing left
            else {
                bulletList.add(new Bullet(playerPositionX - 5, playerPositionY + 30, "left", camera, gameMap));
            }

        }

        // quit to menu
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            soundPlayer.stopMusic();
            game.loadScreen(GameState.MENU);
        }
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

    public void processBullets() {
        for (Bullet bullet : bulletList) {
            for (GameObject gameObject : gameMap.gameObjects) {
                if (gameObject instanceof Enemy) {
                    // if bullet hit enemy
                    if (bullet.getHitbox().checkCollision(((Enemy) gameObject).getHitbox())) {
                        bullet.setNeedToRemove(true);
                        ((Enemy) gameObject).damageEnemy(20);
                    }
                }

                if (gameObject instanceof Boss) {
                    // if bullet hit boss
                    if (bullet.getHitbox().checkCollision(((Boss) gameObject).getHitbox())) {
                        bullet.setNeedToRemove(true);
                        ((Boss) gameObject).damageBoss(20);
                    }
                }
            }
        }
    }


    public void updateGameObjects() {
        ArrayList<Enemy> enemyToKill = new ArrayList<Enemy>();
        ArrayList<Boss> bossToKill = new ArrayList<Boss>();
        // update game objects
        for (GameObject gameObject : gameMap.gameObjects) {
            if (gameObject instanceof Player) {
                // if there are other objects besides player, check collisions
                if (gameMap.gameObjects.size() > 1) {
                    for (GameObject anotherGameObject : gameMap.gameObjects) {
                        // if collide with enemy
                        if (anotherGameObject instanceof Enemy) {
                            if (((Player) gameObject).getHitbox().checkCollision(((Enemy) anotherGameObject).getHitbox())) {
                                ((Player) gameObject).damagePlayer(5);
                            }
                        }

                        // if collide with boss
                        if (anotherGameObject instanceof Boss) {
                            if (((Player) gameObject).getHitbox().checkCollision(((Boss) anotherGameObject).getHitbox())) {
                                ((Player) gameObject).damagePlayer(10);
                            }
                        }
                    }
                }

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
                updateCamera();

                if (playerJustKilled) {
                    ((Player) gameObject).setJustKilled(false);
                }

                if (playerCompletelyDead) {
                    ((Player) gameObject).setCompletelyDead(false);
                }
            }

            // if enemy is dead, prepare to remove
            if (gameObject instanceof Enemy) {
                if (((Enemy) gameObject).isCompletelyDead()) {
                    enemyToKill.add((Enemy) gameObject);
                }
            }

            //if boss dead, prepare to remove
            if (gameObject instanceof Boss) {
                if (((Boss) gameObject).isCompletelyDead()) {
                    bossToKill.add((Boss) gameObject);
                }
            }


        }
        // remove dead enemies / boss
        gameMap.gameObjects.removeAll(enemyToKill);
        gameMap.gameObjects.removeAll(bossToKill);
    }

    public void drawHud() {
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

    public void drawBullets() {
        //draw bullets
        spriteBatch.begin();
        for (Bullet bullet : bulletList) {
            if (!bulletList.isEmpty()) {
                bullet.render(spriteBatch);
            }

        }
        spriteBatch.end();
    }

    public void nextLevel() {
        soundPlayer.stopMusic();
        game.setScreen(new GameScreen(game));
        //this.dispose();
        System.out.println("Gamescreen: next level reached");
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        gameMap.dispose();
        soundPlayer.stopMusic();
        //SoundPlayer.disposeSounds();
        super.dispose();
    }
}
