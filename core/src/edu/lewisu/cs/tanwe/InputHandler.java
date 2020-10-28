package edu.lewisu.cs.tanwe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

class InputHandler extends InputAdapter {
	private boolean shiftHeld = false;
	private SpriteBatch batch;
	private OrthographicCamera cam;
	private Vector3 startCam, startMouse;
	private int WIDTH = Gdx.graphics.getWidth();
	private int HEIGHT = Gdx.graphics.getHeight();

	public InputHandler(SpriteBatch batch, OrthographicCamera cam) {
		this.batch= batch;
		this.cam = cam;
	}

	@Override
	public boolean keyDown(int keyCode) {
		if (keyCode == Keys.SHIFT_LEFT || keyCode == Keys.SHIFT_RIGHT) {
			shiftHeld = true;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keyCode) {
		if (keyCode == Keys.SHIFT_LEFT || keyCode == Keys.SHIFT_RIGHT) {
			shiftHeld = false;
		}
		return true;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		startCam = new Vector3(cam.position.x, cam.position.y,0);
		startMouse = new Vector3(screenX, screenY, 0);
		return true;
	}
	public void updateCamera() {
		cam.update();
		batch.setProjectionMatrix(cam.combined);
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		float diffX = screenX - startMouse.x;
		float diffY = screenY - startMouse.y;
		float theta = (float) Math.atan2(diffY, diffX);
		cam.position.x = startCam.x + diffX;
		cam.position.y = startCam.y - diffY;

		// rotate with shift + mouse
		if (shiftHeld) {
			cam.rotate(theta);
		} else if (screenX < 0 || screenX > WIDTH || screenY < 0 || screenY > HEIGHT) {
			// stop when the mouse moves off the window
			return false;
		}

		updateCamera();
		return true;
	}

}