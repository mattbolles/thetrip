package com.secondgame;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.ApplicationAdapter;

public class TiledMap extends Map {

    com.badlogic.gdx.maps.tiled.TiledMap tiledMap;
    OrthogonalTiledMapRenderer tiledMapRenderer;

    //init
    public TiledMap() {
        tiledMap = new TmxMapLoader().load("testmap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }


    @Override
    public void render(OrthographicCamera camera) {
        //tell what camera to use
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void dispose() {
        tiledMap.dispose();
    }

    @Override
    public TileType getTileTypeByCoordinate(int layer, int col, int row) {
        TiledMapTileLayer currentLayer = (TiledMapTileLayer) tiledMap.getLayers().get(layer);
        Cell currentCell = currentLayer.getCell(col, row);

        if (currentCell != null) {
            TiledMapTile currentTile = currentCell.getTile();

            if (currentTile != null) {
                // return current tile type
                return TileType.getTileType(currentTile.getId());
            }
        }
        return null;
    }

    @Override
    public int getWidth() {
        // return width of current tile
        TiledMapTileLayer currentLayer = ((TiledMapTileLayer) tiledMap.getLayers().get(0));
        return currentLayer.getWidth();
    }

    @Override
    public int getHeight() {
        // return height of current tile
        TiledMapTileLayer currentLayer = ((TiledMapTileLayer) tiledMap.getLayers().get(0));
        return currentLayer.getHeight();
    }

    @Override
    public int getLayers() {
        // return amount of layers
        return tiledMap.getLayers().getCount();
    }
}
