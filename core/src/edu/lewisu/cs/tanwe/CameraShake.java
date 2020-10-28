package edu.lewisu.cs.tanwe;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

class CameraShake extends CameraEffect {
    private int intensity;
    private int speed;

    public CameraShake(OrthographicCamera cam, int duration, SpriteBatch batch,
        ShapeRenderer renderer, int intensity, int speed ) {
        super(cam,duration,batch,renderer);
        setIntensity(intensity);
        setSpeed(speed);
    }
    public int getIntensity() {
        return intensity;
    }
    public void setIntensity(int intensity) {
        if (intensity < 0) {
            this.intensity = 0;
        } else {
            this.intensity = intensity;
        }
    }
    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        if (speed < 0) {
            speed = 0;
        } else {
            if (speed > duration) {
                speed = duration / 2;
            } else {
                this.speed = speed;
            }
        }
    }
    public void start() {
        super.start();
        cam.translate(intensity,0);
        updateCamera();
    }
    @Override
    public void play() {
        if (isActive()) {
            if (progress % speed == 0) {
                intensity = -intensity;
                cam.translate(2*intensity,2*intensity);
            }
            progress++;
            if (!isActive()) {
                cam.translate(-intensity,-intensity);
            }
            updateCamera();
        }
    }

}