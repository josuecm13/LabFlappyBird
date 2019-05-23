package cr.ac.icitcr.jcanales.flappy.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

public class PicAnimation implements IAnimation {


    private List<String> frameNames;
    private HashMap<String, Texture> textureMap;
    int currentIndex;
    int frameCount;
    float maxFrameTime;
    float currentFrameTime;
    String frame;

    public PicAnimation(List<String> frameNames, float cycleTime){
        this.frameNames = frameNames;
        this.currentIndex = 0;
        this.frameCount = frameNames.size();
        this.maxFrameTime = cycleTime / frameCount;
        this.textureMap = new HashMap<String, Texture>();
        update(0.4f);
    }


    @Override
    public void update(float dt) {
        currentFrameTime += dt;
        if(currentFrameTime > maxFrameTime){
            currentIndex++;
            currentFrameTime = 0;
        }
        if(currentIndex >= frameCount)
            currentIndex = 0;
        frame = frameNames.get(currentIndex);
    }

    @Override
    public Texture getFrame() {
        if(!textureMap.containsKey(frame))
            textureMap.put(frame,new Texture(frame));
        return textureMap.get(frame);
    }
}
