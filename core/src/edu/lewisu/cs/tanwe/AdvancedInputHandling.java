package edu.lewisu.cs.tanwe;

import java.util.Scanner;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AdvancedInputHandling extends ApplicationAdapter {
	SpriteBatch batch;
	Texture tex;
	TextureRegion img;
	OrthographicCamera cam;
	int WIDTH;
	int HEIGHT;
	int imgX, imgY;
	int imgWidth, imgHeight;
	int imgOrgX, imgOrgY;
	int imgAngle;
	CameraShake shaker;
	int camSpeed;

	@Override
	public void create () {
		Scanner sc = new Scanner(System.in);
		batch = new SpriteBatch();
		tex = new Texture("badlogic.jpg");
		imgWidth = tex.getWidth();
		imgHeight = tex.getHeight();
		imgAngle = 0;
		img = new TextureRegion(tex);
		imgX = 0;
		imgY = 0;
		imgOrgX = imgX/2;
		imgOrgY = imgY/2;
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		cam = new OrthographicCamera(WIDTH,HEIGHT);
		InputHandler handler = new InputHandler(batch,cam);
		Gdx.input.setInputProcessor(handler);
		cam.translate(WIDTH/2,HEIGHT/2);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		System.out.println("Enter a camera shake speed from 0-100: ");
		camSpeed = sc.nextInt();
		shaker = new CameraShake(cam, 100, batch, null, 10, camSpeed);
	}

	public void handleInput() {
        boolean shiftHeld = false;
        boolean cameraNeedsUpdating = false;
        if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT)) {
            shiftHeld = true;
        }
        
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            if (shiftHeld) {
                //rotate
                cam.rotate(1);
            } else {
                cam.translate(-1,0);
            }
            cameraNeedsUpdating = true;
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            if (shiftHeld) {
                //rotate
                cam.rotate(-1);
            } else {
                cam.translate(1,0);
            }
            cameraNeedsUpdating = true;
        }
        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            Gdx.app.exit();   // quit the game
        }
        if (Gdx.input.isKeyPressed(Keys.W)) {
            imgY += 5;
        }
        if (Gdx.input.isKeyPressed(Keys.S)) {
            imgY -= 5;
        }
        if (Gdx.input.isKeyPressed(Keys.A)) {
            if (shiftHeld) {
                imgAngle += 2;
            } else {
                imgX -= 5;
            }
        }
        if (Gdx.input.isKeyPressed(Keys.D)) {
            if (shiftHeld) {
                imgAngle -= 2;
            } else {
                imgX += 5;
            }
        }
        if (cameraNeedsUpdating) {
            updateCamera();
		}
	}
	
    public void updateCamera() {
        cam.update();
        batch.setProjectionMatrix(cam.combined);
    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			shaker.start();
		}
		shaker.play();

		handleInput();
		batch.begin();
		batch.draw(img, imgX, imgY, imgOrgX, imgOrgY, imgWidth, imgHeight, 1f, 1f, imgAngle);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
