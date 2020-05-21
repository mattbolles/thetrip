package com.secondgame.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.secondgame.map.GameMap;
import com.secondgame.resource.GameInfo;
import com.secondgame.resource.Hitbox;

public class Boss extends GameObject {

    public static final int FRAME_COLS = 4;
    public static final int FRAME_ROWS = 1;
    Animation<TextureRegion> animation;
    Texture moveAnimationSheet;
    float stateTime; // for tracking animation time
    float health;
    int lives;
    boolean completelyDead;
    float x;
    float y;
    int width;
    int height;
    Hitbox hitbox;


    @Override
    public void create(GameObjectState gameObjectState, GameObjectType gameObjectType, GameMap gameMap) {
        super.create(gameObjectState, gameObjectType, gameMap);
        this.x = gameObjectState.getX();
        this.y = gameObjectState.getY();
        this.width = getWidth();
        this.height = getHeight();
        this.hitbox = new Hitbox(x, y, width, height);
        moveAnimationSheet = new Texture(Gdx.files.internal("images/bossspritesheet.png"));
        System.out.println("moveAnimationSheet width: " + moveAnimationSheet.getWidth() + " height: " + moveAnimationSheet.getHeight());
        TextureRegion[][] tmp = TextureRegion.split(moveAnimationSheet, moveAnimationSheet.getWidth() / FRAME_COLS,
                moveAnimationSheet.getHeight() / FRAME_ROWS); // 4 col, 1 row
        TextureRegion[] animationFrames = new TextureRegion[FRAME_COLS];
        for (int i = 0; i < FRAME_COLS; i++) {
            animationFrames[i] = tmp[0][i];
        }
        animation = new Animation<TextureRegion>(GameInfo.ANIMATION_SPEED, animationFrames);
        this.health = gameObjectState.getFloatFromHashMap("health", 100);
        this.lives = gameObjectState.getIntFromHashMap("lives", 3);
        this.completelyDead = false;
    }

    @Override
    public void update(float deltaTime, float gravity) {
        if (health <= 0) {
            this.completelyDead = true;
        }

    }

    public boolean isCompletelyDead() {
        return completelyDead;
    }


    public void damageBoss(float amountToDamage) {
        if (this.health > 0) {
            this.health -= amountToDamage;
        }
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
