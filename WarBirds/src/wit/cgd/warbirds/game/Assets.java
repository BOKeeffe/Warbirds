package wit.cgd.warbirds.game;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import wit.cgd.warbirds.game.Assets.AssetLevelDecoration;
import wit.cgd.warbirds.game.util.Constants;

public class Assets implements Disposable, AssetErrorListener {

	public static final String	TAG			= Assets.class.getName();
	public static final Assets	instance	= new Assets();
	private AssetManager		assetManager;

	public AssetFonts			fonts;
	public AssetPlayer			player;
	public Asset				bullet, doubleBullet;
	public AssetEnemy		    greenEnemy, whiteEnemy,yellowEnemy;
	public Asset				flame;
	public AssetLevelDecoration levelDecoration;
	
	
	
	public AssetSounds			sounds;
	public AssetMusic			music;
	
	private Assets() {}

	public void init(AssetManager assetManager) {

		this.assetManager = assetManager;
		assetManager.setErrorListener(this);

		// load texture for game sprites
		assetManager.load(Constants.TEXTURE_ATLAS_GAME, TextureAtlas.class);

		// TODO load sounds
		 assetManager.load("sounds/warning.wav", Sound.class);
		 assetManager.load("sounds/enemy_shoot.wav", Sound.class);
		 assetManager.load("sounds/player_bullet_01.wav", Sound.class);
		 assetManager.load("sounds/player_hit.wav", Sound.class);
		 assetManager.load("sounds/explosion.wav", Sound.class);
		 assetManager.load("sounds/explosion_large.wav", Sound.class);
		 assetManager.load("sounds/click_01.wav", Sound.class);
		 assetManager.load("sounds/click_02.wav", Sound.class);
		 
		// load music
		assetManager.load("music/starved.mp3", Music.class);
		  
		assetManager.finishLoading();
		
		
		Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
		for (String a : assetManager.getAssetNames())
			Gdx.app.debug(TAG, "asset: " + a);

		// create atlas for game sprites
		TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_GAME);
		for (Texture t : atlas.getTextures())
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		

		// TODO create game resource objects
		fonts = new AssetFonts();
		player = new AssetPlayer(atlas);
		bullet = new Asset(atlas, "bullet");
		doubleBullet  = new Asset(atlas, "bullet_double");
		greenEnemy = new AssetEnemy(atlas);
		whiteEnemy = new AssetEnemy(atlas);
		yellowEnemy = new AssetEnemy(atlas);
		flame = new Asset(atlas, "flame");
		levelDecoration = new AssetLevelDecoration(atlas);
		
		
		
		// TODO create sound and music resource objects
		sounds = new AssetSounds(assetManager);
	    music = new AssetMusic(assetManager);

	}

	@Override
	public void dispose() {
		assetManager.dispose();
	}
	
	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset '" + asset + "'", (Exception) throwable);
	}

	public class Asset {
		public final AtlasRegion	region;

		public Asset(TextureAtlas atlas, String imageName) {
			region = atlas.findRegion(imageName);
			Gdx.app.log(TAG, "Loaded asset '" + imageName + "'");
		}
	}
	
	
	/**
	 * font Assets
	 * 
	 *
	*/
	public class AssetFonts {
		public final BitmapFont	defaultSmall;
		public final BitmapFont	defaultNormal;
		public final BitmapFont	defaultBig;

		public AssetFonts() {
			// create three fonts using Libgdx's built-in 15px bitmap font
			defaultSmall = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true);
			defaultNormal = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true);
			defaultBig = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true);
			// set font sizes
			defaultSmall.setScale(0.75f);
			defaultNormal.setScale(1.0f);
			defaultBig.setScale(4.0f);
			// enable linear texture filtering for smooth fonts
			defaultSmall.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			defaultNormal.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			defaultBig.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
	}
	
	/**
	 * level decoretion
	 */
	public class AssetLevelDecoration{
		
		public final AtlasRegion water;
		public final AtlasRegion islandBig;
		public final AtlasRegion islandSmall;
		public final AtlasRegion islandTiny;
		
		public AssetLevelDecoration (TextureAtlas atlas) {
			water = atlas.findRegion("water");
			islandBig = atlas.findRegion("island_big");
			islandSmall = atlas.findRegion("island_small");
			islandTiny = atlas.findRegion("island_tiny");
		}
	}
	
	/**
	 * 
	 * player Asset
	 *
	 */
	public class AssetPlayer {
		public final AtlasRegion	region;
		public final Animation		animationNormal;
		public final Animation		animationExplosionBig;

		public AssetPlayer(TextureAtlas atlas) {
			region = atlas.findRegion("player");

			Array<AtlasRegion> regions = atlas.findRegions("player");
			animationNormal = new Animation(1.0f / 15.0f, regions, Animation.LOOP);
			regions = atlas.findRegions("explosion_big");
			animationExplosionBig = new Animation(1.0f / 15.0f, regions, Animation.LOOP);
		}
	}
	
	
	/**
	 * 
	 * bullet Assets
	 *
	 */
	public class AssetBullet{
		public final AtlasRegion bullet;
		public final AtlasRegion enemyBullet;
		public final AtlasRegion doubleBullet;
		
		public AssetBullet (TextureAtlas atlas) {
			bullet = atlas.findRegion("bullet");
			enemyBullet = atlas.findRegion("enemy_bullet");
			doubleBullet = atlas.findRegion("bullet_double");
		}
	}
	
	/**
	 * 
	 * flame Assets
	 *
	 */
	public class AssetFlame{
		public final AtlasRegion flame;
		
		public AssetFlame (TextureAtlas atlas){
			flame = atlas.findRegion("flame_1");
		}
	}
	
	
	/**
	 * 
	 * enemy Assets
	 *
	 */
	public class AssetEnemy{
		public final AtlasRegion greenEnemy;
		public final AtlasRegion whiteEnemy;
		public final AtlasRegion yellowEnemy;
		
		public AssetEnemy (TextureAtlas atlas) {
			greenEnemy = atlas.findRegion("enemy_plane_green_01");
			whiteEnemy = atlas.findRegion("enemy_plane_white_01");
			yellowEnemy = atlas.findRegion("enemy_plane_yellow_01");
		}
		
	}

	
	/**
	 *	Done***  Sound asset manager
	 */
	public class AssetSounds {
		
		// TODO list reference to sound assets
		public final Sound	warning;
		public final Sound	enemyShoot;
		public final Sound	playerBullet;
		public final Sound	playerHit;
		public final Sound	explosion;
		public final Sound	bigExplosion;
		public final Sound	click01;
		public final Sound	click02;

		public AssetSounds(AssetManager am) {
			enemyShoot = am.get("sounds/enemy_shoot.wav", Sound.class);
			warning = am.get("sounds/warning.wav", Sound.class);
			playerBullet = am.get("sounds/player_bullet_01.wav", Sound.class);
			playerHit = am.get("sounds/player_Hit.wav", Sound.class);
			explosion = am.get("sounds/explosion.wav", Sound.class);
			bigExplosion = am.get("sounds/explosion_large.wav", Sound.class);
			click01 = am.get("sounds/click_01.wav", Sound.class);
			click02 = am.get("sounds/click_02.wav", Sound.class);
			
		}
	}

	public class AssetMusic {
		// TODO list reference to music assets
		 public final Music	song01;

		public AssetMusic(AssetManager am) {
			song01 = am.get("music/starved", Music.class);
		}
	}

}
