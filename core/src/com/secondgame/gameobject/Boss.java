package com.secondgame.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.secondgame.resource.GameInfo;
import com.secondgame.map.GameMap;
import com.secondgame.Hitbox;

public class Boss extends GameObject {

    private static int speed = 180;
    private static int jumpVelocity = 6;

    Texture bossImage;
    Animation<TextureRegion> animation;

    Texture moveAnimationSheet;
    public static final float ANIMATION_SPEED = 0.5f; // every half a second
    int movePosition;
    public static final int FRAME_COLS = 4;
    public static final int FRAME_ROWS = 1;
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
        moveAnimationSheet = new Texture(Gdx.files.internal("images/bossspritesheet.png"));
        System.out.println("moveAnimationSheet width: " + moveAnimationSheet.getWidth() + " height: " + moveAnimationSheet.getHeight());
        TextureRegion[][] tmp = TextureRegion.split(moveAnimationSheet,moveAnimationSheet.getWidth() / FRAME_COLS,
                moveAnimationSheet.getHeight() / FRAME_ROWS); // 4 col, 1 row
        TextureRegion[] animationFrames = new TextureRegion[FRAME_COLS];
        for (int i = 0; i < FRAME_COLS; i++) {
            animationFrames[i] = tmp[0][i];
        }
        animation = new Animation<TextureRegion>(GameInfo.ANIMATION_SPEED, animationFrames);
        this.health = gameObjectState.getFloatFromHashMap("health", 100);
        this.lives = gameObjectState.getIntFromHashMap("lives", 3);
        this.completelyDead = false;
        //System.out.println("boss health init: " + health);
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


    public void damageBoss(float amountToDamage) {
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


    public float getHealth() {
        return this.health;
    }

    @Override
    public void render(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime(); // accumulate time
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
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
