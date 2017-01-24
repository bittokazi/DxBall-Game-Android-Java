package us.bitto.kazi.dxballgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class bullet {
	float x;
	float y;
	float dx;
	boolean enable;
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public float getDx() {
		return dx;
	}
	public void setDx(float dx) {
		this.dx = dx;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStrokeWidth(9);
		canvas.drawPoint(getX(), getY(), paint);
	}
	public void update() {
		this.setY(this.getY()+this.getDx());
	}
}
