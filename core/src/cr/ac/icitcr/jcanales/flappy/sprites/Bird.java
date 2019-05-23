package cr.ac.icitcr.jcanales.flappy.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Arrays;

public class Bird {
    private static final int GRAVITY = -33;
    private static final int MOVEMENT = 100;
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;
    private IAnimation birdAnimation;
    private Texture texture;
    //private Sound flap;

    public Bird(float x, float y){
        velocity = new Vector3(0, 0, 0);
        birdAnimation = new PicAnimation(new ArrayList<String>(Arrays.asList("bird.png","bird2.png")),0.4f);
        texture = (Texture) birdAnimation.getFrame();
        position = new Vector3(x-((float) texture.getWidth()/2), y-((float) texture.getHeight()/2), 0);
        bounds = new Rectangle(x-((float) texture.getWidth()/2), y-((float) texture.getHeight()/2), texture.getWidth(), texture.getHeight());
        //flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
    }

    public void update(float dt){
        birdAnimation.update(dt);
        if(position.y > 0)
            velocity.add(0, GRAVITY, 0);
        velocity.scl(dt);
        position.add(0, velocity.y, 0);
        if(position.y < 0)
            position.y = 0;
        if(position.y > Gdx.graphics.getHeight() - texture.getHeight()){
            position.y = Gdx.graphics.getHeight() - texture.getHeight();
            velocity.y = 0;
        }
        velocity.scl(1/dt);
        bounds.setPosition(position.x, position.y);
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return (Texture) birdAnimation.getFrame();
    }

    public void jump(){
        velocity.y = 900;
        //flap.play(0.5f);
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public void dispose(){
        texture.dispose();
        //flap.dispose();
    }

}