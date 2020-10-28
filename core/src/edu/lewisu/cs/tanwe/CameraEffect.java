package edu.lewisu.cs.tanwe;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

abstract class CameraEffect {
    protected OrthographicCamera cam;
    protected int duration, progress;
    protected ShapeRenderer renderer;
    protected SpriteBatch batch;
 
    public CameraEffect(OrthographicCamera cam, int duration, SpriteBatch batch,
        ShapeRenderer renderer) {
            this.cam = cam;
            this.duration = duration;
            this.batch = batch;
            this.renderer = renderer;
            progress = duration;
    }

    public void updateCamera() {
        cam.update();
        if (renderer != null) {
            renderer.setProjectionMatrix(cam.combined);
        }
        if (batch != null) {
            batch.setProjectionMatrix(cam.combined);
        }
    }

    public boolean isActive() {
        return (progress < duration);
    }
    public void start() {
        progress = 0;
    }
    public abstract void play();
}