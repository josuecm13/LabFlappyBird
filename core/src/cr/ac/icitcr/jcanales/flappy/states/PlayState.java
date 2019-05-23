package cr.ac.icitcr.jcanales.flappy.states;

        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.graphics.Color;
        import com.badlogic.gdx.graphics.Texture;
        import com.badlogic.gdx.graphics.g2d.Batch;
        import com.badlogic.gdx.graphics.g2d.BitmapFont;
        import com.badlogic.gdx.graphics.g2d.SpriteBatch;
        import com.badlogic.gdx.math.Vector2;
        import com.badlogic.gdx.utils.Array;

        import java.util.Random;

        import cr.ac.icitcr.jcanales.flappy.FlappyBird;
        import cr.ac.icitcr.jcanales.flappy.sprites.Bird;
        import cr.ac.icitcr.jcanales.flappy.sprites.Tube;

public class PlayState extends State{
    /*

    BitmapFont font;
    Texture img, bird, bird1, topTube, bottomTube;
    int witdh, height, birdX, birdY, pipeX, pipeY;
    float gravity;
    float velocity;
    boolean flappy;
    int sec, distance, gap, someY;
    int score;


    protected PlayState(GameStateManager gsm) {
        super(gsm);
        flappy = true;
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
    public void handleInput() {
        if(Gdx.input.justTouched()){
            velocity += 15;
        }
        velocity += gravity;
        birdY += velocity;
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(img, 0, 0, witdh, height);

        batch.draw(topTube,pipeX,pipeY,topTube.getWidth(), topTube.getHeight());
        batch.draw(bottomTube,pipeX,pipeY - (bottomTube.getHeight() + gap), bottomTube.getWidth(), 100 );



        font.draw(batch,String.format("score: %d", score), 10, height-150);


        //Manejo de saltos
        //updateFallVelocity();

        //Cambio de imagenes del pájaro
        rotateImage(batch);

        if(sec == 10)
            flappy = !flappy;
        sec = (sec + 1)%11;


        batch.end();
    }

    private void rotateImage(SpriteBatch batch){
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
    */


    /*
            BORRAR DESDE AQUI
     */


    private static final int TUBE_SPACING = 900;
    private static final int TUBE_COUNT = 4;

    private Bird bird;
    private Texture bg;

    private Array<Tube> tubes;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        //cam.setToOrtho(false, FlappyBird.WIDTH / 2, FlappyDemo.HEIGHT / 2);
        bg = new Texture("bg.png");

        bird = new Bird((float) Gdx.graphics.getHeight()/2, (float) Gdx.graphics.getHeight()/2);
        //ground = new Texture("ground.png");
        //groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);
        //groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);

        tubes = new Array<Tube>();

        for(int i = 1; i <= TUBE_COUNT; i++){
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }


    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched())
            bird.jump();
    }

    @Override
    public void update(float dt) {
        handleInput();
        bird.update(dt);
//        cam.position.x = bird.getPosition().x + 80;

        for(Tube tube: tubes){
            if(0 > tube.getPosTopTube().x + tube.getTopTube().getWidth()){
                tube.reposition( tube.getPosTopTube().x + TUBE_COUNT * (TUBE_SPACING + Tube.TUBE_WIDTH));
            }
            else{
                tube.update(7);
            }

            if(tube.collides(bird.getBounds()))
                //gsm.set(new MenuState(gsm));
                Gdx.app.debug("Collides", bird.getBounds().toString());
        }

        if(bird.getPosition().y <= 0)
            gsm.set(new MenuState(gsm));
    }

    @Override
    public void render(SpriteBatch sb) {
        //sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, 0, 0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        sb.draw(bird.getTexture(), (Gdx.graphics.getWidth() - bird.getBounds().width)/2, bird.getPosition().y);
        for(Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }
        sb.end();
    }


}
