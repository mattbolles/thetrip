package com.secondgame.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.secondgame.resource.GameInfo;
import com.secondgame.map.GameMap;
import com.secondgame.resource.Hitbox;

public class Player extends GameObject {

    private static int speed = 180;
    private static int jumpVelocity = 6;

    Texture playerImage;
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
    Hitbox hitbox;
    float x;
    float y;
    int width;
    int height;


    /*public Player(float x, float y, GameMap gameMap) {
        super(x, y, GameObjectType.PLAYER, gameMap);
        playerImage = new Texture("images/player.png");
    }*/

    @Override
    public void create(GameObjectState gameObjectState, GameObjectType gameObjectType, GameMap gameMap) {
        super.create(gameObjectState, gameObjectType, gameMap);
        this.levelBeat = false;
        this.spawnX = gameObjectState.x;
        this.spawnY = gameObjectState.y;
        this.x = gameObjectState.getX();
        this.y = gameObjectState.getY();
        this.width = getWidth();
        this.height = getHeight();
        this.justKilled = false;
        this.hitbox = new Hitbox(x, y, width, height);
        direction = "right";
        playerImage = new Texture("images/player.png");
        moveAnimationSheet = new Texture(Gdx.files.internal("images/playerspritesheet.png"));
        System.out.println("moveAnimationSheet width: " + moveAnimationSheet.getWidth() + " height: " + moveAnimationSheet.getHeight());
        TextureRegion[][] tmp = TextureRegion.split(moveAnimationSheet,moveAnimationSheet.getWidth() / FRAME_COLS,
                        moveAnimationSheet.getHeight() / FRAME_ROWS); // 4 col, 2 row
        TextureRegion[] moveRightFrames = new TextureRegion[FRAME_COLS]; // top row
        TextureRegion[] moveLeftFrames = new TextureRegion[FRAME_COLS]; // bottom row
        for (int i = 0; i < FRAME_COLS; i++) {
            moveRightFrames[i] = tmp[0][i];
            moveLeftFrames[i] = tmp[1][i];
        }

        rightAnimation = new Animation<TextureRegion>(GameInfo.ANIMATION_SPEED, moveRightFrames);
        leftAnimation = new Animation<TextureRegion>(GameInfo.ANIMATION_SPEED, moveLeftFrames);
        this.health = gameObjectState.getFloatFromHashMap("health", 100);
        this.lives = gameObjectState.getIntFromHashMap("lives", 3);
        completelyDead = false;
        //System.out.println("player health init: " + health);
        // add extra data... spawnradius etc
    }

    @Override
    public void update(float deltaTime, float gravity) {
        // going to need seperate control data structure - single jump button, etc
        // unify it basically - controller support - maybe implement customizable controls


        // hold shift to run
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            speed = 240;
        }


        // reset speed after run
        if (!Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && onGround) {
            speed = 180;
        }

        //initial jump from ground
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && onGround) {
            this.velocityY += jumpVelocity * getWeight();
        }

        //continue jumping if key is still pressed in air (hence velocityY > 0)
        else if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !onGround && this.velocityY > 0) {
            this.velocityY += jumpVelocity * getWeight() * deltaTime;
        }



        // applies gravity
        super.update(deltaTime, gravity);
        this.hitbox.move(this.getX(), this.getY());

        //if a portal is touched


        //move left
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveX(-speed * deltaTime);
            direction = "left";
        }

        // move right
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveX(speed * deltaTime);
            direction = "right";
        }

        if (this.health <= 0) {
            killPlayer();
        }

        if (lives == 0) {
            completelyDead = true;
        }

        if (gameMap.checkIfCollidesWithTiles(this.getX(), this.getY(), width, height) == 2) {
            levelBeat = true;
        }
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

    public void revivePlayer() {
        System.out.println("Player revivePlayer reach");
        this.health = 100;
        this.lives = 3;
        this.setX(spawnX);
        this.setY(spawnY);
        this.levelBeat = false;
        System.out.println("after revive, health: " + health + " lives: " + lives);
        //this.position.set(spawnX, spawnY);
    }

    public void damagePlayer(float amountToDamage) {
        if (this.health > 0) {
            this.health -= amountToDamage;
        }
    }

    public void setLevelBeat(boolean levelBeat) {
        System.out.println("from Player: setLevelBeat reached, set to: " + levelBeat);
        this.levelBeat = levelBeat;
    }

    public boolean isLevelBeat() {
        return this.levelBeat;
    }

    public boolean isKilled() {
        //System.out.println("from Player: isKilled() reached");
        if (this.health <= 0) {
            System.out.println("from Player: isKilled() true");
            return true;
        }
        else {
            //System.out.println("from Player: isKilled() false");
            return false;
        }
    }

    public void killPlayer() {
        System.out.println("from Player: going to kill. Health: " + health + " lives: " + lives);
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
        stateTime += Gdx.graphics.getDeltaTime(); // accumulate time
        TextureRegion currentFrame = leftAnimation.getKeyFrame(stateTime, true);
        if ("left".equals(direction)) {
            currentFrame = leftAnimation.getKeyFrame(stateTime, true);
        }

        else if ("right".equals(direction)) {
            currentFrame = rightAnimation.getKeyFrame(stateTime, true);
        }


        // last two fields scale image if not correct size
        batch.draw(currentFrame, position.x, position.y, getWidth(), getHeight());
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
