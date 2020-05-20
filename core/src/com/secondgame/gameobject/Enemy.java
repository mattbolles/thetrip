package com.secondgame.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.secondgame.GameInfo;
import com.secondgame.GameMap;
import com.secondgame.Hitbox;

public class Enemy extends GameObject {

    private static int speed = 180;
    private static int jumpVelocity = 6;

    Texture enemyImage;
    Animation<TextureRegion> rightAnimation;
    Animation<TextureRegion> leftAnimation;
    Texture moveAnimationSheet;
    public static final float ANIMATION_SPEED = 0.5f; // every half a second
    int movePosition;
    public static final int FRAME_COLS = 4;
    public static final int FRAME_ROWS = 2;
    float stateTime; // for tracking animation time
    String direction; // for keeping track of direction player is facing
    float health;
    int lives;
    boolean completelyDead;
    float spawnX;
    float spawnY;
    boolean justKilled;
    boolean levelBeat;
    float x;
    float y;
    int width;
    int height;

    Hitbox hitbox;

    /*public Player(float x, float y, GameMap gameMap) {
        super(x, y, GameObjectType.PLAYER, gameMap);
        playerImage = new Texture("images/player.png");
    }*/

    @Override
    public void create(GameObjectState gameObjectState, GameObjectType gameObjectType, GameMap gameMap) {
        super.create(gameObjectState, gameObjectType, gameMap);
        this.x = gameObjectState.getX();
        this.y = gameObjectState.getY();
        this.width = getWidth();
        this.height = getHeight();
        this.hitbox = new Hitbox(x, y, width, height);
        direction = "right";
        enemyImage = new Texture("images/enemy1.png");
        this.health = gameObjectState.getFloatFromHashMap("health", 100);
        this.lives = gameObjectState.getIntFromHashMap("lives", 3);
        this.completelyDead = false;
        System.out.println("player health init: " + health);
        // add extra data... spawnradius etc
    }

    @Override
    public void update(float deltaTime, float gravity) {
        if (health <= 0) {
            this. completelyDead = true;
        }

        //hitbox.move(x, y);
        //System.out.println("player health: " + health);
    }

    public boolean isCompletelyDead() {
        //resetPlayer();
        return completelyDead;
    }

    public void setCompletelyDead(boolean completelyDead) {
        this.completelyDead = completelyDead;
    }

    public void respawnPlayer() {
        this.health = 100;
        //this.position.set(spawnX, spawnY);
    }


    public void damageEnemy(float amountToDamage) {
        if (this.health > 0) {
            this.health -= amountToDamage;
        }
    }

    public void setLevelBeat(boolean levelBeat) {
        this.levelBeat = levelBeat;
    }

    public boolean isLevelBeat() {
        return this.levelBeat;
    }

    public boolean isKilled() {
        //System.out.println("from Player: isKilled() reached");
        if (this.health <= 0) {
            System.out.println("from Enemy: isKilled() true");
            return true;
        }
        else {
            //System.out.println("from Player: isKilled() false");
            return false;
        }
    }

    public void killEnemy() {
        System.out.println("from Enemy: going to kill. Health: " + health + " lives: " + lives);
        if (this.lives > 0) {
            this.lives -= 1;
        }

        if (lives == 0) {
            this.completelyDead = true;
            System.out.println("from Player: completely dead");
        }
        System.out.println("from Player: player killed. Health: " + health + " lives: " + lives);
        this.setX(spawnX);
        this.setY(spawnY);
        this.health = 100;
        this.justKilled = true;
        //respawnPlayer();
    }

    public float getHealth() {
        return this.health;
    }

    public int getLives() {
        return this.lives;
    }

    public String getDirection() {
        return direction;
    }

    public void setJustKilled(boolean justKilled) {
        this.justKilled = justKilled;
    }

    public boolean isJustKilled() {
        return justKilled;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(enemyImage, position.x, position.y, getWidth(), getHeight());
    }

    @Override
    public GameObjectState getSaveGameObjectState() {
        GameObjectState gameObjectState = super.getSaveGameObjectState();
        //update extra data here if needed
        return gameObjectState;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }


}
