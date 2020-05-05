package com.secondgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class SecondGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	OrthographicCamera camera;
	Map gameMap;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();
		gameMap = new TiledMap();
	}

	@Override
	public void render () {

		Gdx.gl.glClearColor(0, 256, 100, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		/*batch.begin();
		batch.draw(img, 0, 0);
		batch.end();*/

		// if clicked
		if (Gdx.input.isTouched()) {
			// drag map
			camera.translate(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
			camera.update();
		}

		// if clicked - after click finished
		if (Gdx.input.justTouched()) {

			Vector3 clickPositionOnMap = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			System.out.println("clicked at " + clickPositionOnMap.x + " " + clickPositionOnMap.y);
			// get current mouse click position
			//clickPositionOnScreen = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			//clickPositionOnMap = camera.unproject(clickPositionOnScreen);
			TileType currentTileType = gameMap.getTileTypeByLocation(1, clickPositionOnMap.x, clickPositionOnMap.y);
			System.out.println(currentTileType == null);
			if (currentTileType != null) {
				System.out.println("Click on tile id: " + currentTileType.getId() + " name: " + currentTileType.getName() + " is collidable: " + currentTileType.isCollidable() + " damage: " + currentTileType.getDamage());


			}
		}
		gameMap.render(camera);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
