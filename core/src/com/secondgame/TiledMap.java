package com.secondgame;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

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
        return null;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public int getLayers() {
        return 0;
    }
}
