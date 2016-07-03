package wit.cgd.warbirds.game.screens;

import wit.cgd.warbirds.game.Assets;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;

public abstract class AbstractGameScreen implements Screen {


	protected Game	game;

	public AbstractGameScreen(Game game) {
		this.game = game;
	}

	public abstract void render(float deltaTime);

	public abstract void resize(int width, int height);

	public abstract void show();

	public abstract void hide();

	public abstract void pause();

	public void resume() {
		Assets.instance.init(new AssetManager());
	}

	public void dispose() {
		Assets.instance.dispose();
	}

}