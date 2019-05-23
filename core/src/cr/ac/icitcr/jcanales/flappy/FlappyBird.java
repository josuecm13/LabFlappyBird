package cr.ac.icitcr.jcanales.flappy;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;

import java.util.Random;

import cr.ac.icitcr.jcanales.flappy.states.GameStateManager;
import cr.ac.icitcr.jcanales.flappy.states.MenuState;
import sun.net.www.HeaderParser;


public class FlappyBird extends ApplicationAdapter {
	/*
	BitmapFont font;
	SpriteBatch batch;
	Texture img, bird, bird1, topTube, bottomTube;
	int witdh, height, birdX, birdY, pipeX, pipeY;
	float gravity;
	float velocity;
	boolean flappy;
	int sec, distance, gap, someY;
	int score;
	
	@Override
	public void create () {

		flappy = true;
		batch = new SpriteBatch();
		gravity = -0.7f;
		velocity = 0;
		gap = 100;
		img = new Texture("bg.png");
		bird = new Texture("bird.png");
		bird1 = new Texture("bird2.png");
		topTube = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");
		sec = 0;
		someY = 1700;
		score = 8;
		witdh = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		//Coordinates
		birdX = (witdh - bird.getWidth())/2;
		birdY = (height - bird.getHeight())/2;

		someY = new Random().nextInt(1200)+500;


		pipeX = (witdh - topTube.getWidth())/2;
		pipeY = height - someY;

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(10);


	}


	@Override
	public void render () {
		batch.begin();
		batch.draw(img, 0, 0, witdh, height);

		batch.draw(topTube,pipeX,pipeY,topTube.getWidth(), topTube.getHeight());
		batch.draw(bottomTube,pipeX,pipeY - (bottomTube.getHeight() + gap), bottomTube.getWidth(), 100 );



		font.draw(batch,String.format("score: %d", score), 10, height-150);


		//Manejo de saltos
		updateFallVelocity();

		//Cambio de imagenes del pájaro
		rotateImage();

		if(sec == 10)
			flappy = !flappy;
		sec = (sec + 1)%11;


		batch.end();
	}

	private void rotateImage(){
		Texture currentBird = bird;
		if(!flappy){
			currentBird = bird1;
		}
		batch.draw(currentBird,birdX,birdY);
	}

	private void updateFallVelocity(){
		if (Gdx.input.justTouched()){
			velocity += 15;
		}
		velocity += gravity;
		birdY += velocity;
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
	*/

	public static int WIDTH = 1080;
	public static int HEIGHT = 1794;
	public static final float SCALE = 0.5f;
	public static final String TITLE = "FlappyBird";

	private SpriteBatch spriteBatch;
	private GameStateManager gameStateManager;

	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
		gameStateManager = new GameStateManager();
		gameStateManager.push(new MenuState(gameStateManager));
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.debug("W and H", String.format("width: %d\nHeight:%d",WIDTH,HEIGHT));
		Gdx.gl.glClearColor(1, 0, 0, 1);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gameStateManager.update(Gdx.graphics.getDeltaTime());
		gameStateManager.render(spriteBatch);
	}


}
