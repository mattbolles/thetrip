package com.secondgame.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.secondgame.gameobject.GameObject;
import com.secondgame.gameobject.GameObjectLoader;

import java.util.ArrayList;
import java.util.Objects;

public abstract class GameMap {

    public ArrayList<GameObject> gameObjects;

    public GameMap(int mapToLoad) {
        gameObjects = new ArrayList<GameObject>();
        System.out.println("from GameMap constructor, attempting to load: /maps/level" + mapToLoad + "/states");
        gameObjects.addAll(Objects.requireNonNull(GameObjectLoader.loadGameObjectsIntoMap("maps/level" + mapToLoad + "/states", this)));

    }

    public void render(OrthographicCamera camera, SpriteBatch spriteBatch) {
        for (GameObject currentGameObject : gameObjects) {
            currentGameObject.render(spriteBatch);
        }
    }
    public void update(float delta) {
        for (GameObject currentGameObject: gameObjects) {
            // -9.8 is earth gravity - -9.8f will treat as float also
            currentGameObject.update(delta, (float) -9.8);
        }
    }
    public abstract void dispose();

    /**
     * Returns tile at specified location and layer - aligns to tiles
     * @param layer
     * @param x
     * @param y
     * @return
     */
    public TileType getTileTypeByLocation(int layer, float x, float y) {
        return this.getTileTypeByCoordinate(layer, (int) x / TileType.TILE_SIZE, (int) y / TileType.TILE_SIZE);
    }

    /**
     * Returns tile at specified coordinate and layer
     * @param layer
     * @param col
     * @param row
     * @return
     */
    public abstract TileType getTileTypeByCoordinate(int layer, int col, int row);

    public int checkIfCollidesWithTiles(float x, float y, int width, int height) {
        // return - 0 if not collidable, 1 if collidable, 2 if portal
        // check if object collides with with any of the tiles it overlaps - return true if collision happens
        // check borders
        if (checkBorders(x, y, width, height)) {
            return 1;
        }

        // start in bottom left tile
        int firstTileX = (int) (y / TileType.TILE_SIZE); // bottom left tile
        //might need to change to double if it messes up
        int lastTileX = (int) Math.ceil((y + height) / TileType.TILE_SIZE); // top right tile - ceiling makes sure
        // whole tile gets included
        int firstTileY = (int) (x / TileType.TILE_SIZE); // bottom left tile
        // may need to change to double later
        int lastTileY = (int) Math.ceil((x + width) / TileType.TILE_SIZE); // top right tile
        // go through each row
        for (int row = firstTileX; row < lastTileX; row++) {
            // go through each column
            for (int column = firstTileY; column < lastTileY; column++) {
                // go through each layer
                for (int layer = 0; layer < getLayers(); layer++) {
                    //System.out.println("from GameMap, checkIfCollidesWithTiles, layer " + layer + " row " + row + " col " +
                    // column);
                    TileType currentTileType = getTileTypeByCoordinate(layer, column, row);
                    if (currentTileType != null && currentTileType.isPortal()) {
                        //System.out.println("from GameMap: checkIfTilesCollideWithMap portal branch reached");
                        return 2;
                    }
                    //System.out.println("from GameMap, checkIfCollidesWithTiles, currentTileType: " + currentTileType);
                    if (currentTileType != null && currentTileType.isCollidable()) {
                        return 1;
                    }
                }
            }
        }

        return 0;
    }

    public boolean checkBorders(float x, float y, int width, int height) {
        if (x < 0) {
            return true;
        }

        if (y < 0) {
            return true;
        }

        if ((x + width) > getWidthFromPixel()) {
            return true;
        }

        if ((y + height) > getHeightFromPixel()) {
            return true;
        }

        else {
            return false;
        }
    }

    public abstract int getWidth();
    public abstract int getHeight();
    public abstract int getLayers();

    public int getWidthFromPixel() {
        return this.getWidth() * TileType.TILE_SIZE;
    }

    public int getHeightFromPixel() {
        return this.getHeight() * TileType.TILE_SIZE;
    }

    public abstract int getMapWidth();
}
