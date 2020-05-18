package com.secondgame;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

// generic class to make tiled game map

public class TiledGameMap extends GameMap {

    com.badlogic.gdx.maps.tiled.TiledMap tiledMap;
    OrthogonalTiledMapRenderer tiledMapRenderer;

    //init
    public TiledGameMap(int mapToLoad) {
        super(mapToLoad);
        tiledMap = new TmxMapLoader().load("maps/level" + mapToLoad + "/level" + mapToLoad + ".tmx");
        System.out.println("tiledgamemap loading level: " + mapToLoad);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }


    @Override
    public void render(OrthographicCamera camera, SpriteBatch spriteBatch) {
        //tell what camera to use
        tiledMapRenderer.setView(camera);
        // render gameMap
        tiledMapRenderer.render();
        // render according to camera
        spriteBatch.setProjectionMatrix(camera.combined);
        // render game objects
        spriteBatch.begin();
        super.render(camera, spriteBatch);
        spriteBatch.end();

    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
    }

    @Override
    public TileType getTileTypeByCoordinate(int layer, int col, int row) {
        System.out.println("from TiledGameMap, getTileTypeByCoordinate at layer " + layer + " col " + col + " row " + row);
        TiledMapTileLayer currentLayer = (TiledMapTileLayer) tiledMap.getLayers().get(layer);
        System.out.println("currentLayer: " + currentLayer);
        Cell currentCell = currentLayer.getCell(col, row);
        System.out.println("currentCell: " + currentCell);

        if (currentCell != null) {
            TiledMapTile currentTile = currentCell.getTile();
            System.out.println("currentTile: " + currentTile);

            if (currentTile != null) {
                System.out.println("going to return tile type");
                // return current tile type
                System.out.println("tile id: " + currentTile.getId());
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

    public int getWidthFromLayer(int layer) {
        TiledMapTileLayer currentLayer = ((TiledMapTileLayer) tiledMap.getLayers().get(layer));
        return currentLayer.getWidth();
    }

    public int getHeightFromLayer(int layer) {
        TiledMapTileLayer currentLayer = ((TiledMapTileLayer) tiledMap.getLayers().get(layer));
        return currentLayer.getHeight();
    }

    @Override
    public int getLayers() {
        // return amount of layers
        return tiledMap.getLayers().getCount();
    }
}
