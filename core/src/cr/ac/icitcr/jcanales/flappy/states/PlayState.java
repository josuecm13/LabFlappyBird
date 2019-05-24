package cr.ac.icitcr.jcanales.flappy.states;

        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.audio.Music;
        import com.badlogic.gdx.audio.Sound;
        import com.badlogic.gdx.graphics.Texture;
        import com.badlogic.gdx.graphics.g2d.BitmapFont;
        import com.badlogic.gdx.graphics.g2d.Sprite;
        import com.badlogic.gdx.graphics.g2d.SpriteBatch;
        import com.badlogic.gdx.utils.Array;
        import cr.ac.icitcr.jcanales.flappy.sprites.Bird;
        import cr.ac.icitcr.jcanales.flappy.sprites.Tube;

public class PlayState extends State{

    private static int TUBE_SPACING = 900;
    private static final int TUBE_COUNT = 4;
    public static final int EASY = -1;
    public static final int MEDIUM = -2;
    public static final int HARD = -3;
    private int score;
    private int currentMode;
    BitmapFont font;
    private Sound point, gameOver;
    Music bgsound;

    private Bird bird;
    private Texture bg;
    private boolean started;
    private float velocity;

    private Array<Tube> tubes;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        bg = new Texture("bg.png");
        score = 0;
        currentMode = -1;
        velocity = 7;
        bird = new Bird((float) Gdx.graphics.getHeight()/2, (float) Gdx.graphics.getHeight()/2);
        tubes = new Array<Tube>();
        font = new BitmapFont();
        font.getData().setScale(5);
        point = Gdx.audio.newSound(Gdx.files.internal("score-sound.mp3"));
        gameOver = Gdx.audio.newSound(Gdx.files.internal("gameover.mp3"));
        bgsound = Gdx.audio.newMusic(Gdx.files.internal("easy.mp3"));
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        for(int i = 1; i <= TUBE_COUNT; i++){
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }

        started = false;
    }
    public PlayState(GameStateManager gsm, int mode) {
        this(gsm);
        currentMode = mode;
        tubes = new Array<Tube>();
        for(int i = 1; i <= TUBE_COUNT; i++){
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH),currentMode));
        }
        switch (mode){
            case EASY:
                bgsound = Gdx.audio.newMusic(Gdx.files.internal("easy.mp3"));
                TUBE_SPACING = 1200;
                velocity = 7;
                break;
            case MEDIUM:
                bgsound = Gdx.audio.newMusic(Gdx.files.internal("medium.mp3"));
                bg = new Texture("bgmed.jpg");
                TUBE_SPACING = 1200;
                velocity = 9;
                break;
            default:
                bgsound = Gdx.audio.newMusic(Gdx.files.internal("hard.mp3"));
                bg = new Texture("bghard.jpg");
                TUBE_SPACING = 1600;
                velocity = 12;
                break;
        }
    }


    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            if(!started){
                started = true;
                bgsound.setLooping(true);
                bgsound.setVolume(0.5f);
                bgsound.play();
            }
            else
                bird.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        if(!started){
            bird.flap(dt);
            return;
        }
        bird.update(dt);

        for(Tube tube: tubes){
            if(0 > tube.getPosTopTube().x + tube.getTopTube().getWidth()){
                tube.reposition( tube.getPosTopTube().x + TUBE_COUNT * (TUBE_SPACING + Tube.TUBE_WIDTH));
            }
            else{
                tube.update(velocity);
            }


            if(tube.collides(bird.getBounds()) || bird.getPosition().y <= 0){
                gameOver.play(0.2f);
                bgsound.dispose();
                gsm.set(new MenuState(gsm,true,currentMode));
            }else{
                if(tube.getPosTopTube().x <= bird.getPosition().x && !tube.passed){
                    score++;
                    tube.passed =true;
                    point.play(0.2f);
                    Gdx.app.debug("Game", String.format("Score: %d",score));
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        //sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, 0, 0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        bird.getPosition().set((Gdx.graphics.getWidth()- bird.getBounds().width)/2,bird.getPosition().y,0);


        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);
        for(Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }
        font.draw(sb,String.format("%d",score),(Gdx.graphics.getWidth()- bird.getBounds().width)/2,Gdx.graphics.getHeight()-font.getRegion().getTexture().getHeight()-50);
        sb.end();
    }



}
