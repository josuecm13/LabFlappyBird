package cr.ac.icitcr.jcanales.flappy.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import cr.ac.icitcr.jcanales.flappy.states.PlayState;

public class Tube{
    public static final int TUBE_WIDTH = 52;

    private static final int FLUCTUATION = 1000;
    private static int TUBE_GAP = 400;
    private static final int LOWEST_OPENING = 120;
    public boolean passed;
    private Texture topTube, bottomTube;
    private Vector2 posTopTube, posBotTube;
    private Rectangle boundsTop, boundsBot;
    private Random rand;


    public Tube(float x){
        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        rand = new Random();
        passed = false;


        posTopTube = new Vector2(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBotTube = new Vector2(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());

        boundsTop = new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight());
        boundsBot = new Rectangle(posBotTube.x, posBotTube.y, bottomTube.getWidth(), bottomTube.getHeight());


    }

    public Tube(float x, int mode){
        this(x);
        switch (mode){
            case PlayState.EASY:
                TUBE_GAP = 700;
                break;
            case PlayState.MEDIUM:
                TUBE_GAP = 400;
                break;
            default:
                TUBE_GAP = 350;
        }
    }


    public Texture getBottomTube() {
        return bottomTube;
    }

    public Texture getTopTube() {
        return topTube;
    }

    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    public Vector2 getPosBotTube() {
        return posBotTube;
    }

    public Rectangle getBoundsTop() {
        return boundsTop;
    }

    public Rectangle getBoundsBot() {
        return boundsBot;
    }

    public void reposition(float x){
        passed = false;
        posTopTube.set(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBotTube.set(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());
        boundsTop.setPosition(posTopTube.x, posTopTube.y);
        boundsBot.setPosition(posBotTube.x, posBotTube.y);
    }

    public void update(float i){
        posTopTube.add(-i,0);
        posBotTube.add(-i,0);
        boundsTop.setPosition(posTopTube.x, posTopTube.y);
        boundsBot.setPosition(posBotTube.x, posBotTube.y);
    }

    public boolean collides(Rectangle player){
        boolean result = player.overlaps(boundsTop) || player.overlaps(boundsBot);
        return result;
    }

    public void dispose(){
        topTube.dispose();
        bottomTube.dispose();
    }
}
