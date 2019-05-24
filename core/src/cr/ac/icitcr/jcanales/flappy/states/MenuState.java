package cr.ac.icitcr.jcanales.flappy.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


import java.util.Random;

import cr.ac.icitcr.jcanales.flappy.FlappyBird;

public class MenuState extends State{
    Texture background;
    Texture playBtn, easyBtn, hardBtn, gameOverTexture;
    boolean gameOver;
    int width, height, buttonWidth, buttonHeight, buttonSpacing;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("bg.png");
        buttonWidth = 400;
        buttonHeight = 250;
        buttonSpacing = 100+buttonHeight;
        playBtn = new Texture("playbtn.png");
        easyBtn = new Texture("easy.png");
        hardBtn = new Texture("hard.png");
        gameOverTexture = new Texture("logo.png");
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
    }

    public MenuState(GameStateManager gsm, boolean gameOver,int mode){
        this(gsm);
        this.gameOver = gameOver;
        if(gameOver)
            gameOverTexture = new Texture("game_over.png");
        switch (mode){
            case PlayState.EASY:
                background = new Texture("bg.png");
                break;
            case PlayState.MEDIUM:
                background = new Texture("bgmed.jpg");
                break;
            default:
                background = new Texture("bghard.jpg");
                break;
        }
    }

    @Override
    public void handleInput() {

        if(Gdx.input.justTouched()){
            Rectangle dot = new Rectangle(Gdx.input.getX(),Gdx.input.getY(),1,1);
            if(dot.overlaps(new Rectangle((FlappyBird.WIDTH - buttonWidth)/ 2,(FlappyBird.HEIGHT - buttonHeight)/ 2 ,buttonWidth,buttonHeight))){
                gsm.set(new PlayState(gsm, PlayState.EASY));
                background = new Texture("bg.png");
            }else if(dot.overlaps(new Rectangle((FlappyBird.WIDTH / 2) - (buttonWidth / 2),(FlappyBird.HEIGHT - buttonHeight)/ 2  + buttonSpacing*1,buttonWidth,buttonHeight))){
                background = new Texture("bgmed.jpg");
                gsm.set(new PlayState(gsm, PlayState.MEDIUM));
            }else if(dot.overlaps(new Rectangle((FlappyBird.WIDTH / 2) - (buttonWidth / 2),(FlappyBird.HEIGHT - buttonHeight)/ 2  + buttonSpacing*2,buttonWidth,buttonHeight))){
                background = new Texture("bghard.jpg");
                gsm.set(new PlayState(gsm, PlayState.HARD));
            }

        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sb.draw(gameOverTexture,(FlappyBird.WIDTH / 2) - (gameOverTexture.getWidth() / 2), Gdx.graphics.getHeight()-500);

        sb.draw(playBtn, (FlappyBird.WIDTH / 2) - (buttonWidth / 2), (FlappyBird.HEIGHT - buttonHeight)/ 2  - buttonSpacing*1,buttonWidth,buttonHeight);
        sb.draw(easyBtn, (FlappyBird.WIDTH / 2) - (buttonWidth / 2), (FlappyBird.HEIGHT - buttonHeight)/ 2  - buttonSpacing*0,buttonWidth,buttonHeight);
        sb.draw(hardBtn, (FlappyBird.WIDTH / 2) - (buttonWidth / 2), (FlappyBird.HEIGHT - buttonHeight)/ 2  - buttonSpacing*2,buttonWidth,buttonHeight);
        sb.end();
    }
}
