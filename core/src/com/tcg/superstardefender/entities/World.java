package com.tcg.superstardefender.entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.tcg.superstardefender.Game;
import com.tcg.superstardefender.MyCamera;

public class World {
	 
	private Array<Rectangle> bounds;
	
	private TiledMap tileMap;
	private OrthogonalTiledMapRenderer tmr;
	
	private float width, height, tileWidth, tileHeight;
	
	private float tileSize;
	
	private int numLevels = 4;

	public World() throws LevelDoesNotExist {
		bounds = new Array<Rectangle>();
		if(Game.level < numLevels) {
			createTiles();
		} else {
			throw new LevelDoesNotExist("There is no level " + Game.level);
		}
	}
	
	private void createTiles() {
		bounds.clear();
		
		tileMap = new TmxMapLoader().load("maps/map" + Game.level + ".tmx");
		tmr = new OrthogonalTiledMapRenderer(tileMap);
		tileSize = tileMap.getProperties().get("tilewidth", Integer.class);
		
		TiledMapTileLayer ground;
		
		ground = (TiledMapTileLayer) tileMap.getLayers().get("Tile Layer 1");
		createLayer(ground, bounds);
		
		width = ground.getWidth() * tileSize;
		height = ground.getHeight() * tileSize;
		tileWidth = ground.getWidth();
		tileHeight = ground.getHeight();
	}
	
	private void createLayer(TiledMapTileLayer layer, Array<Rectangle> rect) {		
		for(int row = 0; row < layer.getHeight(); row++) {
			for(int col = 0; col < layer.getWidth(); col++) {
				
				Cell cell = layer.getCell(col, row);
				
				if(cell == null) continue;
				if(cell.getTile() == null) continue;
				
				rect.add(new Rectangle(col * tileSize, row * tileSize, tileSize, tileSize));		
			}
		}
	}
	
	public void render(MyCamera cam) {
		tmr.setView(cam);
		tmr.render();
	}
	
	public void dispose() {
		bounds.clear();
	}
	
	public Array<Rectangle> getBounds() {
		return bounds;
	}
 
	public void setBounds(Array<Rectangle> bounds) {
		this.bounds = bounds;
	}
	
	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getTileWidth() {
		return tileWidth;
	}

	public void setTileWidth(float tileWidth) {
		this.tileWidth = tileWidth;
	}

	public float getTileHeight() {
		return tileHeight;
	}

	public void setTileHeight(float tileHeight) {
		this.tileHeight = tileHeight;
	}

}
