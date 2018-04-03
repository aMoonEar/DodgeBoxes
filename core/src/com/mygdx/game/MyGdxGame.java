package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;

public class MyGdxGame extends Game {

	AssetManager manager;

	@Override
	public void create () {

		manager = new AssetManager();
		showLoadingScreen();
	}

	public void showMenuScreen() {
		setScreen(new MenuScreen(this));
	}

	public void showLoadingScreen() {
		setScreen(new LoadingScreen(this));
	}

	public void showPlayScreen() {
		setScreen(new PlayScreen(this));
	}

	@Override
	public void dispose () {
	}


	public AssetManager getManager() {
		return manager;
	}
}
