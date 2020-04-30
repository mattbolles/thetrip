package com.secondgame;

import com.badlogic.gdx.graphics.OrthographicCamera;

public abstract class Map {

    public abstract void render(OrthographicCamera camera);
    public abstract void update(float delta);
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


    public abstract int getWidth();
    public abstract int getHeight();
    public abstract int getLayers();

}
