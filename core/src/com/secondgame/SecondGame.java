package com.secondgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class SecondGame extends ApplicationAdapter {
	SpriteBatch spriteBatch;
	OrthographicCamera camera;
	GameMap gameMap;
	float deltaX;
	float deltaY;

	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();
		gameMap = new TiledGameMap();
	}

	@Override
	public void render () {

		Gdx.gl.glClearColor(0, 256, 100, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// if clicked
		if (Gdx.input.isTouched()) {
			// drag gameMap
			camera.translate(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
			camera.update();
		}

		// if clicked - after click finished
		if (Gdx.input.justTouched()) {
			Vector3 clickPositionOnScreen = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			Vector3 clickPositionOnMap = camera.unproject(clickPositionOnScreen);
			TileType currentTileType = gameMap.getTileTypeByLocation(1, clickPositionOnMap.x, clickPositionOnMap.y);
			System.out.println(currentTileType == null);
			if (currentTileType != null) {
				System.out.println("Click on tile id: " + currentTileType.getId() + " name: " + currentTileType.getName() + " is collidable: " + currentTileType.isCollidable() + " damage: " + currentTileType.getDamage());


			}
		}
		camera.update();
		// deltatime is time between last update and now
		gameMap.update(Gdx.graphics.getDeltaTime());
		gameMap.render(camera, spriteBatch);
	}
	
	@Override
	public void dispose () {
		spriteBatch.dispose();
		gameMap.dispose();
	}
}
